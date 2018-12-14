package com.arf.installment.service;

import java.util.List;

import com.arf.core.service.BaseService;
import com.arf.installment.dto.InstallmentTypeDto;
import com.arf.installment.entity.InstallmentType;
import com.arf.installment.entity.InstallmentType.Type;

public interface IInstallmentTypeService extends BaseService<InstallmentType,Long>{

	/**
	 * 根据门锁类型查找门锁分期类型
	 * @param type
	 * @return
	 */
	List<InstallmentTypeDto> findByType(Type type);
	
	/**
	 * 通过类型编码来查找
	 * @param typeNum
	 * @return
	 */
	InstallmentType findByTypeNum(String typeNum);
	
	/**
	 * 查询过期的类型
	 * @return
	 */
	List<InstallmentType> findDeadlineTypes();
}
