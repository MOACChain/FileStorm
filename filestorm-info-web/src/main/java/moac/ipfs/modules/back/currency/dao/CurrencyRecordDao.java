package moac.ipfs.modules.back.currency.dao;

import moac.ipfs.modules.back.currency.entity.CurrencyRecordEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;
/**
 * 
 * 
 * @author GZC
 * @email 57855143@qq.com
 * @date 2018-11-21 15:55:03
 */
@Mapper
@Repository
public interface CurrencyRecordDao extends BaseMapper<CurrencyRecordEntity> {

    List<CurrencyRecordEntity> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    void deleteBatch(Long[] currencyRecordIds);
	
}
