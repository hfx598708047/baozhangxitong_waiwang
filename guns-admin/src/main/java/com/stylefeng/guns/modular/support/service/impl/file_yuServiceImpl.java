package com.stylefeng.guns.modular.support.service.impl;

import java.util.List;
import java.util.Map;

import com.stylefeng.guns.modular.support.dao.file_yuMapper;
import com.stylefeng.guns.modular.support.model.file_yuVo;
import com.stylefeng.guns.modular.support.model.sbjbxx_yuVo;
import com.stylefeng.guns.modular.support.service.file_yuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class file_yuServiceImpl implements file_yuService{
	
	@Autowired
	file_yuMapper mapper;
	
	@Override
	public int insertFile(file_yuVo file) {
		// TODO Auto-generated method stub
		return mapper.insertFile(file);
	}

	@Override
	public int insertMat(file_yuVo file) {
		// TODO Auto-generated method stub
		return mapper.insertMat(file);
	}

	@Override
	public int deleteMat(file_yuVo file) {
		// TODO Auto-generated method stub
		return mapper.deleteMat(file);
	}

	@Override
	public int updateMat(file_yuVo file) {
		// TODO Auto-generated method stub
		return mapper.updateMat(file);
	}

	@Override
	public int insertMatFile(file_yuVo file) {
		// TODO Auto-generated method stub
		mapper.insertImageDesc(file);
		return mapper.insertMatFile(file);
	}

	@Override
	public List<file_yuVo> selectFile(sbjbxx_yuVo jbxx) {
		// TODO Auto-generated method stub
		return mapper.selectFile(jbxx);
	}

	@Override
	public int selectimagedesdaul() {
		// TODO Auto-generated method stub
		return mapper.selectimagedesdaul();
	}

	@Override
	public int selectimagedaul() {
		// TODO Auto-generated method stub
		return mapper.selectimagedaul();
	}

	@Override
	public file_yuVo selectSignFile(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.selectSignFile(map);
	}

	@Override
	public int updateSignFile(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.updateSignFile(map);
	}

	@Override
	public List<file_yuVo> selectFileInfo(sbjbxx_yuVo jbxx) {
		// TODO Auto-generated method stub
		return mapper.selectFileInfo(jbxx);
	}

	@Override
	public List<file_yuVo> selectMultFile(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.selectMultFile(map);
	}

	@Override
	public file_yuVo selectSignImage(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.selectSignImage(map);
	}

}
