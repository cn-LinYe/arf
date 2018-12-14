package com.arf.core.entity;

/**
 * 
 * @ClassName: AreaStatistic 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author arf
 * @date 2015年12月4日 下午5:01:15 
 *
 */
public class AreaStatistic {
	/**会员总数*/
	private String memcount;
	/**订单总数*/
	private String ordcount;
	/**地区*/
	private String areaname;
	/**销量*/
	private String salesvolume;
	/**商品名*/
	private String goodsname;
	/**  
	 * 获取 会员总数  
	 * @return 会员总数  
	 */
	public String getMemcount() {
		return memcount;
	}
	/**  
	 * 设置 会员总数 
	 * @param 会员总数 
	 */
	public void setMemcount(String memcount) {
		this.memcount = memcount;
	}
	/**  
	 * 获取 订单总数  
	 * @return 订单总数  
	 */
	public String getOrdcount() {
		return ordcount;
	}
	/**  
	 * 设置 订单总数   
	 * @param 订单总数  
	 */
	public void setOrdcount(String ordcount) {
		this.ordcount = ordcount;
	}
	/**  
	 * 获取 地区  
	 * @return 地区  
	 */
	public String getAreaname() {
		return areaname;
	}
	/**  
	 * 设置 地区 
	 * @param 地区  
	 */
	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}
	/**  
	 * 获取 销量  
	 * @return 销量  
	 */
	public String getSalesvolume() {
		return salesvolume;
	}
	/**  
	 * 设置 销量  
	 * @param 销量  
	 */
	public void setSalesvolume(String salesvolume) {
		this.salesvolume = salesvolume;
	}
	/**  
	 * 获取 商品名  
	 * @return 商品名
	 */
	public String getGoodsname() {
		return goodsname;
	}
	/**  
	 * 设置 商品名
	 * @param 商品名
	 */
	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}
	
	
}
