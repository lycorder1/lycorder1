package com.yaocoder.myset.controllers;

import com.yaocoder.myset.common.Common;
import com.yaocoder.myset.common.Result;
import com.yaocoder.myset.entities.FileRecord;
import com.yaocoder.myset.primaryReposition.FilesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * */
@RestController
//@RequestMapping(value = "/rest")
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

}
