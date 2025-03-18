package mid.term.springstudents.repository;

import mid.term.springstudents.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface StudentRepository extends JpaRepository<Student, Long> {
    void deleteStudentByEmail(String email);
    Student findStudentByEmail(String email);
}
