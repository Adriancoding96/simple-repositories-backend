package com.adrian.simple_repositories.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.adrian.simple_repositories.model.File;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FileRepository extends JpaRepository<File, Long> {
    
  @Query("SELECT f FROM File f WHERE f.directory.id = :directoryId")
  Optional<List<File>> findAllByDirectoryId(@Param("directoryId") Long directoryId);

  Optional<File> findByUuid(String uuid);

  @Query("SELECT f FROM File f WHERE f.directory.project.uuid = :uuid")
  List<File> findAllByProjectUuid(@Param("uuid") String uuid);

  void deleteByUuid(String uuid);

  boolean existsByUuid(String uuid); 
}


