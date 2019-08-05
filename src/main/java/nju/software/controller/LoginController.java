package nju.software.controller;

import net.sf.json.JSONObject;
import nju.software.dao.IUserDao;
import nju.software.model.Status;
import nju.software.model.User;
import nju.software.util.JsonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class LoginController {
    private final Logger logger = LogManager.getLogger();

    // 可以对类成员变量、方法及构造函数进行标注，完成自动装配的工作。
    @Autowired
    private IUserDao iUserDao;

    @RequestMapping(value="/login",method= RequestMethod.GET)
    public String login(HttpServletRequest request){
        logger.info("尝试进入login页面！");

        HttpSession httpSession = request.getSession(false);
        if (httpSession == null){
            logger.info("当前不存在session");
        }else{
            logger.info("当前存在session"+httpSession);
        }

        return "views/login";
    }

    @RequestMapping(value="/login",method= RequestMethod.POST)
    public void checkUser(@RequestParam("name") String reqName, @RequestParam("password") String reqpw, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info(reqName+"用户尝试登陆！密码为："+reqpw);
        User user = iUserDao.findByNickname(reqName);
        logger.info("查询结果为：" + user);

        Status status = new Status();
        if (user != null){
            if(user.getPassword().equals(reqpw)){

                // 设置session及session属性
                HttpSession session = request.getSession();
                session.setAttribute("nickname", user.getNickname());
                session.setAttribute("name", user.getName());
                session.setAttribute("password", user.getPassword());
                session.setAttribute("gender", user.getGender());
                session.setAttribute("apartment", user.getApartment());
                session.setAttribute("job", user.getJob());
                session.setAttribute("contact", user.getContact());
                session.setAttribute("email", user.getEmail());
                //获取session的Id
                String sessionId = session.getId();
                System.out.println("sessionId为："+sessionId);

                logger.warn(reqName+"用户尝试登陆。成功！：");
                status.setStatus("success");
            }else{
                logger.warn(reqName+"用户尝试登陆。失败！：");
                status.setStatus("failed");
            }
            JSONObject json = JSONObject.fromObject(status);
            JsonUtils.ajaxJson(json.toString(), response);
        }else{
            // 此处可设置该用户没有注册
            logger.warn(reqName+"用户尝试登陆。失败！：");
            status.setStatus("failed");
            JSONObject json = JSONObject.fromObject(status);
            JsonUtils.ajaxJson(json.toString(), response);
        }





    }
}
