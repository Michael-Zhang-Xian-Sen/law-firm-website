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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class UserController {
    // 可以对类成员变量、方法及构造函数进行标注，完成自动装配的工作。
    @Autowired(required = false)
    private IUserDao iUserDao;

    /**
     * 增加用户
     * @param user
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping(value = "addUser",method = RequestMethod.POST)
    public void addUserInfo(User user, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(user.getName());
        System.out.println(user.getNickname());
        iUserDao.addUser(user);
        System.out.println("添加用户成功！下面开始查询用户！");
        System.out.println("查询用户中...");

        List<User> userList = iUserDao.findAll();
        JSONArray jsonArray = JSONArray.fromObject(userList);
        JsonUtils.ajaxJson(jsonArray.toString(),response);
    }

    /**
     * 更新用户信息
     * @param user
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping(value = "updateUser",method = RequestMethod.POST)
    public void updateUserInfo(User user, HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        System.out.println(user.getNickname());
        iUserDao.updateUser(user);
        System.out.println("更新完成！");
        User updatedUser = iUserDao.findByNickname(user.getNickname());
        System.out.println("pdatedUser为："+updatedUser);
        JSONObject jsonObject = JSONObject.fromObject(updatedUser);
        JsonUtils.ajaxJson(jsonObject.toString(),response);
    }

    /**
     * 删除用户
     * @param request
     * @param response
     */
    @RequestMapping(value = "deleteUser",method = RequestMethod.POST)
    public void deleteUserInfo(User user,HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        System.out.println("准备删除nickname");
        System.out.println("nickname为："+user.getNickname());
        int flag = iUserDao.deleteByNickname(user.getNickname());
        System.out.println("成功删除，flag为："+flag);
        Status status = new Status();
        status.setStatus("success");
        JSONObject jsonObject = JSONObject.fromObject(status);
        JsonUtils.ajaxJson(jsonObject.toString(),response);
    }


    /*
     *采用spring提供的上传文件的方法
     */
    @RequestMapping(value = "uploadImg",method = RequestMethod.POST)
    public void springUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws IllegalStateException, IOException, ServletException {
        System.out.println("准备存储图片！");
//      String path="C:\\Users\\Himory\\Desktop\\ideaSpringMVC\\src\\main\\webapp\\resources\\images\\ht_lawyer_firm\\head\\"+file.getOriginalFilename();
        String path="F:\\ht_lawyer_pic\\head\\"+file.getOriginalFilename();
        String fileName = file.getOriginalFilename();
        if(fileName != null){
            System.out.println("存放图片！");
            System.out.println("fileName为："+fileName);
            file.transferTo(new File(path));

            // 在数据库中更新
            User updateUser = new User();
            HttpSession session = request.getSession(false);
            updateUser.setHead_img("\\ht_lawyer_pic\\head\\"+fileName);
            updateUser.setNickname((String)session.getAttribute("nickname"));
            iUserDao.updateUserImg(updateUser);

            // 将对象返回给前端
            JSONObject jsonObject = JSONObject.fromObject(updateUser);
            JsonUtils.ajaxJson(jsonObject.toString(),response);
        }else{
            System.out.println("文件不存在！");
            Status status = new Status();
            status.setStatus("failed");
            JSONObject jsonObject = JSONObject.fromObject(status);
            JsonUtils.ajaxJson(jsonObject.toString(),response);
        }
    }

    @RequestMapping(value = "updateUserPrivateInfo",method = RequestMethod.POST)
    public void updateUserPrivateInfo(User u,HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        System.out.println("开始更新用户信息");
        Status status = new Status();
        HttpSession httpSession = request.getSession();
        u.setNickname((String)httpSession.getAttribute("nickname"));
        int flag = iUserDao.updateUserPrivateInfo(u);
        if(flag == 1){
            status.setStatus("success");
            JSONObject jsonObject = JSONObject.fromObject(status);
            JsonUtils.ajaxJson(jsonObject.toString(),response);
        }else{
            status.setStatus("failed");
            JSONObject jsonObject = JSONObject.fromObject(status);
            JsonUtils.ajaxJson(jsonObject.toString(),response);
        }
    }
}