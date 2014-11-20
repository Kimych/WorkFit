package by.uniterra.dai.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the day_type database table.
 * 
 */
@Entity
@Table(name="day_type")
@NamedQuery(name="DayType.findAll", query="SELECT d FROM DayType d")
public class DayType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="DAY_TYPE_ID")
	private int dayTypeId;

	@Column(name="DATE_DAY")
	private Timestamp dateDay;

	private String descrition;

	@Column(name="TYPE_DAY")
	private int typeDay;

	public DayType() {
	}

	public int getDayTypeId() {
		return this.dayTypeId;
	}

	public void setDayTypeId(int dayTypeId) {
		this.dayTypeId = dayTypeId;
	}

	public Timestamp getDateDay() {
		return this.dateDay;
	}

	public void setDateDay(Timestamp dateDay) {
		this.dateDay = dateDay;
	}

	public String getDescrition() {
		return this.descrition;
	}

	public void setDescrition(String descrition) {
		this.descrition = descrition;
	}

	public int getTypeDay() {
		return this.typeDay;
	}

	public void setTypeDay(int typeDay) {
		this.typeDay = typeDay;
	}

}