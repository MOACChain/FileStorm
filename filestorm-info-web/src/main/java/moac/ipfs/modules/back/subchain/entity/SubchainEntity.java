package moac.ipfs.modules.back.subchain.entity;

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
 * @date 2018-11-02 18:10:35
 */
@TableName(value = "tb_subchain")
public class SubchainEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
    /**
     * id
     */
    @TableId
	private Long subchainId;
    /**
     * 子链合约地址
     */
	private String subchainAddress;
    /**
     * 存储大小
     */
	private BigDecimal subchainSize;
    /**
     * 剩余存储大小
     */
	private BigDecimal remainSize;
    /**
     * 已使用存储大小
     */
	private BigDecimal useSize;
    /**
     * 已使用百分比
     */
	private BigDecimal percentageUse;
	/**
	 * 系统用户地址
	 */
	private String sysAddress;
	/**
	 * 矿池存储规格
	 */
	private String storageSpecification;
	/**
	 * 矿池类型(0:0.5T,1:1T,2:2T,3:4T,4:8T,5:16T,6:32T)
	 */
	private Integer subchain_type;

	public Integer getSubchain_type() {
		return subchain_type;
	}

	public void setSubchain_type(Integer subchain_type) {
		this.subchain_type = subchain_type;
	}

	public String getStorageSpecification() {
		return storageSpecification;
	}

	public void setStorageSpecification(String storageSpecification) {
		this.storageSpecification = storageSpecification;
	}

	public String getSysAddress() {
		return sysAddress;
	}

	public void setSysAddress(String sysAddress) {
		this.sysAddress = sysAddress;
	}

	/**
	 * 设置：id
	 */
	public void setSubchainId(Long subchainId) {
		this.subchainId = subchainId;
	}
	/**
	 * 获取：id
	 */
	public Long getSubchainId() {
		return subchainId;
	}
	/**
	 * 设置：子链合约地址
	 */
	public void setSubchainAddress(String subchainAddress) {
		this.subchainAddress = subchainAddress;
	}
	/**
	 * 获取：子链合约地址
	 */
	public String getSubchainAddress() {
		return subchainAddress;
	}

	public BigDecimal getSubchainSize() {
		return subchainSize;
	}

	public void setSubchainSize(BigDecimal subchainSize) {
		this.subchainSize = subchainSize;
	}

	public BigDecimal getRemainSize() {
		return remainSize;
	}

	public void setRemainSize(BigDecimal remainSize) {
		this.remainSize = remainSize;
	}

	public BigDecimal getUseSize() {
		return useSize;
	}

	public void setUseSize(BigDecimal useSize) {
		this.useSize = useSize;
	}

	/**
	 * 设置：已使用百分比
	 */
	public void setPercentageUse(BigDecimal percentageUse) {
		this.percentageUse = percentageUse;
	}
	/**
	 * 获取：已使用百分比
	 */
	public BigDecimal getPercentageUse() {
		return percentageUse;
	}
}
