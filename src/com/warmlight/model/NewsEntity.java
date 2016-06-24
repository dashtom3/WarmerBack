package com.warmlight.model;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.RowId;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import java.io.File;
import java.sql.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
@Entity
@Table(name = "t_news")
public class NewsEntity {
    private Long id;
    private Long userId;
    private String content;
    private Date publishDate;
    private Long votedAmount;

    private List<FileEntity> files;

    private List<CommentEntity> comments;


    private UserView author;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    public UserView getAuthor() {
        return author;
    }

    public void setAuthor(UserView author) {
        this.author = author;
    }


    @OneToMany(cascade = CascadeType.REFRESH,fetch=FetchType.LAZY, mappedBy="newsId")
    @Where(clause = "id = (select my_comment.id from t_comment my_comment where my_comment.news_id = news_id order by my_comment.publish_date desc limit 1)")
    public List<CommentEntity> getComments() {
        return comments;
    }

    public void setComments(List<CommentEntity> comments) {
        this.comments = comments;
    }

    @OneToMany(cascade = CascadeType.REFRESH,fetch=FetchType.LAZY,mappedBy="newsId")
    public List<FileEntity> getFiles() {
        return files;
    }

    public void setFiles(List<FileEntity> files) {
        this.files = files;
    }

    @Basic
    @Column(name = "voted_amount")
    public Long getVotedAmount() {
        return votedAmount;
    }

    public void setVotedAmount(Long votedAmount) {
        this.votedAmount = votedAmount;
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
    @Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "publish_date")
    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewsEntity that = (NewsEntity) o;

        if (id != that.id) return false;
        if (userId != that.userId) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (publishDate != null ? !publishDate.equals(that.publishDate) : that.publishDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (publishDate != null ? publishDate.hashCode() : 0);
        return result;
    }
}
