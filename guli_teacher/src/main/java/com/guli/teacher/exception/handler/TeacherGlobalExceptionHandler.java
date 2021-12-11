package com.guli.teacher.exception.handler;

import com.guli.common.enumeration.resp.RespCode;
import com.guli.common.exception.EduException;
import com.guli.common.util.ExceptionUtil;
import com.guli.common.vo.resp.RespResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class TeacherGlobalExceptionHandler {

    @ExceptionHandler(EduException.class)
    public RespResult error(EduException e) {
        e.printStackTrace();
        log.error(ExceptionUtil.getMessage(e));
        return RespResult.fail().code(e.getCode()).message(e.getMessage());
    }

    @ExceptionHandler(BadSqlGrammarException.class)
    public RespResult error(BadSqlGrammarException e) {
        e.printStackTrace();
        return RespResult.fail().code(RespCode.SQL_ERROR.getCode()).message("SQL语法错误");
    }

    @ExceptionHandler(Exception.class)
    public RespResult error(Exception e) {
        e.printStackTrace();
//        log.error(e.getMessage());
        log.error(ExceptionUtil.getMessage(e));
        return RespResult.fail().message("出错了");
    }

}
