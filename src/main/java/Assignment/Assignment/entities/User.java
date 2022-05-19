package Assignment.Assignment.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @NotBlank(message = "Name is mandatory")
    String name;

    @Size(min = 4, max = 15 ,message="Length between 4 and 15")
    String mobile;

    @Email(message = "Invalid Email")
    @Column(unique=true , length = 50)
    String email;

    @Size(min = 6 , max=50 ,message="Password Length must be larger than 6")
    String password;

    String fileName;
}