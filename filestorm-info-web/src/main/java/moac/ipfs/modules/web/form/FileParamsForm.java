package moac.ipfs.modules.web.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author: GZC
 * @create: 2018-11-02 17:22
 * @description: hash查看文件
 **/
@ApiModel(value = "hash查看文件")
public class FileParamsForm {

    @ApiModelProperty(value = "套餐id")
    @NotBlank(message="请选择套餐！")
    private long storagePackageId;

    @ApiModelProperty(value = "用户地址")
    @NotBlank(message="用户地址不能为空")
    private String address;

    @ApiModelProperty(value = "文件hash")
    private String hash;

    @ApiModelProperty(value = "文件大小")
    private String fileRealSize;

    @ApiModelProperty(value = "GO文件路径")
    private String filePath;

    @ApiModelProperty(value = "文件备注信息")
    private String remark;

    @ApiModelProperty(value = "文件名")
    private String fileName;

    @ApiModelProperty(value = "文件是否加密（0：不加密，1：加密）")
    private Integer encrypt;

    public long getStoragePackageId() {
        return storagePackageId;
    }

    public void setStoragePackageId(long storagePackageId) {
        this.storagePackageId = storagePackageId;
    }

    public Integer getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(Integer encrypt) {
        this.encrypt = encrypt;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileRealSize() {
        return fileRealSize;
    }

    public void setFileRealSize(String fileRealSize) {
        this.fileRealSize = fileRealSize;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
