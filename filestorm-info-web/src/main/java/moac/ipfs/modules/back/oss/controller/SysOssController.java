/**
 * Copyright 2018 moacipfs http://www.moacipfs.io
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package moac.ipfs.modules.back.oss.controller;

import com.google.gson.Gson;
import moac.ipfs.common.utils.ConfigConstant;
import moac.ipfs.common.utils.PageUtils;
import moac.ipfs.common.validator.ValidatorUtils;
import moac.ipfs.common.validator.group.QcloudGroup;
import moac.ipfs.common.validator.group.QiniuGroup;
import moac.ipfs.modules.back.oss.cloud.CloudStorageConfig;
import moac.ipfs.common.utils.Constant;
import moac.ipfs.common.utils.R;
import moac.ipfs.modules.back.oss.entity.SysOssEntity;
import moac.ipfs.modules.back.sys.service.SysConfigService;
import moac.ipfs.common.exception.RRException;
import moac.ipfs.common.validator.group.AliyunGroup;
import moac.ipfs.modules.back.oss.cloud.OSSFactory;
import moac.ipfs.modules.back.oss.service.SysOssService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * 文件上传
 * 
 * @author GZC
 * @email 57855143@qq.com
 * @date 2017-03-25 12:13:26
 */
@RestController
@RequestMapping("sys/oss")
public class SysOssController {
	@Autowired
	private SysOssService sysOssService;
    @Autowired
    private SysConfigService sysConfigService;

    private final static String KEY = ConfigConstant.CLOUD_STORAGE_CONFIG_KEY;
	
	/**
	 * 列表
	 */
	@GetMapping("/list")
	@RequiresPermissions("sys:oss:all")
	public R list(@RequestParam Map<String, Object> params){
		PageUtils page = sysOssService.queryPage(params);

		return R.ok().put("page", page);
	}


    /**
     * 云存储配置信息
     */
    @GetMapping("/config")
    @RequiresPermissions("sys:oss:all")
    public R config(){
        CloudStorageConfig config = sysConfigService.getConfigObject(KEY, CloudStorageConfig.class);

        return R.ok().put("config", config);
    }


	/**
	 * 保存云存储配置信息
	 */
	@PostMapping("/saveConfig")
	@RequiresPermissions("sys:oss:all")
	public R saveConfig(@RequestBody CloudStorageConfig config){
		//校验类型
		ValidatorUtils.validateEntity(config);

		if(config.getType() == Constant.CloudService.QINIU.getValue()){
			//校验七牛数据
			ValidatorUtils.validateEntity(config, QiniuGroup.class);
		}else if(config.getType() == Constant.CloudService.ALIYUN.getValue()){
			//校验阿里云数据
			ValidatorUtils.validateEntity(config, AliyunGroup.class);
		}else if(config.getType() == Constant.CloudService.QCLOUD.getValue()){
			//校验腾讯云数据
			ValidatorUtils.validateEntity(config, QcloudGroup.class);
		}

        sysConfigService.updateValueByKey(KEY, new Gson().toJson(config));

		return R.ok();
	}
	

	/**
	 * 上传文件
	 */
	@PostMapping("/upload")
	@RequiresPermissions("sys:oss:all")
	public R upload(@RequestParam("file") MultipartFile file) throws Exception {
		if (file.isEmpty()) {
			throw new RRException("上传文件不能为空");
		}

		//上传文件
		String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		String url = OSSFactory.build().uploadSuffix(file.getBytes(), suffix);

		//保存文件信息
		SysOssEntity ossEntity = new SysOssEntity();
		ossEntity.setUrl(url);
		ossEntity.setCreateDate(new Date());
		sysOssService.insert(ossEntity);

		return R.ok().put("url", url);
	}


	/**
	 * 删除
	 */
	@PostMapping("/delete")
	@RequiresPermissions("sys:oss:all")
	public R delete(@RequestBody Long[] ids){
		sysOssService.deleteBatchIds(Arrays.asList(ids));

		return R.ok();
	}

}
