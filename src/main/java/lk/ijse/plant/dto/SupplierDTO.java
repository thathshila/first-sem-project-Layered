package lk.ijse.plant.dto;

import lombok.*;

import java.sql.Date;
@AllArgsConstructor
@Data
@NoArgsConstructor
public class SupplierDTO {
    private String Supplier_id;
    private String Supplier_name;
    private String Address;
    private int Contact;
    private int Quantity;
    private double Price;
    private String Product;
    private Date Date;
    private String NIC;
}
