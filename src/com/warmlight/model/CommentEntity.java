package com.warmlight.model;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by Administrator on 2016/6/22.
 */
@Entity
@Table(name = "t_comment")
public class CommentEntity {
    private Long id;
    private Long newsId;
    private Long userId;
    private Date publishDate;
    private String comment;
    private Long votedAmount;

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

    @Basic
    @Column(name = "voted_amount")
    public Long getVotedAmount() {
        return votedAmount;
    }

    public void setVotedAmount(Long votedAmount) {
        this.votedAmount = votedAmount;
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
        if (votedAmount != null ? !votedAmount.equals(that.votedAmount) : that.votedAmount != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (newsId ^ (newsId >>> 32));
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + (publishDate != null ? publishDate.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (votedAmount != null ? votedAmount.hashCode() : 0);
        return result;
    }
}
