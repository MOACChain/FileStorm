package moac.ipfs.modules.back.user.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import java.util.Date;
import java.math.BigDecimal;

/**
 * 
 * 
 * @author GZC
 * @email 57855143@qq.com
 * @date 2018-11-21 15:52:21
 */
@TableName(value = "tb_user_assets")
public class UserAssetsEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
    /**
     * id
     */
    @TableId
	private Long assetsId;
    /**
     * 币种编号
     */
	private String currencyNumber;
    /**
     * 用户id
     */
	private Long userId;
    /**
     * 锁定量
     */
	private BigDecimal lock;
    /**
     * 可用余额
     */
	private BigDecimal balance;

	/**
	 * 设置：id
	 */
	public void setAssetsId(Long assetsId) {
		this.assetsId = assetsId;
	}
	/**
	 * 获取：id
	 */
	public Long getAssetsId() {
		return assetsId;
	}

	public String getCurrencyNumber() {
		return currencyNumber;
	}

	public void setCurrencyNumber(String currencyNumber) {
		this.currencyNumber = currencyNumber;
	}

	/**
	 * 设置：用户id
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户id
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * 设置：锁定量
	 */
	public void setLock(BigDecimal lock) {
		this.lock = lock;
	}
	/**
	 * 获取：锁定量
	 */
	public BigDecimal getLock() {
		return lock;
	}
	/**
	 * 设置：可用余额
	 */
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	/**
	 * 获取：可用余额
	 */
	public BigDecimal getBalance() {
		return balance;
	}
}
