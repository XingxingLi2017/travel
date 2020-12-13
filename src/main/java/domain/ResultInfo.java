package domain;

import java.io.Serializable;

/**
 * Result information for frontend
 */
public class ResultInfo implements Serializable {
    private boolean flag;   // true = success , false = request failed
    private Object data;        // result data
    private String errorMsg;       // error message

    public ResultInfo() {
    }
    public ResultInfo(boolean flag) {
        this.flag = flag;
    }
    /**
     * @param flag
     * @param errorMsg
     */
    public ResultInfo(boolean flag, String errorMsg) {
        this.flag = flag;
        this.errorMsg = errorMsg;
    }
    /**
     * @param flag
     * @param data
     * @param errorMsg
     */
    public ResultInfo(boolean flag, Object data, String errorMsg) {
        this.flag = flag;
        this.data = data;
        this.errorMsg = errorMsg;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
