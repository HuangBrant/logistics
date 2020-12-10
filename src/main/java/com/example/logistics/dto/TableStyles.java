package com.example.logistics.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TableStyles {
    private Integer id;
    private String productName;
    private String Type;
    private String overdue;
    private Date date;
    private Integer expirationDate;
    private BigDecimal price;
}
