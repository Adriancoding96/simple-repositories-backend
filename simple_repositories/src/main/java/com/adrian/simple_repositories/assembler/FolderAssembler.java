package com.adrian.simple_repositories.assembler;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.adrian.simple_repositories.dto.file.FileDTO;
import com.adrian.simple_repositories.dto.folder.FolderFullDTO;
import com.adrian.simple_repositories.model.Folder;
import com.adrian.simple_repositories.model.Project;
import com.adrian.simple_repositories.model.File;

@Component
public class FolderAssembler {

  private final FileAssembler fileAssembler;

  @Autowired
  public FolderAssembler(FileAssembler fileAssembler) {
    this.fileAssembler = fileAssembler;
  }

  public Folder assemble(FolderFullDTO dto, Project project, Folder parentFolder) {
    if(dto == null || project == null) return null; 
    
    Folder folder = new Folder();
    folder.setFolderName(dto.getFolderName());
    folder.setPath(dto.getPath());
    folder.setProject(project);
    folder.setParentFolder(parentFolder);

    List<File> files = new ArrayList<>();
    for(FileDTO fileDTO : dto.getFiles()) {
      File file = fileAssembler.assemble(fileDTO);
      file.setFolder(folder);
      files.add(file);
    }
    folder.setFiles(files);

    List<Folder> subFolders = new ArrayList<>();
    for(FolderFullDTO subFolderDTO : dto.getFolders()) {
      Folder subFolder = assemble(subFolderDTO, project, folder);
      subFolders.add(subFolder);
    }
    folder.setFolders(subFolders);

    return folder;
  }
}
