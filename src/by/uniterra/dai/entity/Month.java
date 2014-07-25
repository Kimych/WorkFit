package by.uniterra.dai.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;

/**
 * The persistent class for the months database table.
 * 
 */
@Entity
@Table(name = "months")
@NamedQuery(name = "Month.findAll", query = "SELECT m FROM Month m")
public class Month implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MONTH_ID")
	private int monthId;

	private String description;

	@Column(name = "WORKING_DAYS_COUNT")
	private int workingDaysCount;

	// bi-directional many-to-one association to DaysOfWork
	@OneToMany(mappedBy = "month")
	private List<DaysOfWork> daysOfWorks;

	// bi-directional many-to-one association to NameMonth
	@ManyToOne
	@JoinColumn(name = "NAME_MONTH_ID")
	private NameMonth nameMonth;

	// bi-directional many-to-one association to Year
	@ManyToOne
	@JoinColumn(name = "YEAR_ID")
	private Year year;

	// bi-directional many-to-one association to SpentHoliday
	@OneToMany(mappedBy = "month")
	private List<SpentHoliday> spentHolidays;

	public Month()
	{
	}

	public int getMonthId()
	{
		return this.monthId;
	}

	public void setMonthId(int monthId)
	{
		this.monthId = monthId;
	}

	public String getDescription()
	{
		return this.description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public int getWorkingDaysCount()
	{
		return this.workingDaysCount;
	}

	public void setWorkingDaysCount(int workingDaysCount)
	{
		this.workingDaysCount = workingDaysCount;
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
		daysOfWork.setMonth(this);

		return daysOfWork;
	}

	public DaysOfWork removeDaysOfWork(DaysOfWork daysOfWork)
	{
		getDaysOfWorks().remove(daysOfWork);
		daysOfWork.setMonth(null);

		return daysOfWork;
	}

	public NameMonth getNameMonth()
	{
		return this.nameMonth;
	}

	public void setNameMonth(NameMonth nameMonth)
	{
		this.nameMonth = nameMonth;
	}

	public Year getYear()
	{
		return this.year;
	}

	public void setYear(Year year)
	{
		this.year = year;
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
		spentHoliday.setMonth(this);

		return spentHoliday;
	}

	public SpentHoliday removeSpentHoliday(SpentHoliday spentHoliday)
	{
		getSpentHolidays().remove(spentHoliday);
		spentHoliday.setMonth(null);

		return spentHoliday;
	}

	@Override
	public String toString()
	{
		return "Month [monthId=" + monthId + ", description=" + description
				+ ", workingDaysCount=" + workingDaysCount + ", daysOfWorks="
				+ daysOfWorks + ", nameMonth=" + nameMonth + ", year=" + year
				+ ", spentHolidays=" + spentHolidays + "]";
	}

}