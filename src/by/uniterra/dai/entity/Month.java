package by.uniterra.dai.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the months database table.
 * 
 */
@Entity
@Table(name = "months")
@NamedQueries({
        @NamedQuery(name = Month.NQ_FIND_ALL, query = "SELECT m FROM Month m"),
        @NamedQuery(name = Month.NQ_FIND_WDAYS_COUNT_BY_MONTH_ID, query = "SELECT m.workingDaysCount FROM Month m where m.nameMonth.nameMonthId  = :"
                + Month.PARAMETER_MONTH) })
public class Month implements Serializable
{
    private static final long serialVersionUID = 1L;

    public static final String NQ_FIND_ALL = "Month.findAll";
    public static final String NQ_FIND_WDAYS_COUNT_BY_MONTH_ID = "Month.findWorkingDayCountForMonth";

    public static final String PARAMETER_MONTH = "month";

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

    public Month(Year yAssciatedYear)
    {
        year = yAssciatedYear;
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
        return "Month [monthId=" + monthId + ", description=" + description + ", workingDaysCount=" + workingDaysCount + ", daysOfWorks=" + daysOfWorks
                + ", nameMonth=" + nameMonth + ", year=" + year + ", spentHolidays=" + spentHolidays + "]";
    }

}