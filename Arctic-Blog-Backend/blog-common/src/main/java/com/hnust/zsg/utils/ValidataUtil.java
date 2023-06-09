package com.hnust.zsg.utils;

import com.hnust.zsg.Excption.validation.*;
import com.hnust.zsg.entity.po.CategoryPO;
import com.hnust.zsg.entity.po.CommentPO;
import com.hnust.zsg.entity.po.TagPO;
import com.hnust.zsg.entity.vo.ArticleContentVO;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class ValidataUtil {
    private static final String PASSWORD_RULE = "^[A-Z][a-zA-Z0-9]{7,14}$";
    private static final String USERNAME_RULE = "^[\\u4e00-\\u9fa5a-zA-Z]{1,12}$";
    private static final String EMAIL_RULE = "^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+";
    private static final String SEX_RULE = "^[男女]$";

    /**
     * 校验用户名和密码
     *
     * @param username
     * @param password
     */
    public static void Valid(String username, String password) {
        Valid(username, password, null);
    }


    /**
     * 校验用户名密码和邮箱的格式
     *
     * @param username
     * @param password
     * @param email
     */
    public static void Valid(String username, String password, String email) {
        if (username != null) {
            ValidUserName(username);
        }
        if (password != null) {
            ValidPassword(password);
        }
        if (email == null) {
            return;
        }
        ValidEmail(email);

    }

    /**
     * 校验用户名格式是否正确
     *
     * @param username
     */
    public static void ValidUserName(String username) throws UsernameillegalException {
        if (username.trim().matches(USERNAME_RULE)) {
            return;
        }
        throw new UsernameillegalException("用户名格式错误,应为1-12位中文/英文字符");

    }

    /**
     * 校验密码格式是否正确
     *
     * @param password
     */
    public static void ValidPassword(String password) throws PasswordillegalException {
        if (password.trim().matches(PASSWORD_RULE)) {
            return;
        }
        throw new PasswordillegalException("密码长度必须为8-15位，仅包含大小写字母和数字且必须以大写字母开头");
    }

    /**
     * 校验邮箱格式是否正确
     *
     * @param email
     */
    public static void ValidEmail(String email) throws EmailillegalException {
        if (isOverMaxSize(email, 32)) {
            throw new EmailillegalException("邮箱最大长度只能为32位");
        }
        if (email.trim().matches(EMAIL_RULE)) {
            return;
        }
        throw new EmailillegalException("邮箱格式错误");
    }

    /**
     * 校验个人简介信息格式内容是否正确
     *
     * @param description
     */
    public static String ValidDescription(String description) {
        if (isOverMaxSize(description, 300)) {
            return description.substring(0, 300);
        }
        return description;
    }

    /**
     * 校验地址信息格式是否正确
     *
     * @param address
     */
    public static void ValidAddress(String address) {

    }

    public static void ValidSex(String sex) {
        if (sex.trim().matches(SEX_RULE)) {
            return;
        }
        throw new SexilleaglException("输入非法性别");
    }

    /**
     * 校验生日日期格式是否正确
     *
     * @param birthday
     * @return
     */
    public static LocalDate ValidBirthday(String birthday) {
        return ValidLocalDate(birthday, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }


    /**
     * 校验字符串日期格式是否正确，并返回转换成功后的LocalDate对象
     *
     * @param datetime 日期字符串
     * @param pattern  字符串转换格式
     * @return
     */
    public static LocalDate ValidLocalDate(String datetime, DateTimeFormatter pattern) {
        LocalDate result = LocalDate.parse(datetime, pattern);
        return result;
    }

    /**
     * 校验字符串日期格式是否正确，并返回转换成功后的LocalDateTime对象
     *
     * @param datetime
     * @param pattern
     * @return
     */
    public static LocalDateTime ValidLocalDateTime(String datetime, DateTimeFormatter pattern) {
        LocalDateTime result = LocalDateTime.parse(datetime, pattern);
        return result;
    }

    /**
     * 对评论内容进行校验并格式化其他参数
     * @param commentPO
     * @return
     */
    public static CommentPO validComment(CommentPO commentPO) {
        String content= commentPO.getContent();
        if(!StringUtils.hasText(content)){
            throw new IllegalArgumentException("请输入有效的评论内容");
        }
        if(content.length()>100){
            commentPO.setContent(content.substring(0,100));
        }
        commentPO.setCreateTime(Timestamp.valueOf(LocalDateTime.now()));
        return commentPO;
    }
    /**
     * 判断参数是否为空
     *
     * @param param
     * @return
     */
    public static Boolean isBlank(Object param) {
        if (StringUtils.hasText(String.valueOf(param))) {
            return false;
        }
        return true;
    }

    /**
     * 判断字符串最大长度是否超过给定的maxlength
     *
     * @param str
     * @param maxLength
     * @return
     */
    public static Boolean isOverMaxSize(String str, int maxLength) {
        if (str.trim().length() > maxLength) {
            return true;
        }
        return false;
    }

    /**
     * 判断字符串长度是否小于给定最小值
     *
     * @param str
     * @param minLength 最小长度
     * @return
     */
    public static Boolean isUnderMinSize(String str, int minLength) {
        if (str.trim().length() < minLength) {
            return true;
        }
        return false;
    }

    public static String getTrueString(String str) {
        if (str == null) {
            return "";
        }
        return str.trim();
    }


    /**
     * 对文章信息进行参数校验
     * @param articleContentVO 文章信息实体类
     */
    public static ArticleContentVO ValidArticleContentVO(ArticleContentVO articleContentVO) {
        String title=articleContentVO.getTitle();
        title=getTrueString(title);
        if(title.length()>80||title.length()<5){
            throw new ArticleInfoException("标题长度不符合要求,应为5-80个字符");
        }
        articleContentVO.setTitle(title);
        String summary= articleContentVO.getSummary();
        summary=getTrueString(summary);
        if(summary.length()<5||summary.length()>100){
            throw new ArticleInfoException(("摘要内容不符合要求，应为5-100个字符"));
        }

        articleContentVO.setSummary(summary);
        List<TagPO> tags=articleContentVO.getTags()==null?new ArrayList<>(0):articleContentVO.getTags();
        //标签是必须的，但不能超过五个
        if(tags.size()==0|| tags.size()>5) {
            throw new ArticleInfoException("请至少选择一个标签，且标签个数不能超过五个");
        }
        for (TagPO tag : tags) {
            String tagName= tag.getTagName();
            tagName=getTrueString(tagName);
            if(tagName.length()>15){
                //只取标签的前十五个字符
                tagName=tagName.substring(0,15);
            }
            tag.setTagName(tagName);
        }
        articleContentVO.setTags(tags);
        List<CategoryPO> categorys=articleContentVO.getCategorys()==null?new ArrayList<>():articleContentVO.getCategorys();
        //分类专栏不能超过三个
        if(categorys.size()>3){
           throw new ArticleInfoException("文章分类专栏不能超过三个");
        }
        for (CategoryPO category : categorys) {
            String categoryName= category.getCategoryName();
            categoryName=getTrueString(categoryName);
            if(categoryName.length()>15){
                categoryName=categoryName.substring(0,15);
            }
            category.setCategoryImg(null);
            category.setCategoryName(categoryName);
        }
        articleContentVO.setCategorys(categorys);
        return articleContentVO;
    }
}
