package com.alsolaarriana.Lab7;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // GET all students
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.findAll());
    }

    // GET student by ID
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        return studentService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST create student (With Validation)
    @PostMapping
    public ResponseEntity<?> createStudent(@RequestBody Student student) {
        if (student.getEmail() == null || student.getEmail().isEmpty()) {
            return ResponseEntity.badRequest().body("Email is required.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.save(student));
    }

    // PUT update student
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        if (studentService.update(id, student)) {
            return ResponseEntity.ok(studentService.findById(id).get());
        }
        return ResponseEntity.notFound().build();
    }

    // DELETE student
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        if (studentService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}