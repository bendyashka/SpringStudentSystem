package mid.term.springstudents.repository;

import mid.term.springstudents.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryStudentDAOTest {

    private InMemoryStudentDAO dao;

    @BeforeEach
    void setUp() {
        dao = new InMemoryStudentDAO();
    }

    @Test
    void shouldReturnAllStudents() {

        Student student = new Student(1L, "John", "Doe", "john.doe@example.com", LocalDate.of(2000, 1, 1), 23);
        dao.saveStudent(student);


        List<Student> students = dao.findAllStudent();


        assertEquals(1, students.size());
        assertEquals("John", students.get(0).getName());
    }

    @Test
    void shouldSaveStudent() {

        Student student = new Student(1L, "Jane", "Doe", "jane.doe@example.com", LocalDate.of(1999, 12, 31), 24);


        Student savedStudent = dao.saveStudent(student);


        assertEquals(student, savedStudent);
        assertEquals(1, dao.findAllStudent().size());
    }

    @Test
    void shouldFindStudentByEmail() {

        String email = "jane.doe@example.com";
        Student student = new Student(1L, "Jane", "Doe", email, LocalDate.of(1999, 12, 31), 24);
        dao.saveStudent(student);


        Student foundStudent = dao.findStudentByEmail(email);


        assertNotNull(foundStudent);
        assertEquals(email, foundStudent.getEmail());
    }

    @Test
    void shouldUpdateStudent() {

        Student student = new Student(1L, "Jane", "Doe", "jane.doe@example.com", LocalDate.of(1999, 12, 31), 24);
        dao.saveStudent(student);
        Student updatedStudent = new Student(1L, "Jane", "Smith", "jane.doe@example.com", LocalDate.of(1999, 12, 31), 24);


        Student result = dao.updateStudent(updatedStudent);


        assertNotNull(result);
        assertEquals("Smith", result.getSurname());
    }

    @Test
    void shouldDeleteStudent() {

        String email = "jane.doe@example.com";
        Student student = new Student(1L, "Jane", "Doe", email, LocalDate.of(1999, 12, 31), 24);
        dao.saveStudent(student);


        dao.deleteStudent(email);


        assertNull(dao.findStudentByEmail(email));
        assertEquals(0, dao.findAllStudent().size());
    }
}
