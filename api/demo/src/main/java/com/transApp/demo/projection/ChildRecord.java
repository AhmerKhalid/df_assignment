package com.transApp.demo.projection;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChildRecord {
    public int id;
    public String senderName;
    public String receiverName;
    public int totalAmount;
    public int paidAmount;
}
