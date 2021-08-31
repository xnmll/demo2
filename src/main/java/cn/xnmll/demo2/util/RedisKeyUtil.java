package cn.xnmll.demo2.util;

import org.omg.CORBA.PRIVATE_MEMBER;

/**
 * @author xnmll
 * @create 2021-08-2021/8/31  17:11
 */

public class RedisKeyUtil {

    private static final String SPLIT = ":";

    private static final String PREFIX_ENTITY_LIKE = "like:entity";

    private static final String PREFIX_USER_LIKE = "like:user";

    //某个实体的赞
    //like:entity:entityType:entityId -> set(userId)
    public static String getEntityLikeKey(int entityType, int entityId) {
        return PREFIX_ENTITY_LIKE + SPLIT + entityType + SPLIT + entityId;
    }

    //某个用户的赞
    public static String getUserLikeKey(int userId) {
        return PREFIX_ENTITY_LIKE + SPLIT + userId;
    }

}
