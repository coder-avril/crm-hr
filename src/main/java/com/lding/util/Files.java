package com.lding.util;

import com.lding.util.properties.UploadInfo;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class Files {
    public static String uploadFile(String oldPath, UploadInfo uploadInfo, MultipartFile photoFile) throws IOException {
        if (photoFile == null) return oldPath;
        String originalFileName = photoFile.getOriginalFilename();
        if (originalFileName == null || originalFileName.trim().equals("")) return oldPath;

        // 获取文件拓展名
        String extension = FilenameUtils.getExtension(photoFile.getOriginalFilename());
        // 获取完整文件名
        String fileName = UUID.randomUUID() + "." + extension;
        File file = new File(uploadInfo.getImageFullPath() + fileName);
        // 创建好目标文件所在的父目录
        FileUtils.forceMkdirParent(file);
        // 将文件数据写到目标文件
        photoFile.transferTo(file);

        return uploadInfo.getImagePath() + fileName;
    }
}
