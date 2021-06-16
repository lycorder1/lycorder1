package com.yaocoder.myset.server;

import com.yaocoder.myset.primaryReposition.FilesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FilesServiceImpl{
    /** 日志处理类 */
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private FilesRepository repository;

//    @Override
//    public Files queryById(Integer id) throws Exception {
////        try {
////            Files result = repository.findOne(id);
////            log.info(result.toString());
////            return result;
////        }catch (Exception e) {
////            log.info(e.toString(),e);
////            throw new ServiceException("根据id查询时发生异常!");
////        }
//        returnn null;
//    }
}