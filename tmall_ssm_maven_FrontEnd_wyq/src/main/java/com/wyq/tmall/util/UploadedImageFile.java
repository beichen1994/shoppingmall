package com.wyq.tmall.util;
 
import org.springframework.web.multipart.MultipartFile;
 

// UploadedImageFile专门用来上传图片文件
public class UploadedImageFile {
    MultipartFile image;
  
    public MultipartFile getImage() {
        return image;
    }
  
    public void setImage(MultipartFile image) {
        this.image = image;
    }
  
}