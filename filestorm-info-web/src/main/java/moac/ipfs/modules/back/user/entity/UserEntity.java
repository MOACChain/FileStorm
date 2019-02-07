package moac.ipfs.modules.back.user.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;


import java.util.Date;

/**
 * 用户
 * 
 * @author GZC
 * @email 57855143@qq.com
 * @date 2018-11-01 09:54:05
 */

@TableName(value = "tb_user")
public class UserEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
    /**
     * 
     */
    @TableId
	private Long userId;
    /**
     * 用户名(用户地址)
     */
	private String address;
    /**
     * 用户昵称
     */
	private String userAccount;
    /**
     * 密码
     */
	private String password;
    /**
     * 创建时间
     */
	private Long createTime;
    /**
     * 最后一次登录时间
     */
	private Long lastLoginTime;
    /**
     * 最后一次登录ip
     */
	private String lastLoginIp;
	/**
	 *密码提示信息
	 */
	private String passwordHint;
	/**
	 *keyStore字符串
	 */
	private String keyStore;

	public String getKeyStore() {
		return keyStore;
	}

	public void setKeyStore(String keyStore) {
		this.keyStore = keyStore;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Long lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public String getPasswordHint() {
		return passwordHint;
	}

	public void setPasswordHint(String passwordHint) {
		this.passwordHint = passwordHint;
	}
}
