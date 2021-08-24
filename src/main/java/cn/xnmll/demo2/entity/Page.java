package cn.xnmll.demo2.entity;

import lombok.Data;

/**
 * @author xnmll
 * @create 2021-08-2021/8/21  21:15
 */

@Data
public class Page {

    //当前页码
    private int current = 1;

    //显示上限
    private int limit = 10;

    //数据总数
    private int rows;

    //查询路径（用于复用分页链接）
    private String path;

    public void setCurrent(int current) {
        if (current > 1)
            this.current = current;
    }

    public void setLimit(int limit) {
        if (limit >= 1 && limit <= 100)
            this.limit = limit;
    }

    public void setRows(int rows) {
        if (rows >= 0)
            this.rows = rows;
    }

    public int getOffset() {
        return (current - 1) * limit;
    }

    public int getTotal() {
        if (rows % limit == 0) return rows / limit;
        return rows / limit + 1;
    }

    public int getFrom() {
        int from = current - 2;
        return from < 1 ? 1 : from;
    }

    public int getTo() {
        int to = current + 2;
        int total = getTotal();
        return to > total ? total : to;
    }


}
