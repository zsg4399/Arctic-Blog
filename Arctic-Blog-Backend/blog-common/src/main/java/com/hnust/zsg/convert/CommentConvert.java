package com.hnust.zsg.convert;

import com.hnust.zsg.entity.dto.CommentDTO;
import com.hnust.zsg.entity.po.CommentPO;
import com.hnust.zsg.entity.vo.CommentVO;
import com.hnust.zsg.entity.vo.ReplyVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentConvert {
    CommentConvert INSTANCE= Mappers.getMapper(CommentConvert.class);

    CommentVO POTOVO(CommentPO commentPO);
    @Mapping(source = "reply_uid",target = "replyUid")
    CommentPO CommentDTO_TO_CommentPO(CommentDTO commentDTO);


    ReplyVO CommentPO_TO_ReplyVO(CommentPO commentPO);
}
