package com.stylefeng.guns.modular.support.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.Result;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.modular.support.dao.HouseProjectMapper;
import com.stylefeng.guns.modular.support.model.*;
import com.stylefeng.guns.modular.support.service.DicService;
import com.stylefeng.guns.modular.support.service.IHouseProjectService;
import com.stylefeng.guns.modular.support.service.file_yuService;
import jdk.nashorn.internal.scripts.JO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/house")
public class ApplicationController extends BaseController {
    private String PREFIX = "/supporthouse/";

    @Autowired
    private HouseProjectMapper houseProjectMapper;
    @Autowired
    private IHouseProjectService houseProjectService;

    @Autowired
    private file_yuService file_yuService;

    @Autowired
    private DicService dicService;
    /**
     * 项目申请
     * @return
     */
    @RequestMapping("/application")
    public String indexApplication(){
        String idCard = ShiroKit.getUser().getIdCard();

        return PREFIX + "application.html";
    }

    /**
     * 项目和申请列表
     * @return
     */
    @RequestMapping("/project/31")
    public String index(Model model){
        model.addAttribute("sRECSTATECODE","0");
        return PREFIX + "index.html";
    }

    /**
     * 项目和申请列表
     * @return
     */
    @RequestMapping("/project/32")
    public String indexQuery(Model model){
        model.addAttribute("sRECSTATECODE","1");
        return PREFIX + "index.html";
    }
    /**
     * 项目和申请列表
     * @param offset
     * @param limit
     * @param sOPTYPENUM
     * @param sOPPARTNUM
     * @param sRecNumGather
     * @param iBATCHNUM
     * @param StartDate
     * @param EndDate
     * @param iFilterMode
     * @param iRecUserNum
     * @param IsEspacial
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public Object projectList(Integer offset,Integer limit,String sRECSTATECODE,String sOPTYPENUM,String sOPPARTNUM,String sRecNumGather ,Integer iBATCHNUM ,String StartDate,String EndDate,Integer iFilterMode,Integer iRecUserNum,Integer IsEspacial){

        String userName = ShiroKit.getUser().getName();
        String userIdCard = ShiroKit.getUser().getIdCard();
        String id = ShiroKit.getUser().getId();
        List<Map<String,Object>> projectList = new ArrayList<Map<String,Object>>();

        Map<String,Object> param = new HashMap<>();
        param.put("iUserNum",id);//用户ID 202001
      /*  param.put("sOPGROUPCODE",31);//项目 30，保障房申请 31
        param.put("sOPTYPENUM","");//业务类型编号
        param.put("sOPPARTNUM","");//业务细项编号
        param.put("sBarCode",null);//条形码，默认 NULL
     *//*   param.put("sRecNumGather",userName + userIdCard);//综合条件*//*
        param.put("sRecNumGather",null);//综合条件
        param.put("iBATCHNUM",iBATCHNUM);//批号，默认 NULL
        param.put("StartDate",StartDate);//开始日期
        param.put("EndDate",EndDate);//结束日期
        param.put("iFilterMode",iFilterMode);//1,待办件，esle，所有件
        param.put("iRecUserNum",id);//收件人编号
        param.put("ischecked",null);//是否签收，NULL
        param.put("sRECSTATECODE",sRECSTATECODE);//案卷状态，0
        param.put("iStart",offset);//0
        param.put("iEnd",limit + offset);//NULL
        param.put("IsEspacial",IsEspacial);//转交箱复选框
        param.put("iStep",null);//阶段

        param.put("bSuccess",null);//NULL
        param.put("iCount",0);//转交箱复选框*/
        param.put("RCQry",projectList);//阶段

