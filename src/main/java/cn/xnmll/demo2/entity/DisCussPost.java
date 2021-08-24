package cn.xnmll.demo2.entity;

import lombok.Data;
import org.jboss.logging.Field;

import java.util.Date;

/**
 * @author xnmll
 * @create 2021-08-2021/8/20  22:52
 */

@Data
public class DisCussPost {

    private int id;

    private int userId;

    private String title;

    private String content;

    private int type;

    private int status;

    private Date createTime;

    private int commentCount;

    private double score;

}
