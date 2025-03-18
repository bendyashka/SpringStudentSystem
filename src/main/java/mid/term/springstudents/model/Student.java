package mid.term.springstudents.model;

import lombok.*;

import java.time.LocalDate;


@Data
@Builder

public class Student {
    private int id;
    private String name;
    private String surname;
    private String email;
    private LocalDate dateOfBirth;
    private int age;

}
