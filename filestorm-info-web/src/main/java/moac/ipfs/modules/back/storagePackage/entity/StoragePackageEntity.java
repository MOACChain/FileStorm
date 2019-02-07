package moac.ipfs.modules.back.storagePackage.entity;

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
 * @date 2018-11-27 10:19:35
 */
@TableName(value = "tb_storage_package")
public class StoragePackageEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
    /**
     * id
     */
    @TableId
	private Long storagePackageId;
    /**
     * 用户id
     */
	private Long userId;
    /**
     * 创建时间
     */
	private Long createDate;
    /**
     * 过期时间
     */
	private Long expireDate;
    /**
     * 存储空间(格式化)
     */
	private String formatSize;
    /**
     * 已经使用的空间(格式化)
     */
	private String useFormatSize;
    /**
     * 原始存储空间
     */
	private BigDecimal realSize;
    /**
     * 已使用存储空间
     */
	private BigDecimal userRealSize;
    /**
     * 价格
     */
	private BigDecimal price;
	/**
	 * 状态(0:正常,1:已过期但在一个月内(文件保留期),2:已过期(超过一个月,文件无法查看)
	 */
	private Integer status;

	/**
	 * 币种编号
	 */
	private String currencyNumber;
	/**
	 * 购买hash
	 */
	private String buyHash;
	/**
	 * 购买状态:0:处理中,1:购买成功.2:购买失败
	 */
	private Integer buyStatus;

	public String getBuyHash() {
		return buyHash;
	}

	public void setBuyHash(String buyHash) {
		this.buyHash = buyHash;
	}

	public Integer getBuyStatus() {
		return buyStatus;
	}

	public void setBuyStatus(Integer buyStatus) {
		this.buyStatus = buyStatus;
	}

	public String getCurrencyNumber() {
		return currencyNumber;
	}

	public void setCurrencyNumber(String currencyNumber) {
		this.currencyNumber = currencyNumber;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 设置：id
	 */
	public void setStoragePackageId(Long storagePackageId) {
		this.storagePackageId = storagePackageId;
	}
	/**
	 * 获取：id
	 */
	public Long getStoragePackageId() {
		return storagePackageId;
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
	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}
	/**
	 * 获取：创建时间
	 */
	public Long getCreateDate() {
		return createDate;
	}
	/**
	 * 设置：过期时间
	 */
	public void setExpireDate(Long expireDate) {
		this.expireDate = expireDate;
	}
	/**
	 * 获取：过期时间
	 */
	public Long getExpireDate() {
		return expireDate;
	}
	/**
	 * 设置：存储空间(格式化)
	 */
	public void setFormatSize(String formatSize) {
		this.formatSize = formatSize;
	}
	/**
	 * 获取：存储空间(格式化)
	 */
	public String getFormatSize() {
		return formatSize;
	}
	/**
	 * 设置：已经使用的空间(格式化)
	 */
	public void setUseFormatSize(String useFormatSize) {
		this.useFormatSize = useFormatSize;
	}
	/**
	 * 获取：已经使用的空间(格式化)
	 */
	public String getUseFormatSize() {
		return useFormatSize;
	}
	/**
	 * 设置：原始存储空间
	 */
	public void setRealSize(BigDecimal realSize) {
		this.realSize = realSize;
	}
	/**
	 * 获取：原始存储空间
	 */
	public BigDecimal getRealSize() {
		return realSize;
	}
	/**
	 * 设置：已使用存储空间
	 */
	public void setUserRealSize(BigDecimal userRealSize) {
		this.userRealSize = userRealSize;
	}
	/**
	 * 获取：已使用存储空间
	 */
	public BigDecimal getUserRealSize() {
		return userRealSize;
	}
	/**
	 * 设置：价格
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	/**
	 * 获取：价格
	 */
	public BigDecimal getPrice() {
		return price;
	}
}
