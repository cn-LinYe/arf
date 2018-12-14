package com.arf.core.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.arf.core.dao.AreaStatisticDao;
import com.arf.core.entity.Area;
import com.arf.core.entity.AreaStatistic;
/**
 * 
 * @ClassName: 地区统计
 * @author arf
 * @date 2015年12月2日 下午4:36:57 
 * @version 4.0
 */
@SuppressWarnings("unchecked")
@Repository("areaStatisticDaoImpl")
public class AreaStatisticDaoImpl extends BaseDaoImpl<Area, Long>  implements AreaStatisticDao {
    
    private static Map<String,String> map=new HashMap<String, String>();
    static{
        map.put("北京市","北京");
        map.put("天津市","天津");
        map.put("河北省","河北");
        map.put("山西省","山西");
        map.put("内蒙古自治区","内蒙古");
        map.put("辽宁省","辽宁");
        map.put("吉林省","吉林");
        map.put("黑龙江省","黑龙江");
        map.put("上海市","上海");
        map.put("江苏省","江苏");
        map.put("浙江省","浙江");
        map.put("安徽省","安徽");
        map.put("福建省","福建");
        map.put("江西省","江西");
        map.put("山东省","山东");
        map.put("河南省","河南");
        map.put("湖北省","湖北");
        map.put("湖南省","湖南");
        map.put("广东省","广东");
        map.put("广西壮族自治区","广西");
        map.put("海南省","海南");
        map.put("重庆市","重庆");
        map.put("四川省","四川");
        map.put("贵州省","贵州");
        map.put("云南省","云南");
        map.put("西藏自治区","西藏");
        map.put("陕西省","陕西");
        map.put("甘肃省","甘肃");
        map.put("青海省","青海");
        map.put("宁夏回族自治区","宁夏");
        map.put("新疆维吾尔自治区","新疆");
        map.put("台湾省","台湾");
        map.put("香港特别行政区","香港");
        map.put("澳门特别行政区","澳门");
    }
    
	@Override
	public List<AreaStatistic> findMemcount() {
		String sql="select count(1) memcount,area.full_name areaname from xx_member m "
				+" inner join (select id,case when substring(tree_path,2,LOCATE(',',tree_path,2)-2)='' "
				+" then id else substring(tree_path,2,LOCATE(',',tree_path,2)-2) end fid from xx_area) "
				+" a on m.area=a.id inner join xx_area area on area.id=a.fid group by a.fid,area.full_name ";
		List<Object []> list=entityManager.createNativeQuery(sql).getResultList();
		List<AreaStatistic> biglist = new ArrayList<AreaStatistic>();
		for(Object[] obj : list){
			AreaStatistic area=new AreaStatistic();			
			area.setMemcount(obj[0].toString());
			area.setAreaname(map.get(obj[1].toString()));
			biglist.add(area);
		}
		return biglist;
	}

	@Override
	public List<AreaStatistic> findArdcount() {
		String sql="select count(1) ordcount,area.full_name areaname from xx_order m "
				+ "inner join (select id,case when substring(tree_path,2,LOCATE(',',tree_path,2)-2)=''"
				+ " then id else substring(tree_path,2,LOCATE(',',tree_path,2)-2) end fid from xx_area) a "
				+ " on m.area=a.id inner join xx_area area on area.id=a.fid group by a.fid,area.full_name" ;
		List<Object []> list=entityManager.createNativeQuery(sql).getResultList();
		List<AreaStatistic> biglist = new ArrayList<AreaStatistic>();
		for(Object[] obj : list){
			AreaStatistic area=new AreaStatistic();						
			area.setOrdcount(obj[0].toString());
			area.setAreaname(map.get(obj[1].toString()));
			biglist.add(area);
		}
		return biglist;
	}

	@Override
	public List<AreaStatistic> findSalesvolume() {
		String sql="SELECT a.gname goodsname ,a.full_name areaname ,max(a.count) salesvolume from (select goods.id id,goods.`name` gname,count(1)"
				+ " count,a.fid fid,area.full_name full_name from xx_order o inner join xx_order_item item on o.id=item.orders "
				+ "inner join xx_product product on item.product=product.id inner join xx_goods goods on product.goods=goods.id "
				+ " inner join ( select id,case when substring(tree_path,2,LOCATE(',',tree_path,2)-2)='' then id else "
				+ " substring(tree_path,2,LOCATE(',',tree_path,2)-2) end fid from xx_area ) a on o.area=a.id inner "
				+ " join xx_area area on area.id=a.fid group by goods.id,goods.`name`,a.fid,area.full_name ) a group by a.id ";
		List<Object []> list=entityManager.createNativeQuery(sql).getResultList();
		List<AreaStatistic> biglist = new ArrayList<AreaStatistic>();
		for(Object[] obj : list){
			AreaStatistic area=new AreaStatistic();	
			area.setGoodsname(obj[0].toString());
			area.setAreaname(map.get(obj[1].toString()));
			area.setSalesvolume(obj[2].toString());
			biglist.add(area);
		}		
		return biglist;
	}
	
}
