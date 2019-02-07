package moac.ipfs.modules.back.user.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import java.util.Date;

/**
 * 
 * 
 * @author GZC
 * @email 57855143@qq.com
 * @date 2018-11-02 18:07:06
 */
@TableName(value = "tb_file_log")
public class FileLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
    /**
     * id
     */
    @TableId
	private Long logId;
    /**
     * 用户id
     */
	private Long userId;
    /**
     * 用户地址
     */
	private String userAddress;
    /**
     * 文件hash
     */
	private String fileHash;
    /**
     * 操作类型
     */
	private String operationType;
    /**
     * 操作时间
     */
	private Long operationTime;
    /**
     * 文件备注
     */
	private String fileRemark;

	/**
	 * 设置：id
	 */
	public void setLogId(Long logId) {
		this.logId = logId;
	}
	/**
	 * 获取：id
	 */
	public Long getLogId() {
		return logId;
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
	 * 设置：用户地址
	 */
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}
	/**
	 * 获取：用户地址
	 */
	public String getUserAddress() {
		return userAddress;
	}
	/**
	 * 设置：文件hash
	 */
	public void setFileHash(String fileHash) {
		this.fileHash = fileHash;
	}
	/**
	 * 获取：文件hash
	 */
	public String getFileHash() {
		return fileHash;
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
	/**
	 * 设置：文件备注
	 */
	public void setFileRemark(String fileRemark) {
		this.fileRemark = fileRemark;
	}
	/**
	 * 获取：文件备注
	 */
	public String getFileRemark() {
		return fileRemark;
	}
}
