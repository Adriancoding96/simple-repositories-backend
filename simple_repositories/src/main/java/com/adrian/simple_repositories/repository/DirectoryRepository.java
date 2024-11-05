package com.adrian.simple_repositories.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

import com.adrian.simple_repositories.model.Directory;

public interface DirectoryRepository extends JpaRepository<Directory, Long> {

  @Query("SELECT subDirectory FROM Directory f JOIN f.directories subDirectory WHERE f.id = :directoryId AND subDirectory.id <> :directoryId")
  List<Directory> findAllSubDirectoriesByFolderIdExcludingSelf(@Param("directoryId") Long directoryId);
  
  @Query("SELECT f FROM Directory f WHERE f.project.id = :projectId")
  List<Directory> findAllDirectoriesByProjectId(@Param("projectId") Long projectId);

  @Query("SELECT f FROM Directory f WHERE f.parentDirectory.id = :parentDirectoryId")
  List<Directory> findNestedDirectoriesByParentFolderId(@Param("parentDirectoryId") Long parentDirectoryId);
  
  Optional<Directory> findDirectoryByUuid(String uuid);

  @Query("SELECT f FROM Directory f WHERE f.project.uuid = :uuid")
  List<Directory> findAllDirectoriesByProjectUuid(@Param("uuid") String uuid);

  void deleteByUuid(String uuid);

  boolean existsByUuid(String uuid);

}
