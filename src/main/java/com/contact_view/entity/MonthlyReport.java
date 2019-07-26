package com.contact_view.entity;
import lombok.Data;

@Data
public class MonthlyReport {
    private String name;
    private Long number;
    private Long count;
    private Long totalDuration;
}
