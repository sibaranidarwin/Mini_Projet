package com.example.mini_project.util;

import org.springframework.core.io.InputStreamResource;

import java.io.IOException;
import java.io.InputStream;

public class Multipartinputstreamfileresource extends InputStreamResource {

    private final String filename;

    public Multipartinputstreamfileresource(InputStream inputStream, String filename) {
        super(inputStream);
        this.filename = filename;
    }

    @Override
    public String getFilename() {
        return this.filename;
    }

    @Override
    public long contentLength() throws IOException {
        return -1; // Tidak bisa dihitung tanpa membaca seluruh stream
    }
}

