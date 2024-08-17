package com.asdsoft.mavala.web;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FolderToZip {
    public static void main(String[] args) throws IOException {
        File folder = new File("/Users/ameyd/Documents/Testzip"); // Folder path
        System.out.println(zipB64(folder.listFiles()));
    }

    private static String zipB64(File[] files) throws IOException {
        byte[] buffer = new byte[1024];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ZipOutputStream zos = new ZipOutputStream(baos)) {
            for (File f : files) {
                try (FileInputStream fis = new FileInputStream(f)) {
                    zos.putNextEntry(new ZipEntry(f.getName()));
                    int length;
                    while ((length = fis.read(buffer)) > 0) {
                        zos.write(buffer, 0, length);
                    }
                    zos.closeEntry();
                }
            }
        }
        byte[] bytes = baos.toByteArray();
        return Base64.getEncoder().encodeToString(bytes);
    }
}
