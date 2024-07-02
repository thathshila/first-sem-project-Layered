package lk.ijse.plant.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomEntity {
   //customer
   private String Customer_name;
    private int Contact;
    private String Address;
    private String Nic;
    private java.sql.Date Date;

   //Item
   private String Item_id;
    private String Item_name;
    private int Quantity;
    private double Price;
    private String Description;
//    private Date Date;

    //Order
    private String Order_id;
  //  private Date Date;
  //  private double Price;
    private String Customer_id;
    private String User_id;

    //OrderItem
 //   private String Order_id;
  //  private String Item_id;
//    private int Quantity;
//    private double Price;

    public CustomEntity(String orderId, String date, String customerId, String itemId, int quantity, double price) {
        this.Order_id = orderId;
        this.Date = java.sql.Date.valueOf(date);
        this.Customer_id = customerId;
        this.Item_id = itemId;
        this.Quantity = quantity;
        this.Price = price;
    }
}
