package com.stylefeng.guns.modular.support.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.core.common.Result;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.support.StrKit;
import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.support.dao.HouseProjectMapper;
import com.stylefeng.guns.modular.support.model.FamilySurvey;
import com.stylefeng.guns.modular.support.model.JointApplicant;
import com.stylefeng.guns.modular.support.service.IHouseProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HouseProjectImpl implements IHouseProjectService {

    @Autowired
    private HouseProjectMapper houseProjectMapper;

    @Override
    public Object projectDetail(String sRecNumGather,String iOpTypeNum,String iRecYear,String iRecNum){

        //ICOUNT =0 "提示", "此案卷已转至下一阶段,请在信息查询中查看"
        ////ICOUNT >0 调用 up_LogicId_Disp，判断该业务打开哪个界面
        Map<String,Object> openPagePrama = new HashMap<>();
        openPagePrama.put("iOpTypeNum",Convert.toInt(iOpTypeNum));
        openPagePrama.put("iRecYear",Convert.toInt(iRecYear));
        openPagePrama.put("iRecNum",Convert.toInt(iRecNum));
        openPagePrama.put("sPage","");
        openPagePrama.put("sMsg","");
        houseProjectMapper.openPage(openPagePrama);
        if(ToolUtil.isEmpty(openPagePrama.get("sPage"))){
            return Result.error(502,"该业务没有配置相应的输入界面!");
        }
        Map<String,Object> recListCurrentPrama = new HashMap<>();
        recListCurrentPrama.put("sRecNumGather",sRecNumGather);
        recListCurrentPrama.put("bSuccess",-1);
        recListCurrentPrama.put("RCSurface",new ArrayList<Map<String,Object>>());
        houseProjectMapper.recListCurrent(recListCurrentPrama);
        //获取案卷信息失败
        if(recListCurrentPrama.size() == 0){
            return Result.error(504,"案卷当前信息获取失败!");
        }
        return Result.success("success",openPagePrama);
    }

    @Override
    public Object projectData(String OpTypeNum,String RecYear,String RecNum){
        //获取项目详细
        Map<String,Object> projectDetailParam = new HashMap<>();
        projectDetailParam.put("OpTypeNum",OpTypeNum);
        projectDetailParam.put("RecYear",RecYear);
        projectDetailParam.put("RecNum",RecNum);
        Map<String,Object> project = houseProjectMapper.getProjectDetail(projectDetailParam);
        return project;
    }



    /**
     * 检查权限
     * @param iOpTypeNum
     * @return
     */
    @Override
    public boolean checkCreatePermission(String iOpTypeNum){
        String userId = ShiroKit.getUser().getId();
        Map<String,Object> param = new HashMap<>();
        param.put("iOpTypeNum",iOpTypeNum);
        param.put("iUserNum",userId);
        param.put("iUserOpPower",6);
        param.put("iSuccess",-1);
        houseProjectMapper.checkCreatePermission(param);
        if(Convert.toInt(param.get("iSuccess")) == 1){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 生成新项目
     * @param iOpTypeNum
     * @param iOpPartNum
     */
    @Override
    public Map<String,Object> createNewRec(String iOpTypeNum,String iOpPartNum){
        String userId = ShiroKit.getUser().getId();
        Map<String,Object> param = new HashMap<>();
        param.put("iOpTypeNum",iOpTypeNum);
        param.put("iOpPartNum",iOpPartNum);
        param.put("iUserNum",userId);
        param.put("iYear",-1);
        param.put("iMaxRecNum",-1);
        houseProjectMapper.createNewRec(param);
        return param;
    }

    @Override
    public String getPage(String iOpTypeNum,String iRecYear,String iRecNum){
        Map<String,Object> openPagePrama = new HashMap<>();
        openPagePrama.put("iOpTypeNum",Convert.toInt(iOpTypeNum));
        openPagePrama.put("iRecYear",Convert.toInt(iRecYear));
        openPagePrama.put("iRecNum",Convert.toInt(iRecNum));
        openPagePrama.put("sPage","");
        openPagePrama.put("sMsg","");
        houseProjectMapper.openPage(openPagePrama);
        return (String)openPagePrama.get("sPage");
    }

    @Override
    public void saveFamilySurvey(FamilySurvey familySurvey){
        houseProjectMapper.saveFamilySurvey(familySurvey);
    }

    @Override
    public void submitInfo( Map<String,Object> param){
        houseProjectMapper.submitInfo(param);
    }

    @Override
    public void addJointApplicant(JointApplicant jointApplicant){
        houseProjectMapper.addJointApplicant(jointApplicant);
    }

    @Override
    public void updateFamilySurveySpouse(FamilySurvey familySurvey){
        houseProjectMapper.updateFamilySurveySpouse(familySurvey);
    }

    @Override
    public void savePicture(byte[] avatar, String OpTypeNum, String RecYear, String RecNum, String judge, String FamilyID){
        try {
            // 文件保存路径
//               String filePath = request.getSession().getServletContext().getRealPath("/") + "upload/"
//                       + avatar.getOriginalFilename();
            // 转存文件
//               avatar.transferTo(new File(filePath));
            Map<String, Object> insertMap = new HashMap<String, Object>();
            insertMap.put("byt", avatar);
            insertMap.put("OPTYPENUM", OpTypeNum);
            insertMap.put("RECYEAR", RecYear);
            insertMap.put("RECNUM", RecNum);
            if(("1").equals(judge)){
                houseProjectMapper.savePicture(insertMap);
            }else{
                insertMap.put("FamilyID", FamilyID);
                houseProjectMapper.saveJointPicture(insertMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
