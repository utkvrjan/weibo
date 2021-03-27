package com.zlt.weibo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zlt.weibo.entity.*;
import com.zlt.weibo.service.UserService;
import com.zlt.weibo.service.WeiBoService;
import com.zlt.weibo.service.impl.UserServiceImpl;
import com.zlt.weibo.service.impl.WeiBoServiceImpl;
import com.zlt.weibo.utils.*;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

@WebServlet("/user/*")
public class UserController extends BaseController {

    private UserService userService = new UserServiceImpl();
    private WeiBoService weiBoService = new WeiBoServiceImpl();

    /**
     * 显示主界面
     * @param request
     * @param response
     */
    public void main(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        User cUser = (User) request.getSession().getAttribute("CUR_USER");

        if(cUser != null){
            //查询粉丝数
            int followCount = userService.selectFollowCount(cUser.getUid());
            //查询关注数
            int aboutCount = userService.selectAboutCount(cUser.getUid());
            request.setAttribute("followCount",followCount);
            request.setAttribute("aboutCount",aboutCount);
        }
        //显示微博  分为登录后的微博显示和未登录时的微博显示

        String pageNow = request.getParameter("pageNow");

        String pageSize = request.getParameter("pageSize");

        //默认微博有一页
        if(!StringUtils.isNotNull(pageNow)){
            pageNow = "1";
        }

        //默认每页有5条微博数量
        if(!StringUtils.isNotNull(pageSize)){
            pageSize = "6";
        }

        Pager<WeiBo> pager = new Pager<>();
        pager.setPageNow(Integer.parseInt(pageNow));
        pager.setPageSize(Integer.parseInt(pageSize));

        //查询微博
        if(cUser == null) {
            //未登录
            pager = weiBoService.selectWeiboByNotLogin(pager);
        }else{
            //已经登录的
            pager = weiBoService.selectWeiboByLogin(pager,cUser.getUid());
        }
        request.setAttribute("pager",pager);

        //url重写
        HttpSession session = request.getSession();
        //System.out.println(response.encodeURL(request.getContextPath() + "/user/main"));
        request.getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(request,response);
    }

