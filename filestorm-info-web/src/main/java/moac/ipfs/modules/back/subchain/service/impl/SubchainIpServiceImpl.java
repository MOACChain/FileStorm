package moac.ipfs.modules.back.subchain.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import moac.ipfs.common.utils.MoacUtils;
import moac.ipfs.modules.back.currency.entity.CurrencyEntity;
import moac.ipfs.modules.back.currency.entity.CurrencyRecordEntity;
import moac.ipfs.modules.back.currency.service.CurrencyRecordService;
import moac.ipfs.modules.back.gain.entity.DividendEntity;
import moac.ipfs.modules.back.gain.entity.GainEntity;
import moac.ipfs.modules.back.gain.service.DividendService;
import moac.ipfs.modules.back.gain.service.GainService;
import moac.ipfs.modules.back.sys.service.SysConfigService;
import moac.ipfs.modules.back.user.entity.UserEntity;
import moac.ipfs.modules.back.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import moac.ipfs.modules.back.subchain.dao.SubchainIpDao;
import moac.ipfs.modules.back.subchain.entity.SubchainIpEntity;
import moac.ipfs.modules.back.subchain.service.SubchainIpService;
import org.springframework.transaction.annotation.Transactional;

@Service("subchainIpService")
public class SubchainIpServiceImpl extends ServiceImpl<SubchainIpDao, SubchainIpEntity> implements SubchainIpService {
    @Autowired
    private SubchainIpDao subchainIpDao;
    @Autowired
    private SysConfigService sysConfigService;
    @Autowired
    private MoacUtils moacUtils;
    @Autowired
    private UserService userService;
    @Autowired
    private GainService gainService;
    @Autowired
    private DividendService dividendService;
    @Autowired
    private SubchainIpService subchainIpService;
    @Autowired
    private CurrencyRecordService currencyRecordService;

    @Override
    public List<SubchainIpEntity> queryList(Map<String, Object> map) {
        return subchainIpDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return subchainIpDao.queryTotal(map);
    }

