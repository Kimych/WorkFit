package by.uniterra.dai.entity;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The persistent class for the holiday database table.
 * 
 */
@Entity
@NamedQuery(name = "Holiday.findAll", query = "SELECT h FROM Holiday h")
public class Holiday implements Serializable
{
    private static final long serialVersionUID = 1L;

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
    }

    public Worker getWorker()
    {
	return this.worker;
    }

    public void setWorker(Worker worker)
    {
	this.worker = worker;
    }

    @Override
    public String toString()
    {
	return "Holiday [id=" + id + ", countDays=" + countDays + ", year=" + year + ", worker=" + worker + "]";
    }

    @Override
    public int hashCode()
    {
	final int prime = 31;
	int result = 1;
	result = prime * result + countDays;
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	result = prime * result + ((worker == null) ? 0 : worker.hashCode());
	result = prime * result + ((year == null) ? 0 : year.hashCode());
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
	Holiday other = (Holiday) obj;
	if (countDays != other.countDays)
	    return false;
	if (id == null)
	{
	    if (other.id != null)
		return false;
	} else if (!id.equals(other.id))
	    return false;
	if (worker == null)
	{
	    if (other.worker != null)
		return false;
	} else if (!worker.equals(other.worker))
	    return false;
	if (year == null)
	{
	    if (other.year != null)
		return false;
	} else if (!year.equals(other.year))
	    return false;
	return true;
    }

}