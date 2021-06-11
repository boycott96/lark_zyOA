package ink.lch.common;

import lombok.Data;

@Data
public class ResultBody {

    private String code;

    private String message;

    private Object result;

    public ResultBody() {
        this.code = CodeEnum.SUCCESS.getResultCode();
        this.message = CodeEnum.SUCCESS.getResultMsg();
    }

    public void setErrorCode() {
        this.code = CodeEnum.SERVER_ERROR.getResultCode();
        this.message = CodeEnum.SERVER_ERROR.getResultMsg();
    }


    public static ResultBody success() {
        return success(null);
    }

    public static ResultBody success(Object data) {
        ResultBody rb = new ResultBody();
        rb.setCode(CodeEnum.SUCCESS.getResultCode());
        rb.setMessage(CodeEnum.SUCCESS.getResultMsg());
        rb.setResult(data);
        return rb;
    }

    public static ResultBody error(BaseErrorInfoInterface errorInfo) {
        ResultBody rb = new ResultBody();
        rb.setCode(errorInfo.getResultCode());
        rb.setMessage(errorInfo.getResultMsg());
        rb.setResult(null);
        return rb;
    }

    public static ResultBody error(String code, String message) {
        ResultBody rb = new ResultBody();
        rb.setCode(code);
        rb.setMessage(message);
        rb.setResult(null);
        return rb;
    }

    /**
     * 失败
     */
    public static ResultBody error(String message) {
        ResultBody rb = new ResultBody();
        rb.setCode("-1");
        rb.setMessage(message);
        rb.setResult(null);
        return rb;
    }
}
