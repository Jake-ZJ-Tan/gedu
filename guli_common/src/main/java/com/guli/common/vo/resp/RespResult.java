package com.guli.common.vo.resp;

import com.guli.common.enumeration.resp.RespCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@ApiModel("全局统一返回结果")
@Data
public class RespResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("是否成功")
    private Boolean success;

    @ApiModelProperty("响应码")
    private int code;

    @ApiModelProperty("响应消息")
    private String message;

    @ApiModelProperty("响应数据")
    private Map<String, Object> data = new HashMap<>();

    private RespResult() {
    }

    public static RespResult ok() {
        RespResult result = new RespResult();
        result.setSuccess(true);
        result.setCode(RespCode.OK.getCode());
        result.setMessage("成功");
        return result;
    }

    public static RespResult fail() {
        RespResult result = new RespResult();
        result.setSuccess(false);
        result.setCode(RespCode.FAIL.getCode());
        result.setMessage("失败");
        return result;
    }

    public RespResult message(String message) {
        this.setMessage(message);
        return this;
    }

    public RespResult code(int code) {
        this.setCode(code);
        return this;
    }

    public RespResult data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    public RespResult data(Map<String, Object> map) {
        this.setData(map);
        return this;
    }

}
