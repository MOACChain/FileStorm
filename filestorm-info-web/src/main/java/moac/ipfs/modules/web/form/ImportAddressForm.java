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

/**
 * 登录表单
 *
 * @author Mark 57855143@qq.com
 * @since 3.1.0 2018-01-25
 */
@ApiModel(value = "导入地址表单")
public class ImportAddressForm {

    @ApiModelProperty(value = "导入方式,keyStore导入地址方式:KEYSTORE_TYPE,助记词导入地址方式：MNEMONIC_TYPE，私钥导入地址方式：PLAINTEXTPRIVATEKEY_TYPE")
    private String importType;

    @ApiModelProperty(value = "keyStore")
    private String keyStore;

    @ApiModelProperty(value = "密码")
    @NotBlank(message="密码不能为空")
    private String password;

    @ApiModelProperty(value = "密码提示信息")
    private String passwordHint;

    @ApiModelProperty(value = "助记词")
    private String mnemonic;

    @ApiModelProperty(value = "加密方式")
    private String encryption;

    @ApiModelProperty(value = "明文私钥")
    private String plaintextPrivateKey;

    public String getImportType() {
        return importType;
    }

    public void setImportType(String importType) {
        this.importType = importType;
    }

    public String getKeyStore() {
        return keyStore;
    }

    public void setKeyStore(String keyStore) {
        this.keyStore = keyStore;
    }

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

    public String getMnemonic() {
        return mnemonic;
    }

    public void setMnemonic(String mnemonic) {
        this.mnemonic = mnemonic;
    }

    public String getEncryption() {
        return encryption;
    }

    public void setEncryption(String encryption) {
        this.encryption = encryption;
    }

    public String getPlaintextPrivateKey() {
        return plaintextPrivateKey;
    }

    public void setPlaintextPrivateKey(String plaintextPrivateKey) {
        this.plaintextPrivateKey = plaintextPrivateKey;
    }
}
