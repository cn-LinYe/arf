package com.arf.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 救援类型表
 * @author Administrator
 *
 */
@Entity
@Table(name = "rescueType")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "rescueType_sequence")
public class RescueTypeModel extends BaseEntity<Long>{

/**
     * 
     */
    private static final long serialVersionUID = 7518754257154589706L;
    //	private int id;
	/**
	 * 救援事故名称
	 */
	private String accidentName;
	
//	@Id
//	@Column(name = "id")
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	public int getId() {
//		return id;
//	}
//	public void setId(int id) {
//		this.id = id;
//	}
	@Column(name = "accidentName", length=1024, nullable = false)
	public String getAccidentName() {
		return accidentName;
	}
	public void setAccidentName(String accidentName) {
		this.accidentName = accidentName;
	}
}
