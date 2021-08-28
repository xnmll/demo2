package cn.xnmll.demo2.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author xnmll
 * @create 2021-08-2021/8/28  14:28
 */

@Data
public class Comment {

    private int id;
    private int userId;
    private int entityType;
    private int targetId;
    private String content;
    private int status;
    private Date createTime;

}
