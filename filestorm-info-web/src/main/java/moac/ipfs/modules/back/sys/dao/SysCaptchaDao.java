/**
 * Copyright 2018 http://www.moacipfs.io
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

package moac.ipfs.modules.back.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import moac.ipfs.modules.back.sys.entity.SysCaptchaEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 验证码
 *
 * @author Mark 57855143@qq.com
 * @since 3.1.0 2018-02-10
 */
@Mapper
public interface SysCaptchaDao extends BaseMapper<SysCaptchaEntity> {

}
