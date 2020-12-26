package com.dd.hubino.test.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dd.hubino.test.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	
    @Query(value = "select u.id,u.name,u.email,u.password,u.role_id,u.department_id from user u where u.email = :email and u.password = :password" , nativeQuery = true)
    List<User> findALL(@Param("email") String email, @Param("password") String password);


}
