package com.hnust.zsg.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hnust.zsg.entity.po.TagPO;
import com.hnust.zsg.mapper.TagMapper;
import com.hnust.zsg.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TagServiceimpl extends ServiceImpl<TagMapper, TagPO> implements TagService {
    @Autowired
    private TagMapper tagMapper;

    @Override
    public Boolean insertTagsByArticleId(Long article_id, List<TagPO> tags, Long authorId) {
        boolean t1 = true;
        for (TagPO tag : tags) {
            if (tagMapper.insertTagsByArticleId(article_id, tag.getTagName(),authorId) == 0) {
                return false;
            }
        }
        return true;
    }
}
