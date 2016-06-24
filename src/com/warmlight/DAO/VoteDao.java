package com.warmlight.DAO;

import com.warmlight.model.VoteEntity;

/**
 * Created by Administrator on 2016/6/24.
 */
public interface VoteDao {
    boolean saveVote(VoteEntity vote);
    VoteEntity getVoteByNewsAndUser(Long newsId,Long userId);
    boolean deleteVoteByNewsAndUser(Long newsId,Long userId);
}
