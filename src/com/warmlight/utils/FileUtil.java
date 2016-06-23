package com.warmlight.utils;

import com.warmlight.enums.ErrorCodeEnum;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Administrator on 2016/6/22.
 */
public class FileUtil {
    public static boolean saveFile(String filePath,String newFileName,MultipartFile file) {

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
            return false;
        }
        return true;
    }

    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        try {
            file.delete();
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
