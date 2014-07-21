package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the days_of_work database table.
 * 
 */
@Entity
@Table(name="days_of_work")
@NamedQuery(name="DaysOfWork.findAll", query="SELECT d FROM DaysOfWork d")
public class DaysOfWork implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="DAYS_OF_WORK_ID")
	private int daysOfWorkId;

	@Column(name="AKTUAL_WORKED_DAYS")
	private int aktualWorkedDays;

	@Column(name="BONUS_TIME")
	private double bonusTime;

	@Column(name="BONUS_TIME_DESCRIPTION")
	private String bonusTimeDescription;

	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;

	private double worklog;

	//bi-directional many-to-one association to Worker
	@ManyToOne
	@JoinColumn(name="WORKER_ID")
	private Worker worker;

	//bi-directional many-to-one association to Month
	@ManyToOne
	@JoinColumn(name="MONTH_ID")
	private Month month;

	public DaysOfWork() {
	}

	public int getDaysOfWorkId() {
		return this.daysOfWorkId;
	}

	public void setDaysOfWorkId(int daysOfWorkId) {
		this.daysOfWorkId = daysOfWorkId;
	}

	public int getAktualWorkedDays() {
		return this.aktualWorkedDays;
	}

	public void setAktualWorkedDays(int aktualWorkedDays) {
		this.aktualWorkedDays = aktualWorkedDays;
	}

	public double getBonusTime() {
		return this.bonusTime;
	}

	public void setBonusTime(double bonusTime) {
		this.bonusTime = bonusTime;
	}

	public String getBonusTimeDescription() {
		return this.bonusTimeDescription;
	}

	public void setBonusTimeDescription(String bonusTimeDescription) {
		this.bonusTimeDescription = bonusTimeDescription;
	}

	public Date getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public double getWorklog() {
		return this.worklog;
	}

	public void setWorklog(double worklog) {
		this.worklog = worklog;
	}

	public Worker getWorker() {
		return this.worker;
	}

	public void setWorker(Worker worker) {
		this.worker = worker;
	}

	public Month getMonth() {
		return this.month;
	}

	public void setMonth(Month month) {
		this.month = month;
	}

}