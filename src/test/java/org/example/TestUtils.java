package org.example;

import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class TestUtils {
  private TestUtils() {}

  public static InputStream inputStreamFromResources(String name) throws IOException {
    return Files.newInputStream(ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + name).toPath());
  }

  public static byte[] bytesFromResources(String name) throws IOException {
    return Files.readAllBytes(ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + name).toPath());
  }
}
