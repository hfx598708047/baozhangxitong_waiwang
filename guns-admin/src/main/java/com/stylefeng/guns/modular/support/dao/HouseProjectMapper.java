package com.stylefeng.guns.modular.support.dao;

import com.stylefeng.guns.modular.support.model.ApplicantReason;
import com.stylefeng.guns.modular.support.model.FamilySurvey;
import com.stylefeng.guns.modular.support.model.JointApplicant;
import com.stylefeng.guns.modular.support.model.file_yuVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface HouseProjectMapper {

   /**
    * 当前案卷基本信息
    *sRecNumGather
    *bSuccess
    *RCSurface
    * @param param
    */
   void recListCurrent(Map param);

   /**
     * iOpTypeNum
     *iRecYear
     *iRecNum
     *sPage
     *sMsg
     * @param param
     */
    void openPage(Map param);
    /**
     * 生成新项目
     */
    void createNewRec(Map param);
    /**
     * 检查用户创建项目权限
     * @param param
     * @return
     */
    Map<String,Object> checkCreatePermission(Map param);

    /**
     * 获取项目onwer详细
     * @param param
     * @return
     */
    Map<String,Object> getProjectDetail(Map param);

    List<Integer> getMatInfo(file_yuVo file_yuVo);
    /**
     * 材料列表
     * @param map
     */
    void getArchList(Map map);
    /**
     * 获取家庭情况调查表
     * @param OPTYPENUM
     * @param RECYEAR
     * @param RECNUM
     * @return
     */
    FamilySurvey getFamilySurvey(@Param("OPTYPENUM") Integer OPTYPENUM,@Param("RECYEAR") Integer RECYEAR,@Param("RECNUM") Integer RECNUM);

    //获取办理节点列表
    List<Map<String,Object>> getNodeList(Map map);

    //保存房屋信息
    void saveFamilySurvey(FamilySurvey familySurvey);

    /**
     * 保障房项目列表
     * @param param
     * @return
     */
    Map<String, Object> projectList(Map param);

   /**
    * 提交
    * @param param
    */
   void submitInfo( Map param);

    //保存共同申请人
    void addJointApplicant(JointApplicant jointApplicant);

    //删除共同申请人
    void deleteJointApplicant(Map param);

    //查找共同申请人
    List<JointApplicant> selectJointApplicant(Map param);

    //保存信息
    void saveOtherFamilySurvey(ApplicantReason applicantReason);

   //更新配偶信息
   void updateFamilySurveySpouse(FamilySurvey familySurvey);

    //保存信息
    void savePicture(Map<String, Object> param);

    String selectTypeNum(@Param("OPTYPENUM") Integer OPTYPENUM,@Param("RECYEAR") Integer RECYEAR,@Param("RECNUM") Integer RECNUM);

    void setCivilServant(@Param("OPTYPENUM") String OPTYPENUM,@Param("RECYEAR") String RECYEAR,@Param("RECNUM") String RECNUM);

    //保存共同申请人图像
    void saveJointPicture(Map<String, Object> param);

    //返回共同申请人
    JointApplicant getJointApplicant(@Param("OPTYPENUM") Integer OPTYPENUM,@Param("RECYEAR") Integer RECYEAR,@Param("RECNUM") Integer RECNUM,@Param("FamilyID") Integer FamilyID);
}
