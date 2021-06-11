package ink.lch.config.exception;

import ink.lch.common.CodeEnum;
import ink.lch.common.ResultBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常捕获类
 *
 * @author huaisun@github.com
 * @date 2020/12/2
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理自定义的业务异常
     *
     * @param e 异常
     * @return 返回异常
     */
    @ExceptionHandler(value = BizException.class)
    public ResultBody bizExceptionHandler(BizException e) {
        log.error("发生业务异常！原因是：{}", e.getErrorMsg());
        return ResultBody.error(e.getErrorCode(), e.getErrorMsg());
    }


    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResultBody validationException(HttpMessageNotReadableException e) {
        log.error("数据为空！原因是：", e);
        return ResultBody.error(CodeEnum.NOT_EMPTY);
    }

    /**
     * 处理空指针的异常
     *
     * @param e 异常
     * @return 异常内容
     */
    @ExceptionHandler(value = NullPointerException.class)
    public ResultBody exceptionHandler(NullPointerException e) {
        log.error("发生空指针异常！原因是:", e);
        return ResultBody.error(CodeEnum.INTERNAL_SERVER_ERROR.name());
    }


    /**
     * 处理其他异常
     *
     * @param e 异常
     * @return 异常内容
     */
    @ExceptionHandler(value = Exception.class)
    public ResultBody exceptionHandler(Exception e) {
        log.error("未知异常！原因是:", e);
        return ResultBody.error(CodeEnum.INTERNAL_SERVER_ERROR);
    }
}