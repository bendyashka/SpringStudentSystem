package mid.term.springstudents.impl;

import mid.term.springstudents.model.Student;
import mid.term.springstudents.repository.StudentRepository;
import mid.term.springstudents.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @InjectMocks
    private StudentServiceImpl studentService;

    @Mock
    private StudentRepository studentRepository;

    @Test
    void shouldFindAllStudents() {

        List<Student> students = List.of(new Student(1L, "John", "Doe", "john.doe@example.com", null, 20));
        when(studentRepository.findAll()).thenReturn(students);


        List<Student> result = studentService.findAllStudent();


        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getName());
        verify(studentRepository).findAll();
    }

    @Test
    void shouldSaveStudent() {

        Student student = new Student(1L, "Jane", "Doe", "jane.doe@example.com", null, 20);
        when(studentRepository.save(Mockito.any(Student.class))).thenReturn(student);


        Student result = studentService.saveStudent(student);


        assertEquals("Jane", result.getName());
        verify(studentRepository).save(student);
    }

    @Test
    void shouldFindStudentByEmail() {

        String email = "jane.doe@example.com";
        Student student = new Student(1L, "Jane", "Doe", email, null, 20);
        when(studentRepository.findStudentByEmail(email)).thenReturn(student);


        Student result = studentService.findStudentByEmail(email);


        assertEquals(email, result.getEmail());
        verify(studentRepository).findStudentByEmail(email);
    }

    @Test
    void shouldDeleteStudent() {

        String email = "jane.doe@example.com";
        doNothing().when(studentRepository).deleteStudentByEmail(email);

        studentService.deleteStudent(email);

        verify(studentRepository).deleteStudentByEmail(email);
    }
}
