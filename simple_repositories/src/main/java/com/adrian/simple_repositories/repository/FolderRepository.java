package com.adrian.simple_repositories.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

import com.adrian.simple_repositories.model.Folder;

public interface FolderRepository extends JpaRepository<Folder, Long> {

  @Query("SELECT subFolder FROM Folder f JOIN f.folders subFolder WHERE f.id = :folderId AND subFolder.id <> :folderId")
  List<Folder> findAllSubFoldersByFolderIdExcludingSelf(@Param("folderId") Long folderId);
  
  @Query("SELECT f FROM Folder f WHERE f.project.id = :projectId")
  List<Folder> findAllFoldersByProjectId(@Param("projectId") Long projectId);

  @Query("SELECT f FROM Folder f WHERE f.parentFolder.id = :parentFolderId")
  List<Folder> findNestedFoldersByParentFolderId(@Param("parentFolderId") Long parentFolderId);
  
  Optional<Folder> findFolderByUuid(String uuid);

  @Query("SELECT f FROM Folder f WHERE f.project.uuid = :uuid")
  List<Folder> findAllFoldersByProjectUuid(@Param("uuid") String uuid);

  void deleteByUuid(String uuid);

  boolean existsByUuid(String uuid);

}
