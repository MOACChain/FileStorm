package moac.ipfs.modules.back.currency.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import java.util.Date;

/**
 * 
 * 
 * @author GZC
 * @email 57855143@qq.com
 * @date 2018-11-21 15:55:03
 */
@TableName(value = "tb_currency")
public class CurrencyEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
    /**
     * id
     */
    @TableId
	private Long currencyId;
    /**
     * 币种编号
     */
	private String number;
    /**
     * 币种系列
     */
	private String series;
    /**
     * 币种英文名
     */
	private String englishName;
    /**
     * 总发行量
     */
	private String totalSupply;
    /**
     * 币种精度
     */
	private String decimals;
    /**
     * 合约地址
     */
	private String contractAddress;

	/**
	 * 设置：id
	 */
	public void setCurrencyId(Long currencyId) {
		this.currencyId = currencyId;
	}
	/**
	 * 获取：id
	 */
	public Long getCurrencyId() {
		return currencyId;
	}
	/**
	 * 设置：币种编号
	 */
	public void setNumber(String number) {
		this.number = number;
	}
	/**
	 * 获取：币种编号
	 */
	public String getNumber() {
		return number;
	}
	/**
	 * 设置：币种系列
	 */
	public void setSeries(String series) {
		this.series = series;
	}
	/**
	 * 获取：币种系列
	 */
	public String getSeries() {
		return series;
	}
	/**
	 * 设置：币种英文名
	 */
	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	/**
	 * 获取：币种英文名
	 */
	public String getEnglishName() {
		return englishName;
	}
	/**
	 * 设置：总发行量
	 */
	public void setTotalSupply(String totalSupply) {
		this.totalSupply = totalSupply;
	}
	/**
	 * 获取：总发行量
	 */
	public String getTotalSupply() {
		return totalSupply;
	}
	/**
	 * 设置：币种精度
	 */
	public void setDecimals(String decimals) {
		this.decimals = decimals;
	}
	/**
	 * 获取：币种精度
	 */
	public String getDecimals() {
		return decimals;
	}
	/**
	 * 设置：合约地址
	 */
	public void setContractAddress(String contractAddress) {
		this.contractAddress = contractAddress;
	}
	/**
	 * 获取：合约地址
	 */
	public String getContractAddress() {
		return contractAddress;
	}
}
