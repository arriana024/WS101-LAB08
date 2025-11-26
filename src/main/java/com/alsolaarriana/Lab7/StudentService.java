package com.alsolaarriana.Lab7;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class StudentService {
    private final List<Student> students = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong();

    public StudentService() {
        // Mock Data
        save(new Student(null, "John", "Doe", "john@example.com", "IT"));
        save(new Student(null, "Jane", "Smith", "jane@example.com", "CS"));
    }

    public List<Student> findAll() {
        return students;
    }

    public Optional<Student> findById(Long id) {
        return students.stream().filter(s -> s.getId().equals(id)).findFirst();
    }

    public Student save(Student student) {
        student.setId(idCounter.incrementAndGet());
        students.add(student);
        return student;
    }

    public boolean delete(Long id) {
        return students.removeIf(s -> s.getId().equals(id));
    }

    // New Logic: Update existing student
    public boolean update(Long id, Student updatedInfo) {
        Optional<Student> existing = findById(id);
        if (existing.isPresent()) {
            Student s = existing.get();
            s.setFirstName(updatedInfo.getFirstName());
            s.setLastName(updatedInfo.getLastName());
            s.setEmail(updatedInfo.getEmail());
            s.setCourse(updatedInfo.getCourse());
            return true;
        }
        return false;
    }
}