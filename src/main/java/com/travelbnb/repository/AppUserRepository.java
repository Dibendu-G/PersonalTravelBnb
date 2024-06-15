package com.travelbnb.repository;

import com.travelbnb.entity.AppUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUserEntity, Long> {

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Optional<AppUserEntity> findByUsername(String username);

}