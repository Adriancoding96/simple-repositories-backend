package com.adrian.simple_repositories.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.adrian.simple_repositories.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {

  @Query("SELECT p FROM Project p "
         + "LEFT JOIN FETCH p.folders f "
         + "LEFT JOIN FETCH f.files "
         + "LEFT JOIN FETCH f.folders subfolders "
         + "WHERE p.id = :projectId")
  Optional<Project> findByIdWithFoldersAndFiles(@Param("projectId") Long projectId);

}
