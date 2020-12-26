package com.dd.hubino.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.dd.hubino.test.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer>{
	


}
