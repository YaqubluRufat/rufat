package com.example.demo.Repository;

import com.example.demo.Entity.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

@org.springframework.stereotype.Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<Department> findByName(String name);

    @Query("SELECT COUNT(u) FROM Department d JOIN d.userList u WHERE d.id= :id")
    Long couldAllDepartments(Long id);
    Page<Department> findAllById(Long id, Pageable pageable);

}
