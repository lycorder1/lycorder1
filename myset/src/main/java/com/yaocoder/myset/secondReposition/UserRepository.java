package com.yaocoder.myset.secondReposition;

import com.yaocoder.myset.entitiesMysql.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository//代表此为一个dao层实现
public interface UserRepository extends JpaRepository<User, Integer>{

//    @Transactional(timeout = 60)

    @Query("select u from User u where u.name = ?1 and u.password = ?2 ")
    User getUserByNamePwd(String userName, String password);
}
