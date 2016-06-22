package com.warmlight.model;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by Administrator on 2016/6/22.
 */
@Entity
@Table(name = "t_vote")
public class VoteEntity {
    private Long id;
    private Long newsId;
    private Long userId;
    private Date votedDate;

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
    @Column(name = "voted_date")
    public Date getVotedDate() {
        return votedDate;
    }

    public void setVotedDate(Date votedDate) {
        this.votedDate = votedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VoteEntity that = (VoteEntity) o;

        if (id != that.id) return false;
        if (newsId != that.newsId) return false;
        if (userId != that.userId) return false;
        if (votedDate != null ? !votedDate.equals(that.votedDate) : that.votedDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (newsId ^ (newsId >>> 32));
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + (votedDate != null ? votedDate.hashCode() : 0);
        return result;
    }
}
