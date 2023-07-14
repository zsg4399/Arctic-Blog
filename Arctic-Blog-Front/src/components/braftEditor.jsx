import "braft-editor/dist/index.css";
import React from "react";
import BraftEditor from "braft-editor";
import addstyle from "../pages/addarticle/addArticle.module.scss";

export default class BasicDemo extends React.Component {
  state = {
    editorState: BraftEditor.createEditorState(""), // 设置编辑器初始内容
  };

  componentDidMount() {
    this.isLivinig = true;
  }

  componentWillUnmount() {
    this.isLivinig = false;
  }

  handleChange = (editorState) => {
    // console.log(editorState.toText().length);获取内容长度
    this.props.editorstate(editorState)
    this.setState({
      editorState: editorState,
    });
  };

  render() {
    const { editorState } = this.state;

    return (
      <div>
        <div className="editor-wrapper">
          <BraftEditor
            contentClassName="article-content"
            value={editorState}
            onChange={this.handleChange}
            className={`${addstyle.braftstyle}`}
            placeholder="输入文章内容"
          />
        </div>
      </div>
    );
  }
}
