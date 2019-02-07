package moac.ipfs.modules.back.user.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * 
 * @author GZC
 * @email 57855143@qq.com
 * @date 2018-11-02 18:07:06
 */
@TableName(value = "tb_user_file")
public class UserFileEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
    /**
     * 主键id
     */
    @TableId
	private Long fileId;
    /**
     * 用户id
     */
	private Long userId;
    /**
     * 文件hash
     */
	private String hash;
    /**
     * 创建时间
     */
	private Long createTime;
    /**
     * 文件名字
     */
	private String fileName;
    /**
     * 文件格式化大小
     */
	private String fileSize;
    /**
     * 文件真实大小
     */
	private BigDecimal fileRealSize;
    /**
     * 子链合约地址
     */
	private String subchainAddress;
    /**
     * 备注信息
     */
	private String remark;
	/**
	 * 逻辑删除标记
	 */
	private Integer deleteType;
	/**
	 * 文件是否加密（0：不加密，1：加密）
	 */
	private Integer encrypt;

	public Integer getEncrypt() {
		return encrypt;
	}

	public void setEncrypt(Integer encrypt) {
		this.encrypt = encrypt;
	}

	public Integer getDeleteType() {
		return deleteType;
	}

	public void setDeleteType(Integer deleteType) {
		this.deleteType = deleteType;
	}

	/**
	 * 设置：主键id
	 */
	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}
	/**
	 * 获取：主键id
	 */
	public Long getFileId() {
		return fileId;
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
	 * 设置：文件hash
	 */
	public void setHash(String hash) {
		this.hash = hash;
	}
	/**
	 * 获取：文件hash
	 */
	public String getHash() {
		return hash;
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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * 设置：文件格式化大小
	 */
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	/**
	 * 获取：文件格式化大小
	 */
	public String getFileSize() {
		return fileSize;
	}

	public BigDecimal getFileRealSize() {
		return fileRealSize;
	}

	public void setFileRealSize(BigDecimal fileRealSize) {
		this.fileRealSize = fileRealSize;
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
	/**
	 * 设置：备注信息
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：备注信息
	 */
	public String getRemark() {
		return remark;
	}
}
