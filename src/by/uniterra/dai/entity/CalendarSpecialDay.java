package by.uniterra.dai.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the `calendar_special _days` database table.
 * 
 */
@Entity
@Table(name="calendar_special _days")
@NamedQuery(name="CalendarSpecialDay.findAll", query="SELECT c FROM CalendarSpecialDay c")
public class CalendarSpecialDay implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="DAY_TYPE_ID")
	private int dayTypeId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_DAY")
	private Date dateDay;

	private String descrition;

	@Column(name="TYPE_DAY")
	private int typeDay;

	public CalendarSpecialDay() {
	}

	public int getDayTypeId() {
		return this.dayTypeId;
	}

	public void setDayTypeId(int dayTypeId) {
		this.dayTypeId = dayTypeId;
	}

	public Date getDateDay() {
		return this.dateDay;
	}

	public void setDateDay(Date dateDay) {
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