package com.hnust.zsg.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hnust.zsg.entity.po.TagPO;

import java.util.List;

public interface TagService extends IService<TagPO> {
    Boolean insertTagsByArticleId(Long article_id, List<TagPO> tags,Long authorId);
}
