package com.arf.core.service;

import java.util.List;
import java.util.Map;

import com.arf.core.entity.Sysconfig;

/**
 * Service - 系统配置表
 * 
 * @author arf
 * @version 4.0
 */
public interface SysconfigService extends BaseService<Sysconfig, Long> {
	/**
	 * 该方法已废弃,不再使用
	 * 根据参数名获取参数值，参数名在表中不存在时创建
	 * @param name
	 * @return
	 */
	@Deprecated
    public Sysconfig getValueByName(String name,String defvalue,String defdesc);
	
	/**
	 * 通过namespace来获取sysconfig值
	 * eg. 1 ns="POINT.RAMAIN_VOUCHER_TO_POINT" 则获取parent="Point",paraname="RAMAIN_VOUCHER_TO_POINT"的参数值
	 * eg. 2 ns="MODELBUSINESSINFO" 则获取parent=null,paraname="MODELBUSINESSINFO"的参数值
	 * 该方法会优先从缓存获取,如果缓存获取不到则从数据库获取.
	 * @param ns
	 * @return 参数值
	 */
	public String getValueByNameSpace(String ns);
	
	/**
	 * 通过父节点和参数名获取单个参数值
	 * 该方法会优先从缓存获取,如果缓存获取不到则从数据库获取.
	 * @param parent 父节点
	 * @param paraName 实际参数名
	 * @return
	 */
	public String getValue(String parent,String paraName);
	
    /**
     * 根据参数名获取参数值
     * @param name
     * @return
     */
    public Sysconfig getValueByName(String name);
    
    /**获取系统所有配置信息
     * @return
     */
    public List<Sysconfig> getAllValue();
    
    /**获取系统所有配置信息
     * @return
     */
    public List<Sysconfig> getValueByParent(String parentname);
    
    /**获取系统配置表中KEY所对应的VALUE
     * @param name parmname
     * @return 首先返回redis中value 没有进行查询数据库操作
     */
    public String getValue(String name);
    
    /**获取系统配置表中KEY所对应的VALUE
     * @param name parentname
     * @return 首先返回redis中value 没有进行查询数据库操作
     */
    public Map<String, String> getParentValue(String parentname);   
    
}
