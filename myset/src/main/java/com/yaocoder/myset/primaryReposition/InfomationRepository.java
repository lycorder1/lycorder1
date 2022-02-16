package com.yaocoder.myset.primaryReposition;

import com.yaocoder.myset.entities.Information;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository//代表此为一个dao层实现
public interface InfomationRepository extends JpaRepository<Information, Integer>{

    Information findByUserIdEquals(int userId);



    @Query(value = "select t from Information t where t.userId=?1 and t.oId=?2 and t.isRec=false order by t.createDate desc")
    List<Information> getInfoMsg(int uid,int oid);


    @Query(value = "select t from Information t where t.isRec = false and (t.userId =?1 and t.oId =?2) or (t.userId =?2 and t.oId =?1) order by t.createDate asc")
    List<Information> getNewMsg(int uid,int oid);

//    @Query(value =" select t from Information t left join User u1 on u1.id = t.oId  left join User u2 on u2.id = t.userId  where t.isRec = false and (t.userId =?1 and t.oId =?2) or (t.userId =?2 and t.oId =?1) order by t.createDate asc" )
//    List<Information> getNewMsg1(int uid, int oid);
//    @Query(value = "select t from Information t ")
//    List<Information> getNewMsg(int uid,int oid);
}
