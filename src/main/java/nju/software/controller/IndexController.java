package nju.software.controller;

import net.sf.json.JSONArray;
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
import java.util.List;

@Controller
public class IndexController {
    private final Logger logger = LogManager.getLogger();

    @Autowired
    private IUserDao iUserDao;

    /**
     * 判断能否进入index页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="index",method= RequestMethod.GET)
    public String login(HttpServletRequest request, HttpServletResponse response){
        logger.info("尝试进入index页面");

        // 判断是否具有session
        HttpSession session = request.getSession(false);
        if(session == null){
            logger.info("不存在session，不允许进入index页面。即将返回登录页面！");
            return "views/login";
        }else{
            String user = (String)session.getAttribute("nickname");
            logger.info("用户"+user+"登录至系统");
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
        HttpSession session = request.getSession(false);
        String user = (String)session.getAttribute("nickname");
        User sendUser = iUserDao.findByNickname(user);
        logger.info("初始化用户"+user+"的信息");

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
        logger.info("尝试初始化\"用户管理\"模块信息");
        List<User> user = iUserDao.findAll();
        if(user != null) {
            JSONArray jsonArray = JSONArray.fromObject(user);
            JsonUtils.ajaxJson(jsonArray.toString(), response);
            logger.info("尝试初始化\"用户管理\"模块信息 成功！");

        }else{
            logger.warn("尝试初始化\"用户管理\"模块信息 失败！");
        }
    }

    /**
     * 退出登录
     */
    @RequestMapping(value = "logOut",method = RequestMethod.GET)
    public void logOutUser(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
        logger.info("尝试注销用户信息");
        HttpSession httpSession = request.getSession(false);
        // httpSession存在，注销httpSession,并且返回login页面
        if(httpSession != null){
            httpSession.invalidate();
            Status status = new Status();
            status.setStatus("success");
            JSONObject jsonObject = JSONObject.fromObject(status);
            JsonUtils.ajaxJson(jsonObject.toString(),response);
            logger.info("尝试注销用户信息 成功！");
        }else{
            logger.warn("尝试注销用户信息 失败！");
        }
    }

    @RequestMapping("")
    public String index(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "views/login";
    }
}