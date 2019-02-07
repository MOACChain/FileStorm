package moac.ipfs.common.httputils;
/**
 * @description: 响应对象
 * @author: GZC
 * @create: 2018-11-01 14:57
 **/
public class OkhttpResult<T> {
    private int status;
    private String result;
    private T resultObject;

    public OkhttpResult() {
    }

    public OkhttpResult(int status, String result, T resultObject) {
        this.status = status;
        this.result = result;
        this.resultObject = resultObject;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public T getResultObject() {
        return resultObject;
    }

    public void setResultObject(T resultObject) {
        this.resultObject = resultObject;
    }
}
