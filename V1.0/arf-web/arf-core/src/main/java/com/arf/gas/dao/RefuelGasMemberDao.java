package com.arf.gas.dao;

import java.util.List;
import java.util.Map;

import com.arf.base.PageResult;
import com.arf.core.dao.BaseDao;
import com.arf.gas.entity.RefuelGasMember;
import com.arf.gas.entity.RefuelGasMember.MemberStatus;

public interface RefuelGasMemberDao extends BaseDao<RefuelGasMember, Long>{

	/**
	 * 查询会员信息列表（可供加油站或会员查询）
	 * @param userName
	 * @param gasNum
	 * @param businessNum
	 * @param pageSize
	 * @param pageNo
	 * @param memberStatus
	 * @return
	 */
	public PageResult<Map<String, Object>> findByGasNum(String userName,String gasNum,String businessNum,String licenseNumber,
			Integer pageSize,Integer pageNo,Integer memberStatus);
	
	/**
	 * 查询会员在某个加油站的信息
	 * @param userName
	 * @param gasNum
	 * @param businessNum
	 * @return
	 */
	public RefuelGasMember findByUserNameAndGasNum(String userName ,Integer gasNum,Integer businessNum,MemberStatus memberStatus);
	/**
	 * 根据用户数组查询
	 * @param userNames
	 * @return
	 */
	public List<RefuelGasMember> findByUserName(String[] userNames,Integer gasNum);
	
	/**
	 * 根据会员状态集合查询
	 * @param memberStatusList
	 * @return
	 */
	public List<RefuelGasMember> findByMemberStatus(List<RefuelGasMember.MemberStatus> memberStatusList,Integer gasNum);
	
	/**
	 * 通过用户名和车牌查找
	 * @param license
	 * @param userName
	 * @return
	 */
	public int findLicenseUser(String license,String userName,Integer gasNum);
	
	/**
	 * 根据身份证号查询会员信息
	 * @param idCard
	 * @return
	 */
	public RefuelGasMember findByIdCard(String idCard);
	
}
