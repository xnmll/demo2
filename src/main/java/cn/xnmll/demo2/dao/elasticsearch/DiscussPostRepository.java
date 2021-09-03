package cn.xnmll.demo2.dao.elasticsearch;

import cn.xnmll.demo2.entity.DisCussPost;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author xnmll
 * @create 2021-09-2021/9/2  21:19
 */
@Repository
public interface DiscussPostRepository extends ElasticsearchRepository<DisCussPost, Integer> {


}



