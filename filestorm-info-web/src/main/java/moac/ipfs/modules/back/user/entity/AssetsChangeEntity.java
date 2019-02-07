package moac.ipfs.modules.back.user.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * 
 * @author GZC
 * @email 57855143@qq.com
 * @date 2018-11-21 16:26:00
 */
@TableName(value = "tb_assets_change")
public class AssetsChangeEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
    /**
     * 资产变更记录id
     */
    @TableId
	private Long assetsChangeId;
    /**
     * 用户id
     */
	private Long userId;
    /**
     * 关联id
     */
	private Long associateId;
    /**
     * 类型：默认-1，0：用户充币,1：用户提币，3：活动
     */
	private Integer type;
    /**
     * 变更前的余额
     */
	private BigDecimal beforeBalance;
    /**
     * 变更后的余额
     */
	private BigDecimal afterBalance;
    /**
     * 变更前的锁定量
     */
	private BigDecimal beforeLock;
    /**
     * 变更后的锁定量
     */
	private BigDecimal afterLock;
    /**
     * 余额变更量
     */
	private BigDecimal balanceChange;
    /**
     * 锁定变更量
     */
	private BigDecimal lockChange;
    /**
     * 创建时间
     */
	private Long createTime;
    /**
     * 备注
     */
	private String note;
	/**
	 * 币种编号
	 */
	private String currencyNumber;

	public String getCurrencyNumber() {
		return currencyNumber;
	}

	public void setCurrencyNumber(String currencyNumber) {
		this.currencyNumber = currencyNumber;
	}

	/**
	 * 设置：资产变更记录id
	 */
	public void setAssetsChangeId(Long assetsChangeId) {
		this.assetsChangeId = assetsChangeId;
	}
	/**
	 * 获取：资产变更记录id
	 */
	public Long getAssetsChangeId() {
		return assetsChangeId;
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
	 * 设置：关联id
	 */
	public void setAssociateId(Long associateId) {
		this.associateId = associateId;
	}
	/**
	 * 获取：关联id
	 */
	public Long getAssociateId() {
		return associateId;
	}
	/**
	 * 设置：类型：默认-1，0：用户充币,1：用户提币，3：活动
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取：类型：默认-1，0：用户充币,1：用户提币，3：活动
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置：变更前的余额
	 */
	public void setBeforeBalance(BigDecimal beforeBalance) {
		this.beforeBalance = beforeBalance;
	}
	/**
	 * 获取：变更前的余额
	 */
	public BigDecimal getBeforeBalance() {
		return beforeBalance;
	}
	/**
	 * 设置：变更后的余额
	 */
	public void setAfterBalance(BigDecimal afterBalance) {
		this.afterBalance = afterBalance;
	}
	/**
	 * 获取：变更后的余额
	 */
	public BigDecimal getAfterBalance() {
		return afterBalance;
	}
	/**
	 * 设置：变更前的锁定量
	 */
	public void setBeforeLock(BigDecimal beforeLock) {
		this.beforeLock = beforeLock;
	}
	/**
	 * 获取：变更前的锁定量
	 */
	public BigDecimal getBeforeLock() {
		return beforeLock;
	}
	/**
	 * 设置：变更后的锁定量
	 */
	public void setAfterLock(BigDecimal afterLock) {
		this.afterLock = afterLock;
	}
	/**
	 * 获取：变更后的锁定量
	 */
	public BigDecimal getAfterLock() {
		return afterLock;
	}
	/**
	 * 设置：余额变更量
	 */
	public void setBalanceChange(BigDecimal balanceChange) {
		this.balanceChange = balanceChange;
	}
	/**
	 * 获取：余额变更量
	 */
	public BigDecimal getBalanceChange() {
		return balanceChange;
	}
	/**
	 * 设置：锁定变更量
	 */
	public void setLockChange(BigDecimal lockChange) {
		this.lockChange = lockChange;
	}
	/**
	 * 获取：锁定变更量
	 */
	public BigDecimal getLockChange() {
		return lockChange;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Long getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：备注
	 */
	public void setNote(String note) {
		this.note = note;
	}
	/**
	 * 获取：备注
	 */
	public String getNote() {
		return note;
	}
}
