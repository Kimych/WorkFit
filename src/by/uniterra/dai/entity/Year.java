package by.uniterra.dai.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the year database table.
 * 
 */
@Entity
@NamedQuery(name="Year.findAll", query="SELECT y FROM Year y")
public class Year implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="YEAR_ID")
	private int yearId;

	private String deskription;

	private int number;

	//bi-directional many-to-one association to Holiday
	@OneToMany(mappedBy="year")
	private List<Holiday> holidays;

	//bi-directional many-to-one association to Month
	@OneToMany(mappedBy="year")
	private List<Month> months;

	public Year() {
	}

	public int getYearId() {
		return this.yearId;
	}

	public void setYearId(int yearId) {
		this.yearId = yearId;
	}

	public String getDeskription() {
		return this.deskription;
	}

	public void setDeskription(String deskription) {
		this.deskription = deskription;
	}

	public int getNumber() {
		return this.number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public List<Holiday> getHolidays() {
		return this.holidays;
	}

	public void setHolidays(List<Holiday> holidays) {
		this.holidays = holidays;
	}

	public Holiday addHoliday(Holiday holiday) {
		getHolidays().add(holiday);
		holiday.setYear(this);

		return holiday;
	}

	public Holiday removeHoliday(Holiday holiday) {
		getHolidays().remove(holiday);
		holiday.setYear(null);

		return holiday;
	}

	public List<Month> getMonths() {
		return this.months;
	}

	public void setMonths(List<Month> months) {
		this.months = months;
	}

	public Month addMonth(Month month) {
		getMonths().add(month);
		month.setYear(this);

		return month;
	}

	public Month removeMonth(Month month) {
		getMonths().remove(month);
		month.setYear(null);

		return month;
	}

}