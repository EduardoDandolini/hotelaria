package com.eduardo.hotelaria.repository;

import com.eduardo.hotelaria.entity.Reserve;
import com.eduardo.hotelaria.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("SELECT r " +
            "FROM tb_roles r " +
            "where r.name = :name")
    Role findByName(@Param("name") String name);
}
