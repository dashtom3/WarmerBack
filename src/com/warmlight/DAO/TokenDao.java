package com.warmlight.DAO;

import com.warmlight.model.TokenEntity;

/**
 * Created by Administrator on 2016/6/23.
 */
public interface TokenDao {
    boolean addToken(TokenEntity token);
    boolean deleteToken(Long id);
    TokenEntity getByUserId(Long userId);
    TokenEntity getByTokenString(String tokenString);
    boolean updateToken(TokenEntity token);

}
