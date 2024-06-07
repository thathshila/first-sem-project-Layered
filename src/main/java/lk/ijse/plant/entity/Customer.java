package lk.ijse.plant.entity;

import lombok.*;

import java.sql.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Setter
@Getter
@ToString
public class Customer {
    private String Customer_id;
    private String Customer_name;
    private int Contact;
    private String Address;
    private String Nic;
    private Date Date;

}
