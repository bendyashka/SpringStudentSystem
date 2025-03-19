package mid.term.springstudents.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mid.term.springstudents.model.Student;
import mid.term.springstudents.service.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
    }

    @Test
    public void testFindAllStudents() throws Exception {
        when(studentService.findAllStudent()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/students"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(studentService, times(1)).findAllStudent();
    }

    @Test
    public void testSaveStudent() throws Exception {
        Student student = new Student(1L, "John", "Doe", "john.doe@example.com", LocalDate.of(2000, 1, 1));

        when(studentService.saveStudent(any(Student.class))).thenReturn(student);

        mockMvc.perform(post("/api/v1/students/save_student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk())
                .andExpect(content().string("Student saved successfully"));

        verify(studentService, times(1)).saveStudent(any(Student.class));
    }

    @Test
    public void testFindStudentByEmail() throws Exception {
        Student student = new Student(1L, "John", "Doe", "john.doe@example.com", LocalDate.of(2000, 1, 1));

        when(studentService.findStudentByEmail("john.doe@example.com")).thenReturn(student);

        mockMvc.perform(get("/api/v1/students/john.doe@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));

        verify(studentService, times(1)).findStudentByEmail("john.doe@example.com");
    }

    @Test
    public void testUpdateStudent() throws Exception {
        Student student = new Student(1L, "John", "Doe", "john.doe@example.com", LocalDate.of(2000, 1, 1));

        when(studentService.updateStudent(any(Student.class))).thenReturn(student);

        mockMvc.perform(put("/api/v1/students/update_student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));

        verify(studentService, times(1)).updateStudent(any(Student.class));
    }

    @Test
    public void testDeleteStudent() throws Exception {
        doNothing().when(studentService).deleteStudent("john.doe@example.com");

        mockMvc.perform(delete("/api/v1/students/delete_student/john.doe@example.com"))
                .andExpect(status().isOk());

        verify(studentService, times(1)).deleteStudent("john.doe@example.com");
    }
}