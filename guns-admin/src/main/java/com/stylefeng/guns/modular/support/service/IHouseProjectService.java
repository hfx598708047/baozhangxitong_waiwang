package com.stylefeng.guns.modular.support.service;

import com.stylefeng.guns.modular.support.model.FamilySurvey;
import com.stylefeng.guns.modular.support.model.JointApplicant;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface IHouseProjectService {



    /**
     * 请求详细页
     *
     * @param sRecNumGather
     * @param iOpTypeNum
     * @param iRecYear
     * @param iRecNum
     * @return
     */
    Object projectDetail(String sRecNumGather,String iOpTypeNum,String iRecYear,String iRecNum);

    /**
     * 检查是否有权限创建项目
     * @param iOpTypeNum
     * @return
     */
    boolean checkCreatePermission(String iOpTypeNum);
    /**
     * 详细页数据
     * @param OpTypeNum
     * @param RecYear
     * @param RecNum
     * @return
     */
    Object projectData(String OpTypeNum,String RecYear,String RecNum);
    /**
     * 获取要打开的页面链接
     * @param iOpTypeNum
     * @param iRecYear
     * @param iRecNum
     * @return
     */
    String getPage(String iOpTypeNum,String iRecYear,String iRecNum);
    /**
     * 生成新项目
     * @param iOpTypeNum
     * @param iOpPartNum
     */
    Map<String,Object> createNewRec(String iOpTypeNum, String iOpPartNum);

    /**
     * 保存家庭信息
     * @param familySurvey
     */
    void saveFamilySurvey(FamilySurvey familySurvey);

    /**
     * 提交
     * @param param
     */
    void submitInfo( Map<String,Object> param);

    /**
     * 保存共同申请人
     * @param jointApplicant
     */
    void addJointApplicant(JointApplicant jointApplicant);

    /**
     * 更新配偶信息
     * @param familySurvey
     */
    void updateFamilySurveySpouse(FamilySurvey familySurvey);

    void savePicture(byte[] avatar, String OpTypeNum, String RecYear, String RecNum, String judge, String FamilyID);
}
