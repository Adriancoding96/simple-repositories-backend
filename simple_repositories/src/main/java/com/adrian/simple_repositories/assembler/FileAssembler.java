package com.adrian.simple_repositories.assembler;

import org.springframework.stereotype.Component;

import com.adrian.simple_repositories.dto.file.FileDTO;
import com.adrian.simple_repositories.model.File;

@Component
public class FileAssembler {

  public File assemble(FileDTO dto) {
    if(dto == null) return null;

    File file = new File();
    file.setFileName(dto.getFileName());
    file.setExtension(dto.getExtension());
    file.setPath(dto.getPath());
    file.setContent(dto.getContent());
    return file;
  }
}
