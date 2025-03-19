package mid.term.springstudents.controller;

import mid.term.springstudents.model.Student;
import mid.term.springstudents.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class StudentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
    }

    @Test
    void findAllStudent() throws Exception {
        Student student1 = new Student();
        student1.setName("John");
        student1.setEmail("john@example.com");

        Student student2 = new Student();
        student2.setName("Alice");
        student2.setEmail("alice@example.com");

        List<Student> students = Arrays.asList(student1, student2);

        when(studentService.findAllStudent()).thenReturn(students);

        mockMvc.perform(get("/api/v1/students"))
                .andExpect(status().isOk());

        verify(studentService, times(1)).findAllStudent();
    }

    @Test
    void saveStudent() throws Exception {
        Student student = new Student();
        student.setName("John");
        student.setSurname("Doe");
        student.setEmail("john@example.com");
        student.setDateOfBirth(LocalDate.of(2000, 1, 1));

        when(studentService.saveStudent(any(Student.class))).thenReturn(student);

        mockMvc.perform(post("/api/v1/students/save_student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"name\":\"John\"," +
                                "\"surname\":\"Doe\"," +
                                "\"email\":\"john@example.com\"," +
                                "\"dateOfBirth\":\"2000-01-01\"}"))
                .andExpect(status().isOk());

        verify(studentService, times(1)).saveStudent(any(Student.class));
    }

    @Test
    void findStudentByEmail() throws Exception {
        Student student = new Student();
        student.setName("John");
        student.setEmail("john@example.com");

        when(studentService.findStudentByEmail("john@example.com")).thenReturn(student);

        mockMvc.perform(get("/api/v1/students/john@example.com"))
                .andExpect(status().isOk());

        verify(studentService, times(1)).findStudentByEmail("john@example.com");
    }

    @Test
    void updateStudent() throws Exception {
        Student student = new Student();
        student.setName("John");
        student.setSurname("Doe");
        student.setEmail("john@example.com");
        student.setDateOfBirth(LocalDate.of(2000, 1, 1));

        when(studentService.updateStudent(any(Student.class))).thenReturn(student);

        mockMvc.perform(put("/api/v1/students/update_student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"name\":\"John\"," +
                                "\"surname\":\"Doe\"," +
                                "\"email\":\"john@example.com\"," +
                                "\"dateOfBirth\":\"2000-01-01\"}"))
                .andExpect(status().isOk());

        verify(studentService, times(1)).updateStudent(any(Student.class));
    }

    @Test
    void deleteStudent() throws Exception {
        doNothing().when(studentService).deleteStudent("john@example.com");

        mockMvc.perform(delete("/api/v1/students/delete_student/john@example.com"))
                .andExpect(status().isOk());

        verify(studentService, times(1)).deleteStudent("john@example.com");
    }
}