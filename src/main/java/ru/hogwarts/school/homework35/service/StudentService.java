package ru.hogwarts.school.homework35.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.homework35.repository.StudentRepository;
import ru.hogwarts.school.homework35.model.Faculty;
import ru.hogwarts.school.homework35.model.Student;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student){
        return studentRepository.save(student);
    }

    public Optional<Student> getStudent(long id) {
        return studentRepository.findStudentById(id);
    }

    public Student updateStudent(long id, Student student) {
        Optional<Student> optional = studentRepository.findStudentById(id);
        if (optional.isPresent()) {
            Student studentFromDb = optional.get();
            studentFromDb.setAge(student.getAge());
            studentFromDb.setName(student.getName());
            return studentRepository.save(studentFromDb);
        } else {
            return null;
        }
    }

    public void deleteStudent(long id) {
        studentRepository.deleteById(id);
    }

    public List<Student> getStudentsByAge(int age) {
        return studentRepository.getStudentsByAge(age);
    }

    public List<Student> findByAgeBetween(int min, int max) {
        return studentRepository.findByAgeBetween(min, max);
    }

    public Faculty getFacultyById(Long id) {
        Optional<Student> optional = studentRepository.findStudentById(id);
        return optional.map(Student::getFaculty).orElse(null);
    }
}
