package com.warmlight.serviceImpl;

import com.warmlight.enums.ErrorCodeEnum;
import com.warmlight.service.FileService;
import com.warmlight.utils.DataWrapper;
import com.warmlight.utils.MD5Util;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.UUID;


/**
 * Created by Administrator on 2016/6/22.
 */
@Service("fileService")
public class FileServiceImpl implements FileService {
    @Override
    public DataWrapper<Void> uploadFile(HttpServletRequest request, MultipartFile file) {
        DataWrapper<Void> retDataWrapper = new DataWrapper<Void>();
        String filePath = request.getSession().getServletContext().getRealPath("/") + "upload";
        String newFileName = MD5Util.getMD5String(file.getOriginalFilename() + new Date() + UUID.randomUUID().toString()).replace(".","")
                    + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));

        File fileDir = new File(filePath);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        try {
            FileOutputStream out = new FileOutputStream(filePath + "\\"
                    + newFileName);
                // 写入文件
            out.write(file.getBytes());
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
            retDataWrapper.setErrorCode(ErrorCodeEnum.Error);
        }
        return retDataWrapper;
    }
}
