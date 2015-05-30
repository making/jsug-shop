package jsug.domain.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
public class Account implements Serializable {
    private String email;
    private String password;
    private String name;
    private LocalDate birthDay;
    private String zip;
    private String address;
}
