/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PreRemove;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Entity - 地区
 * 
 * @author arf
 * @version 4.0
 */
@Entity
@Table(name = "xx_area")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_area_sequence")
public class Area extends OrderEntity<Long> {

	private static final long serialVersionUID = -2158109459123036967L;

	/** 树路径分隔符 */
	public static final String TREE_PATH_SEPARATOR = ",";

	/** 名称 */
	private String name;

	/** 全称 */
	private String fullName;

	/** 树路径 */
	private String treePath;

	/** 层级 */
	private Integer grade;

	/** 上级地区 */
	private Area parent;

	/** 下级地区 */
	private Set<Area> children = new HashSet<Area>(); 

	/** 会员 */
	private Set<Member> members = new HashSet<Member>();

	/** 收货地址 */
	private Set<Receiver> receivers = new HashSet<Receiver>();

	/** 订单 */
	private Set<Order> orders = new HashSet<Order>();

	/** 发货点 */
	private Set<DeliveryCenter> deliveryCenters = new HashSet<DeliveryCenter>();

	/** 运费配置 */
	private Set<FreightConfig> freightConfigs = new HashSet<FreightConfig>();

	/** 货品 */
	private Set<Goods> goods = new HashSet<Goods>();
	/**
	 * 地区类型
	 * @author arf
	 */
	public enum Type{
	    /** 省市区 */
	    city,
	    /** 小区 */
	    village
	}
	/** 地区类型 */
	private Type type;
	/** 地区/小区编码 */
	private String no;
	/** 上级地区/上级编码 */
	private String topno;
	/** 市下面的区  */
	private String areaCode;
	/** 区域级别 */
	private Integer areaLevel;
	/** 物业id */
	private Integer propertyNumber;
	/** 开通vip价格  */
	private Integer amountType;
	/** 描述 */
	private String description;
	/** 小区地址 */
	private String communityAddress;
	
	
	/**  
     * 获取小区地址  
     * @return communityAddress 小区地址  
     */
	@Column(length=300)
    public String getCommunityAddress() {
        return communityAddress;
    }

    /**  
     * 设置小区地址  
     * @param communityAddress 小区地址  
     */
    public void setCommunityAddress(String communityAddress) {
        this.communityAddress = communityAddress;
    }

    /**
	 * 获取名称
	 * 
	 * @return 名称
	 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	public String getName() {
		return name;
	}

	/**
	 * 设置名称
	 * 
	 * @param name
	 *            名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取全称
	 * 
	 * @return 全称
	 */
	@Column(nullable = false, length = 4000)
	public String getFullName() {
		return fullName;
	}

	/**
	 * 设置全称
	 * 
	 * @param fullName
	 *            全称
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * 获取树路径
	 * 
	 * @return 树路径
	 */
	@Column(nullable = false)
	public String getTreePath() {
		return treePath;
	}

	/**
	 * 设置树路径
	 * 
	 * @param treePath
	 *            树路径
	 */
	public void setTreePath(String treePath) {
		this.treePath = treePath;
	}

	/**
	 * 获取层级
	 * 
	 * @return 层级
	 */
	@Column(nullable = false)
	public Integer getGrade() {
		return grade;
	}

	/**
	 * 设置层级
	 * 
	 * @param grade
	 *            层级
	 */
	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	/**
	 * 获取上级地区
	 * 
	 * @return 上级地区
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	public Area getParent() {
		return parent;
	}

	/**
	 * 设置上级地区
	 * 
	 * @param parent
	 *            上级地区
	 */
	public void setParent(Area parent) {
		this.parent = parent;
	}

	/**
	 * 获取下级地区
	 * 
	 * @return 下级地区
	 */
	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@OrderBy("order asc")
	public Set<Area> getChildren() {
		return children;
	}

	/**
	 * 设置下级地区
	 * 
	 * @param children
	 *            下级地区
	 */
	public void setChildren(Set<Area> children) {
		this.children = children;
	}

	/**
	 * 获取会员
	 * 
	 * @return 会员
	 */
	@OneToMany(mappedBy = "area", fetch = FetchType.LAZY)
	public Set<Member> getMembers() {
		return members;
	}

	/**
	 * 设置会员
	 * 
	 * @param members
	 *            会员
	 */
	public void setMembers(Set<Member> members) {
		this.members = members;
	}

