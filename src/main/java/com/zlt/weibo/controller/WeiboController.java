package com.zlt.weibo.controller;

import com.zlt.weibo.entity.Comments;
import com.zlt.weibo.entity.User;
import com.zlt.weibo.entity.WeiBo;
import com.zlt.weibo.service.WeiBoService;
import com.zlt.weibo.service.impl.WeiBoServiceImpl;
import com.zlt.weibo.utils.ClassUtils;
import com.zlt.weibo.utils.StringUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;

@WebServlet("/weibo/*")
public class WeiboController extends BaseController{
    private WeiBoService  weiBoService = new WeiBoServiceImpl();

    /**
     * 发送微博
     * @param request
     * @param response
     */
    public  void sendWeibo(HttpServletRequest request, HttpServletResponse response) throws FileUploadException, IOException {
        WeiBo weibo = new WeiBo();
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
                ClassUtils.invokeMethod(WeiBo.class,weibo,name,value);

            }else{//如果是文件参数
                if(fileItem.getSize() > 0){//确保有文件上传 才处理文件
                    //获取真实的文件名
                    String fileName = fileItem.getName();
                    //处理文件名
                    fileName = StringUtils.randomUUID() + fileName.substring(fileName.lastIndexOf("."));
                    //将处理以后的文件名  放入对象

                    //开始处理文件上传
                    String path = "";
                    String name = fileItem.getFieldName();
                    if("pic".equals(name)){
                        path = getServletContext().getRealPath("files/images");
                        weibo.setPic(fileName);
                    }else if("video".equals(name)){
                        path = getServletContext().getRealPath("files/video");
                        weibo.setVideo(fileName);
                    }
                    FileUtils.copyInputStreamToFile(fileItem.getInputStream(),new File(path + File.separator + fileName));
                }
            }
        }
        //设置发送者
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("CUR_USER");
        weibo.setSender(user);
        //保存微博到数据库
        weiBoService.saveWeibo(weibo);

        //修改完成去主页
        response.sendRedirect(request.getContextPath() + "/user/main");
    }

    /**
     * 点赞功能
     * @param request
     * @param response
     */
    public  void fabulous(HttpServletRequest request, HttpServletResponse response) throws FileUploadException, IOException {
        String wid = request.getParameter("wid");
        String url = request.getParameter("url");
        String uid = request.getParameter("uid");
        User cUser = (User) request.getSession().getAttribute("CUR_USER");
        weiBoService.fabulous(wid,cUser.getUid());
        response.sendRedirect(request.getContextPath() + url + "?uid=" + uid);
    }

    /**
     * 取消赞功能
     * @param request
     * @param response
     */
    public  void unfabulous(HttpServletRequest request, HttpServletResponse response) throws FileUploadException, IOException {
        String wid = request.getParameter("wid");
        String url = request.getParameter("url");
        String uid = request.getParameter("uid");
        User cUser = (User) request.getSession().getAttribute("CUR_USER");
        weiBoService.unfabulous(wid,cUser.getUid());
        response.sendRedirect(request.getContextPath() + url + "?uid=" + uid);
    }

    /**
     * 执行评论功能
     * @param request
     * @param response
     */
    public  void comments(HttpServletRequest request, HttpServletResponse response) throws FileUploadException, IOException {
        String wid = request.getParameter("wid");
        String content = request.getParameter("content");
        String url = request.getParameter("url");
        String uid = request.getParameter("uid");

        //执行评论
        User user = (User) request.getSession().getAttribute("CUR_USER");
        Comments comm = new Comments();
        comm.setCid(StringUtils.randomUUID());
        comm.setContent(content);
        comm.setUid(user);
        comm.setSendTime(StringUtils.getCurrentDateTime());
        comm.setWid(wid);

        weiBoService.saveComments(comm);

        response.sendRedirect(request.getContextPath() + url + "?uid=" + uid);
    }
}
