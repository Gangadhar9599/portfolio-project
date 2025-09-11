package com.gangadhar.portfolio.repository;

import com.gangadhar.portfolio.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {}