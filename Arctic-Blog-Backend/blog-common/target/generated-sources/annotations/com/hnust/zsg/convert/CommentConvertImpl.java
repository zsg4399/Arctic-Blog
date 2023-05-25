package com.hnust.zsg.convert;

import com.hnust.zsg.entity.dto.CommentDTO;
import com.hnust.zsg.entity.po.CommentPO;
import com.hnust.zsg.entity.vo.CommentVO;
import com.hnust.zsg.entity.vo.ReplyVO;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.annotation.Generated;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-24T14:32:44+0800",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 1.8.0_121 (Oracle Corporation)"
)
public class CommentConvertImpl implements CommentConvert {

    private final DatatypeFactory datatypeFactory;

    public CommentConvertImpl() {
        try {
            datatypeFactory = DatatypeFactory.newInstance();
        }
        catch ( DatatypeConfigurationException ex ) {
            throw new RuntimeException( ex );
        }
    }

    @Override
    public CommentVO POTOVO(CommentPO commentPO) {
        if ( commentPO == null ) {
            return null;
        }

        CommentVO commentVO = new CommentVO();

        commentVO.setId( commentPO.getId() );
        commentVO.setContent( commentPO.getContent() );
        commentVO.setCreateTime( xmlGregorianCalendarToString( dateToXmlGregorianCalendar( commentPO.getCreateTime() ), null ) );
        commentVO.setCommentLikes( commentPO.getCommentLikes() );

        return commentVO;
    }

    @Override
    public CommentPO CommentDTO_TO_CommentPO(CommentDTO commentDTO) {
        if ( commentDTO == null ) {
            return null;
        }

        CommentPO commentPO = new CommentPO();

        commentPO.setReplyUid( commentDTO.getReply_uid() );
        commentPO.setContent( commentDTO.getContent() );
        commentPO.setPid( commentDTO.getPid() );

        return commentPO;
    }

    @Override
    public ReplyVO CommentPO_TO_ReplyVO(CommentPO commentPO) {
        if ( commentPO == null ) {
            return null;
        }

        ReplyVO replyVO = new ReplyVO();

        replyVO.setId( commentPO.getId() );
        replyVO.setContent( commentPO.getContent() );
        replyVO.setCreateTime( xmlGregorianCalendarToString( dateToXmlGregorianCalendar( commentPO.getCreateTime() ), null ) );
        replyVO.setCommentLikes( commentPO.getCommentLikes() );

        return replyVO;
    }

    private String xmlGregorianCalendarToString( XMLGregorianCalendar xcal, String dateFormat ) {
        if ( xcal == null ) {
            return null;
        }

        if (dateFormat == null ) {
            return xcal.toString();
        }
        else {
            Date d = xcal.toGregorianCalendar().getTime();
            SimpleDateFormat sdf = new SimpleDateFormat( dateFormat );
            return sdf.format( d );
        }
    }

    private XMLGregorianCalendar dateToXmlGregorianCalendar( Date date ) {
        if ( date == null ) {
            return null;
        }

        GregorianCalendar c = new GregorianCalendar();
        c.setTime( date );
        return datatypeFactory.newXMLGregorianCalendar( c );
    }
}
