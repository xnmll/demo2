package cn.xnmll.demo2.service;

import cn.xnmll.demo2.dao.MessageMapper;
import cn.xnmll.demo2.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xnmll
 * @create 2021-08-2021/8/28  19:43
 */

@Service
public class MessageService {

    @Autowired
    private MessageMapper messageMapper;


    public List<Message> findConversations(int userId, int offset, int limit) {
        return messageMapper.selectConversations(userId, offset, limit);

    }

    public int findConversationCount(int userId) {
        return messageMapper.selectConversationCount(userId);
    }

    public List<Message> findLetters(String conversationId, int offset, int limit) {
        return messageMapper.selectLetters(conversationId, offset, limit);
    }

    public int findLettersCount(String conversationId) {
        return messageMapper.selectLetterCount(conversationId);
    }

    public int findLetterUnreadCount(int userId,String conversationId) {
        return messageMapper.selectLetterUnreadCount(userId,conversationId);
    }
}
