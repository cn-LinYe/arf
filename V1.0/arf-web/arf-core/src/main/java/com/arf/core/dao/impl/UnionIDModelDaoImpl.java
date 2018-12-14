package com.arf.core.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.arf.core.dao.UnionIDModelDao;
import com.arf.core.entity.UnionIDModel;

/**
 * Dao - UnionID表
 * 
 * @author arf
 * @version 4.0
 */
@Repository("unionIDModelDaoImpl")
public class UnionIDModelDaoImpl extends BaseDaoImpl<UnionIDModel, Long> implements UnionIDModelDao {

	
	@Override
	public List<UnionIDModel> selectAll() {
		String jpql = "select unionIDModel from UnionIDModel unionIDModel";
		return entityManager.createQuery(jpql, UnionIDModel.class).getResultList();
	}

	@Override
	public void executeBatchBySql(List<UnionIDModel> wxUserUpdates, List<UnionIDModel> wxUserInserts) {
		
		@SuppressWarnings("deprecation")
		ConnectionProvider cp = ((SessionFactoryImplementor)entityManager.unwrap(org.hibernate.Session.class).getSessionFactory()).getConnectionProvider();  
		Connection conn = null;
		try {
		    conn = cp.getConnection();
			conn.setAutoCommit(false);
			java.util.Date date  = new java.util.Date();
			PreparedStatement saveSmt = conn.prepareStatement("INSERT INTO unionidmodel (openid,unionID,subscribe_time,city,country,language,nickname,province,sex,create_date,modify_date,version) VALUES(?,?,?,'','','','','',0,?,?,?)");
			PreparedStatement updatSmt = conn.prepareStatement("UPDATE unionidmodel SET openid = ?,unionID = ?,subscribe_time = ? WHERE `id` = ?");
			
			UnionIDModel unionIDModel = null;
			int count = wxUserInserts.size();
			if(count > 0){
				for (int i = 0; i < count; i++) {
					unionIDModel = wxUserInserts.get(i);
					saveSmt.setString(1, unionIDModel.getOpenid());
					saveSmt.setString(2, unionIDModel.getUnionID());
					saveSmt.setLong(3, unionIDModel.getSubscribe_time());
					saveSmt.setObject(4, date);
					saveSmt.setObject(5, date);
					saveSmt.setObject(6, 1.1);
					saveSmt.addBatch();
					//每500条记录 就提交一次。
					if(i%500 == 0){
						saveSmt.executeBatch();
						conn.commit();
					}
				}
				saveSmt.executeBatch();
				conn.commit();
			}
			count = wxUserUpdates.size();
			if(count > 0){
				for (int i = 0; i < count; i++) {
					unionIDModel = wxUserUpdates.get(i);					
					updatSmt.setString(1, unionIDModel.getOpenid());
					updatSmt.setString(2, unionIDModel.getUnionID());
					updatSmt.setLong(3, unionIDModel.getSubscribe_time());
					updatSmt.setObject(4, unionIDModel.getId());//改了
					updatSmt.addBatch();
					if(i%500 == 0){
						updatSmt.executeBatch();
						conn.commit();
					}
				}
				updatSmt.executeBatch();
				conn.commit();
			}
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
//			logInfo("同步微信用户信息失败", e);
			return;
		}finally {
			if(null != conn){
				try {
					cp.closeConnection(conn);
//					conn.close();
					cp = null;
				} catch (SQLException e) {
					e.printStackTrace();
//					logInfo("数据库连接 关闭失败", e);
				}
			}
		}
	}

	
	@Override
	public List<UnionIDModel> sellectByUnionId(String unionID) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<UnionIDModel> criteriaQuery = criteriaBuilder.createQuery(UnionIDModel.class);
		Root<UnionIDModel> root = criteriaQuery.from(UnionIDModel.class);
		criteriaQuery.select(root);		
		criteriaQuery.where(criteriaBuilder.equal(root.get("unionID"), unionID));
		return super.findList(criteriaQuery, null, null, null, null);
	}
	
	/**
     * 将最新的openid一起插入到数据库中
     * @param ids
     */
	@Transactional
    public void insertOpenids(String ids[]){
        StringBuffer sb=new StringBuffer("INSERT INTO unionidmodel (openid,unionID,subscribe_time,city,country,language,nickname,province,sex,create_date,modify_date,version) VALUES");
        SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date=sim.format(new java.util.Date());
        for(int i=0;i<ids.length;i++){
            sb.append("('"+ids[i]+"','',0,'','','ZH-CN','','',0,'"+date+"','"+date+"',0)");
            if((i+1)%500==0||i==ids.length-1){//如果是第500条或者最后一条,将字符串插入
                entityManager.createNativeQuery(sb.toString()).executeUpdate();
                sb=null;
                sb=new StringBuffer("INSERT INTO unionidmodel (openid,unionID,subscribe_time,city,country,language,nickname,province,sex,create_date,modify_date,version) VALUES");
            }else{
                sb.append(",");
            }
        }
    }
    /**
     * 获取所有unionId为空的记录
     * @return
     */
    public List<UnionIDModel> getNoUnionID(){
        String hql="select u from UnionIDModel u where u.unionID='' or u.unionID is null";        
        return entityManager.createQuery(hql,UnionIDModel.class).getResultList();
    }
    
    /**
     * 批量更新unionID
     * @param list
     */
    @Transactional
    public void updateListUnionID(List<UnionIDModel> list){
//        flush();
        for(int i=0;i<list.size();i++){
            refresh(list.get(i));
        }
    }

	@Override
	public UnionIDModel selectByOpenId(String openid) {
		this.clear();
		String jpql = "select unionIDModel from UnionIDModel unionIDModel where openid = :openid order by id DESC";
		List<UnionIDModel> list = entityManager.createQuery(jpql, UnionIDModel.class).setParameter("openid", openid).getResultList();
		if(list != null && list.size() > 0){
			return list.get(0);
		}else{
			return null;
		}
		
	}
}
