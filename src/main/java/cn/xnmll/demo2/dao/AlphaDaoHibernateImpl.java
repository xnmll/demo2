package cn.xnmll.demo2.dao;

import org.springframework.stereotype.Repository;

/**
 * @author xnmll
 * @create 2021-08-2021/8/19  22:23
 */

@Repository("aHibernate")
public class AlphaDaoHibernateImpl implements AlphaDao{

    @Override
    public String select() {
        return "Hibernate";
    }
}
