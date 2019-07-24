package nju.software.controller;

import net.sf.json.JSONObject;
import nju.software.dao.IUserDao;
import nju.software.model.Status;
import nju.software.model.User;
import nju.software.util.JsonUtils;
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

    // 可以对类成员变量、方法及构造函数进行标注，完成自动装配的工作。
    @Autowired(required = false)
    private IUserDao iUserDao;

    @RequestMapping(value="/login",method= RequestMethod.GET)
    public String login(HttpServletRequest request){
        System.out.println("进入login页面！");
        System.out.println("22222！");
        HttpSession httpSession = request.getSession(false);
        System.out.println("session值为："+httpSession);

        String dir = System.getProperty("user.dir");
        System.out.println("dir为："+dir);

        return "views/login";
    }

    @RequestMapping(value="/login",method= RequestMethod.POST)
    public void chceckUser(@RequestParam("name") String reqName, @RequestParam("password") String reqpw, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 从数据库中获取数据
        System.out.println("接收到post数据！");
        System.out.println("post用户名："+reqName);
        System.out.println("post密码："+reqpw);

        User user = iUserDao.findByNickname(reqName);
        System.out.println("查询结果为："+user);
        Status status = new Status();


        // 密码正确，设置用户session，返回状态码
        if(user!=null) {
            System.out.println("用户对象不为空");
        }else{
            System.out.println("用户对象为空！");
        }
        if(user.getPassword().equals(reqpw)){
            System.out.println("密码正确");
        }else{
            System.out.println("密码错误！");
            System.out.println("请求中的密码为："+reqpw);
            System.out.println("数据库中的密码为："+user.getPassword());
        }

        if(user != null && user.getPassword().equals(reqpw)){
            System.out.println("nickname为：" + user.getNickname());
            System.out.println("name为：" + user.getName());
            System.out.println("密码验证正确！");

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

            status.setStatus("success");
        }else{
            System.out.println("密码验证失败！");
            status.setStatus("failed");
        }
        System.out.println("验证结束！");
        System.out.println();

        // 将数据返回给前端
        JSONObject json = JSONObject.fromObject(status);
        JsonUtils.ajaxJson(json.toString(), response);
    }
}
