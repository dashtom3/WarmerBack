package com.warmlight.model;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by Administrator on 2016/6/24.
 */
@Entity
@Table(name = "t_comment")
public class CommentEntity {
    private Long id;
    private Long newsId;
    private Long userId;
    private Date publishDate;
    private String comment;
    private Integer type;
    private String voiceSrc;
    private Long toUserId;


    private UserView author;

    private UserView toUser;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "to_user_id", insertable = false, updatable = false)
    public UserView getToUser() {
        return toUser;
    }

    public void setToUser(UserView toUser) {
        this.toUser = toUser;
    }

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    public UserView getAuthor() {
        return author;
    }

    public void setAuthor(UserView author) {
        this.author = author;
    }


    @Basic
    @Column(name = "to_user_id")
    public Long getToUserId() {
        return toUserId;
    }

    public void setToUserId(Long toUserId) {
        this.toUserId = toUserId;
    }

    @Basic
    @Column(name = "type")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Basic
    @Column(name = "voice_src")
    public String getVoiceSrc() {
        return voiceSrc;
    }

    public void setVoiceSrc(String voiceSrc) {
        this.voiceSrc = voiceSrc;
    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "news_id")
    public Long getNewsId() {
        return newsId;
    }

    public void setNewsId(Long newsId) {
        this.newsId = newsId;
    }

    @Basic
    @Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "publish_date")
    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    @Basic
    @Column(name = "comment")
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommentEntity that = (CommentEntity) o;

        if (id != that.id) return false;
        if (newsId != that.newsId) return false;
        if (userId != that.userId) return false;
        if (comment != null ? !comment.equals(that.comment) : that.comment != null) return false;
        if (publishDate != null ? !publishDate.equals(that.publishDate) : that.publishDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (newsId ^ (newsId >>> 32));
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + (publishDate != null ? publishDate.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        return result;
    }
}
