package com.phoen17x.mytopmovies.repository;

import com.phoen17x.mytopmovies.models.ERole;
import com.phoen17x.mytopmovies.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
