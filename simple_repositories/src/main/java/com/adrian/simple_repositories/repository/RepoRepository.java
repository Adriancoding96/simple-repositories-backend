package com.adrian.simple_repositories.repository;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.adrian.simple_repositories.model.Repo;
import com.adrian.simple_repositories.model.User;

public interface RepoRepository extends JpaRepository<Repo, Long> {
  @Query("SELECT r FROM Repo r "
         + "LEFT JOIN FETCH r.directories f "
         + "LEFT JOIN FETCH f.files "
         + "LEFT JOIN FETCH f.directories subdirectories "
         + "WHERE r.id = :repoId")
  Optional<Repo> findByIdWithDirectoriesAndFiles(@Param("repoId") Long repoId);

  List<Repo> findAllByUser(User user);

  List<Repo> findAllByUserId(Long id);

  List<Repo> findAllByUserEmail(String email);

  Optional<Repo> findByUuid(String uuid); 

  @Query("SELECT r FROM Repo r WHERE r.uuid = :uuid AND r.user.email = :email")
  Optional<Repo> findRepoByUuidAndUserEmail(@Param("uuid") String uuid, @Param("email") String email);

  @Query("SELECT r FROM Repo r WHERE r.repoName = :repoName AND r.user.email = :email")
  Optional<Repo> findRepoByRepoNameAndUserEmail(@Param("repoName") String repoName, @Param("email") String email);

  void deleteByUuid(String uuid);

  boolean existsByUuid(String uuid);
}
