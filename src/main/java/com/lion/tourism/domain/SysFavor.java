package com.lion.tourism.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * 收藏
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("sys_favor")
public class SysFavor implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 路线id
     */
    private String lineId;

    /**
     * 名称
     */
    private String name;

    /**
     * 介绍
     */
    private String introduce;

    /**
     * 图片
     */
    private String images;

    /**
     * 用户id
     */
    private String userId;

    @TableField(exist = false)
    private Integer pageNumber;

    @TableField(exist = false)
    private Integer pageSize;
}
