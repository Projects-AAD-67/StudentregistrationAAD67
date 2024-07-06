package lk.ijse.gdse.aad67.studentmanagemant2024.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentDTO implements Serializable {
    private String id;
    private String name;
    private String email;
    private String city;
    private String level;
}
