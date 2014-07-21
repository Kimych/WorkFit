package by.uniterra.dai.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the worker database table.
 * 
 */
@Entity
@NamedQuery(name="Worker.findAll", query="SELECT w FROM Worker w")
public class Worker implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="WORKER_ID")
	private int workerId;

	@Column(name="FIRST_NAME")
	private String firstName;

	@Column(name="SECOND_NAME")
	private String secondName;

	@Column(name="THIRD_NAME")
	private String thirdName;

	//bi-directional many-to-one association to DaysOfWork
	@OneToMany(mappedBy="worker")
	private List<DaysOfWork> daysOfWorks;

	//bi-directional many-to-one association to Holiday
	@OneToMany(mappedBy="worker")
	private List<Holiday> holidays;

	//bi-directional many-to-one association to SpentHoliday
	@OneToMany(mappedBy="worker")
	private List<SpentHoliday> spentHolidays;

	public Worker() {
	}

	public int getWorkerId() {
		return this.workerId;
	}

	public void setWorkerId(int workerId) {
		this.workerId = workerId;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSecondName() {
		return this.secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getThirdName() {
		return this.thirdName;
	}

	public void setThirdName(String thirdName) {
		this.thirdName = thirdName;
	}

	public List<DaysOfWork> getDaysOfWorks() {
		return this.daysOfWorks;
	}

	public void setDaysOfWorks(List<DaysOfWork> daysOfWorks) {
		this.daysOfWorks = daysOfWorks;
	}

	public DaysOfWork addDaysOfWork(DaysOfWork daysOfWork) {
		getDaysOfWorks().add(daysOfWork);
		daysOfWork.setWorker(this);

		return daysOfWork;
	}

	public DaysOfWork removeDaysOfWork(DaysOfWork daysOfWork) {
		getDaysOfWorks().remove(daysOfWork);
		daysOfWork.setWorker(null);

		return daysOfWork;
	}

	public List<Holiday> getHolidays() {
		return this.holidays;
	}

	public void setHolidays(List<Holiday> holidays) {
		this.holidays = holidays;
	}

	public Holiday addHoliday(Holiday holiday) {
		getHolidays().add(holiday);
		holiday.setWorker(this);

		return holiday;
	}

	public Holiday removeHoliday(Holiday holiday) {
		getHolidays().remove(holiday);
		holiday.setWorker(null);

		return holiday;
	}

	public List<SpentHoliday> getSpentHolidays() {
		return this.spentHolidays;
	}

	public void setSpentHolidays(List<SpentHoliday> spentHolidays) {
		this.spentHolidays = spentHolidays;
	}

	public SpentHoliday addSpentHoliday(SpentHoliday spentHoliday) {
		getSpentHolidays().add(spentHoliday);
		spentHoliday.setWorker(this);

		return spentHoliday;
	}

	public SpentHoliday removeSpentHoliday(SpentHoliday spentHoliday) {
		getSpentHolidays().remove(spentHoliday);
		spentHoliday.setWorker(null);

		return spentHoliday;
	}

}