	/**
	 * 获取收货地址
	 * 
	 * @return 收货地址
	 */
	@OneToMany(mappedBy = "area", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public Set<Receiver> getReceivers() {
		return receivers;
	}

	/**
	 * 设置收货地址
	 * 
	 * @param receivers
	 *            收货地址
	 */
	public void setReceivers(Set<Receiver> receivers) {
		this.receivers = receivers;
	}

	/**
	 * 获取订单
	 * 
	 * @return 订单
	 */
	@OneToMany(mappedBy = "area", fetch = FetchType.LAZY)
	public Set<Order> getOrders() {
		return orders;
	}

	/**
	 * 设置订单
	 * 
	 * @param orders
	 *            订单
	 */
	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	/**
	 * 获取发货点
	 * 
	 * @return 发货点
	 */
	@OneToMany(mappedBy = "area", fetch = FetchType.LAZY)
	public Set<DeliveryCenter> getDeliveryCenters() {
		return deliveryCenters;
	}

	/**
	 * 设置发货点
	 * 
	 * @param deliveryCenters
	 *            发货点
	 */
	public void setDeliveryCenters(Set<DeliveryCenter> deliveryCenters) {
		this.deliveryCenters = deliveryCenters;
	}

	/**
	 * 获取运费配置
	 * 
	 * @return 运费配置
	 */
	@OneToMany(mappedBy = "area", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public Set<FreightConfig> getFreightConfigs() {
		return freightConfigs;
	}

	/**
	 * 设置运费配置
	 * 
	 * @param freightConfigs
	 *            运费配置
	 */
	public void setFreightConfigs(Set<FreightConfig> freightConfigs) {
		this.freightConfigs = freightConfigs;
	}
	
	/**
	 * 获取货品
	 * 
	 * @return 货品
	 */
	@ManyToMany(mappedBy = "areas", fetch = FetchType.LAZY)
	public Set<Goods> getGoods() {
		return goods;
	}

	/**
	 * 设置货品
	 * 
	 * @param goods
	 *            货品
	 */
	public void setGoods(Set<Goods> goods) {
		this.goods = goods;
	}
	

	/**
	 * 获取所有上级地区ID
	 * 
	 * @return 所有上级地区ID
	 */
	@Transient
	public Long[] getParentIds() {
		String[] parentIds = StringUtils.split(getTreePath(), TREE_PATH_SEPARATOR);
		Long[] result = new Long[parentIds.length];
		for (int i = 0; i < parentIds.length; i++) {
			result[i] = Long.valueOf(parentIds[i]);
		}
		return result;
	}

	/**
	 * 获取所有上级地区
	 * 
	 * @return 所有上级地区
	 */
	@Transient
	public List<Area> getParents() {
		List<Area> parents = new ArrayList<Area>();
		recursiveParents(parents, this);
		return parents;
	}

	/**
	 * 递归上级地区
	 * 
	 * @param parents
	 *            上级地区
	 * @param area
	 *            地区
	 */
	private void recursiveParents(List<Area> parents, Area area) {
		if (area == null) {
			return;
		}
		Area parent = area.getParent();
		if (parent != null) {
			parents.add(0, parent);
			recursiveParents(parents, parent);
		}
	}

	/**
	 * 删除前处理
	 */
	@PreRemove
	public void preRemove() {
		Set<Member> members = getMembers();
		if (members != null) {
			for (Member member : members) {
				member.setArea(null);
			}
		}
		Set<Order> orders = getOrders();
		if (orders != null) {
			for (Order order : orders) {
				order.setArea(null);
			}
		}
		Set<DeliveryCenter> deliveryCenters = getDeliveryCenters();
		if (deliveryCenters != null) {
			for (DeliveryCenter deliveryCenter : deliveryCenters) {
				deliveryCenter.setArea(null);
			}
		}
	}

    /**  
     * 获取地区类型  
     * @return type 地区类型  
     */
	@Column(name="type")
    public Type getType() {
        return type;
    }

    /**  
     * 设置地区类型  
     * @param type 地区类型  
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**  
     * 获取地区小区编码  
     * @return no 地区小区编码  
     */
    @Column(name="no")
    public String getNo() {
        return no;
    }

    /**  
     * 设置地区小区编码  
     * @param no 地区小区编码  
     */
    public void setNo(String no) {
        this.no = no;
    }

    /**  
     * 获取上级地区上级编码  
     * @return topno 上级地区上级编码  
     */
    @Column(name="topno")
    public String getTopno() {
        return topno;
    }

    /**  
     * 设置上级地区上级编码  
     * @param topno 上级地区上级编码  
     */
    public void setTopno(String topno) {
        this.topno = topno;
    }

    /**  
     * 获取市下面的区  
     * @return areaCode 市下面的区  
     */
    @Column(name="areacode")
    public String getAreaCode() {
        return areaCode;
    }

    /**  
     * 设置市下面的区  
     * @param areaCode 市下面的区  
     */
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    /**  
     * 获取区域级别  
     * @return areaLevel 区域级别  
     */
    @Column(name="arealevel")
    public Integer getAreaLevel() {
        return areaLevel;
    }

    /**  
     * 设置区域级别  
     * @param areaLevel 区域级别  
     */
    public void setAreaLevel(Integer areaLevel) {
        this.areaLevel = areaLevel;
    }

    /**  
     * 获取物业id  
     * @return propertyNumber 物业id  
     */
    @Column(name="property_number")
    public Integer getPropertyNumber() {
        return propertyNumber;
    }

    /**  
     * 设置物业id  
     * @param propertyNumber 物业id  
     */
    public void setPropertyNumber(Integer propertyNumber) {
        this.propertyNumber = propertyNumber;
    }

    /**  
     * 获取开通vip价格  
     * @return amountType 开通vip价格  
     */
    @Column(name="amount_type")
    public Integer getAmountType() {
        return amountType;
    }

    /**  
     * 设置开通vip价格  
     * @param amountType 开通vip价格  
     */
    public void setAmountType(Integer amountType) {
        this.amountType = amountType;
    }

    /**  
     * 获取描述  
     * @return description 描述  
     */
    @Column(name="description")
    public String getDescription() {
        return description;
    }

    /**  
     * 设置描述  
     * @param description 描述  
     */
    public void setDescription(String description) {
        this.description = description;
    }
}