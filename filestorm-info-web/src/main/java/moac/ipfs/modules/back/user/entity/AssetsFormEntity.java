package moac.ipfs.modules.back.user.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * @author: GZC
 * @create: 2018-11-22 11:25
 * @description: 资产参数列表
 **/
@ApiModel(value = "WEB资产参数列表")
public class AssetsFormEntity {
    @ApiModelProperty(value = "用户地址")
    private String address;

    @ApiModelProperty(value = "用户密码")
    private String password;

    @ApiModelProperty(value = "金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "币种编号")
    private String currencyNumber;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCurrencyNumber() {
        return currencyNumber;
    }

    public void setCurrencyNumber(String currencyNumber) {
        this.currencyNumber = currencyNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
