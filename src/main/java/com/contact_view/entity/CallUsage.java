package com.contact_view.entity;
import lombok.Data;

@Data
public class CallUsage {
   private Long slNo;
   private String month;
   private String date;
   private String time;
   private Long number;
   private Long duration;
   private String name;
}
