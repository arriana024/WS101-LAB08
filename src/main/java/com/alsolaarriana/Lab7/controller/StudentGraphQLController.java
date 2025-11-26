package com.alsolaarriana.Lab7.controller;

import com.alsolaarriana.Lab7.model.Student;
import com.alsolaarriana.Lab7.model.StudentInput;
import com.alsolaarriana.Lab7.service.StudentService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

/**
 * GraphQL Controller (Data Fetcher) for Student entity.
 * This maps the fields defined in schema.graphqls to methods in the service layer.
 */
@Controller
public class StudentGraphQLController {

    private final StudentService studentService;

    public StudentGraphQLController(StudentService studentService) {
        this.studentService = studentService;
    }

    // --- Query Mappings (Read Operations) ---

    @QueryMapping
    public List<Student> allStudents() {
        // Maps to Query.allStudents
        return studentService.findAll();
    }

    @QueryMapping
    public Optional<Student> studentById(@Argument Long id) {
        // Maps to Query.studentById(id: ID!)
        return studentService.findById(id);
    }

    // --- Mutation Mappings (Write Operations) ---

    @MutationMapping
    public Student addStudent(@Argument StudentInput student) {
        // Maps to Mutation.addStudent(student: StudentInput!)
        // Convert the input DTO to the model object
        Student newStudent = new Student(null, student.getFirstName(), student.getLastName(), student.getEmail(), student.getCourse());
        return studentService.save(newStudent);
    }

    @MutationMapping
    public Student updateStudent(@Argument Long id, @Argument StudentInput student) {
        // Maps to Mutation.updateStudent(id: ID!, student: StudentUpdateInput!)

        // Since StudentInput is used for both create and update here (for simplicity),
        // we map the fields to a new Student model object to pass to the service.
        Student updatedStudent = new Student(id, student.getFirstName(), student.getLastName(), student.getEmail(), student.getCourse());

        if (studentService.update(id, updatedStudent)) {
            // Return the updated student (which exists if update returned true)
            return studentService.findById(id).orElse(null);
        }

        return null; // Return null if the student was not found/updated
    }

    @MutationMapping
    public Boolean deleteStudent(@Argument Long id) {
        // Maps to Mutation.deleteStudent(id: ID!)
        return studentService.delete(id);
    }
}