package com.yaocoder.myset.secondReposition;

import com.yaocoder.myset.entitiesMysql.User;
import com.yaocoder.myset.entitiesMysql.UserFriend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository//代表此为一个dao层实现
public interface UserFriendRepository extends JpaRepository<UserFriend, Integer>{

//    @Transactional(timeout = 60)

    @Query("select u from UserFriend u where u.UserId = ?1 and u.isRec = false ")
    List<UserFriend> getUserFriends(Integer userId);
}
