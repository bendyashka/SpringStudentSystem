package mid.term.springstudents.controller;

import mid.term.springstudents.model.Student;
import mid.term.springstudents.service.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class StudentControllerTest {

    @InjectMocks
    private StudentController studentController;

    @Mock
    private StudentService studentService;

    @Test
    void shouldReturnAllStudents() {

        List<Student> students = List.of(new Student(1L, "John", "Doe", "john.doe@example.com", null, 20));
        when(studentService.findAllStudent()).thenReturn(students);

        List<Student> result = studentController.findAllStudent();

        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getName());
    }

    @Test
    void shouldSaveStudent() {

        Student student = new Student(1L, "Jane", "Doe", "jane.doe@example.com", null, 20);
        when(studentService.saveStudent(Mockito.any(Student.class))).thenReturn(student);


        String response = studentController.saveStudent(student);


        assertEquals("Student saved successfully", response);
        Mockito.verify(studentService).saveStudent(student);
    }

    @Test
    void shouldFindStudentByEmail() {

        String email = "jane.doe@example.com";
        Student student = new Student(1L, "Jane", "Doe", email, null, 20);
        when(studentService.findStudentByEmail(email)).thenReturn(student);


        Student foundStudent = studentController.findStudentByEmail(email);


        assertEquals(email, foundStudent.getEmail());
        assertEquals("Jane", foundStudent.getName());
        Mockito.verify(studentService).findStudentByEmail(email);
    }


    @Test
    void shouldUpdateStudent() {

        Student student = new Student(1L, "Jane", "Doe", "jane.doe@example.com", null, 20);
        when(studentService.updateStudent(Mockito.any(Student.class))).thenReturn(student);


        Student updatedStudent = studentController.updateStudent(student);


        assertEquals("Jane", updatedStudent.getName());
        Mockito.verify(studentService).updateStudent(student);
    }

    @Test
    void shouldDeleteStudent() {

        String email = "jane.doe@example.com";
        Mockito.doNothing().when(studentService).deleteStudent(email);


        studentController.deleteStudent(email);


        Mockito.verify(studentService).deleteStudent(email);
    }


}
