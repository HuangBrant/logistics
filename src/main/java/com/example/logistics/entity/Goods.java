package com.example.logistics.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "goods")
public class Goods {

    @Id
    private Integer id;

    private String productName;
    private String generatedAddress;
    private Date date;
    private BigDecimal price;
}
