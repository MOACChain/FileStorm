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
 * @date 2018-11-30 15:02:08
 */
@TableName(value = "tb_dividend")
public class DividendEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
    /**
     * 分红记录id
     */
    @TableId
	private Long dividendId;
    /**
     * 创建时间
     */
	private Long createTime;
    /**
     * 分红总量
     */
	private BigDecimal dividendAmount;
    /**
     * 0.5T总分红
     */
	private BigDecimal halfTbAll;
    /**
     * 0.5T单个节点分红
     */
	private BigDecimal halfTb;
    /**
     * 1T总分红
     */
	private BigDecimal oneTbAll;
    /**
     * 1T单个节点分红
     */
	private BigDecimal oneTb;
    /**
     * 2T总分红
     */
	private BigDecimal twoTbAll;
    /**
     * 2T单个节点分红
     */
	private BigDecimal twoTb;
    /**
     * 4T总分红
     */
	private BigDecimal fourTbAll;
    /**
     * 4T单个节点分红
     */
	private BigDecimal fourTb;
    /**
     * 8T总分红
     */
	private BigDecimal eightTbAll;
    /**
     * 8T单个节点分红
     */
	private BigDecimal eightTb;
    /**
     * 16T总分红
     */
	private BigDecimal sixteenTbAll;
    /**
     * 16T单个节点分红
     */
	private BigDecimal sixteenTb;
	/**
	 * 32T总分红
	 */
	private BigDecimal thirtyTwoTbAll;
	/**
	 * 32T单个节点分红
	 */
	private BigDecimal thirtyTwoTb;
    /**
     * 币种编号
     */
	private String currencyNumber;

	public BigDecimal getThirtyTwoTbAll() {
		return thirtyTwoTbAll;
	}

	public void setThirtyTwoTbAll(BigDecimal thirtyTwoTbAll) {
		this.thirtyTwoTbAll = thirtyTwoTbAll;
	}

	public BigDecimal getThirtyTwoTb() {
		return thirtyTwoTb;
	}

	public void setThirtyTwoTb(BigDecimal thirtyTwoTb) {
		this.thirtyTwoTb = thirtyTwoTb;
	}

	/**
	 * 设置：分红记录id
	 */
	public void setDividendId(Long dividendId) {
		this.dividendId = dividendId;
	}
	/**
	 * 获取：分红记录id
	 */
	public Long getDividendId() {
		return dividendId;
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
	 * 设置：分红总量
	 */
	public void setDividendAmount(BigDecimal dividendAmount) {
		this.dividendAmount = dividendAmount;
	}
	/**
	 * 获取：分红总量
	 */
	public BigDecimal getDividendAmount() {
		return dividendAmount;
	}
	/**
	 * 设置：0.5T总分红
	 */
	public void setHalfTbAll(BigDecimal halfTbAll) {
		this.halfTbAll = halfTbAll;
	}
	/**
	 * 获取：0.5T总分红
	 */
	public BigDecimal getHalfTbAll() {
		return halfTbAll;
	}
	/**
	 * 设置：0.5T单个节点分红
	 */
	public void setHalfTb(BigDecimal halfTb) {
		this.halfTb = halfTb;
	}
	/**
	 * 获取：0.5T单个节点分红
	 */
	public BigDecimal getHalfTb() {
		return halfTb;
	}
	/**
	 * 设置：1T总分红
	 */
	public void setOneTbAll(BigDecimal oneTbAll) {
		this.oneTbAll = oneTbAll;
	}
	/**
	 * 获取：1T总分红
	 */
	public BigDecimal getOneTbAll() {
		return oneTbAll;
	}
	/**
	 * 设置：1T单个节点分红
	 */
	public void setOneTb(BigDecimal oneTb) {
		this.oneTb = oneTb;
	}
	/**
	 * 获取：1T单个节点分红
	 */
	public BigDecimal getOneTb() {
		return oneTb;
	}
	/**
	 * 设置：2T总分红
	 */
	public void setTwoTbAll(BigDecimal twoTbAll) {
		this.twoTbAll = twoTbAll;
	}
	/**
	 * 获取：2T总分红
	 */
	public BigDecimal getTwoTbAll() {
		return twoTbAll;
	}
	/**
	 * 设置：2T单个节点分红
	 */
	public void setTwoTb(BigDecimal twoTb) {
		this.twoTb = twoTb;
	}
	/**
	 * 获取：2T单个节点分红
	 */
	public BigDecimal getTwoTb() {
		return twoTb;
	}
	/**
	 * 设置：4T总分红
	 */
	public void setFourTbAll(BigDecimal fourTbAll) {
		this.fourTbAll = fourTbAll;
	}
	/**
	 * 获取：4T总分红
	 */
	public BigDecimal getFourTbAll() {
		return fourTbAll;
	}
	/**
	 * 设置：4T单个节点分红
	 */
	public void setFourTb(BigDecimal fourTb) {
		this.fourTb = fourTb;
	}
	/**
	 * 获取：4T单个节点分红
	 */
	public BigDecimal getFourTb() {
		return fourTb;
	}
	/**
	 * 设置：8T总分红
	 */
	public void setEightTbAll(BigDecimal eightTbAll) {
		this.eightTbAll = eightTbAll;
	}
	/**
	 * 获取：8T总分红
	 */
	public BigDecimal getEightTbAll() {
		return eightTbAll;
	}
	/**
	 * 设置：8T单个节点分红
	 */
	public void setEightTb(BigDecimal eightTb) {
		this.eightTb = eightTb;
	}
	/**
	 * 获取：8T单个节点分红
	 */
	public BigDecimal getEightTb() {
		return eightTb;
	}
	/**
	 * 设置：16T总分红
	 */
	public void setSixteenTbAll(BigDecimal sixteenTbAll) {
		this.sixteenTbAll = sixteenTbAll;
	}
	/**
	 * 获取：16T总分红
	 */
	public BigDecimal getSixteenTbAll() {
		return sixteenTbAll;
	}
	/**
	 * 设置：16T单个节点分红
	 */
	public void setSixteenTb(BigDecimal sixteenTb) {
		this.sixteenTb = sixteenTb;
	}
	/**
	 * 获取：16T单个节点分红
	 */
	public BigDecimal getSixteenTb() {
		return sixteenTb;
	}
	/**
	 * 设置：币种编号
	 */
	public void setCurrencyNumber(String currencyNumber) {
		this.currencyNumber = currencyNumber;
	}
	/**
	 * 获取：币种编号
	 */
	public String getCurrencyNumber() {
		return currencyNumber;
	}

	@Override
	public String toString() {
		return "DividendEntity{" +
				"createTime=" + createTime +
				", dividendAmount=" + dividendAmount +
				", halfTbAll=" + halfTbAll +
				", halfTb=" + halfTb +
				", oneTbAll=" + oneTbAll +
				", oneTb=" + oneTb +
				", twoTbAll=" + twoTbAll +
				", twoTb=" + twoTb +
				", fourTbAll=" + fourTbAll +
				", fourTb=" + fourTb +
				", eightTbAll=" + eightTbAll +
				", eightTb=" + eightTb +
				", sixteenTbAll=" + sixteenTbAll +
				", sixteenTb=" + sixteenTb +
				", thirtyTwoTbAll=" + thirtyTwoTbAll +
				", thirtyTwoTb=" + thirtyTwoTb +
				", currencyNumber='" + currencyNumber + '\'' +
				'}';
	}
}
