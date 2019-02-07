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
 * @date 2018-11-23 16:02:55
 */
@TableName(value = "key_store")
public class KeyStoreEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
    /**
     * id
     */
    @TableId
	private Long keyStoreId;
    /**
     * 用户id
     */
	private Long userId;
    /**
     * 存储路径
     */
	private String keyStorePath;
    /**
     * 创建时间
     */
	private Long createTime;

	/**
	 * 设置：id
	 */
	public void setKeyStoreId(Long keyStoreId) {
		this.keyStoreId = keyStoreId;
	}
	/**
	 * 获取：id
	 */
	public Long getKeyStoreId() {
		return keyStoreId;
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
	 * 设置：存储路径
	 */
	public void setKeyStorePath(String keyStorePath) {
		this.keyStorePath = keyStorePath;
	}
	/**
	 * 获取：存储路径
	 */
	public String getKeyStorePath() {
		return keyStorePath;
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
}
