package com.todo.todo_backend.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todo.todo_backend.auth.model.dto.entity.Certification;

public interface CertificationRepository extends JpaRepository<Certification, String> {

}
