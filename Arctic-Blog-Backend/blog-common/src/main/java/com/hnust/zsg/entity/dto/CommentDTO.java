package com.hnust.zsg.entity.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

public class CommentDTO {
    private String content;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long pid;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long reply_uid;

    public CommentDTO() {
    }

    public CommentDTO(String content, Long pid, Long reply_uid) {
        this.content = content;
        this.pid = pid;
        this.reply_uid = reply_uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Long getReply_uid() {
        return reply_uid;
    }

    public void setReply_uid(Long reply_uid) {
        this.reply_uid = reply_uid;
    }

    @Override
    public String toString() {
        return "CommentDTO{" +
                "content='" + content + '\'' +
                ", pid=" + pid +
                ", reply_uid=" + reply_uid +
                '}';
    }
}
