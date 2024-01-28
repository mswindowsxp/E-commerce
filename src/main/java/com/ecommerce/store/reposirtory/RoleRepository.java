package com.ecommerce.store.reposirtory;

import com.ecommerce.store.common.RoleConstant;
import com.ecommerce.store.model.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleConstant roleName);
}
