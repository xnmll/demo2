package cn.xnmll.demo2.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author xnmll
 * @create 2021-08-2021/8/24  21:11
 */

@Data
public class LoginTicket {

    private int id;

    private int userId;

    private String ticket;

    private int status;

    private Date expired;

}
