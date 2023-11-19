package by.clevertec.lobacevich.data;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDto {

    private Integer id;
    private String firstname;
    private String lastname;
    private LocalDate dateOfBirth;
    private String email;
}
