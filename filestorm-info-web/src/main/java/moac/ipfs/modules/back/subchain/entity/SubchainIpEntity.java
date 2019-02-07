package moac.ipfs.modules.back.subchain.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 
 * 
 * @author GZC
 * @email 57855143@qq.com
 * @date 2018-11-19 09:30:41
 */
@TableName(value = "tb_subchain_ip")
public class SubchainIpEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
    /**
     * id
     */
    @TableId
	private Long id;
    /**
     * 存储子链合约地址
     */
	private String subchainAddress;
    /**
     * 存储子链子链ip
     */
	private String sip;
	/**
	 * 收益地址
	 */
	private String incomeAddress;
	/**
	 *节点存储空间大小
	 */
	private String storageSize;
	/**
	 *创建时间
	 */
	private long createTime;
	/**
	 *矿池类型(0:0.5T,1:1T,2:2T,3:4T,4:8T,5:16T,6:32T)
	 */
	private Integer subchainType;

	public Integer getSubchainType() {
		return subchainType;
	}

	public void setSubchainType(Integer subchainType) {
		this.subchainType = subchainType;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getIncomeAddress() {
		return incomeAddress;
	}

	public void setIncomeAddress(String incomeAddress) {
		this.incomeAddress = incomeAddress;
	}

	public String getStorageSize() {
		return storageSize;
	}

	public void setStorageSize(String storageSize) {
		this.storageSize = storageSize;
	}

	/**
	 * 设置：id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：存储子链合约地址
	 */
	public void setSubchainAddress(String subchainAddress) {
		this.subchainAddress = subchainAddress;
	}
	/**
	 * 获取：存储子链合约地址
	 */
	public String getSubchainAddress() {
		return subchainAddress;
	}
	/**
	 * 设置：存储子链子链ip
	 */
	public void setSip(String sip) {
		this.sip = sip;
	}
	/**
	 * 获取：存储子链子链ip
	 */
	public String getSip() {
		return sip;
	}
}
