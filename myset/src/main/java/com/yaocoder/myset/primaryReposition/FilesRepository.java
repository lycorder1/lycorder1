package com.yaocoder.myset.primaryReposition;

import com.yaocoder.myset.entities.FileRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository//代表此为一个dao层实现
public interface FilesRepository extends JpaRepository<FileRecord, Integer>{

    FileRecord findByDownCodeEquals(String downCode);

    @Query(value = "select t from FileRecord t where t.isRec=false ")
    List<FileRecord> allExistx();

    @Query(value = "select t from FileRecord t where t.userId=?1 order by t.createDate desc")
    List<FileRecord> getFilesByUid(int uid);
}
