package com.guli.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.guli.common.enumeration.resp.RespCode;
import com.guli.common.exception.EduException;
import com.guli.oss.conf.OssConf;
import com.guli.oss.service.OssService;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Slf4j
@Service
public class OssServiceImpl implements OssService {
    @Override
    public String fileUpload(MultipartFile file) {

        OSS ossClient = null;
        try {
            // 创建OSSClient实例。
            ossClient = new OSSClientBuilder().build(OssConf.END_POINT, OssConf.ACCESS_KEY_ID, OssConf.ACCESS_KEY_SECRET);

            String randomFilename = UUID.randomUUID().toString() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")); // 上传到云服务的随机文件名
            String date = new DateTime().toString("yyyy/MM/dd"); // 日期文件夹
            String path = OssConf.FILE_HOST + "/" + date + "/" + randomFilename;

            // 填写本地文件的完整路径。如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
            // 依次填写Bucket名称（例如examplebucket）和Object完整路径（例如exampledir/exampleobject.txt）。Object完整路径中不能包含Bucket名称。
            ossClient.putObject(OssConf.BUCKET_NAME, path, file.getInputStream());

            // https://scw20211003.oss-cn-guangzhou.aliyuncs.com/pic/63e2614ccf0745c59a16e5885c665241_p3.jpg
            String url = "https://" + OssConf.BUCKET_NAME + "." + OssConf.END_POINT + "/" + path;

            return url;

        } catch (Exception e) {
            e.printStackTrace();
            throw new EduException(RespCode.FILE_UPLOAD_ERROR.getCode(), "文件上传失败");
        } finally {
            // 关闭OSSClient
            if (ossClient != null)
                ossClient.shutdown();
        }

    }
}
