package com.warmlight.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/6/24.
 */
@Entity
@Table(name = "t_user")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
public class UserView {
    private Long id;
    private String userImg;
    private String realName;

    public UserView() {}

    @Id
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_img")
    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    @Basic
    @Column(name = "real_name")
    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserView userView = (UserView) o;

        if (id != userView.id) return false;
        if (realName != null ? !realName.equals(userView.realName) : userView.realName != null) return false;
        if (userImg != null ? !userImg.equals(userView.userImg) : userView.userImg != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (userImg != null ? userImg.hashCode() : 0);
        result = 31 * result + (realName != null ? realName.hashCode() : 0);
        return result;
    }
}
