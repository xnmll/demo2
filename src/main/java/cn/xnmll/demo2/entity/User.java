package cn.xnmll.demo2.entity;

import javax.xml.crypto.Data;
import java.util.Date;

/**
 * @author xnmll
 * @create 2021-08-2021/8/20  15:51
 */

@lombok.Data
public class User {
    private int id;
    private String username;
    private String password;
    private String salt;
    private String email;
    private int type;
    private int status;
    private String activationCode;
    private String headerUrl;
    private Date createTime;

}
