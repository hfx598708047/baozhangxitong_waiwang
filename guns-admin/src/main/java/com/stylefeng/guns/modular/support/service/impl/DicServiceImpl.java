package com.stylefeng.guns.modular.support.service.impl;

import com.stylefeng.guns.modular.support.dao.DicMapper;
import com.stylefeng.guns.modular.support.model.Dic;
import com.stylefeng.guns.modular.support.service.DicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DicServiceImpl implements DicService {

    @Autowired
    private DicMapper mapper;

    //查找身份类型字典表
    @Override
    public List<Dic> getIdCard(){
        return mapper.getIdCard();
    }

    //查找建筑结构字典表
    @Override
    public List<Dic> getBuildingStructure(){
        return mapper.getBuildingStructure();
    }

    //查找区域字典表
    @Override
    public List<Dic> getRegion(){
        return mapper.getRegion();
    }

    //查找房屋户型字典表
    @Override
    public List<Dic> getHouseType(){
        return mapper.getHouseType();
    }

    //查找材料类型字典表
    @Override
    public List<Dic> getMaterial(){
        return mapper.getMaterial();
    }

    //查找退件类型字典表
    @Override
    public List<Dic> getRefund(){
        return mapper.getRefund();
    }

    //查找家庭关系字典表
    @Override
    public List<Dic> getFamilyType(){
        return mapper.getFamilyType();
    }

    //查找退件类型字典表
    @Override
    public List<Dic> getHouseuSage(){
        return mapper.getHouseuSage();
    }

}
