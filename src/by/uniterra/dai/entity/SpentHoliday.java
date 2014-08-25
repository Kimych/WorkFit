package by.uniterra.dai.entity;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The persistent class for the spent_holiday database table.
 * 
 */
@Entity
@Table(name = "spent_holiday")
@NamedQueries({
        @NamedQuery(name = SpentHoliday.NQ_FIND_ALL, query = "SELECT s FROM SpentHoliday s"),
        @NamedQuery(name = SpentHoliday.NQ_FIND_SPEND_HOLIDAY_BY_WORKER_AND_YEAR, query = "SELECT SUM(s.countDays) FROM SpentHoliday s  JOIN s.month m  JOIN m.year y WHERE  y.number = :" + SpentHoliday.PARAMETER_YEAR + " and s.worker = :"
                + SpentHoliday.PARAMETER_WORKER)

})
public class SpentHoliday implements Serializable
{
    private static final long serialVersionUID = 1L;

    public static final String NQ_FIND_ALL = "SpentHoliday.findAll";
    public static final String NQ_FIND_SPEND_HOLIDAY_BY_WORKER_AND_YEAR = "Holiday.findSpendHolidayByWorkerAndYear";

    public static final String PARAMETER_WORKER = "worker";
    public static final String PARAMETER_YEAR = "year";

    @EmbeddedId
    private SpentHolidayPK id;

    @Column(name = "COUNT_DAYS")
    private int countDays;

    private String description;

    // bi-directional many-to-one association to Worker
    @ManyToOne
    @JoinColumn(name = "WORKER_ID")
    private Worker worker;

    // bi-directional many-to-one association to Month
    @ManyToOne
    @JoinColumn(name = "MONTH_ID")
    private Month month;

    public SpentHoliday()
    {
    }

    public SpentHoliday(Worker wWorker, Month mMonth)
    {
        id = new SpentHolidayPK(wWorker.getWorkerId(), mMonth.getMonthId());
        worker = wWorker;
        month = mMonth;
    }

    public SpentHolidayPK getId()
    {
        return this.id;
    }

    public void setId(SpentHolidayPK id)
    {
        this.id = id;
    }

    public int getCountDays()
    {
        return this.countDays;
    }

    public void setCountDays(int countDays)
    {
        this.countDays = countDays;
    }

    public String getDescription()
    {
        return this.description;
    }

    public void setDescription(String description)
    {
        this.description = description;
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
        return "SpentHoliday [id=" + id + ", countDays=" + countDays + ", description=" + description + ", worker=" + worker + ", month=" + month + "]";
    }

}