package by.uniterra.dai.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;

/**
 * The persistent class for the worker database table.
 * 
 */
@Entity
@NamedQuery(name = "Worker.findAll", query = "SELECT w FROM Worker w")
public class Worker implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "WORKER_ID")
	private int workerId;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "SECOND_NAME")
	private String secondName;

	@Column(name = "THIRD_NAME")
	private String thirdName;
	
	@Column(name = "ALIAS")
        private String alias;
	

	// bi-directional many-to-one association to DaysOfWork
	@OneToMany(mappedBy = "worker")
	private List<DaysOfWork> daysOfWorks;

	// bi-directional many-to-one association to Holiday
	@OneToMany(mappedBy = "worker")
	private List<Holiday> holidays;

	// bi-directional many-to-one association to SpentHoliday
	@OneToMany(mappedBy = "worker")
	private List<SpentHoliday> spentHolidays;

	public Worker()
	{
	}

	public int getWorkerId()
	{
		return this.workerId;
	}

	public void setWorkerId(int workerId)
	{
		this.workerId = workerId;
	}

	public String getFirstName()
	{
		return this.firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getSecondName()
	{
		return this.secondName;
	}

	public void setSecondName(String secondName)
	{
		this.secondName = secondName;
	}

	public String getThirdName()
	{
		return this.thirdName;
	}

	public void setThirdName(String thirdName)
	{
		this.thirdName = thirdName;
	}

	public String getAlias()
        {
                return this.alias;
        }

        public void setAlias(String alias)
        {
                this.alias = alias;
        }
        
	public List<DaysOfWork> getDaysOfWorks()
	{
		return this.daysOfWorks;
	}

	public void setDaysOfWorks(List<DaysOfWork> daysOfWorks)
	{
		this.daysOfWorks = daysOfWorks;
	}

	public DaysOfWork addDaysOfWork(DaysOfWork daysOfWork)
	{
		getDaysOfWorks().add(daysOfWork);
		daysOfWork.setWorker(this);

		return daysOfWork;
	}

	public DaysOfWork removeDaysOfWork(DaysOfWork daysOfWork)
	{
		getDaysOfWorks().remove(daysOfWork);
		daysOfWork.setWorker(null);

		return daysOfWork;
	}

	public List<Holiday> getHolidays()
	{
		return this.holidays;
	}

	public void setHolidays(List<Holiday> holidays)
	{
		this.holidays = holidays;
	}

	public Holiday addHoliday(Holiday holiday)
	{
		getHolidays().add(holiday);
		holiday.setWorker(this);

		return holiday;
	}

	public Holiday removeHoliday(Holiday holiday)
	{
		getHolidays().remove(holiday);
		holiday.setWorker(null);

		return holiday;
	}

	public List<SpentHoliday> getSpentHolidays()
	{
		return this.spentHolidays;
	}

	public void setSpentHolidays(List<SpentHoliday> spentHolidays)
	{
		this.spentHolidays = spentHolidays;
	}

	public SpentHoliday addSpentHoliday(SpentHoliday spentHoliday)
	{
		getSpentHolidays().add(spentHoliday);
		spentHoliday.setWorker(this);

		return spentHoliday;
	}

	public SpentHoliday removeSpentHoliday(SpentHoliday spentHoliday)
	{
		getSpentHolidays().remove(spentHoliday);
		spentHoliday.setWorker(null);

		return spentHoliday;
	}

	@Override
	public String toString()
	{
	    return String.valueOf(secondName + " " + firstName + " " + thirdName);
	}

	@Override
	public int hashCode()
	{
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((alias == null) ? 0 : alias.hashCode());
	    result = prime * result + ((daysOfWorks == null) ? 0 : daysOfWorks.hashCode());
	    result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
	    result = prime * result + ((holidays == null) ? 0 : holidays.hashCode());
	    result = prime * result + ((secondName == null) ? 0 : secondName.hashCode());
	    result = prime * result + ((spentHolidays == null) ? 0 : spentHolidays.hashCode());
	    result = prime * result + ((thirdName == null) ? 0 : thirdName.hashCode());
	    result = prime * result + workerId;
	    return result;
	}

	@Override
	public boolean equals(Object obj)
	{
	    if (this == obj)
		return true;
	    if (obj == null)
		return false;
	    if (getClass() != obj.getClass())
		return false;
	    Worker other = (Worker) obj;
	    if (alias == null)
	    {
		if (other.alias != null)
		    return false;
	    } else if (!alias.equals(other.alias))
		return false;
	    if (daysOfWorks == null)
	    {
		if (other.daysOfWorks != null)
		    return false;
	    } else if (!daysOfWorks.equals(other.daysOfWorks))
		return false;
	    if (firstName == null)
	    {
		if (other.firstName != null)
		    return false;
	    } else if (!firstName.equals(other.firstName))
		return false;
	    if (holidays == null)
	    {
		if (other.holidays != null)
		    return false;
	    } else if (!holidays.equals(other.holidays))
		return false;
	    if (secondName == null)
	    {
		if (other.secondName != null)
		    return false;
	    } else if (!secondName.equals(other.secondName))
		return false;
	    if (spentHolidays == null)
	    {
		if (other.spentHolidays != null)
		    return false;
	    } else if (!spentHolidays.equals(other.spentHolidays))
		return false;
	    if (thirdName == null)
	    {
		if (other.thirdName != null)
		    return false;
	    } else if (!thirdName.equals(other.thirdName))
		return false;
	    if (workerId != other.workerId)
		return false;
	    return true;
	}

}