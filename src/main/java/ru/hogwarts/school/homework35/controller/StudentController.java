package ru.hogwarts.school.homework35.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.homework35.service.StudentService;
import ru.hogwarts.school.homework35.model.Faculty;
import ru.hogwarts.school.homework35.model.Student;
import java.util.List;
import java.util.Optional;

@RequestMapping("/student")
@RestController
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student newStudent = studentService.createStudent(student);
        return ResponseEntity.ok(newStudent);
    }

    @GetMapping("/find")
    public ResponseEntity<Student> getStudent(@RequestParam("id") Long id) {
        Optional<Student> optional = studentService.getStudent(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(optional.get());
    }

    @PutMapping
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        Student updatedStudent = studentService.updateStudent(student.getId(), student);
        return  ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/filter")
    public List<Student> getStudentsByAge(@RequestParam("age") int age) {
        if (age < 0) {
            System.out.println("Возраст не может быть меньше 0!");;
        }
        return studentService.getStudentsByAge(age);
    }

    @GetMapping
    public List<Student> findByAgeBetween(@RequestParam ("age1")int min, @RequestParam ("age2")int max) {
        if (min < 0 || max < 0) {
            System.out.println("Возраст не может быть меньше 0!");;
        }
        return studentService.findByAgeBetween(min, max);
    }

    @GetMapping("/{id}/faculty")
    public Faculty getFacultyById(@PathVariable Long id) {
        return studentService.getFacultyById(id);
    }
}
