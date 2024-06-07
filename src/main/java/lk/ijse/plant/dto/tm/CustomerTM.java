package lk.ijse.plant.dto.tm;

import lombok.*;

import java.sql.Date;
@NoArgsConstructor
@Data
@AllArgsConstructor
@Setter
@ToString
@Getter
public class CustomerTM {
    private String Customer_id;
    private String Customer_name;
    private int Contact;
    private String Address;
    private String Nic;
    private Date Date;
}
