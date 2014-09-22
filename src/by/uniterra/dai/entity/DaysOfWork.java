package by.uniterra.dai.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;

/**
 * The persistent class for the days_of_work database table.
 * 
 */
@Entity
@Table(name = "days_of_work")
@NamedQueries(
	{ 
	    @NamedQuery(name = DaysOfWork.NQ_FIND_ALL, query = "SELECT d FROM DaysOfWork d"),
	    @NamedQuery(name = DaysOfWork.NQ_FIND_LAST_BY_WORKER_AND_NUMBERMONTH, query = "SELECT d FROM DaysOfWork d where d.timestamp = (SELECT MAX(m.timestamp) FROM DaysOfWork m WHERE m.worker = :" 
		    + DaysOfWork.PARAMETER_WORKER +" and m.month.nameMonth.nameMonthId = :" + DaysOfWork.PARAMETER_MONTH + ") and " + "d.worker = :" + DaysOfWork.PARAMETER_WORKER +" and d.month.nameMonth.nameMonthId = :" + DaysOfWork.PARAMETER_MONTH),
	    @NamedQuery(name = DaysOfWork.NQ_FIND_ALL_BY_WORKER_AND_NUMBERMONTH,  query = "SELECT SUM(m.bonusTime) FROM DaysOfWork m WHERE m.worker = :" 
	                    + DaysOfWork.PARAMETER_WORKER +" and m.month.nameMonth.nameMonthId = :" + DaysOfWork.PARAMETER_MONTH),
	    @NamedQuery(name = DaysOfWork.NQ_FIND_LAST_BY_WORKER_AND_TIMESTAMP, query = "SELECT d FROM DaysOfWork d WHERE d.timestamp = (SELECT MAX(m.timestamp) FROM DaysOfWork m WHERE m.worker = :" + DaysOfWork.PARAMETER_WORKER + " AND m.timestamp  <= :" + DaysOfWork.PARAMETER_TIMESTAMP + ") AND d.worker = :" + DaysOfWork.PARAMETER_WORKER)    
                              
	})
public class DaysOfWork implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    //constants
    public static final String NQ_FIND_ALL = "DaysOfWork.findAll";
    public static final String NQ_FIND_LAST_BY_WORKER_AND_NUMBERMONTH = "DaysOfWork.findLastForWorkerAndMonth";
    public static final String NQ_FIND_ALL_BY_WORKER_AND_NUMBERMONTH = "DaysOfWork.findAllForWorkerAndMonth";
    public static final String NQ_FIND_LAST_BY_WORKER_AND_TIMESTAMP = "DaysOfWork.findLastForWorkerAndTimestamp";

    public static final String PARAMETER_WORKER = "worker";
    public static final String PARAMETER_MONTH = "month";
    public static final String PARAMETER_TIMESTAMP = "timestamp";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DAYS_OF_WORK_ID")
    private int daysOfWorkId;

    @Column(name = "AKTUAL_WORKED_DAYS")
    private int aktualWorkedDays;

    @Column(name = "BONUS_TIME")
    private double bonusTime;

    @Column(name = "BONUS_TIME_DESCRIPTION")
    private String bonusTimeDescription;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    private double worklog;

    // bi-directional many-to-one association to Worker
    @ManyToOne
    @JoinColumn(name = "WORKER_ID")
    private Worker worker;

    // bi-directional many-to-one association to Month
    @ManyToOne
    @JoinColumn(name = "MONTH_ID")
    private Month month;

    public DaysOfWork()
    {
    }

    public DaysOfWork(Month mAssociatedMonth, Worker wAssociatedWorker)
    {
	month = mAssociatedMonth;
	worker = wAssociatedWorker;
    }

    public int getDaysOfWorkId()
    {
	return this.daysOfWorkId;
    }

    public void setDaysOfWorkId(int daysOfWorkId)
    {
	this.daysOfWorkId = daysOfWorkId;
    }

    public int getAktualWorkedDays()
    {
	return this.aktualWorkedDays;
    }

    public void setAktualWorkedDays(int aktualWorkedDays)
    {
	this.aktualWorkedDays = aktualWorkedDays;
    }

    public double getBonusTime()
    {
	return this.bonusTime;
    }

    public void setBonusTime(double bonusTime)
    {
	this.bonusTime = bonusTime;
    }

    public String getBonusTimeDescription()
    {
	return this.bonusTimeDescription;
    }

    public void setBonusTimeDescription(String bonusTimeDescription)
    {
	this.bonusTimeDescription = bonusTimeDescription;
    }

    public Date getTimestamp()
    {
	return this.timestamp;
    }

    public void setTimestamp(Date timestamp)
    {
	this.timestamp = timestamp;
    }

    public double getWorklog()
    {
	return this.worklog;
    }

    public void setWorklog(double worklog)
    {
	this.worklog = worklog;
    }

    public Worker getWorker()
    {
	return this.worker;
    }

    public void setWorker(Worker worker)
    {
	this.worker = worker;
    }

    public Month getMonth()
    {
	return this.month;
    }

    public void setMonth(Month month)
    {
	this.month = month;
    }

    @Override
    public String toString()
    {
	return "DaysOfWork [daysOfWorkId=" + daysOfWorkId + ", aktualWorkedDays=" + aktualWorkedDays + ", bonusTime=" + bonusTime + ", bonusTimeDescription="
		+ bonusTimeDescription + ", timestamp=" + timestamp + ", worklog=" + worklog + ", worker=" + worker + ", month=" + month + "]";
    }

}