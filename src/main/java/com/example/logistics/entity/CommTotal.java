package com.example.logistics.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "commodity_total")
public class CommTotal {

    @Id
    private Integer id;

    private Integer cid;
    private Integer send;
    private Integer receive;
    private Date startTime;
    private Date endTime;

}
