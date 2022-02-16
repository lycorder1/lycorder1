package com.yaocoder.myset.controllers.third;

import com.yaocoder.myset.common.RedisHelp;
import com.yaocoder.myset.common.Result;
import com.yaocoder.myset.secondReposition.UserRepository;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/third")
public class ThirdController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedisHelp redisHelp;

    @CrossOrigin
    @RequestMapping("/getUserBySid")
    Object getUserBySid(HttpServletRequest request, HttpSession session, String sid) {
//        var result = session.getAttribute(sid);

        return new Result(0, null, "success");
    }
}
