package com.yaocoder.myset.primaryReposition;

import com.yaocoder.myset.entities.GroupInformation;
import com.yaocoder.myset.entities.Information;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository//代表此为一个dao层实现
public interface GroupInfomationRepository extends JpaRepository<GroupInformation, Integer>{

    GroupInformation findByUserIdEquals(int userId);


    @Query(value = "select t from GroupInformation t where t.userId=?1 and t.oId=?2 and t.isRec=false order by t.createDate desc")
    List<GroupInformation> getInfoMsg(int uid,int oid);
}
