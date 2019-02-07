package moac.ipfs.modules.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import moac.ipfs.common.utils.PageUtils;
import moac.ipfs.common.utils.Query;
import moac.ipfs.common.utils.R;
import moac.ipfs.modules.back.storagePackage.entity.StoragePackageEntity;
import moac.ipfs.modules.back.user.entity.*;
import moac.ipfs.modules.back.user.service.FileLogService;
import moac.ipfs.modules.back.user.service.UserFileService;
import moac.ipfs.modules.web.form.*;
import moac.ipfs.modules.web.service.BaseWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author gzc
 * @email 57855143@qq.com
 * @date 2018-09-23 15:31
 */
@RestController
@RequestMapping("/web")
@Api("WEB用户顶层接口")
public class BaseWebController {
    @Autowired
    private BaseWebService baseWebService;
    @Autowired
    private UserFileService userFileService;
    @Autowired
    private FileLogService fileLogService;


    /**
     * 查询首页数据
     * @param request
     * @return
     */
    @PostMapping("queryHomeData")
    @ApiOperation("查询首页数据")
    public R queryHomeData(HttpServletRequest request) {
        Map<String,Object> data = baseWebService.queryHomeDataService(request);
        return R.ok("查询首页数据成功！").put("data", data);
    }

    /**
     * 导入地址
     * @param form
     * @param request
     * @return
     */
    @PostMapping("importAddress")
    @ApiOperation("导入地址")
    public R importAddress(@RequestBody ImportAddressForm form,HttpServletRequest request) {
        UserEntity userEntity = baseWebService.importAddressService(form,request);
        return R.ok("导入成功！").put("data", userEntity);
    }

    /**
     * 创建地址
     * @param form
     * @param request
     * @return
     */
    @PostMapping("createAddress")
    @ApiOperation("创建地址")
    public R createAddress(@RequestBody CreateAddressForm form,HttpServletRequest request) {
        UserEntity userEntity = baseWebService.createAddressService(form, request);
        return R.ok("创建地址成功！").put("data", userEntity);
    }

    /**
     * 查询私钥
     * @param form
     * @param request
     * @return
     */
    @PostMapping("queryPrivateKey")
    @ApiOperation("查询私钥")
    public R queryPrivateKey(@RequestBody ImportAddressForm form,HttpServletRequest request) {
        String privateKey = baseWebService.queryPrivateKeyService(form);
        return R.ok("查询私钥成功！").put("privateKey", privateKey);
    }

    /**
     * 新增文件
     * @param form
     * @return
     */
    @PostMapping("addFile")
    @ApiOperation("新增文件")
    public synchronized R addFile(@RequestBody FileParamsForm form) {
        baseWebService.addFileService(form);
        return R.ok("新增文件成功！");
    }

    /**
     * 输入hash查看文件
     * @param form
     * @return
     */
    @PostMapping("queryFile")
    @ApiOperation("查看文件")
    public R queryFile(@RequestBody FileParamsForm form) {
        String url = baseWebService.queryFileByHashService(form);
        return R.ok("查看文件成功！").put("data", url);
    }

    /**
     * 删除文件
     * @param form
     * @return
     */
    @PostMapping("deleteFile")
    @ApiOperation("删除文件")
    public R deleteFile(@RequestBody FileParamsForm form) {
        baseWebService.deleteFileService(form);
        return R.ok("删除文件成功！");
    }

    /**
     * 查看文件列表
     * @param form
     * @return
     */
    @PostMapping("queryFileList")
    @ApiOperation("查看文件列表")
    public R queryFileList(@RequestBody QueryFileListForm form) {
        String jsonStr = JSON.toJSONString(form);
        Map<String,Object> params = JSONObject.parseObject(jsonStr);
        //查询列表数据
        Query query = new Query(params);

        List<UserFileEntity> userFileList = userFileService.queryList(query);
        int total = userFileService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(userFileList, total, query.getLimit(), query.getPage().getCurrent());

        return R.ok().put("userFileList", pageUtil);
    }

    /**
     * 查看文件操作日志列表
     * @param form
     * @return
     */
    @PostMapping("queryFileLogList")
    @ApiOperation("查看文件操作日志列表")
    public R queryFileLogList(@RequestBody QueryFileListForm form) {
        String jsonStr = JSON.toJSONString(form);
        Map<String,Object> params = JSONObject.parseObject(jsonStr);
        //查询列表数据
        Query query = new Query(params);

        List<FileLogEntity> fileLogList = fileLogService.queryList(query);
        int total = fileLogService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(fileLogList, total, query.getLimit(), query.getPage().getCurrent());

        return R.ok().put("page", pageUtil);
    }

    /**
     * fst兑换子链coin
     * @param assetsForm
     * @return
     */
    @PostMapping("fstToSubChainCoin")
    @ApiOperation("fst兑换子链coin")
    public R fstToSubChainCoin(@RequestBody AssetsFormEntity assetsForm){
        baseWebService.fstToSubChainCoinService(assetsForm);
        return R.ok("fst兑换子链coin成功！");
    }

    /**
     * 子链coin兑换fst
     * @param assetsForm
     * @return
     */
    @PostMapping("subChainCoinToFst")
    @ApiOperation("子链coin兑换fst")
    public R subChainCoinToFst(@RequestBody AssetsFormEntity assetsForm){
        baseWebService.subChainCoinToFstService(assetsForm);
        return R.ok("子链coin兑换fst！");
    }


    /**
     * 用户购买套餐
     * @param from
     * @return
     */
    @PostMapping("buyPackage")
    @ApiOperation("用户购买套餐")
    public R buyPackage(@RequestBody StoragePackageParamsFrom from){
        baseWebService.subChainTransService(from);
        return R.ok("购买成功!");
    }

    /**
     * 用户查询余额
     * @param form
     * @return
     */
    @PostMapping("queryBalance")
    @ApiOperation("用户查询余额")
    public R queryBalance(@RequestBody AssetsFormEntity form){
        UserAssetsEntity userAssetsEntity =baseWebService.queryBalanceService(form);
        return R.ok("查询余额成功！").put("userAssets",userAssetsEntity);
    }

    /**
     * 用户查询资产列表
     * @param form
     * @return
     */
    @PostMapping("queryAssetsList")
    @ApiOperation("用户查询资产列表")
    public R queryAssetsList(@RequestBody AssetsFormEntity form){
        List<UserAssetsEntity> userAssetsEntityList =baseWebService.queryAssetsListService(form);
        return R.ok("查询资产列表成功！").put("data",userAssetsEntityList);
    }

    /**
     * 用户套餐列表
     * @param form
     * @return
     */
    @PostMapping("queryPackageList")
    @ApiOperation("用户套餐列表")
    public R queryPackageList(@RequestBody StoragePackageParamsFrom form) {
        List<StoragePackageEntity> list = baseWebService.queryPackageListService(form);
        return R.ok("查询用户套餐列表成功！").put("data",list);
    }


}
