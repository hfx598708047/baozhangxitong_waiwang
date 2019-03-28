package com.stylefeng.guns.modular.support.controller;


import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.modular.api.ExcelUtil;
import com.stylefeng.guns.modular.api.QRCodeUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;

@Controller
@RequestMapping("/excel")
public class ExcelController extends BaseController {


    @RequestMapping(value = "/apply_approval")
    @ResponseBody
    public void exportData(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //获取数据
        ArrayList<Map<String, Object>> list = new ArrayList<>();

        //excel标题
        String[] title = {"项目名称","累计完成度","投入资金","存在的问题"};
        //excel文件名
        String fileName = "审批模板"+System.currentTimeMillis()+".xls";
        //sheet名
        String sheetName = "项目信息表";
        //值
        String[][] content ;


        //创建HSSFWorkbook
//        HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);
        File excelFile = new File("D:/测试.xlsx"); //替换你文档地址
        XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(excelFile));
        ExcelUtil.excel2Pdf("D:/测试pdf.xlsx","D:/测试.pdf");
        QRCodeUtil.zxingCodeCreate("http://www.baidu.com", 300, 300, "D:/qrcode.jpg", "jpg");
        //响应到客户端
        try {
            this.setResponseHeader(response, fileName);
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //发送响应流方法
    public void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(),"ISO8859-1");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
