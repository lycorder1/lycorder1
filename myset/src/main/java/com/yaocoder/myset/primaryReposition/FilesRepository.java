package com.yaocoder.myset.primaryReposition;

import com.yaocoder.myset.entities.Files;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository//代表此为一个dao层实现
public interface FilesRepository extends JpaRepository<Files, Integer>{

}
