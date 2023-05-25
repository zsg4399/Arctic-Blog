package com.hnust.zsg.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hnust.zsg.entity.po.TagPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
public interface TagMapper extends BaseMapper<TagPO>{
    List<TagPO> getAllTagsById(@Param("id") Long id);
    @Transactional
    Integer insertTagsByArticleId(@Param("article_id")Long id,@Param("tag_name")String tag_name,@Param("authorId")Long authorId);
}
