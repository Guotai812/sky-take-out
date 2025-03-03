package com.sky.utils;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

@Data
@AllArgsConstructor
@Slf4j
@Builder
public class UpLoadUtil {
    private String uploadPath;
    private String hostPath;

    public String upload(MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        String fileName = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf(".") );
        File file = new File(uploadPath, fileName);
        try {
            InputStream inputStream = multipartFile.getInputStream();

            FileOutputStream outputStream = new FileOutputStream(file);

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            return hostPath + fileName;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