    public void logoutDo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        response.sendRedirect(request.getContextPath() + "/user/login");
    }

    /**
     * 获取网络ip地址
     * @param request
     * @param response
     */
    public void getAddress(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        String ip = request.getRemoteAddr();//获取用户的ip
        //System.out.println("用户的ip为"+ip);
        //获取响应的json
        String str = IPAddress.getIPAddress("183.228.20.127");
        //解析json
        JSONObject jsonObject = JSONUtils.parseJSONObject(str);
        String address = null;
        if("success".equals(jsonObject.getString("status"))){
            address = jsonObject.getString("country") + jsonObject.getString("regionName") + jsonObject.getString("city");
        }else{
            address = "ip异常";
        }
        //直接将字符串响应给前端
        response.getWriter().write(address);

    }

    /**
     * 发送私信
     * @param request
     * @param response
     */
    public void sendMessage(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        Message message = new Message();
        message.setMcontent(request.getParameter("mcontent"));
        User receiver = new User();
        receiver.setUid(request.getParameter("uid"));
        message.setReceiver(receiver);
        message.setSendTime(StringUtils.getCurrentDateTime());
        //设置私信主键
        message.setMid(StringUtils.randomUUID());
        User sender = (User) request.getSession().getAttribute("CUR_USER");
        message.setSender(sender);
        boolean isSuccess = userService.sendMessage(message);
        Result result = new Result();
        result.setState(isSuccess?0:1);
        //响应到客户端
        response.getWriter().write(JSON.toJSONString(result));
    }


    /**
     * 查询私信
     * @param request
     * @param response
     */
    public void getMessage(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {

        String uid = request.getParameter("uid");
        User receiver = (User) request.getSession().getAttribute("CUR_USER");
        List<Message> messages = userService.getMessage(uid,receiver.getUid());
        Result<List<Message>> result = new Result();
        result.setState(messages.size() > 0?0:1);
        result.setData(messages);
        //响应到客户端
        response.getWriter().write(JSON.toJSONString(result));
    }

    /**
     * 获取私信列表对象
     * @param request
     * @param response
     */
    public void getFriends(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {

        User receiver = (User) request.getSession().getAttribute("CUR_USER");

        List<User> users = userService.getFriends(receiver.getUid());
        Result<List<User>> result = new Result();
        result.setState(users.size() > 0?0:1);
        result.setData(users);
        //响应到客户端
        response.getWriter().write(JSON.toJSONString(result));
    }


    /**
     * 取消关注
     * @param request
     * @param response
     */
    public void unfollow(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        String uid = request.getParameter("uid");
        User cUser = (User) request.getSession().getAttribute("CUR_USER");
        userService.unfollow(cUser.getUid(),uid);
        //再跳转回个人界面
        response.sendRedirect(request.getContextPath() + "/user/personInfo?uid="+uid);

    }

    /**
     * 关注
     * @param request
     * @param response
     */
    public void follow(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        //个人信息界面的那个人
        String uid = request.getParameter("uid");
        User cUser = (User) request.getSession().getAttribute("CUR_USER");
        userService.follow(cUser.getUid(),uid);
        //再跳转回个人界面
        response.sendRedirect(request.getContextPath() + "/user/personInfo?uid="+uid);
    }

    /**
     * 搜索个人信息主界面
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void personInfo(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        //获取搜索人uid
        String uid = request.getParameter("uid");
        //加判断uid等于空怎么处理


        String pageNow = request.getParameter("pageNow");

        String pageSize = request.getParameter("pageSize");

        if(!StringUtils.isNotNull(pageNow)){
            pageNow = "1";
        }

        if(!StringUtils.isNotNull(pageSize)){
            pageSize = "5";
        }

        Pager<WeiBo> pager = new Pager<>();
        pager.setPageNow(Integer.parseInt(pageNow));
        pager.setPageSize(Integer.parseInt(pageSize));

        //根据uid找到user对象
        User user = userService.selectUserByUid(uid);
        request.setAttribute("user",user);
        if(user != null){
            //判断当前登录的人和这个人是否有关注的关系
            //该界面没有登录的时候其实也可以跳转进去
            User cUser = (User) request.getSession().getAttribute("CUR_USER");
            //当isFollow为false的时候，表示没有关注
            boolean isFollow = false;
            if(cUser != null){
                //没有登录的时候 显示关注，即isFollow为false
                //查询两人是否关注
                isFollow = userService.isFollow(cUser.getUid(),uid);
            }
            request.setAttribute("isFollow",isFollow);


            //在找到粉丝数和关注数
            int followCount = userService.selectFollowCount(user.getUid());
            //查询关注数
            int aboutCount = userService.selectAboutCount(user.getUid());
            request.setAttribute("followCount",followCount);
            request.setAttribute("aboutCount",aboutCount);
            //找到这个人的微博
            //查询微博  顺便查询是否点赞
            pager = weiBoService.selectWeiboByUid(pager,user.getUid(),cUser == null?"":cUser.getUid());
            request.setAttribute("pager",pager);
        }

        request.getRequestDispatcher("/WEB-INF/jsp/personInfo.jsp").forward(request,response);
    }

    /**
     * 显示搜索结果界面
     * @param request
     * @param response
     */
    public void search(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        //搜索人
        String keyWords = request.getParameter("keyWords");
        List<User> users = userService.searchUser(keyWords);

        request.setAttribute("users",users);
        request.getRequestDispatcher("/WEB-INF/jsp/searchResult.jsp").forward(request,response);
    }

    /**
     * 获取用户更新界面
     * @param request
     * @param response
     */
    public void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/update.jsp").forward(request,response);
    }

    /**
     * 处理更新信息的方法
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void updateDo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, FileUploadException {
        User user = new User();
        //使用第三方包的方式实现文件的上传
        FileItemFactory fileItemFactory = new DiskFileItemFactory();
        ServletFileUpload servletFileUpload = new ServletFileUpload(fileItemFactory);
        //所有的请求参数
        List<FileItem> fileItems = servletFileUpload.parseRequest(request);
        //解析每一个请求参数
        for(FileItem fileItem : fileItems){
            if(fileItem.isFormField()){//如果是普通的参数
                String name = fileItem.getFieldName();//参数名字
                String value = fileItem.getString();//参数的值
                //参数值如果有中文会出现乱码
                value = new String(value.getBytes("ISO-8859-1"),"UTF-8");
                //通过反射将name和value注入到对象中
                ClassUtils.invokeMethod(User.class,user,name,value);

            }else{//如果是文件参数
                if(fileItem.getSize() > 0){//确保有文件上传 才处理文件
                    //获取真实的文件名
                    String fileName = fileItem.getName();
                    //处理文件名
                    fileName = StringUtils.randomUUID() + fileName.substring(fileName.lastIndexOf("."));
                    //将处理以后的文件名  放入对象
                    user.setPhoto(fileName);
                    //开始处理文件上传
                    //??为什么调用getServletContext()不需要类或者对象
                    String path = getServletContext().getRealPath("files/photo");

                    FileUtils.copyInputStreamToFile(fileItem.getInputStream(),new File(path + File.separator + fileName));
                }
            }
        }
        //修改user到数据库
        userService.update(user);
        //需要将数据修改同步到session中
        request.getSession().setAttribute("CUR_USER",userService.selectUserByUid(user.getUid()));
        //修改完成去主页
        response.sendRedirect(request.getContextPath() + "/user/main");
    }

    /**
     * 获取登录界面
     * @param request
     * @param response
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request,response);
    }

    /**
     * 处理登录信息的方法
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void loginDo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String val = request.getParameter("val");
        //判断验证码
        HttpSession session = request.getSession();
        String validate = (String)session.getAttribute("val");
        if(!validate.equalsIgnoreCase(val)){//验证码不相同
            response.sendRedirect(request.getContextPath() + "/user/login");
            return;
        }
        //判断账号密码是否符合规范

        //执行登录
        User cu = new User();
        cu.setUsername(username);
        cu.setPassword(MD5Utils.getMd5(password));
        //验证登录
        User user = userService.login(cu);
        if(user != null){
            session.setAttribute("CUR_USER",user);//将登录用户放入session作用域
            response.sendRedirect(request.getContextPath() + "/user/main");
        }else{
            response.sendRedirect(request.getContextPath() + "/user/login");
        }
    }

    /**
     * 获取注册界面
     * @param request
     * @param response
     */
    public void reg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/reg.jsp").forward(request,response);
    }

    /**
     * 处理注册信息的方法
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void regDo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //接收参数--BeanUtils会根据请求参数自动解析请求对象
        //User user = BeanUtils.resolveBean(User.class,request);
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String nickname = request.getParameter("nickname");
        String sex = request.getParameter("sex");

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setNickname(nickname);
        user.setSex(sex);
        System.out.println(user);
        if(userService.reg(user)){
            response.sendRedirect(request.getContextPath()+"/user/login");
        }else{
            response.sendRedirect(request.getContextPath()+"/user/reg");
        }
    }


    /**
     * 图片验证码
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void validate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //随机的生成字符串
        String str = StringUtils.randomStr(4);
        System.out.println(str);
        //随机的字符串还需要存入session
        request.getSession().setAttribute("val",str);
        //将随机的字符串写入图片中
        //确认响应的内容是图片
        response.setContentType("image/JPEG");
        //创建图片对象
        BufferedImage image = new BufferedImage(80,20,BufferedImage.TYPE_INT_RGB);
        //获取图片的画笔
        Graphics g = image.createGraphics();
        //随机一种背景颜色 设置给画笔
        g.setColor(ColorUtils.getColor(1));
        //绘制一个满屏矩形
        g.fillRect(0,0,80,20);
        //再随机一种颜色设置成文字的颜色
        g.setColor(ColorUtils.getColor(0));
        //设置下绘制内容的字体
        g.setFont(new Font("黑体",Font.BOLD,18));
        //绘制文字  这个坐标是文字的左下角的点
        g.drawString(str,5,18);
        //添加障碍物 干扰物
        //干扰线条
        for(int i  = 0; i < 5;i++){
            g.setColor(ColorUtils.getColor(0));
            int x = (int) (Math.random() * 80);
            int y = (int) (Math.random() * 20);
            int x1 = (int) (Math.random() * 80);
            int y1 = (int) (Math.random() * 20);
            g.drawLine(x,y,x1,y1);//绘制线条，两点确认一线
        }

        //添加雪花
        for(int i  = 0; i < 5;i++){
            g.setColor(ColorUtils.getColor(0));
            int x = (int) (Math.random() * 80);
            int y = (int) (Math.random() * 20);
            g.drawString("*",x,y);
        }

        //销毁画笔
        g.dispose();
        //把图片响应给客户端
        ImageIO.write(image,"jpg",response.getOutputStream());


    }
}
