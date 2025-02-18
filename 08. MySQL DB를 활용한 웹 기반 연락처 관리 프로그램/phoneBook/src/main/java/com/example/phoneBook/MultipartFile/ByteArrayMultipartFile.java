package com.example.phoneBook.MultipartFile;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class ByteArrayMultipartFile implements MultipartFile {

    private String name;
    private byte[] content;

    public ByteArrayMultipartFile(String name, byte[] content) {
        this.name = name;
        this.content = content;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getOriginalFilename() {
        return name; // You can modify this to include a file extension if needed
    }

    @Override
    public String getContentType() {
        return "application/octet-stream"; // Default, modify based on your needs
    }

    @Override
    public boolean isEmpty() {
        return content == null || content.length == 0;
    }

    @Override
    public long getSize() {
        return content.length;
    }

    @Override
    public byte[] getBytes() {
        return content;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(content);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        try (OutputStream os = new FileOutputStream(dest)) {
            os.write(content);
        }
    }
}
