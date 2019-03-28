package com.stylefeng.guns.modular.support.controller;

import com.alibaba.fastjson.JSONObject;
import com.stylefeng.guns.config.properties.GunsProperties;
import com.stylefeng.guns.core.common.Result;
import com.stylefeng.guns.modular.support.model.file_yuVo;
import com.stylefeng.guns.modular.support.model.sbjbxx_yuVo;
import com.stylefeng.guns.modular.support.service.file_yuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.*;

@Controller
public class FileController {

    @Autowired
    public file_yuService sbfile;

    // 上传材料
    @RequestMapping("upload")
    @ResponseBody
    public Object scimage(HttpServletRequest request , Model model) throws Exception{

        int optypenum = Integer.valueOf(request.getParameter("optypenum"));
        int recyear = Integer.valueOf(request.getParameter("recyear"));
        int recnum = Integer.valueOf(request.getParameter("recnum"));
        int recmatnum = Integer.valueOf(request.getParameter("recmatnum"));

        MultipartHttpServletRequest Murequest = (MultipartHttpServletRequest)request;
        MultipartFile files = Murequest.getFile("file");

        file_yuVo file = new file_yuVo();

        file.setOptypenum(optypenum);
        file.setRecyear(recyear);
        file.setRecnum(recnum);
        file.setRecmatnum(recmatnum);
        file.setImagedescid(sbfile.selectimagedesdaul());
        if(sbfile.insertMatFile(file)==1){
            file.setImageid(sbfile.selectimagedaul());
            file.setImagefile(files.getBytes());
            /*insertFile(file);*/
            sbfile.insertFile(file);
        }

        JSONObject json = new JSONObject();
        json.put("code","0");
        json.put("msg","success");
        return json;

    }

    // 通过imageid获取文件流
    @RequestMapping("get_file")
    public String getFileByimageid(HttpServletRequest request,HttpServletResponse response, Model model) throws Exception{

        Map<String,Object> map = new HashMap<String,Object>();

        map.put("imageid", request.getParameter("imageid"));

      /*  file_yuVo files = sbfile.selectSignImage(map);*/
        response.setContentType("img/jpg");
        response.setCharacterEncoding("utf-8");
        getFile(map,response);
        /*OutputStream outputStream = null;
        InputStream in = null;
        // 文件预览
        try {

            outputStream=response.getOutputStream();
            in=new ByteArrayInputStream(files.getImagefile());

            int len=0;
            byte[]buf=new byte[1024];
            while((len=in.read(buf,0,1024))!=-1){
                outputStream.write(buf, 0, len);
            }
        } catch (IOException e) {
            throw new FileNotFoundException();
        } finally {
            if(outputStream != null){
                outputStream.close();
            }
            if(in != null){
                in.close();
            }
        }*/
        return null;
    }





    public static void insertFile(file_yuVo file) {
        Connection connection = null;
        try {
            String sql = " insert into bzfimage.Tbbwimage (imageid,imagefile,imagedescid)values(?,?,? ) ";
            connection = getORACLEConn();

          /*  File f = new File("c://myimage.png");
            FileInputStream fis = new FileInputStream(f);
*/
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, file.getImageid());
            ps.setBytes(2, file.getImagefile());
            ps.setInt(3, file.getImagedescid());
            ps.execute();

           /* fis = new FileInputStream(f);
            sql = "insert into imagetable (image_long_raw )values (? )";
            ps = connection.prepareStatement(sql);
            ps.setBinaryStream(1, fis, f.length());
            ps.execute();*/

        }  catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {

            try {
                connection.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public static void getFile( Map<String,Object> map,HttpServletResponse response) throws SQLException {

        ResultSet rs = null;
        Connection connection = null;
        InputStream in = null;

        try {
            OutputStream out = response.getOutputStream();
            String sql = "select * from bzfimage.Tbbwimage where imageid = " + map.get("imageid");
            connection = getORACLEConn();
            Statement stmt = connection.createStatement();
            rs = stmt.executeQuery(sql);

            while(rs.next()) {
                in = rs.getBinaryStream("imagefile");
                int len = 0;
                byte[] bytes = new byte[1024];
                while ((len = in.read(bytes)) != -1) {
                    out.write(bytes,0,len);
                }
                out.close();
                in.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            rs.close();
            connection.close();
        }


    }
    public static Connection getORACLEConn() throws ClassNotFoundException,
            SQLException {
        GunsProperties gp = new GunsProperties();
        Connection connection;
        Class.forName("oracle.jdbc.driver.OracleDriver");
        connection = DriverManager.getConnection(gp.getUrl(), gp.getUsername(), gp.getPassword());
        return connection;
    }
}
