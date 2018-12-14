package com.arf.member.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.arf.core.Filter;
import com.arf.core.Filter.Operator;
import com.arf.core.Order;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.member.dao.IAppTerminalDeviceDao;
import com.arf.member.entity.AppTerminalDevice;
import com.arf.member.service.IAppTerminalDeviceService;

@Service("appTerminalDeviceService")
public class AppTerminalDeviceServiceImpl extends BaseServiceImpl<AppTerminalDevice, Long> implements IAppTerminalDeviceService{

	@Resource(name = "appTerminalDeviceDao")
	private IAppTerminalDeviceDao appTerminalDeviceDao;

	@Override
	protected BaseDao<AppTerminalDevice, Long> getBaseDao() {
		return appTerminalDeviceDao;
	}

	@Override
	public boolean exist(String userName, String uniqueCode) {
		return this.findByUserNameUniqueCode(userName, uniqueCode) == null ? false : true;
	}

	@Override
	public List<AppTerminalDevice> findByUserName(String userName) {
		Assert.notNull(userName,"根据用户名查询终端设备是否存在时用户名不能为空");
		List<AppTerminalDevice> list = this.findList(null, (List<Order>)null, 
				new Filter("userName",Operator.eq,userName)
				);
		return list;
	}

	@Override
	public AppTerminalDevice findByUserNameUniqueCode(String userName, String uniqueCode) {
		Assert.notNull(userName,"查询终端设备是否存在时用户名不能为空");
		Assert.notNull(uniqueCode,"查询终端设备是否存在时设备唯一码不能为空");
		List<AppTerminalDevice> list = this.findList(null, (List<Order>)null, 
				new Filter("uniqueCode",Operator.eq,uniqueCode),
				new Filter("userName",Operator.eq,userName)
				);
		if(CollectionUtils.isEmpty(list)){
			return null;
		}else{
			return list.get(0);
		}
	}
	
	
	
}
