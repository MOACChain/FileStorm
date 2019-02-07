package moac.ipfs.modules.back.subchain.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import java.util.Date;

/**
 * 
 * 
 * @author GZC
 * @email 57855143@qq.com
 * @date 2018-11-09 10:27:30
 */
@TableName(value = "tb_subchain_log")
public class SubchainLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
    /**
     * id
     */
    @TableId
	private Integer subLogId;
    /**
     * 操作系统用户名
     */
	private String sysUserName;
    /**
     * 存储子链地址
     */
	private String subchainAddress;
    /**
     * 操作类型
     */
	private String operationType;
    /**
     * 操作时间
     */
	private Long operationTime;

	/**
	 * 设置：id
	 */
	public void setSubLogId(Integer subLogId) {
		this.subLogId = subLogId;
	}
	/**
	 * 获取：id
	 */
	public Integer getSubLogId() {
		return subLogId;
	}
	/**
	 * 设置：操作系统用户名
	 */
	public void setSysUserName(String sysUserName) {
		this.sysUserName = sysUserName;
	}
	/**
	 * 获取：操作系统用户名
	 */
	public String getSysUserName() {
		return sysUserName;
	}
	/**
	 * 设置：存储子链地址
	 */
	public void setSubchainAddress(String subchainAddress) {
		this.subchainAddress = subchainAddress;
	}
	/**
	 * 获取：存储子链地址
	 */
	public String getSubchainAddress() {
		return subchainAddress;
	}
	/**
	 * 设置：操作类型
	 */
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	/**
	 * 获取：操作类型
	 */
	public String getOperationType() {
		return operationType;
	}
	/**
	 * 设置：操作时间
	 */
	public void setOperationTime(Long operationTime) {
		this.operationTime = operationTime;
	}
	/**
	 * 获取：操作时间
	 */
	public Long getOperationTime() {
		return operationTime;
	}
}
