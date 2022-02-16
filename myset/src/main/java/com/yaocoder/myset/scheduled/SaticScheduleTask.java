package com.yaocoder.myset.scheduled;

import com.bestvike.linq.Linq;
import com.yaocoder.myset.entities.FileRecord;
import com.yaocoder.myset.primaryReposition.FilesRepository;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class SaticScheduleTask {
    ExecutorService service = Executors.newFixedThreadPool(5);

    @Autowired
    private FilesRepository repository;

    //3.添加定时任务
    @Scheduled(cron = "0 0 1 * * ?")
    //或直接指定时间间隔，例如：5秒
//    @Scheduled(fixedRate=5000)
    private void configureTasks() {
        service.execute(()->{
            Sort sort = Sort.by(Sort.Direction.ASC,"createDate");
            Pageable pageable = PageRequest.of(0,100,sort);
            var resultSum = repository.findAll(pageable);
            var content = resultSum.getContent();
//            var list = repository.findAll();
            var effectContent = Linq.of(content).where(x -> x.isRec() == false).toList();

            List<FileRecord> flist = new ArrayList<FileRecord>();
            for(var item : content){
                if(item.getEffectiveDate().compareTo(new Date())<=0&&!item.isRec()){
                    File file = new File(item.getPath());
                    //对超过有效期的文件进行删除
                    file.delete();
                    item.setRec(true);
                    flist.add(item);
                }
            }

            repository.saveAll(flist);
            System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
        });
        //目前只做前100 条数据 后面数据再说
    }
}