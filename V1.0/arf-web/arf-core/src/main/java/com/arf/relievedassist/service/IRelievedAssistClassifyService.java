package com.arf.relievedassist.service;

import java.util.List;

import com.arf.core.service.BaseService;
import com.arf.relievedassist.entity.RelievedAssistClassify;

public interface IRelievedAssistClassifyService extends BaseService<RelievedAssistClassify, Long>{
	
	/**查询所有正常的帖子分类
	 * @return
	 */
	List<RelievedAssistClassify> findNormalClassify(RelievedAssistClassify.Status status);
}
