package ru.hogwarts.school.homework35;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.hogwarts.school.homework35.controller.StudentController;
import ru.hogwarts.school.homework35.model.Faculty;
import ru.hogwarts.school.homework35.model.Student;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class Homework35ApplicationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    void createGetUpdateDeleteStudent() {
        Student student = new Student(1L, "test", 22);
        Assertions
                .assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/student", student, Student.class))
                .isEqualTo(student);
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/find?id=1", Student.class))
                .isEqualTo(student);
        Student studentUpdated = new Student(1L, "test2", 22);
        this.restTemplate.put("http://localhost:" + port + "/student", studentUpdated);
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/find?id=1", Student.class))
                .isEqualTo(studentUpdated);
        this.restTemplate.delete("http://localhost:" + port + "/student/" + studentUpdated.getId());
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/find?id=1", Student.class))
                .isNull();
    }

    @Test
    void getStudentsByAgeAndFindByAgeBetween() {
        Student student = new Student(1L, "test", 10);
        this.restTemplate.postForObject("http://localhost:" + port + "/student", student, Student.class);
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/filter?age=10", List.class))
                .isNotEmpty();
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student?age1=10&age2=11", List.class))
                .isNotEmpty();
        this.restTemplate.delete("http://localhost:" + port + "/student/" + student.getId());
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/find?id=1", Student.class))
                .isNull();

    }

    /*@Test
    void getFacultyById() {
        Student student = new Student(1L, "test", 10);
        Faculty faculty = new Faculty(1L, "Random", "Random");
        student.setFaculty(faculty);
        this.restTemplate.postForObject("http://localhost:" + port + "/student", student, Student.class);
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/1/faculty", Faculty.class))
                .isEqualTo(faculty);
    }*/
}