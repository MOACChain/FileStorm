package moac.ipfs.modules.back.currency.entity;

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
 * @date 2018-11-21 15:55:03
 */
@TableName(value = "tb_currency_record")
public class CurrencyRecordEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
    /**
     * id
     */
	@TableId
	private Long currencyRecordId;
    /**
     * 币种id
     */
	private Long currencyId;
    /**
     * 用户id,矿池分红则为矿机节点id
     */
	private Long userId;
    /**
     * 创建时间
     */
	private Long createTime;
    /**
     * 记录编号
     */
	private String no;
    /**
     * 发送地址
     */
	private String fromAddress;
    /**
     * 接受地址
     */
	private String receiveAddress;
    /**
     * 交易hash
     */
	private String hash;
    /**
     * 交易数量
     */
	private BigDecimal amount;
    /**
     * 手续费
     */
	private BigDecimal fee;
    /**
     * 类型：默认-1,0:FST转COIN,1:COIN转FST
     */
	private Integer type;
    /**
     * 备注
     */
	private String notes;

	/**
	 * 设置：id
	 */
	public void setCurrencyRecordId(Long currencyRecordId) {
		this.currencyRecordId = currencyRecordId;
	}
	/**
	 * 获取：id
	 */
	public Long getCurrencyRecordId() {
		return currencyRecordId;
	}
	/**
	 * 设置：币种id
	 */
	public void setCurrencyId(Long currencyId) {
		this.currencyId = currencyId;
	}
	/**
	 * 获取：币种id
	 */
	public Long getCurrencyId() {
		return currencyId;
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
	 * 设置：记录编号
	 */
	public void setNo(String no) {
		this.no = no;
	}
	/**
	 * 获取：记录编号
	 */
	public String getNo() {
		return no;
	}
	/**
	 * 设置：发送地址
	 */
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}
	/**
	 * 获取：发送地址
	 */
	public String getFromAddress() {
		return fromAddress;
	}
	/**
	 * 设置：接受地址
	 */
	public void setReceiveAddress(String receiveAddress) {
		this.receiveAddress = receiveAddress;
	}
	/**
	 * 获取：接受地址
	 */
	public String getReceiveAddress() {
		return receiveAddress;
	}
	/**
	 * 设置：交易hash
	 */
	public void setHash(String hash) {
		this.hash = hash;
	}
	/**
	 * 获取：交易hash
	 */
	public String getHash() {
		return hash;
	}
	/**
	 * 设置：交易数量
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	/**
	 * 获取：交易数量
	 */
	public BigDecimal getAmount() {
		return amount;
	}
	/**
	 * 设置：手续费
	 */
	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
	/**
	 * 获取：手续费
	 */
	public BigDecimal getFee() {
		return fee;
	}
	/**
	 * 设置：类型：默认-1,0:充币,1:提币
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取：类型：默认-1,0:充币,1:提币
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置：备注
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}
	/**
	 * 获取：备注
	 */
	public String getNotes() {
		return notes;
	}
}
