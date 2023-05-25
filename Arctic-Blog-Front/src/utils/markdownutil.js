import markdownIt from "markdown-it";
import parse, { domToReact } from "html-react-parser";
const md = markdownIt().use(require("markdown-it-multimd-table"), {
  multiline: false,
  rowspan: false,
  headerless: false,
  multibody: true,
  aotolabel: true,
});

const options = (DomNode) => {
  if (DomNode.name === "table")
    return <table>{domToReact(DomNode.children, options)}</table>;
};

/**
 * 根据提供的Markdown生成对应HTML字符串，然后渲染成reactdomnode到页面上
 * @param {}} markdown 
 */
const markdownToHtml = (markdown) => {
  const html= md.render(markdown);
  parse(html,options)
};
