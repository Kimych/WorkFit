package by.uniterra.dai.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import by.uniterra.udi.util.EDayType;

/**
 * The persistent class for the `calendar_special _days` database table.
 * 
 */
@Entity
@Table(name = "calendar_special _days")
@NamedQueries({
    @NamedQuery( name = CalendarSpecialDay.NQ_FIND_ALL, query = "SELECT c FROM CalendarSpecialDay c"),
    @NamedQuery( name = CalendarSpecialDay.NQ_FIND_SPECIAL_DAYS_BY_YEAR, query = "SELECT c FROM CalendarSpecialDay c WHERE c.year.number = :" + CalendarSpecialDay.PARAMETER_YEAR_NUMBER)
})
public class CalendarSpecialDay implements Serializable
{
    private static final long serialVersionUID = 1L;

    public static final String NQ_FIND_ALL = "CalendarSpecialDay.findAll";
    public static final String NQ_FIND_SPECIAL_DAYS_BY_YEAR = "CalendarSpecialDay.findSpecialDayByYear";
    
    public static final String PARAMETER_YEAR_NUMBER = "yearNumber";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DAY_TYPE_ID")
    private int dayTypeId;

    private String descrition;

    @Column(name = "TYPE_DAY")
    private int typeDay;

    @Column(name = "YEAR_DAY_NUMBER")
    private int yearDayNumber;

    @ManyToOne
    @JoinColumn(name = "YEAR_ID")
    private Year year;

    public CalendarSpecialDay()
    {
        descrition = "";
    }
    
    public CalendarSpecialDay(CalendarSpecialDay calSpecialDay)
    {
        this.dayTypeId = calSpecialDay.getDayTypeId();
        this.descrition = calSpecialDay.getDescrition();
        this.year = calSpecialDay.getYear();
        this.yearDayNumber = calSpecialDay.getYearDayNumber();
        this.setTypeDay(calSpecialDay.getTypeDay());
        
    }

    public int getDayTypeId()
    {
        return this.dayTypeId;
    }

    public void setDayTypeId(int dayTypeId)
    {
        this.dayTypeId = dayTypeId;
    }

    public String getDescrition()
    {
        return this.descrition;
    }

    public void setDescrition(String descrition)
    {
        this.descrition = descrition;
    }

    public List<EDayType> getTypeDay()
    {
        List<EDayType> lstResult = new ArrayList<EDayType>();
        for (EDayType edt : EDayType.values())
        {
            if ((this.typeDay & (int) Math.pow(2, edt.ordinal())) > 0)
            {
                lstResult.add(edt);
            }
        }
        return lstResult;
    }

    public void setTypeDay(List<EDayType> lstEDayType)
    {
        int typeDayResult = 0;
        for (EDayType eDayType : lstEDayType)
        {
            typeDayResult |=  (int) Math.pow(2, eDayType.ordinal());
        }
        this.typeDay = typeDayResult;
    }

    public int getYearDayNumber()
    {
        return this.yearDayNumber;
    }

    public void setYearDayNumber(int yearDayNumber)
    {
        this.yearDayNumber = yearDayNumber;
    }

    public Year getYear()
    {
        return this.year;
    }

    public void setYear(Year year)
    {
        this.year = year;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((descrition == null) ? 0 : descrition.hashCode());
        result = prime * result + typeDay;
        result = prime * result + ((year == null) ? 0 : year.hashCode());
        result = prime * result + yearDayNumber;
        return result;
    }

    /* (non-Javadoc)
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
        CalendarSpecialDay other = (CalendarSpecialDay) obj;
        if (descrition == null)
        {
            if (other.descrition != null)
                return false;
        }
        else if (!descrition.equals(other.descrition))
            return false;
        if (typeDay != other.typeDay)
            return false;
        if (year == null)
        {
            if (other.year != null)
                return false;
        }
        else if (!year.equals(other.year))
            return false;
        if (yearDayNumber != other.yearDayNumber)
            return false;
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "CalendarSpecialDay [dayTypeId=" + dayTypeId + ", descrition=" + descrition + ", typeDay=" + typeDay + ", yearDayNumber=" + yearDayNumber
                + ", year=" + year + "]";
    }

}