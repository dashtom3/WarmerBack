package com.warmlight.DAO;

import com.warmlight.model.FileEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/6/24.
 */
public interface FileDao {
    boolean saveFile(FileEntity file);
    List<FileEntity> getFileListByNews(Long newsId);
}
