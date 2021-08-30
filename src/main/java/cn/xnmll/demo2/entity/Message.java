package cn.xnmll.demo2.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author xnmll
 * @create 2021-08-2021/8/28  17:28
 */

@Data
public class Message {
    private int id;
    private int fromId;
    private int toId;
    private String conversationId;
    private String content;
    private int status;
    private Date createTime;

}
