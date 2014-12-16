package by.uniterra.dai.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;

/**
 * The persistent class for the year database table.
 * 
 */
@Entity
@NamedQueries({
        @NamedQuery(name = Year.NQ_FIND_ALL, query = "SELECT y FROM Year y"),
        @NamedQuery(name = Year.NQ_FIND_YEAR_BY_NUM, query = "SELECT y FROM Year y WHERE y.number = :" + Year.PARAMETER_NUM_YEAR )
        
})
public class Year implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    
    public static final String NQ_FIND_ALL = "Year.findAll";
    public static final String NQ_FIND_YEAR_BY_NUM = "Year.getYearByYearNumber";
    
    public static final String PARAMETER_NUM_YEAR = "numYear";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "YEAR_ID")
    private int yearId;

    private String deskription;

    private int number;

    // bi-directional many-to-one association to Holiday
    @OneToMany(mappedBy = "year")
    private List<Holiday> holidays;

    // bi-directional many-to-one association to Month
    @OneToMany(mappedBy = "year")
    private List<Month> months;

    public Year()
    {
    }

    public int getYearId()
    {
        return this.yearId;
    }

    public void setYearId(int yearId)
    {
        this.yearId = yearId;
    }

    public String getDeskription()
    {
        return this.deskription;
    }

    public void setDeskription(String deskription)
    {
        this.deskription = deskription;
    }

    public int getNumber()
    {
        return this.number;
    }

    public void setNumber(int number)
    {
        this.number = number;
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
        holiday.setYear(this);

        return holiday;
    }

    public Holiday removeHoliday(Holiday holiday)
    {
        getHolidays().remove(holiday);
        holiday.setYear(null);

        return holiday;
    }

    public List<Month> getMonths()
    {
        return this.months;
    }

    public void setMonths(List<Month> months)
    {
        this.months = months;
    }

    public Month addMonth(Month month)
    {
        getMonths().add(month);
        month.setYear(this);

        return month;
    }

    public Month removeMonth(Month month)
    {
        getMonths().remove(month);
        month.setYear(null);

        return month;
    }

    @Override
    public String toString()
    {
        return String.valueOf(number);
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((deskription == null) ? 0 : deskription.hashCode());
        result = prime * result + number;
        result = prime * result + yearId;
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
        Year other = (Year) obj;
        if (deskription == null)
        {
            if (other.deskription != null)
                return false;
        }
        else if (!deskription.equals(other.deskription))
            return false;
        if (number != other.number)
            return false;
        if (yearId != other.yearId)
            return false;
        return true;
    }

}