package com.adrian.simple_repositories.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.adrian.simple_repositories.model.File;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FileRepository extends JpaRepository<File, Long> {
    
    @Query("SELECT f FROM File f WHERE f.folder.id = :folderId")
    Optional<List<File>> findAllByFolderId(@Param("folderId") Long folderId);

}


