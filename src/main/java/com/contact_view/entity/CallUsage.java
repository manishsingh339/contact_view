package com.contact_view.entity;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("CallUsage")
public class CallUsage {
   private Long slNo;
   private String month;
   private String date;
   private String time;
   private Long number;
   private Long duration;
   private String name;
   private Long callTime;
}