    @Override
    public void deleteBatch(Integer[] ids) {
        subchainIpDao.deleteBatch(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addService(SubchainIpEntity subchainIp) {
        subchainIp.setCreateTime(System.currentTimeMillis());
        this.insert(subchainIp);
    }

    /**
     * 矿工分红
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void minePoolNodeDividend() {
        String subChainAddres = sysConfigService.getValue("subChainAddres");
        UserEntity userEntity = userService.selectOne(new EntityWrapper<UserEntity>().eq("address", subChainAddres));
        String foundationAddress = sysConfigService.getValue("foundationAddress");
        String currencyNumber = sysConfigService.getValue("dividendCurrencyNumber");
        /**
         * 得到总收益(FST)
         */
        BigDecimal total = moacUtils.queryMainChainBalance(subChainAddres);
        /**
         * 5%发到基金会
         */
        BigDecimal foundationAmount = total.multiply(new BigDecimal("0.05"));
        JSONObject json = MoacUtils.subChainTrans(subChainAddres, foundationAddress, userEntity.getPassword(), userEntity.getKeyStore(), foundationAmount.toPlainString());
        /**
         * 产生交易记录
         */
        CurrencyRecordEntity currencyRecordEntity = new CurrencyRecordEntity();
        currencyRecordEntity.setType(2);
        currencyRecordEntity.setUserId(0L);
        currencyRecordEntity.setReceiveAddress(foundationAddress);
        currencyRecordEntity.setNotes("");
        currencyRecordEntity.setFromAddress(subChainAddres);
        currencyRecordEntity.setFee(new BigDecimal(0));
        currencyRecordEntity.setCurrencyId(1L);
        currencyRecordEntity.setCreateTime(System.currentTimeMillis());
        currencyRecordEntity.setAmount(foundationAmount);
        JSONObject jsonObject = json.getJSONObject("resultData");
        currencyRecordEntity.setHash(jsonObject.getString("hash"));
        currencyRecordService.insert(currencyRecordEntity);
        /**
         * 95%分给矿工,执行矿工分红算法，生成分红记录
         */
        BigDecimal dividendAmount = total.subtract(foundationAmount);
        long divedendId = dividend(dividendAmount, currencyNumber, userEntity);
        /**
         * 新增基金会收益记录
         */
        GainEntity gainEntity = new GainEntity();
        gainEntity.setAssociateId(divedendId);
        gainEntity.setCreateTime(System.currentTimeMillis());
        gainEntity.setCurrencyNumber(currencyNumber);
        gainEntity.setGainQuantity(foundationAmount);
        gainEntity.setGainType(1);
        gainEntity.setUserId(0L);
        gainService.insert(gainEntity);
    }

    public long dividend(BigDecimal dividendAmount, String currencyNumber, UserEntity userEntity) {
        DividendEntity dividendEntity = new DividendEntity();
        dividendEntity.setCreateTime(System.currentTimeMillis());
        dividendEntity.setCurrencyNumber(currencyNumber);
        dividendEntity.setDividendAmount(dividendAmount);
        System.out.println("dividendAmount：" + dividendAmount);
        /**
         * 查询平台所有矿池节点数量
         */
        List<SubchainIpEntity> halfTbList = subchainIpService.selectList(new EntityWrapper<SubchainIpEntity>().eq("subchain_type", 0));
        List<SubchainIpEntity> oneTbList = subchainIpService.selectList(new EntityWrapper<SubchainIpEntity>().eq("subchain_type", 1));
        List<SubchainIpEntity> twoTbList = subchainIpService.selectList(new EntityWrapper<SubchainIpEntity>().eq("subchain_type", 2));
        List<SubchainIpEntity> fourTbList = subchainIpService.selectList(new EntityWrapper<SubchainIpEntity>().eq("subchain_type", 3));
        List<SubchainIpEntity> eightTbList = subchainIpService.selectList(new EntityWrapper<SubchainIpEntity>().eq("subchain_type", 4));
        List<SubchainIpEntity> sixteenTbList = subchainIpService.selectList(new EntityWrapper<SubchainIpEntity>().eq("subchain_type", 5));
        List<SubchainIpEntity> thirtyTwoTbList = subchainIpService.selectList(new EntityWrapper<SubchainIpEntity>().eq("subchain_type", 6));

        double halfTbNumber = halfTbList.size();
        double oneTbNumber = oneTbList.size();
        double twoTbNumber = twoTbList.size();
        double fourTbNumber = fourTbList.size();
        double eightTbNumber = eightTbList.size();
        double sixteenTbNumber = sixteenTbList.size();
        double thirtyTwoTbNumber = thirtyTwoTbList.size();
        System.out.println("halfTbNumber:" + halfTbNumber
                + ",oneTbNumber:" + oneTbNumber
                + ",twoTbNumber:" + twoTbNumber
                + ",fourTbNumber:" + fourTbNumber
                + ",eightTbNumber:" + eightTbNumber
                + ",sixteenTbNumber:" + sixteenTbNumber
                + ",thirtyTwoTbNumber:" + thirtyTwoTbNumber
                + ",totalNumber:" + halfTbNumber + oneTbNumber + twoTbNumber + fourTbNumber + eightTbNumber + sixteenTbNumber + thirtyTwoTbNumber);
        /**
         * 计算单元收益
         */
        double value = dividendAmount.doubleValue() /
                (halfTbNumber * 1 + oneTbNumber * 2 + twoTbNumber * 4 + fourTbNumber * 8 + eightTbNumber * 16 + sixteenTbNumber * 32 + thirtyTwoTbNumber * 64);
        System.out.println(value);
        double halfTb = value;
        double oneTb = value * 2;
        double twoTb = value * 4;
        double fourTb = value * 8;
        double eightTb = value * 16;
        double sixteenTb = value * 32;
        double thirtyTwoTb = value * 64;

        double halfTbAll = halfTb * halfTbNumber;
        double oneTbAll = oneTb * oneTbNumber;
        double twoTbAll = twoTb * twoTbNumber;
        double fourTbAll = fourTb * fourTbNumber;
        double eightTbAll = eightTb * eightTbNumber;
        double sixteenTbAll = sixteenTb * sixteenTbNumber;
        double thirtytwoTbAll = thirtyTwoTb * thirtyTwoTbNumber;
        /**
         * 新增分红记录
         */
        dividendEntity.setHalfTb(new BigDecimal(String.valueOf(halfTb)).setScale(4, BigDecimal.ROUND_FLOOR));
        dividendEntity.setHalfTbAll(new BigDecimal(String.valueOf(halfTbAll)).setScale(4, BigDecimal.ROUND_FLOOR));
        dividendEntity.setOneTb(new BigDecimal(String.valueOf(oneTb)).setScale(4, BigDecimal.ROUND_FLOOR));
        dividendEntity.setOneTbAll(new BigDecimal(String.valueOf(oneTbAll)).setScale(4, BigDecimal.ROUND_FLOOR));
        dividendEntity.setTwoTb(new BigDecimal(String.valueOf(twoTb)).setScale(4, BigDecimal.ROUND_FLOOR));
        dividendEntity.setTwoTbAll(new BigDecimal(String.valueOf(twoTbAll)).setScale(4, BigDecimal.ROUND_FLOOR));
        dividendEntity.setFourTb(new BigDecimal(String.valueOf(fourTb)).setScale(4, BigDecimal.ROUND_FLOOR));
        dividendEntity.setFourTbAll(new BigDecimal(String.valueOf(fourTbAll)).setScale(4, BigDecimal.ROUND_FLOOR));
        dividendEntity.setEightTb(new BigDecimal(String.valueOf(eightTb)).setScale(4, BigDecimal.ROUND_FLOOR));
        dividendEntity.setEightTbAll(new BigDecimal(String.valueOf(eightTbAll)).setScale(4, BigDecimal.ROUND_FLOOR));
        dividendEntity.setSixteenTb(new BigDecimal(String.valueOf(sixteenTb)).setScale(4, BigDecimal.ROUND_FLOOR));
        dividendEntity.setSixteenTbAll(new BigDecimal(String.valueOf(sixteenTbAll)).setScale(4, BigDecimal.ROUND_FLOOR));
        dividendEntity.setThirtyTwoTb(new BigDecimal(String.valueOf(thirtyTwoTb)).setScale(4, BigDecimal.ROUND_FLOOR));
        dividendEntity.setThirtyTwoTbAll(new BigDecimal(String.valueOf(thirtytwoTbAll)).setScale(4, BigDecimal.ROUND_FLOOR));
        dividendService.insert(dividendEntity);
        System.out.println(dividendEntity.toString());
        /**
         * 给矿工转账FST
         */
        /**
         * 500G矿池
         */
        mainChainTrans(halfTbList, userEntity, dividendEntity.getHalfTb());

        /**
         * 1T矿池
         */
        mainChainTrans(oneTbList, userEntity, dividendEntity.getOneTb());
        /**
         * 2T矿池
         */
        mainChainTrans(twoTbList, userEntity, dividendEntity.getTwoTb());
        /**
         * 4T矿池
         */
        mainChainTrans(fourTbList, userEntity, dividendEntity.getFourTb());
        /**
         * 8T矿池
         */
        mainChainTrans(eightTbList, userEntity, dividendEntity.getEightTb());
        /**
         * 16T矿池
         */
        mainChainTrans(sixteenTbList, userEntity, dividendEntity.getSixteenTb());
        /**
         * 32T矿池
         */
        mainChainTrans(thirtyTwoTbList, userEntity, dividendEntity.getThirtyTwoTb());
        return dividendEntity.getDividendId();
    }

    /**
     * 矿工分红(主链ERC20)并产生交易记录
     *
     * @param list
     * @param userEntity
     * @param amount
     */
    private void mainChainTrans(List<SubchainIpEntity> list, UserEntity userEntity, BigDecimal amount) {
        for (SubchainIpEntity entity : list) {
            JSONObject json = MoacUtils.mainChainTrans(userEntity.getAddress(), entity.getIncomeAddress(), userEntity.getPassword(), userEntity.getKeyStore(), amount.toPlainString());
            JSONObject jsonObject = json.getJSONObject("resultData");
            CurrencyRecordEntity currencyRecordEntity = new CurrencyRecordEntity();
            currencyRecordEntity.setType(3);
            currencyRecordEntity.setUserId(entity.getId());
            currencyRecordEntity.setReceiveAddress(entity.getIncomeAddress());
            currencyRecordEntity.setNotes("矿工分红,矿池存储大小：" + entity.getStorageSize());
            currencyRecordEntity.setFromAddress(userEntity.getAddress());
            currencyRecordEntity.setFee(new BigDecimal(0));
            currencyRecordEntity.setCurrencyId(1L);
            currencyRecordEntity.setCreateTime(System.currentTimeMillis());
            currencyRecordEntity.setAmount(amount);
            currencyRecordEntity.setHash(jsonObject.getString("hash"));
            currencyRecordService.insert(currencyRecordEntity);
        }
    }
}
