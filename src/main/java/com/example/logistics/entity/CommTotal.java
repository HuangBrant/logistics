package com.example.logistics.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "commodity_total")
public class CommTotal {

    @Id
    private Integer id;

    private Integer cid;
    private Integer sendStatus;
    private Integer receive;
    private BigDecimal price;
    private Date startTime;
    private Date endTime;

}