        houseProjectMapper.projectList(param);
        projectList = (List)param.get("RCQry");
        List<Map<String,Object>> newProjectList = new ArrayList<Map<String,Object>>();
        //构建新的数据集，排除掉空的数据
        for(Map<String,Object> map : projectList){
            if(!map.get("申请人").toString().equals("-")){
                if(sRECSTATECODE.equals("0")){
                    if(map.get("案卷状态").toString().equals("待提交")){
                        map.put("IROW",newProjectList.size()+1);
                        newProjectList.add(map);
                    }
                }else if(sRECSTATECODE.equals("1")){
                    if(map.get("案卷状态").toString().equals("在办")){
                        map.put("IROW",newProjectList.size()+1);
                        newProjectList.add(map);
                    }
                }
            }
        }
        Page page = new Page();
        page.setRecords(newProjectList);
        page.setTotal(newProjectList.size());
        /*page.setCurrent(offset / limit + 1);*/
        return super.packForBT(page);
    }

    /**
     * 节点列表
     * @param iOptypenum
     * @param iRecyear
     * @param iRecnum
     * @param model
     * @return
     */
    @RequestMapping("/node_list")
    public String nodeList(String iOptypenum,String iRecyear,String iRecnum,Model model){
        Map param = new HashMap();
        param.put("iOptypenum",iOptypenum);
        param.put("iRecyear",iRecyear);
        param.put("iRecnum",iRecnum);
        param.put("rcList",new ArrayList<Map<String,Object>>());
        houseProjectMapper.getNodeList(param);
        model.addAttribute("nodeList",param.get("rcList"));
        return PREFIX + "node.html";
    }

    /**
     * 创建新项目和申请
     * @param iOpTypeNum
     * @param iOpPartNum
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Object create(String iOpTypeNum,String iOpPartNum,String isCivilServant){
        boolean isHavePermission = houseProjectService.checkCreatePermission(iOpTypeNum);
        if(isHavePermission){
            Map<String,Object> newRec = houseProjectService.createNewRec(iOpTypeNum,iOpPartNum);

            String iYear = String.valueOf(newRec.get("iYear"));
            String iMaxRecNum = String.valueOf(newRec.get("iMaxRecNum"));
            String page = houseProjectService.getPage(iOpTypeNum,iYear,iMaxRecNum);
            if( "".equals(page) ){
                return Result.error(502,"该业务没有配置相应的输入界面!");
            }
            if(("1").equals(isCivilServant)){
                houseProjectMapper.setCivilServant(iOpTypeNum,iYear,iMaxRecNum);
            }
            newRec.put("sPage",page);
            return Result.success("操作成功",newRec);
        }else {
            return Result.error(505,"无权限");
        }
    }

    /**
     *项目详细
     */
    @RequestMapping("/detail")
    public String projectDetail(String page,String OpTypeNum,String RecYear,String RecNum,Model model){
        Map param = new HashMap();
        param.put("iOptypenum",OpTypeNum);
        param.put("iRecyear",RecYear);
        param.put("iRecnum",RecNum);
        param.put("rcList",new ArrayList<Map<String,Object>>());
        //节点列表
        houseProjectMapper.getNodeList(param);

        List<Dic> idCardList = dicService.getIdCard();
        model.addAttribute("idCardList",idCardList);

        //查询材料类型字典值
        List<Dic> materialList = dicService.getMaterial();
        model.addAttribute("materialList",materialList);
        model.addAttribute("materialListJson",JSON.toJSONString(materialList));
        //查询家庭类型字典值
        List<Dic> familyType = dicService.getFamilyType();
        model.addAttribute("familyType",familyType);
        model.addAttribute("familyTypeJson",JSON.toJSONString(familyType));

        //房屋建筑结构字典值
        List<Dic> buildingStructureList = dicService.getBuildingStructure();
        model.addAttribute("buildingStructureListJson",JSON.toJSONString(buildingStructureList));
        //房屋户型字典值
        List<Dic> houseTypeList = dicService.getHouseType();
        model.addAttribute("houseTypeListJson",JSON.toJSONString(houseTypeList));
        //房屋用途字典值
        List<Dic> houseuSageList = dicService.getHouseuSage();
        model.addAttribute("houseuSageListJson",JSON.toJSONString(houseuSageList));

        //基本信息
        if("80".equals(OpTypeNum)){
            Object project = houseProjectService.projectData(OpTypeNum,RecYear,RecNum);
            model.addAttribute("project",project);
        }else if("81".equals(OpTypeNum)){
            FamilySurvey apply = houseProjectMapper.getFamilySurvey(Convert.toInt(OpTypeNum),Convert.toInt(RecYear),Convert.toInt(RecNum));
            //判断点击的是否为公租房公务员
            if("1".equals(apply.getCivilServant().toString())){
                model.addAttribute("civilServant","1");
            }else{
                model.addAttribute("civilServant","0");
            }
            Map<String,Object> arch = new HashMap<>();
            arch.put("iOpTypeNum",OpTypeNum);
            arch.put("iRecYear",RecYear);
            arch.put("iRecNum",RecNum);
            arch.put("IsReadOnly",0);
            arch.put("curRec",new ArrayList<>());
            houseProjectMapper.getArchList(arch);
            model.addAttribute("apply",apply);
            model.addAttribute("archList",arch.get("curRec"));
            ArrayList<HashMap> arrayList = (ArrayList)arch.get("curRec");
            //获取文件材料最大的数值，便于前端展示
            BigDecimal neWrecmattum = new BigDecimal("1");
            BigDecimal recmattum = new BigDecimal("0");
            for(int i = 0;i<arrayList.size();i++){
                recmattum = (BigDecimal)arrayList.get(i).get("RECMATNUM");
                if(neWrecmattum.compareTo(recmattum)< 1){
                    neWrecmattum = recmattum;
                }
            }
            //获取共同申请人列表
            List<JointApplicant> jointApplicantList = houseProjectMapper.selectJointApplicant(param);
            //共同申请人为配偶
            JointApplicant spouse = new JointApplicant();
            //获取配偶信息
            for(int i = 0;i<jointApplicantList.size();i++){
                if(jointApplicantList.get(i).getJointApplicantRelation().equals("2") || jointApplicantList.get(i).getJointApplicantRelation().equals("9")){
                    //提取配偶信息
                    spouse = jointApplicantList.get(i);
                    //移除
                    jointApplicantList.remove(i);
                    break;
                }
            }
            model.addAttribute("spouse",spouse);
            model.addAttribute("jointApplicantList",jointApplicantList);
            model.addAttribute("jointApplicantListSize",jointApplicantList.size());
        }else if("82".equals(OpTypeNum)){//腾退业务
            FamilySurvey apply = houseProjectMapper.getFamilySurvey(Convert.toInt(OpTypeNum),Convert.toInt(RecYear),Convert.toInt(RecNum));
            Map<String,Object> arch = new HashMap<>();
            arch.put("iOpTypeNum",OpTypeNum);
            arch.put("iRecYear",RecYear);
            arch.put("iRecNum",RecNum);
            arch.put("IsReadOnly",0);
            arch.put("curRec",new ArrayList<>());
            houseProjectMapper.getArchList(arch);
            model.addAttribute("apply",apply);
            ArrayList<HashMap> arrayList = (ArrayList)arch.get("curRec");
            //获取文件材料最大的数值，便于前端展示
            BigDecimal neWrecmattum = new BigDecimal("1");
            BigDecimal recmattum = new BigDecimal("0");
            for(int i = 0;i<arrayList.size();i++){
                recmattum = (BigDecimal)arrayList.get(i).get("RECMATNUM");
                if(neWrecmattum.compareTo(recmattum)< 1){
                    neWrecmattum = recmattum;
                }
            }
            model.addAttribute("RECMATNUM",neWrecmattum);
            model.addAttribute("archList",arch.get("curRec"));
        }

        model.addAttribute("nodeList",param.get("rcList"));

        return page;
    }

    /**
     * 获取请求页面
     * @param sRecNumGather
     * @param iOpTypeNum
     * @param iRecYear
     * @param iRecNum
     * @return
     */
    @RequestMapping("/get_page_info")
    @ResponseBody
    public Object projectDetailPage(String sRecNumGather,String iOpTypeNum,String iRecYear,String iRecNum){
        return houseProjectService.projectDetail(sRecNumGather,iOpTypeNum,iRecYear,iRecNum);
    }

    /**
     * 图片上传页面
     * @param iOptypenum
     * @param iRecyear
     * @param iRecnum
     * @param iRecMatnum
     * @param model
     * @return
     */
    @RequestMapping("/open_upload")
    public String uploadPage(String iOptypenum,String iRecyear,String iRecnum,String iRecMatnum,Model model){

        model.addAttribute("iOptypenum",iOptypenum);
        model.addAttribute("iRecyear",iRecyear);
        model.addAttribute("iRecnum",iRecnum);
        model.addAttribute("iRecMatnum",iRecMatnum);
        return PREFIX + "upload_page.html";
    }

    /**
     * 图片展示页面
     * @param iOptypenum
     * @param iRecyear
     * @param iRecnum
     * @param iRecMatnum
     * @param model
     * @return
     */
    @RequestMapping("/show_image")
    public String showImagePage(String iOptypenum,String iRecyear,String iRecnum,String iRecMatnum,Model model){

        file_yuVo file = new file_yuVo();
        file.setOptypenum(Convert.toInt(iOptypenum));
        file.setRecyear(Convert.toInt(iRecyear));
        file.setRecnum(Convert.toInt(iRecnum));
        file.setRecmatnum(Convert.toInt(iRecMatnum));
        List<Integer> imageIdList = houseProjectMapper.getMatInfo(file);
        model.addAttribute("imageIdList",imageIdList);
        return PREFIX + "show_image.html";
    }

    @RequestMapping(value = "/save_family")
    @ResponseBody
    public Object saveFamily(String json) {
        FamilySurvey familySurvey =  JSON.parseObject(json,FamilySurvey.class);
        houseProjectService.saveFamilySurvey(familySurvey);
        return SUCCESS_TIP;
    }

    @RequestMapping(value = "/save_file_yu")
    @ResponseBody
    public Object saveFileYu(@RequestBody  List<file_yuVo> file_yuVoList) {
        try{
            sbjbxx_yuVo sbjbxxYuVo = new sbjbxx_yuVo();
            sbjbxxYuVo.setOptypenum(file_yuVoList.get(0).getOptypenum());
            sbjbxxYuVo.setRecnum(file_yuVoList.get(0).getRecnum());
            sbjbxxYuVo.setRecyear(file_yuVoList.get(0).getRecyear());
            List<file_yuVo> file_yuVos = file_yuService.selectFileInfo(sbjbxxYuVo);
            //循环判断该数据是否已存进数据库
            for(file_yuVo file_yuVo:file_yuVoList){
                //用于判断是否存在
                boolean isExist = false;
                for(file_yuVo oleFile_yuVo:file_yuVos){
                    //如果两个相同，则说明是同一条数据，采用更新方法，并从数据库查询出的list移除此数据
                    if(oleFile_yuVo.getRecmatnum() == file_yuVo.getRecmatnum()){
                        file_yuService.updateMat(file_yuVo);
                        file_yuVos.remove(oleFile_yuVo);
                        isExist = true;
                        break;
                    }
                }
                //若没有相同的，则插入该数据
                if(!isExist){
                    file_yuService.insertMat(file_yuVo);
                }
            }
            //把之前保存，现在没有的数据删除
            for(file_yuVo oleFile_yuVo:file_yuVos){
                file_yuService.deleteMat(oleFile_yuVo);
            }
            return SUCCESS_TIP;
        }catch (Exception e){
            return ERROR;
        }
    }

    @RequestMapping(value = "/submitInfo")
    @ResponseBody
    public Object submitInfo(String iOptypenum,String iRecyear,String iRecnum){
        Map<String,Object> param = new HashMap<>();
        param.put("iOptypenum",iOptypenum);
        param.put("iRecyear",iRecyear);
        param.put("iRecnum",iRecnum);

        houseProjectService.submitInfo(param);
        return SUCCESS_TIP;
    }

    @RequestMapping(value = "/save_joint_applicant")
    @ResponseBody
    public Object saveJointApplicant(@RequestBody  List<JointApplicant> jointApplicantList) {
        Map<String,Object> param = new HashMap<>();

        if(jointApplicantList.size() != 0){
            param.put("iOptypenum",jointApplicantList.get(0).getOPTYPENUM());
            param.put("iRecyear",jointApplicantList.get(0).getRECYEAR());
            param.put("iRecnum",jointApplicantList.get(0).getRECNUM());
            //获取旧的共同申请人
            List<JointApplicant> jointApplicants = houseProjectMapper.selectJointApplicant(param);
            //删除旧的共同申请人，避免保存重复
            houseProjectMapper.deleteJointApplicant(param);
            //保存
            for(int i = 0;i<jointApplicantList.size();i++){
                if(jointApplicantList.get(i).getJointApplicantCard() != null && !("").equals(jointApplicantList.get(i).getJointApplicantCard())){
                    houseProjectService.addJointApplicant(jointApplicantList.get(i));
                    //判断为配偶或者媳妇，则保存信息
                    if(jointApplicantList.get(i).getJointApplicantRelation().equals("2") || jointApplicantList.get(i).getJointApplicantRelation().equals("9")){
                        FamilySurvey familySurvey = new FamilySurvey();
                        familySurvey.setOPTYPENUM((int)param.get("iOptypenum"));
                        familySurvey.setRECYEAR((int)param.get("iRecyear"));
                        familySurvey.setRECNUM((int)param.get("iRecnum"));
                        //姓名
                        familySurvey.setOWNERNAME_PEIOU(jointApplicantList.get(i).getJointApplicantName());
                        //性别
                        familySurvey.setOWNERSEXCODE_PEIOU(jointApplicantList.get(i).getJointApplicantSex());
                        //证件号码
                        familySurvey.setOWNERCERTNUM_PEIOU(jointApplicantList.get(i).getJointApplicantCard());
                        //证件类型
                        familySurvey.setOWNERCERTTYPECODE_PEIOU("1");
                        houseProjectService.updateFamilySurveySpouse(familySurvey);
                    }
                    //遍历旧的共同申请人
                    for(JointApplicant jointApplicant:jointApplicants){
                        //判断旧的共同申请人是否存有头像，若有
                        if(jointApplicant.getMY_PHOTO() != null){
                            //根据身份证号码，判断旧的和新的是否为同一个人
                            if(jointApplicant.getJointApplicantCard().equals(jointApplicantList.get(i).getJointApplicantCard())){
                                //覆盖头像
                                houseProjectService.savePicture(jointApplicant.getMY_PHOTO(),jointApplicant.getOPTYPENUM().toString(), jointApplicant.getRECYEAR().toString(),
                                        jointApplicant.getRECNUM().toString(),"0",jointApplicantList.get(i).getJointApplicantId());
                                break;
                            }
                        }
                    }
                }
            }
        }

        return SUCCESS_TIP;
    }

    /**
     * 保存申请理由等
     * @param json
     * @return
     */
    @RequestMapping(value = "/save_applicant_reason")
    @ResponseBody
    public Object saveApplicantReason(String json) {
        ApplicantReason applicantReason =  JSON.parseObject(json,ApplicantReason.class);
        houseProjectMapper.saveOtherFamilySurvey(applicantReason);
        return SUCCESS_TIP;
    }

    @RequestMapping(value = "/pay_rent")
    public String payRent() {
        return PREFIX + "pay_rent.html";
    }

    /**
     * 打印
     * @param OpTypeNum
     * @param RecYear
     * @param RecNum
     * @param num
     * @param model
     * @return
     */
    @RequestMapping(value = "/export_pdf")
    public String exportPdf(String OpTypeNum,String RecYear,String RecNum,String num,Model model) {
        //查询家庭类型字典值
        List<Dic> familyType = dicService.getFamilyType();
        model.addAttribute("familyType",familyType);
        model.addAttribute("familyTypeJson",JSON.toJSONString(familyType));
        //房屋建筑结构字典值
        List<Dic> buildingStructureList = dicService.getBuildingStructure();
        model.addAttribute("buildingStructureListJson",JSON.toJSONString(buildingStructureList));
        //房屋户型字典值
        List<Dic> houseTypeList = dicService.getHouseType();
        model.addAttribute("houseTypeListJson",JSON.toJSONString(houseTypeList));
        //房屋用途字典值
        List<Dic> houseuSageList = dicService.getHouseuSage();
        model.addAttribute("houseuSageListJson",JSON.toJSONString(houseuSageList));

        //查找该业务的数据
        FamilySurvey apply = houseProjectMapper.getFamilySurvey(Convert.toInt(OpTypeNum),Convert.toInt(RecYear),Convert.toInt(RecNum));
        model.addAttribute("apply",apply);

        Map param = new HashMap();
        param.put("iOptypenum",OpTypeNum);
        param.put("iRecyear",RecYear);
        param.put("iRecnum",RecNum);
        param.put("rcList",new ArrayList<Map<String,Object>>());
        //获取共同申请人列表
        List<JointApplicant> jointApplicantList = houseProjectMapper.selectJointApplicant(param);
//        model.addAttribute("jointApplicantList",jointApplicantList);

        if(apply.getCivilServant() == 1){
            //共同申请人为配偶
            JointApplicant spouse = new JointApplicant();
            //获取配偶信息
            for(int i = 0;i<jointApplicantList.size();i++){
                if(jointApplicantList.get(i).getJointApplicantRelation().equals("2") || jointApplicantList.get(i).getJointApplicantRelation().equals("9")){
                    //提取配偶信息
                    spouse = jointApplicantList.get(i);
                    //移除
                    jointApplicantList.remove(i);
                    break;
                }
            }
            model.addAttribute("spouse",spouse);
            model.addAttribute("jointApplicantListJson",JSON.toJSONString(jointApplicantList));
        }else{
            List<JointApplicant> jointApplicantList1 = new ArrayList<JointApplicant>();
            List<JointApplicant> jointApplicantList2 = new ArrayList<JointApplicant>();

            int flage = 0;/*0代表共同申请人小于2，1代表共同申请人大于2且小于5*/
            if(jointApplicantList!=null&&jointApplicantList.size()>0){
                for(int i=0;i<jointApplicantList.size();i++){
                    if(i<=1){
                        jointApplicantList1.add(jointApplicantList.get(i));
                    }else if(i>1&&i<=4){
                        flage = 1;
                        jointApplicantList2.add(jointApplicantList.get(i));
                    }
                }
            }
            model.addAttribute("flage",flage);
            model.addAttribute("jointApplicantList1",jointApplicantList1);
            model.addAttribute("jointApplicantList2",jointApplicantList2);
        }

        /*判断类型*/
        String typeNum = houseProjectMapper.selectTypeNum(Convert.toInt(OpTypeNum),Convert.toInt(RecYear),Convert.toInt(RecNum));
        if(typeNum.equals("312")){
            model.addAttribute("applyType",1);
        }else{
            model.addAttribute("applyType",0);
            //判断公务员
            if(apply.getCivilServant() == 0){
                model.addAttribute("isCivilServant",0);
            }else{
                model.addAttribute("isCivilServant",1);
            }
        }
        //编号
        model.addAttribute("num",num);
        return PREFIX + "application_pdf.html";
    }

    /**
     * 保存头像
     * @param avatar
     * @param OpTypeNum
     * @param RecYear
     * @param RecNum
     * @param judge 1为申请人头像，其他为共同申请人头像
     * @param FamilyID
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/save_picture")
    @ResponseBody
    public Object savePicture(MultipartFile avatar, String OpTypeNum, String RecYear, String RecNum, String judge,String FamilyID,HttpServletRequest request) throws IOException {
        if (!avatar.isEmpty()) {
            try {
                houseProjectService.savePicture(avatar.getBytes(),OpTypeNum,RecYear,RecNum,judge,FamilyID);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 读取头像
     * @param page
     * @param OpTypeNum
     * @param RecYear
     * @param RecNum
     * @param judge 1为申请人头像，其他为共同申请人头像
     * @param FamilyID
     * @param model
     * @param info
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/getPhoto")
    @ResponseBody
    public String getPhoto(String page,String OpTypeNum,String RecYear,String RecNum, String judge,String FamilyID,Model model,String info,HttpServletRequest request,HttpServletResponse response){
        byte[] data;
        if(("1").equals(judge)){
            FamilySurvey apply = houseProjectMapper.getFamilySurvey(Convert.toInt(OpTypeNum),Convert.toInt(RecYear),Convert.toInt(RecNum));
            data=apply.getMY_PHOTO();
        }else{
            JointApplicant apply = houseProjectMapper.getJointApplicant(Convert.toInt(OpTypeNum),Convert.toInt(RecYear),Convert.toInt(RecNum),Convert.toInt(FamilyID));
            data=apply.getMY_PHOTO();
        }

        response.setContentType("img/jpeg");
        response.setCharacterEncoding("utf-8");
        try {

            OutputStream outputStream=response.getOutputStream();
            InputStream in=new ByteArrayInputStream(data);
            int len=0;
            byte[]buf=new byte[1024];
            while((len=in.read(buf,0,1024))!=-1){
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "test";
    }

}