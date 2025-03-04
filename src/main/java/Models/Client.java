package Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import Validators.CreateGroup;
import Validators.UpdateGroup;

@Entity
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Identification is required", groups = CreateGroup.class)
    @Column(unique = true, length = 20)
    private String identification;

    @NotBlank(message = "First name is required", groups = CreateGroup.class)
    private String firstName;

    private String middleName;

    @NotBlank(message = "Last name is required", groups = CreateGroup.class)
    private String lastName;

    private String secondLastName;

    @Email(message = "Invalid email format", groups = {CreateGroup.class, UpdateGroup.class})
    @NotBlank(message = "Email is required", groups = {CreateGroup.class, UpdateGroup.class})
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Address is required", groups = {CreateGroup.class, UpdateGroup.class})
    private String address;

    @NotBlank(message = "Phone number is required", groups = {CreateGroup.class, UpdateGroup.class})
    private String phone;

    @NotBlank(message = "Country code is required", groups = {CreateGroup.class, UpdateGroup.class})
    private String country;

    private String nationality;
}
