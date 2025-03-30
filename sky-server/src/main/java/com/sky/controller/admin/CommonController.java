package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOSSUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @version V1.0
 * @Title:
 * @Description:
 * @Copyright 2024 Cuiyq
 * @author: Cuiyq
 * @date: 2025/3/30 08:31
 */
@ApiOperation("通用接口")
@RestController
@RequestMapping("/admin/common")
@Slf4j
public class CommonController {

    @Resource
    private AliOSSUtils aliOSSUtils;

    @ApiOperation("文件上传接口")
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file)  {
        log.info("开始文件上传接口");
        try
            {
                String upload = aliOSSUtils.upload(file);
                return Result.success(upload);
            }
        catch (IOException e)
            {
                e.printStackTrace();
            }
        return Result.error(MessageConstant.UPLOAD_FAILED);
    }
}
