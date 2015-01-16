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
        @NamedQuery(name = Month.NQ_FIND_MONTH_BY_MONTH_NUMBER_AND_YEAR_NUMBER, query = "SELECT m from Month m WHERE m.year.number = :"
                + Month.PARAMETER_YEAR_NUMBER + " AND m.nameMonth.nameMonthId = :" + Month.PARAMETER_MONTH_NUMBER) })
public class Month implements Serializable
{
    private static final long serialVersionUID = 1L;

    public static final String NQ_FIND_ALL = "Month.findAll";
    public static final String NQ_FIND_MONTH_BY_MONTH_NUMBER_AND_YEAR_NUMBER = "Month.findMonthByMontNumberAndYearNumber";

    public static final String PARAMETER_MONTH = "month";
    public static final String PARAMETER_MONTH_NUMBER = "monthNumber";
    public static final String PARAMETER_YEAR_NUMBER = "yearNumber";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MONTH_ID")
    private int monthId;

    private String description;

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
        return String.valueOf(nameMonth + " " + year);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + monthId;
        result = prime * result + ((nameMonth == null) ? 0 : nameMonth.hashCode());
        result = prime * result + ((year == null) ? 0 : year.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Month other = (Month) obj;
        if (description == null)
        {
            if (other.description != null)
                return false;
        }
        else if (!description.equals(other.description))
            return false;
        if (monthId != other.monthId)
            return false;
        if (nameMonth == null)
        {
            if (other.nameMonth != null)
                return false;
        }
        else if (!nameMonth.equals(other.nameMonth))
            return false;
        if (year == null)
        {
            if (other.year != null)
                return false;
        }
        else if (!year.equals(other.year))
            return false;
        return true;
    }

}