package mid.term.springstudents.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudentTest {

    private Student student;

    @BeforeEach
    public void setUp() {

        student = new Student();
        student.setName("John");
        student.setSurname("Doe");
        student.setEmail("john.doe@example.com");
        student.setDateOfBirth(LocalDate.of(2000, 1, 1));
    }

    @Test
    public void testGetAge() {

        int expectedAge = 25;
        int actualAge = student.getAge();
        assertEquals(expectedAge, actualAge, "The age should be correctly calculated.");
    }

    @Test
    public void testGetAgeWhenBirthdayHasNotOccurredYetThisYear() {
        student.setDateOfBirth(LocalDate.of(2000, 12, 31));
        int expectedAge = 24;
        int actualAge = student.getAge();
        assertEquals(expectedAge, actualAge, "The age should be correctly calculated before the birthday.");
    }
}
