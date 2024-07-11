package lk.ijse.plant.dto;

import lombok.*;

import java.sql.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerDTO {
    private String Customer_id;
    private String Customer_name;
    private int Contact;
    private String Address;
    private String Nic;
    private Date Date;
}
