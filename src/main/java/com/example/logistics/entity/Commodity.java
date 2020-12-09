package com.example.logistics.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "commodity")
public class Commodity {

    @Id
    private Integer id;

    private String tid;
    private String name;
    private String type;
    private String overdue;
    private Date date;
    private String expirationDate;
    private BigDecimal price;
}
