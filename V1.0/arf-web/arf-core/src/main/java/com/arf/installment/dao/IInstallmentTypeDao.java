package com.arf.installment.dao;

import java.util.List;

import com.arf.core.dao.BaseDao;
import com.arf.installment.dto.InstallmentTypeDto;
import com.arf.installment.entity.InstallmentType;
import com.arf.installment.entity.InstallmentType.Type;

public interface IInstallmentTypeDao extends BaseDao<InstallmentType, Long>{
	/**
	 * 根据门锁类型查找门锁分期类型
	 * @param type
	 * @return
	 */
	List<InstallmentTypeDto> findByType(Type type);
}
