package com.guli.common.exception;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "全局异常模型类", description = "用于封装异常响应码和消息")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EduException extends RuntimeException {

    @ApiModelProperty("异常响应码")
    private int code;

    @ApiModelProperty("异常响应消息")
    private String message;

    @Override
    public String toString() {
        return "EduException{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
