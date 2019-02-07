package moac.ipfs.modules.back.gain.entity;

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
 * @date 2018-11-21 15:57:21
 */
@TableName(value = "tb_gain")
public class GainEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
    /**
     * id
     */
	@TableId
	private Long gainId;
    /**
     * 收益数量
     */
	private BigDecimal gainQuantity;
    /**
     * 创建时间
     */
	private Long createTime;
    /**
     * 关联id，类型为1发起时为发起id，类型为2提币时为提币记录id,类型为3时为付费记录id
     */
	private Long associateId;
    /**
     * 类型 1：发起，2：提币，3：付费查看,4：活动,5,创建俱乐部,6:事件预测
     */
	private Integer gainType;
    /**
     * 币种id
     */
	private String currencyNumber;
    /**
     * 产生收益的用户
     */
	private Long userId;

	/**
	 * 设置：id
	 */
	public void setGainId(Long gainId) {
		this.gainId = gainId;
	}
	/**
	 * 获取：id
	 */
	public Long getGainId() {
		return gainId;
	}
	/**
	 * 设置：收益数量
	 */
	public void setGainQuantity(BigDecimal gainQuantity) {
		this.gainQuantity = gainQuantity;
	}
	/**
	 * 获取：收益数量
	 */
	public BigDecimal getGainQuantity() {
		return gainQuantity;
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
	 * 设置：关联id，类型为1发起时为发起id，类型为2提币时为提币记录id,类型为3时为付费记录id
	 */
	public void setAssociateId(Long associateId) {
		this.associateId = associateId;
	}
	/**
	 * 获取：关联id，类型为1发起时为发起id，类型为2提币时为提币记录id,类型为3时为付费记录id
	 */
	public Long getAssociateId() {
		return associateId;
	}
	/**
	 * 设置：类型 1：发起，2：提币，3：付费查看,4：活动,5,创建俱乐部,6:事件预测
	 */
	public void setGainType(Integer gainType) {
		this.gainType = gainType;
	}
	/**
	 * 获取：类型 1：发起，2：提币，3：付费查看,4：活动,5,创建俱乐部,6:事件预测
	 */
	public Integer getGainType() {
		return gainType;
	}

	public String getCurrencyNumber() {
		return currencyNumber;
	}

	public void setCurrencyNumber(String currencyNumber) {
		this.currencyNumber = currencyNumber;
	}

	/**
	 * 设置：产生收益的用户
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：产生收益的用户
	 */
	public Long getUserId() {
		return userId;
	}
}
