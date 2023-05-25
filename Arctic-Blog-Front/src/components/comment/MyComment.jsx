import React, { useState } from "react";
import commentStyle from "./MyComment.module.scss";
import { Avatar, Button, Comment, Input, Spin, Tooltip, message } from "antd";
import { LikeFilled, LikeOutlined, LoadingOutlined } from "@ant-design/icons";
import InfiniteScroll from "react-infinite-scroller";
import { addReply } from "../../api/comment";
const { TextArea } = Input;

const MyComment = (props) => {
  const userinfo = JSON.parse(localStorage.getItem("user-basic-info"));
  const { commentData, hasmore, loadmore, addComment, articleId, talkId } =
    props;
  const [InputValue, setInputValue] = useState("");
  const [reply, setReply] = useState("");
  const [replyId, setReplyId] = useState(null);
  const [pid, setPid] = useState(null);
  return (
    <div className={`${props.className}`}>
      <h3 className={commentStyle.h3Style}>COMMENTS</h3>
      <TextArea
        rows={4}
        placeholder="Input your comment"
        className={commentStyle.AddCommentArea}
        value={InputValue}
        onChange={(e) => {
          if (e.target.value.length === 101) {
            message.warn("评论字数限制为100字以内");
            return;
          }
          setInputValue(e.target.value);
        }}
      />
      <Button
        onClick={() => {
          if (articleId !== null) {
            addComment(articleId, InputValue);
            return;
          }
          if (talkId !== null) {
            addComment(talkId, InputValue);
            return;
          }
          addComment(InputValue);
        }}
        className={commentStyle.AddButton}
      >
        Add Comment
      </Button>
      <InfiniteScroll
        pageStart={1}
        hasMore={hasmore}
        loader={
          <div key={"my-loader"} className={commentStyle.loader}>
            <Spin
              indicator={
                <LoadingOutlined
                  style={{ fontSize: 24, marginRight: 10 }}
                  spin
                />
              }
            />
            <span>Loading ...</span>
          </div>
        }
        loadMore={() => {
          loadmore();
        }}
      >
        <ul>
          {commentData.map((item) => {
            return (
              <li key={item.id}>
                <Comment
                  key={item.id}
                  actions={[
                    <Tooltip key={item.id} title="Like">
                      <span
                        className={commentStyle.LikeContainer}
                        onClick={item.commentLikes}
                      >
                        {item.isLike ? <LikeFilled /> : <LikeOutlined />}
                        <span style={{ marginLeft: 5 }}>
                          {item.commentLikes}
                        </span>
                      </span>
                    </Tooltip>,
                    <span
                      onClick={() => {
                        setPid(item.id);
                        setReplyId(null);
                      }}
                      style={{ color: "#bebebe" }}
                    >
                      Reply
                    </span>,
                  ]}
                  author={
                    <span className={commentStyle.authorName}>
                      {item.username}
                    </span>
                  }
                  avatar={<Avatar src={`/avatar/${item.avatar}`} />}
                  content={
                    <p className={commentStyle.commentContent}>
                      {item.content}
                    </p>
                  }
                  datetime={item.createTime}
                >
                  {item.children !== null &&
                    typeof item.children !== "undefined" &&
                    item.children.map((e) => {
                      return (
                        <Comment
                          key={e.id}
                          actions={[
                            <Tooltip key={e.id} title="Like">
                              <span
                                className={commentStyle.LikeContainer}
                                onClick={e.commentLikes}
                              >
                                {e.isLike ? <LikeFilled /> : <LikeOutlined />}
                                <span style={{ marginLeft: 5 }}>
                                  {e.commentLikes}
                                </span>
                              </span>
                            </Tooltip>,
                            <span
                              onClick={() => {
                                setPid(item.id);
                                setReplyId(e.uid);
                              }}
                              style={{ color: "#bebebe" }}
                            >
                              Reply
                            </span>,
                          ]}
                          author={
                            <span className={commentStyle.authorName}>
                              {e.username}
                            </span>
                          }
                          avatar={<Avatar src={`/avatar/${e.avatar}`} />}
                          content={
                            <p className={commentStyle.commentContent}>
                              {e.reply_user
                                ? "@" + e.reply_user + ":" + e.content
                                : e.content}
                            </p>
                          }
                          datetime={e.createTime}
                        />
                      );
                    })}
                </Comment>
                {item.id === pid && (
                  <div className={commentStyle.replyContainer}>
                    <Avatar
                      style={{ marginLeft: "6.5%" }}
                      src={`/avatar/${userinfo.avatar}`}
                    />
                    <TextArea
                      rows={3}
                      className={commentStyle.replyArea}
                      value={reply}
                      onChange={(e) => {
                        if (e.currentTarget.value.length === 101) {
                          message.warn("回复字数限制在100字以内");
                          return;
                        }
                        setReply(e.currentTarget.value);
                      }}
                      placeholder="Input your reply"
                    />
                    <Button
                      className={commentStyle.replyButton}
                      onClick={() => {
                        addReply(reply, pid, replyId);
                      }}
                    >
                      回复
                    </Button>
                  </div>
                )}
              </li>
            );
          })}
          {!hasmore && (
            <li key="no-more" style={{ textAlign: "center", marginTop: 20 }}>
              暂无更多评论
            </li>
          )}
        </ul>
      </InfiniteScroll>
    </div>
  );
};

export default MyComment;
