package com.warmlight.model;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/6/22.
 */
@Entity
@Table(name = "t_file")
public class FileEntity {
    private Long id;
    private String src;
    private Integer type;
    private Integer backgroundNo;
    private Long newsId;

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
    @Column(name = "src")
    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
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
    @Column(name = "background_no")
    public Integer getBackgroundNo() {
        return backgroundNo;
    }

    public void setBackgroundNo(Integer backgroundNo) {
        this.backgroundNo = backgroundNo;
    }

    @Basic
    @Column(name = "news_id")
    public Long getNewsId() {
        return newsId;
    }

    public void setNewsId(Long newsId) {
        this.newsId = newsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FileEntity that = (FileEntity) o;

        if (id != that.id) return false;
        if (newsId != that.newsId) return false;
        if (backgroundNo != null ? !backgroundNo.equals(that.backgroundNo) : that.backgroundNo != null) return false;
        if (src != null ? !src.equals(that.src) : that.src != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (src != null ? src.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (backgroundNo != null ? backgroundNo.hashCode() : 0);
        result = 31 * result + (int) (newsId ^ (newsId >>> 32));
        return result;
    }
}
