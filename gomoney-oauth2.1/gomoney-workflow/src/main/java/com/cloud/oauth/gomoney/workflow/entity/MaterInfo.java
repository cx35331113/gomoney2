package com.cloud.oauth.gomoney.workflow.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@TableName("matter_info")
public class MaterInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = -5735299489154245390L;

    @TableId(type = IdType.ASSIGN_UUID)
    private String mid;

    private String matterName;

    private String specs;

    private String brand;

    private BigDecimal price;

    private BigDecimal count;

    private String url;

    private String userId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp optrdate;

    private int state;
}
