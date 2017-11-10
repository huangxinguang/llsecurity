package com.laile.security.admin.controller;

import com.laile.esf.common.exception.ResultCode;
import com.laile.esf.common.exception.SystemException;
import com.laile.security.core.model.auth.admin.Admin;
import com.laile.security.dsf.dto.FileDto;
import com.laile.security.dsf.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by huangxinguang on 2017/9/29 上午11:26.
 */
public abstract class AbstractController extends BaseController {

    @Autowired
    protected IFileService fileService;

    protected String uploadFile(Admin admin, MultipartFile file) {
        FileDto fileDto = new FileDto();
        fileDto.setFileSize(file.getSize());
        fileDto.setFileName(file.getOriginalFilename());
        fileDto.setAccount(admin.getAdminName());
        try {
            fileDto.setInputStream(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            throw new SystemException(ResultCode.COMMON_ERROR, "文件上传失败！");
        }
        return fileService.add(fileDto);
    }
}
