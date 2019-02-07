package moac.ipfs.modules.back.gain.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import java.util.Date;

/**
 * 
 * 
 * @author GZC
 * @email 57855143@qq.com
 * @date 2018-11-21 16:33:43
 */
@TableName(value = "tb_block_height")
public class BlockHeightEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
    /**
     * id
     */
	@TableId
	private Long blockHeightId;
    /**
     * 币种名称
     */
	private String name;
    /**
     * 块高
     */
	private Integer height;

	/**
	 * 设置：id
	 */
	public void setBlockHeightId(Long blockHeightId) {
		this.blockHeightId = blockHeightId;
	}
	/**
	 * 获取：id
	 */
	public Long getBlockHeightId() {
		return blockHeightId;
	}
	/**
	 * 设置：币种名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：币种名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：块高
	 */
	public void setHeight(Integer height) {
		this.height = height;
	}
	/**
	 * 获取：块高
	 */
	public Integer getHeight() {
		return height;
	}
}
