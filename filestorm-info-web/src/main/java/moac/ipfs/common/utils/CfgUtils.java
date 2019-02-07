package moac.ipfs.common.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @program: moacipfs
 * @author: GZC
 * @create: 2018-09-13 09:29
 * @description: 读取自定义配置文件
 **/
@Component
@ConfigurationProperties(prefix = "myCfg")
public class CfgUtils {

    private boolean ignoreSign;
    private boolean ignoreLogin;
    private boolean saveFlag;
    private Long testUserId;

    public boolean isSaveFlag() {
        return saveFlag;
    }

    public void setSaveFlag(boolean saveFlag) {
        this.saveFlag = saveFlag;
    }

    public Long getTestUserId() {
        return testUserId;
    }

    public void setTestUserId(Long testUserId) {
        this.testUserId = testUserId;
    }

    public boolean isIgnoreSign() {
        return ignoreSign;
    }

    public void setIgnoreSign(boolean ignoreSign) {
        this.ignoreSign = ignoreSign;
    }

    public boolean isIgnoreLogin() {
        return ignoreLogin;
    }

    public void setIgnoreLogin(boolean ignoreLogin) {
        this.ignoreLogin = ignoreLogin;
    }

}
