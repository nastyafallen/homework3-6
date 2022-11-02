package ru.hogwarts.school.homework35;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import ru.hogwarts.school.homework35.model.Faculty;
import ru.hogwarts.school.homework35.model.Student;
import ru.hogwarts.school.homework35.repository.StudentRepository;
import ru.hogwarts.school.homework35.service.StudentService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class StudentServiceTest {
    @Mock
    private StudentRepository repositoryMock;
    private StudentService out;
    private final Student student1 = new Student(0L, "test", 99);

    @BeforeEach
    public void initOut() {
        out = new StudentService(repositoryMock);
    }

    @Test
    void createStudent() {
        Mockito.when(repositoryMock.save(student1)).thenReturn(student1);
        assertThat(out.createStudent(student1)).isEqualTo(student1);
    }

    @Test
    void getStudent() {
        Mockito.when(repositoryMock.save(student1)).thenReturn(student1);
        Mockito.when(repositoryMock.findStudentById(0L)).thenReturn(Optional.of(student1));
        out.createStudent(student1);
        assertThat(out.getStudent(0).get()).isEqualTo(student1);
    }

    @Test
    void updateStudent() {
        Mockito.when(repositoryMock.save(student1)).thenReturn(student1);
        Mockito.when(repositoryMock.findStudentById(0L)).thenReturn(Optional.of(student1));
        out.createStudent(student1);
        Student student2 = new Student(4L, "test2", 34);
        out.updateStudent(0, student2);
        assertThat(out.getStudent(0).get().getName()).isEqualTo("test2");
        assertThat(out.getStudent(0).get().getAge()).isEqualTo(34);
    }

    @Test
    void deleteStudent() {
        Mockito.when(repositoryMock.save(student1)).thenReturn(student1);
        Mockito.when(repositoryMock.findStudentById(1L)).thenReturn(Optional.of(student1));
        out.createStudent(student1);
        assertThat(out.getStudent(1).get()).isEqualTo(student1);
        out.deleteStudent(1);
        assertThat(out.getStudent(1).isEmpty());
    }

    @Test
    void getStudentsByAge() {
        List<Student> expected = new ArrayList<>();
        Student student2 = new Student(4L, "test2", 34);
        Student student3 = new Student(9L, "test3", 1);
        expected.add(student3);
        Mockito.when(repositoryMock.save(student1)).thenReturn(student1);
        Mockito.when(repositoryMock.getStudentsByAge(1)).thenReturn(expected);
        out.createStudent(student1);
        out.createStudent(student2);
        out.createStudent(student3);
        assertThat(out.getStudentsByAge(1)).isEqualTo(expected);
    }

    @Test
    void findByAgeBetween() {
        List<Student> expected = new ArrayList<>();
        Student student2 = new Student(4L, "test2", 34);
        Student student3 = new Student(9L, "test3", 1);
        expected.add(student3);
        Mockito.when(repositoryMock.save(student1)).thenReturn(student1);
        Mockito.when(repositoryMock.findByAgeBetween(0, 10)).thenReturn(expected);
        out.createStudent(student1);
        out.createStudent(student2);
        out.createStudent(student3);
        assertThat(out.findByAgeBetween(0, 10)).isEqualTo(expected);
    }

    @Test
    void getFacultyById() {
        Student student2 = new Student(88L, "test2", 39);
        Faculty testFaculty = new Faculty(9L, "RandomFaculty", "black");
        student2.setFaculty(testFaculty);
        Mockito.when(repositoryMock.save(student2)).thenReturn(student2);
        Mockito.when(repositoryMock.findStudentById(88L)).thenReturn(Optional.of(student2));
        out.createStudent(student2);
        assertThat(out.getFacultyById(88L)).isEqualTo(testFaculty);
    }
}
