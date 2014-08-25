package by.uniterra.dai.entity;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The persistent class for the holiday database table.
 * 
 */
@Entity
@NamedQueries(
        {
            @NamedQuery(name = Holiday.NQ_FIND_ALL, query = "SELECT h FROM Holiday h"),
            @NamedQuery(name = Holiday.NQ_FIND_HOLIDAY_BY_WORKER_AND_YEAR, query = "SELECT h.countDays FROM Holiday h WHERE h.worker = :" + Holiday.PARAMETER_WORKER + " and h.year = :" + Holiday.PARAMETER_YEAR) 
        })
public class Holiday implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    public static final String NQ_FIND_ALL = "Holiday.findAll";
    public static final String NQ_FIND_HOLIDAY_BY_WORKER_AND_YEAR = "Holiday.findHolidayByWorkerAndYear";
    
    public static final String PARAMETER_WORKER = "worker";
    public static final String PARAMETER_YEAR = "year";

    @EmbeddedId
    private HolidayPK id;

    @Column(name = "COUNT_DAYS")
    private int countDays;

    // bi-directional many-to-one association to Year
    @ManyToOne
    @JoinColumn(name = "YEAR_ID")
    private Year year;

    // bi-directional many-to-one association to Worker
    @ManyToOne
    @JoinColumn(name = "WORKER_ID")
    private Worker worker;

    public Holiday()
    {
        id = new HolidayPK();
    }

    public Holiday(Worker wWorker, Year yYear)
    {
        id = new HolidayPK(wWorker.getWorkerId(), yYear.getYearId());
        worker = wWorker;
        year = yYear;
    }

    public HolidayPK getId()
    {
        return this.id;
    }

    public void setId(HolidayPK id)
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

    public Year getYear()
    {
        return this.year;
    }

    public void setYear(Year year)
    {
        this.year = year;
        id.setYearId(year.getYearId());
    }

    public Worker getWorker()
    {
        return this.worker;
    }

    public void setWorker(Worker worker)
    {
        this.worker = worker;
        id.setWorkerId(worker.getWorkerId());
    }

    @Override
    public String toString()
    {
        return "Holiday [id=" + id + ", countDays=" + countDays + ", year=" + year + ", worker=" + worker + "]";
    }

}