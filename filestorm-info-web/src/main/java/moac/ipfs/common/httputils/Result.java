package moac.ipfs.common.httputils;

/**
 * @author: GZC
 * @create: 2018-11-05 14:50
 * @description:
 **/
public class Result {
    private Integer code;
    private String message;
    private String resultData;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResultData() {
        return resultData;
    }

    public void setResultData(String resultData) {
        this.resultData = resultData;
    }
}
