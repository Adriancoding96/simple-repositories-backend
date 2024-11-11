package com.adrian.simple_repositories.assembler;

import org.springframework.stereotype.Component;

import com.adrian.simple_repositories.dto.file.FileDTO;
import com.adrian.simple_repositories.model.File;


/*
 * FileAssembler serves as the only stage in the assembly chain for creating file entitys,
 * and the final stage for directories and repos 
 */
@Component
public class FileAssembler {

  /*
   * Assembles a file entity based on data in file dto
   *
   * @param dto: DTO containing file data
   * @return file: returns assembled file entity
   */
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
