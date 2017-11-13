package com.laile.security.admin.controller;

import com.laile.esf.common.exception.ResultCode;
import com.laile.esf.common.exception.SystemException;
import com.laile.security.admin.constant.WebConstant;
import com.laile.security.admin.util.Jurisdiction;
import com.laile.security.core.model.auth.admin.Admin;
import com.laile.security.dsf.dto.FileDto;
import com.laile.security.dsf.service.IFileService;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

public class BaseController {
    protected static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    protected IFileService fileService;
    /**
     * 得到ModelAndView
     *
     * @return
     */
    public ModelAndView getModelAndView() {
        return new ModelAndView();
    }

    /**
     * 得到request对象
     *
     * @return
     */
    public HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        return request;
    }

    /**
     * 得到response对象
     *
     * @return
     */
    public HttpServletResponse getResponse() {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getResponse();
        return response;
    }

    /**
     * 获取out输出流
     *
     * @return
     */
    public Writer getWriter() {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getResponse();
        Writer writer = null;
        try {
            writer = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return writer;
    }

    public Admin getSessionUser() {
        return (Admin) getSession().getAttribute(WebConstant.SESSION_USER);
    }


    public Session getSession() {
        return Jurisdiction.getSession();
    }



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
