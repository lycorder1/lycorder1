package com.yaocoder.myset.secondReposition;

import com.yaocoder.myset.entitiesMysql.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository//代表此为一个dao层实现
public interface UserRepository extends JpaRepository<User, Integer>{

}
