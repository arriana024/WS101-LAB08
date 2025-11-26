package com.alsolaarriana.Lab7.graphql;

import com.alsolaarriana.Lab7.Student;
import com.alsolaarriana.Lab7.StudentService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * GraphQL Controller (Resolver) for Student management.
 * This class implements the Queries and Mutations defined in the schema.graphqls
 * by calling your existing StudentService.
 */
@Controller
public class StudentGraphQLController {

    private final StudentService studentService;

    // Inject the existing StudentService
    public StudentGraphQLController(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * Implements the 'findAllStudents' query.
     * Maps to: type Query { findAllStudents: [Student!]! }
     */
    @QueryMapping
    public List<Student> findAllStudents() {
        // Calls the business logic in the Service layer
        return studentService.findAll();
    }

    /**
     * Implements the 'findStudentById' query.
     * Maps to: type Query { findStudentById(id: ID!): Student }
     */
    @QueryMapping
    public Student findStudentById(@Argument Long id) {
        // Note: GraphQL often handles the optional nature, but we check and return the result
        return studentService.findById(id).orElse(null);
    }

    /**
     * Implements the 'createStudent' mutation.
     * Maps to: type Mutation { createStudent(newStudent: StudentInput!): Student! }
     * The argument name 'newStudent' from the schema maps to the Java object argument.
     * We map the StudentInput (record/class) from GraphQL directly to the Student Model.
     */
    @MutationMapping
    public Student createStudent(@Argument Student newStudent) {
        // Simple validation check, similar to the REST controller
        if (newStudent.getEmail() == null || newStudent.getEmail().isEmpty()) {
            // Throwing an exception is the standard way to handle errors in GraphQL resolvers
            throw new IllegalArgumentException("Email is required for student registration.");
        }
        return studentService.save(newStudent);
    }

    /**
     * Implements the 'deleteStudent' mutation.
     * Maps to: type Mutation { deleteStudent(id: ID!): Boolean! }
     */
    @MutationMapping
    public Boolean deleteStudent(@Argument Long id) {
        return studentService.delete(id);
    }
}