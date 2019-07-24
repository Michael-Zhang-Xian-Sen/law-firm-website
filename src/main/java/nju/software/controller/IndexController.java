package nju.software.controller;

import net.sf.json.JSONArray;
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
import java.util.List;

@Controller
public class IndexController {

    @Autowired(required = false)
    private IUserDao iUserDao;

    /**
     * 判断能否进入index页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="index",method= RequestMethod.GET)
    public String login(HttpServletRequest request, HttpServletResponse response){
        System.out.println("判断能否进入index页面！");

        // 判断是否具有session
        HttpSession session = request.getSession(false);
        if(session == null){
            System.out.println("不存在session，不允许进入index页面，并返回登录页面！");
            return "views/login";
        }else{
            System.out.println("存在session，允许进入index页面");
            // 输出session信息
            System.out.println("观察元素内容："+session.getAttribute("nickname"));

            return "views/index";
        }
    }

    /**
     * 初始化"用户信息"
     * @param postMessage
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping(value="initIndexUser",method = RequestMethod.POST)
    public void initUser(@RequestParam("message") String postMessage, HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
        System.out.println("开始获取用户个人信息！");
        System.out.println("POST请求中的message为："+postMessage);

        // 从数据库中查询该用户的信息
        HttpSession session = request.getSession(false);
        User sendUser = iUserDao.findByNickname((String)session.getAttribute("nickname"));

        // 将数据返回给前端
        JSONObject jsonObject = JSONObject.fromObject(sendUser);
        JsonUtils.ajaxJson(jsonObject.toString(), response);
    }

    /**
     * 初始化"用户管理"栏目信息
     * @param postMessage
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping(value = "initUserManagement",method = RequestMethod.POST)
    public void initUserManagement(@RequestParam("message") String postMessage, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("开始获取用户管理信息！");
        // 查表
        List<User> user = iUserDao.findAll();
        if(user != null) {
            System.out.println("查找用户管理信息成功！");
            // 将数据返回给前端
            JSONArray jsonArray = JSONArray.fromObject(user);
            JsonUtils.ajaxJson(jsonArray.toString(), response);
        }else{
            System.out.println("查找用户管理信息失败！");
        }
    }

    /**
     * 退出登录
     */
    @RequestMapping(value = "logOut",method = RequestMethod.GET)
    public void logOutUser(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
        HttpSession httpSession = request.getSession(false);
        // httpSession存在，注销httpSession,并且返回login页面
        if(httpSession != null){
            httpSession.invalidate();
            System.out.println("成功注销session！");
            Status status = new Status();
            status.setStatus("success");
            JSONObject jsonObject = JSONObject.fromObject(status);
            JsonUtils.ajaxJson(jsonObject.toString(),response);
        }else{
            System.out.println("注销session失败！");
        }
    }

    @RequestMapping("")
    public String index(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "views/login";
    }
}