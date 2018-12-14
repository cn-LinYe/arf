package com.arf.gas.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.base.PageResult;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.gas.dao.RefuelGasMemberDao;
import com.arf.gas.entity.RefuelGasMember;
import com.arf.gas.entity.RefuelGasMember.MemberStatus;
import com.arf.gas.service.RefuelGasMemberService;

@Service("refuelGasMemberService")
public class RefuelGasMemberServiceImpl extends BaseServiceImpl<RefuelGasMember, Long> implements RefuelGasMemberService{

	@Resource(name="refuelGasMemberDao")
	RefuelGasMemberDao refuelGasMemberDao;
	
	@Override
	protected BaseDao<RefuelGasMember, Long> getBaseDao() {
		return refuelGasMemberDao;
	}
	
	@Override
	public PageResult<Map<String, Object>> findByGasNum(String userName, String gasNum, String businessNum,String licenseNumber,
			Integer pageSize,Integer pageNo,Integer memberStatus) {
		return refuelGasMemberDao.findByGasNum(userName, gasNum, businessNum,licenseNumber, pageSize, pageNo, memberStatus);
	}

	@Override
	public RefuelGasMember findByUserNameAndGasNum(String userName ,Integer gasNum,Integer businessNum,MemberStatus memberStatus) {
		return refuelGasMemberDao.findByUserNameAndGasNum(userName, gasNum, businessNum, memberStatus);
	}

	@Override
	public List<RefuelGasMember> findByUserName(String[] userNames,Integer gasNum) {
		return refuelGasMemberDao.findByUserName(userNames,gasNum);
	}

	@Override
	public List<RefuelGasMember> findByMemberStatus(List<MemberStatus> memberStatusList, Integer gasNum) {
		return refuelGasMemberDao.findByMemberStatus(memberStatusList, gasNum);
	}

	@Override
	public int findLicenseUser(String license,String userName,Integer gasNum) {
		return refuelGasMemberDao.findLicenseUser(license, userName, gasNum);
	}

	@Override
	public RefuelGasMember findByIdCard(String idCard) {
		return refuelGasMemberDao.findByIdCard(idCard);
	}

}
