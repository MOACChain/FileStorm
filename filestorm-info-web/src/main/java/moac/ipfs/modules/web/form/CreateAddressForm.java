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

package moac.ipfs.modules.web.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * 注册表单
 *
 * @author Mark 57855143@qq.com
 * @since 3.1.0 2018-01-25
 */
@ApiModel(value = "WEB注册基础表单")
public class CreateAddressForm implements Serializable{
    private static final long serialVersionUID = -4523416629440938210L;


    @ApiModelProperty(value = "密码")
    @NotBlank(message="密码不能为空")
    private String password;

    @ApiModelProperty(value = "密码提示信息")
    private String passwordHint;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordHint() {
        return passwordHint;
    }

    public void setPasswordHint(String passwordHint) {
        this.passwordHint = passwordHint;
    }
}
