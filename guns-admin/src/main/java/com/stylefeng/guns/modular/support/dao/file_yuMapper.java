package com.stylefeng.guns.modular.support.dao;

import java.util.List;
import java.util.Map;

import com.stylefeng.guns.modular.support.model.file_yuVo;
import com.stylefeng.guns.modular.support.model.sbjbxx_yuVo;

public interface file_yuMapper {
	
	public int insertFile(file_yuVo file);
	
	public int insertMat(file_yuVo file);
	
	public int deleteMat(file_yuVo file);

	public int updateMat(file_yuVo file);

	public int insertImageDesc(file_yuVo file);

	public int insertMatFile(file_yuVo file);

	public List<file_yuVo> selectFile(sbjbxx_yuVo jbxx);

	public List<file_yuVo> selectFileInfo(sbjbxx_yuVo jbxx);
	
	public file_yuVo selectSignFile(Map<String, Object> map);
	
	public List<file_yuVo> selectMultFile(Map<String, Object> map);
	
	public file_yuVo selectSignImage(Map<String, Object> map);
	
	public int updateSignFile(Map<String, Object> map);
	
	public int selectimagedesdaul();
	
	public int selectimagedaul();
}
