package com.example.logistics.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
    private Integer expirationDate;
    private BigDecimal price;

    @OneToMany
    @JoinColumn(name = "cid")
    private List<CommTotal> commTotal;
}
