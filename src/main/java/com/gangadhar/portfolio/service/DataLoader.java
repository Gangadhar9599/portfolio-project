package com.gangadhar.portfolio.service;

import com.gangadhar.portfolio.model.Project;
import com.gangadhar.portfolio.repository.ProjectRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
    private final ProjectRepository projectRepository;

    public DataLoader(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (projectRepository.count() == 0) {
            Project p1 = new Project();
            p1.setTitle("Photography Website");
            p1.setDescription("A dynamic website with user registration, login, and a secure booking system.");
            projectRepository.save(p1);

            Project p2 = new Project();
            p2.setTitle("E-Commerce Platform");
            p2.setDescription("A robust backend for an e-commerce site using Java and Spring Boot.");
            projectRepository.save(p2);
        }
    }
}