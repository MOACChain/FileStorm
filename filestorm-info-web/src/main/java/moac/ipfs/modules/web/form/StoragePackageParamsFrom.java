package moac.ipfs.modules.web.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;


/**
 * @author: GZC
 * @create: 2018-11-27 10:28
 * @description: 用户套餐通用参数
 **/
@ApiModel(value = "用户套餐通用参数")
public class StoragePackageParamsFrom {

    @ApiModelProperty(value = "用户id")
    private long userId;

    @ApiModelProperty(value = "用户address")
    private String address;

    @ApiModelProperty(value = "用户密码")
    private String password;

    @ApiModelProperty(value = "状态(-2:处理中,-1:永久有效,0:正常,1:已过期但在一个月内,2:已过期,3:查询所有套餐)")
    private int status;

    @ApiModelProperty(value = "购买状态:0:处理中,1:购买成功.2:购买失败,3查询全部")
    private int buyStatus;

    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    @ApiModelProperty(value = "购买的月数")
    private Integer monthNumber;

    @ApiModelProperty(value = "套餐存储大小")
    private String storageSize;

    public Integer getMonthNumber() {
        return monthNumber;
    }

    public void setMonthNumber(Integer monthNumber) {
        this.monthNumber = monthNumber;
    }

    public String getStorageSize() {
        return storageSize;
    }

    public void setStorageSize(String storageSize) {
        this.storageSize = storageSize;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getBuyStatus() {
        return buyStatus;
    }

    public void setBuyStatus(int buyStatus) {
        this.buyStatus = buyStatus;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
