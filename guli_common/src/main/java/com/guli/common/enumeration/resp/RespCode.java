package com.guli.common.enumeration.resp;

public enum RespCode {

    OK(20000), // 成功
    FAIL(40000), // 失败
    SQL_ERROR(40001), // SQL异常
    FILE_UPLOAD_ERROR(40002); // 文件上传异常

    private int code;

    RespCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
