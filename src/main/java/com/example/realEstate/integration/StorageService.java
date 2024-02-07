package com.example.realEstate.integration;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

    void init();

    void save(MultipartFile file);

    Stream<Path> loadAll();

    Resource load(String filename);

    void deleteAll();

}