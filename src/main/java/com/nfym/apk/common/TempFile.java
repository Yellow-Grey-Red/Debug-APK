package com.nfym.apk.common;

import com.nfym.apk.pages.apk.Utils.APKSigner;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

public class TempFile {




    public static String extractResourceToTempFile(String resourcePath) {
        // 1. 获取Jar内资源的输入流
        InputStream resourceStream = TempFile.class.getResourceAsStream(resourcePath);
        if (resourceStream == null) {
            return null;
        }

        File tempFile = null;
        try {
            String suffix = resourcePath.substring(resourcePath.lastIndexOf("."));
            tempFile = File.createTempFile("temp-resource-", suffix);
            tempFile.deleteOnExit();
            // 3. 将资源流写入临时文件
            try (OutputStream out = Files.newOutputStream(tempFile.toPath())) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = resourceStream.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }

            return tempFile.getAbsolutePath();

        } catch (IOException e) {
            return null;
        }finally {
            try {
                resourceStream.close();
            }catch (Exception ignored){

            }
        }
    }
}