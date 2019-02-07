package moac.ipfs.modules.back.subchain.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import moac.ipfs.modules.back.subchain.entity.SubchainIpEntity;
import moac.ipfs.modules.back.subchain.service.SubchainIpService;
import moac.ipfs.common.utils.PageUtils;
import moac.ipfs.common.utils.Query;
import moac.ipfs.common.utils.R;

/**
 * @author GZC
 * @email 57855143@qq.com
 * @date 2018-11-19 09:30:41
 */
@RestController
@RequestMapping("/subchain/subchainip")
@EnableScheduling
public class SubchainIpController {
    @Autowired
    private SubchainIpService subchainIpService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("subchain:subchainip:list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

        List<SubchainIpEntity> subchainIpList = subchainIpService.queryList(query);
        int total = subchainIpService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(subchainIpList, total, query.getLimit(), query.getPage().getCurrent());

        return R.ok().put("page", pageUtil);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("subchain:subchainip:info")
    public R info(@PathVariable("id") Integer id) {
        SubchainIpEntity subchainIp = subchainIpService.selectById(id);

        return R.ok().put("subchainIp", subchainIp);
    }

    /**
     * 保存
     */
    @RequestMapping("/add")
    //@RequiresPermissions("subchain:subchainip:save")
    public R save(@RequestBody SubchainIpEntity subchainIp) {
        subchainIpService.addService(subchainIp);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("subchain:subchainip:update")
    public R update(@RequestBody SubchainIpEntity subchainIp) {
        subchainIpService.updateById(subchainIp);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("subchain:subchainip:delete")
    public R delete(@RequestBody Integer[] ids) {
        subchainIpService.deleteBatch(ids);

        return R.ok();
    }

    /**
     * 每月末矿池分红
     */
    @RequestMapping("/minePoolNodeDividend")
    public void minePoolNodeDividend() {
        subchainIpService.minePoolNodeDividend();
    }
}
