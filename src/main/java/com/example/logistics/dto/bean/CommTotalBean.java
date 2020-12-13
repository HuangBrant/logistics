package com.example.logistics.dto.bean;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class CommTotalBean {

    private Integer id;

    private Integer cid;
    private Integer sendStatus;
    private Integer receive;
    private BigDecimal price;
    private Date startTime;
    private Date endTime;

}
