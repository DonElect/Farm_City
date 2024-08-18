package com.farmCity.farm_city_be.repository;

import com.farmCity.farm_city_be.enums.Roles;
import com.farmCity.farm_city_be.models.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findUserEntityByEmail(String email);
    Optional<UserEntity> findFirstByRoles(Roles role);
    boolean existsByEmail(String email);
    List<UserEntity> findByRoles(Roles role);
    Slice<UserEntity> findAllByRoles(Roles roles, Pageable pageable);
    List<UserEntity> findAllByRoles(Roles role);
    long countDistinctByRoles(Roles roles);
}