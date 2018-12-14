package com.arf.access.dao;

import com.arf.access.entity.AccessNumQrCodeInit;
import com.arf.core.dao.BaseDao;

public interface IAccessNumQrCodeInitDao extends BaseDao<AccessNumQrCodeInit, Long> {
	int findCountByAccessNum(long accessNum);
}
