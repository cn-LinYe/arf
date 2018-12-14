package com.arf.relievedassist.dao;

import java.util.List;

import com.arf.core.dao.BaseDao;
import com.arf.relievedassist.entity.RelievedAssistClassify;

public interface IRelievedAssistClassifyDao extends BaseDao<RelievedAssistClassify, Long>{

	/**查询所有正常的帖子分类
	 * @return
	 */
	List<RelievedAssistClassify> findNormalClassify(RelievedAssistClassify.Status status);
}
