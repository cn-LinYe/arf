package com.arf.installment.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.arf.core.Filter;
import com.arf.core.Filter.Operator;
import com.arf.core.Order;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.installment.dao.IInstallmentTypeDao;
import com.arf.installment.dto.InstallmentTypeDto;
import com.arf.installment.entity.InstallmentType;
import com.arf.installment.entity.InstallmentType.Type;
import com.arf.installment.service.IInstallmentTypeService;

@Service("installmentTypeServiceImpl")
public class InstallmentTypeServiceImpl extends BaseServiceImpl<InstallmentType,Long> implements IInstallmentTypeService{

	@Resource(name="installmentTypeDaoImpl")
	IInstallmentTypeDao installmentTypeDao;
	@Override
	protected BaseDao<InstallmentType, Long> getBaseDao() {
		return installmentTypeDao;
	}
	@Override
	public List<InstallmentTypeDto> findByType(Type type) {
		return installmentTypeDao.findByType(type);
	}
	@Override
	public InstallmentType findByTypeNum(String typeNum) {
		List<InstallmentType> list = this.findList(null, (List<Order>)null, new Filter("typeNum",Operator.eq,typeNum));
		return CollectionUtils.isEmpty(list)?null:list.get(0);
	}
	@Override
	public List<InstallmentType> findDeadlineTypes() {
		return this.findList(null, (List<Order>)null, 
				new Filter("outOfDate",Operator.lt,new Date()),
				new Filter("status",Operator.eq,InstallmentType.Status.UNDERWAY)
				);
	}

}
