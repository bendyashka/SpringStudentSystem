package mid.term.springstudents.service.impl;

import jakarta.persistence.Transient;
import jakarta.transaction.TransactionScoped;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import mid.term.springstudents.model.Student;
import mid.term.springstudents.repository.StudentRepository;
import mid.term.springstudents.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Primary
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repository;



    @Override
    public List<Student> findAllStudent() {
        return repository.findAll();
    }

    @Override
    public Student saveStudent(Student student) {
        return repository.save(student);
    }

    @Override
    public Student findStudentByEmail(String email) {
        return repository.findStudentByEmail(email);
    }

    @Override
    public Student updateStudent(Student student) {
        return repository.save(student);
    }

    @Override
    @Transactional
    public void deleteStudent(String email) {
        repository.deleteStudentByEmail(email);

    }
}
