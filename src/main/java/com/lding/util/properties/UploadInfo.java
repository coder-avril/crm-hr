package com.lding.util.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("upload-info")
@Component
@Data
public class UploadInfo {
    private String basePath;
    private String imagePath;
    // 获得完整图片的路径
    public String getImageFullPath() {
        return this.basePath + this.imagePath;
    }
}
