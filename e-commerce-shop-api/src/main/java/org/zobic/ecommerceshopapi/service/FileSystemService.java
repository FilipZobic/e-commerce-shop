package org.zobic.ecommerceshopapi.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class FileSystemService {

  public static final String MIME_IMAGE_PNG = "image/png";
  public static final String MIME_IMAGE_JPEG = "image/jpeg"; // jpg & jpeg

  public static Set<String> ACCEPTED_FILES = new HashSet<>();

  private final Path ROOT_DIRECTORY = Paths.get("product-images/");

  static {
    ACCEPTED_FILES.add("png");
    ACCEPTED_FILES.add("jpg");
    ACCEPTED_FILES.add("jpeg");
  }

  @SneakyThrows
  @PostConstruct
  void postConstruct() {
    if (!Files.exists(ROOT_DIRECTORY)) {

      Files.createDirectory(ROOT_DIRECTORY);
    }
  }
  // add file type checking only allow images
  public byte[] getFile(String fileName) {
    try {
      Path file = generateFilePath(fileName);
      Resource resource = new UrlResource(file.toUri());

      if (resource.exists() || resource.isReadable()) {
        return Base64.getEncoder().encode(resource.getInputStream().readAllBytes());
      } else {
        log.warn("Failed to read file");
        throw new RuntimeException("Could not read the file!");
      }
    } catch (IOException e) {
      log.warn("Something went wrong while reading the file");
      throw new RuntimeException("Error: " + e.getMessage());
    }
  }

  public String getFile(Path file) {
    try {
      Resource resource = new UrlResource(file.toUri());

      if (resource.exists() || resource.isReadable()) {
        return Base64.getEncoder().encodeToString(resource.getInputStream().readAllBytes());
      } else {
        log.warn("Failed to read file");
        throw new RuntimeException("Could not read the file!");
      }
    } catch (IOException e) {
      log.warn("Something went wrong while reading the file");
      throw new RuntimeException("Error: " + e.getMessage());
    }
  }

  public String saveFile(String base64, String fileName) throws IOException {
    byte[] fileBytes = Base64.getDecoder().decode(base64);
    InputStream targetStream = new ByteArrayInputStream(fileBytes);

    String mimeType = URLConnection.guessContentTypeFromStream(targetStream);

//    String mimetype= new MimetypesFileTypeMap().getContentType(f);
    String[] format = mimeType.split("/");

    if (!format[0].equals("image") || !ACCEPTED_FILES.contains(format[1])) {
      log.warn("Bad file format");
      throw new RuntimeException("File format is not support make sure to send image in png, jpg, jpeg format");
    }

    Path path = generateFilePath(fileName);
    System.out.println(path.toString());
    Files.copy(targetStream, path);
    return fileName;
  }

  public void deleteAll() {
    log.warn("Deleting all files");
    FileSystemUtils.deleteRecursively(ROOT_DIRECTORY.toFile());
  }

  public void deleteFile(String fileName) {
    try {
      Path path = generateFilePath(fileName);
      FileSystemUtils.deleteRecursively(path);
    } catch (IOException e) {
      log.warn("Failed to delete file, file not found");
    }
  }

  public Path generateFilePath(String fileName) {
    return this.ROOT_DIRECTORY.resolve(fileName);
  }
}
