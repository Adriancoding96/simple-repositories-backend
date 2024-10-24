package com.adrian.simple_repositories.util;

import org.springframework.stereotype.Component;

@Component
public class FilePathUtil {

  public String setNewFilePath(String filePath, String name) {
    if(filePath == null || name == null) {
      throw new IllegalArgumentException("filepath or file/folder name is null"); //TODO Think of better error text
    }
    int lastIndexOfSlash = filePath.lastIndexOf('/');
    if(lastIndexOfSlash == -1) return name; //If no slash is found entire filepath is name
    if(lastIndexOfSlash == filePath.length() - 1) filePath = filePath.concat(name); //Path ends with a /. append name
    if(lastIndexOfSlash < filePath.length() -1) filePath = replaceName(filePath, name, lastIndexOfSlash);
    return filePath;
  }

  private String replaceName(String filePath, String name, int lastSlashIndex) {
    String directoryPath = filePath.substring(0, lastSlashIndex + 1);
    return directoryPath.concat(name);
  }
}
