package com.example.demo.Repository;

import com.example.demo.Entity.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department,Long> {
    boolean existsByIdAndMarketUserId(Long departmentId, Long userId);
    @Query("SELECT d FROM Department d LEFT JOIN FETCH d.market WHERE d.id= :id")
    Optional<Department>findDepartmentById(@Param("id") Long id);
    @EntityGraph(attributePaths = "market")
    Page<Department> findAllBy(Pageable pageable);
}
