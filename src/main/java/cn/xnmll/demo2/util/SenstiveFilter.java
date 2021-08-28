package cn.xnmll.demo2.util;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xnmll
 * @create 2021-08-2021/8/27  17:58
 */


@Component
public class SenstiveFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SenstiveFilter.class);

    private static final String REPLACEMENT = "***";

    //根节点
    private TrieNode rootNode = new TrieNode();

    @PostConstruct
    public void init() {
        try (
                InputStream is = this.getClass().getClassLoader().getResourceAsStream("sensitive-words.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        ) {
            String keyword;
            while ((keyword = reader.readLine()) != null) {
                this.addKeyword(keyword);
            }
        } catch (IOException e) {
            LOGGER.error("加载敏感词失败" + e.getMessage());

        }


    }


    //将一个敏感词添加到前缀树中去
    private void addKeyword(String keyword) {
        TrieNode tempNode = rootNode;
        for (int i = 0; i < keyword.length(); i++) {
            char c = keyword.charAt(i);
            TrieNode subNode = tempNode.getSubNode(c);
            if (subNode == null) {
                subNode = new TrieNode();
                tempNode.addSubNode(c, subNode);
            }
            tempNode = subNode;
            if (i == keyword.length() - 1) {
                tempNode.setKeywordEnd(true);
            }
        }
    }

    /**
     * 过滤敏感词
     *
     * @param str
     * @return
     */
    public String filter(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        //指针1
        TrieNode tempNode = rootNode;
        //指针23
        int begin = 0, postion = 0;
        //结果
        StringBuilder sb = new StringBuilder();
        while (postion < str.length()) {
            char c = str.charAt(postion);
            //跳过符号
            if (isSymbol(c)) {
                //如果指针1处于根节点，记录结果，让指针2向下走一步
                if (tempNode == rootNode) {
                    sb.append(c);
                    begin++;
                }
                postion++;
                continue;
            }
            tempNode = tempNode.getSubNode(c);
            if (tempNode == null) {
                sb.append(str.charAt(begin));
                postion = ++begin;
                tempNode = rootNode;
            }else if (tempNode.isKeywordEnd()) {
                sb.append(REPLACEMENT);
                begin = ++postion;
                tempNode = rootNode;
            } else {
                postion ++ ;
            }
        }

        sb.append(str.substring(begin));
        return sb.toString();

    }

    //判断是否为符号
    private boolean isSymbol(Character c) {
        return !CharUtils.isAsciiAlphanumeric(c) && (c < 0x2E80 || c > 0x9FFF);
    }


    // 前缀树
    private class TrieNode {

        //关键词结束标识
        private boolean isKeywordEnd = false;

        public void setKeywordEnd(boolean keywordEnd) {
            isKeywordEnd = keywordEnd;
        }

        //子节点（key是下级字符，value是下级节点）
        private Map<Character, TrieNode> subNodes = new HashMap<>();

        //添加子节点
        public void addSubNode(Character c, TrieNode node) {
            subNodes.put(c, node);
        }

        //获取子节点
        public TrieNode getSubNode(Character c) {
            return subNodes.get(c);
        }

        public boolean isKeywordEnd() {
            return isKeywordEnd;
        }


    }

}
