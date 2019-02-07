package moac.ipfs.modules.web.form;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author: GZC
 * @create: 2018-11-02 18:15
 * @description: 查询文件列表
 **/
@ApiModel(value = "查询文件列表")
public class QueryFileListForm {

    @ApiModelProperty(value = "用户id")
    @NotBlank(message="用户id不能为空")
    private Long userId;

    @ApiModelProperty(value = "文件hash")
    private String hash;

    @ApiModelProperty(value = "文件名")
    private String fileName;

    @ApiModelProperty(value = "页码")
    @NotBlank(message="页码不能为空")
    private Integer page;

    @ApiModelProperty(value = "每页条数")
    @NotBlank(message="每页条数不能为空")
    private Integer limit;

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
