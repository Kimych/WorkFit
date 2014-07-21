package by.uniterra.dai.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the holiday database table.
 * 
 */
@Embeddable
public class HolidayPK implements Serializable
{
    // default serial version id, required for serializable classes.
    private static final long serialVersionUID = 1L;

    @Column(name = "YEAR_ID", insertable = false, updatable = false)
    private int yearId;

    @Column(name = "WORKER_ID", insertable = false, updatable = false)
    private int workerId;

    public HolidayPK()
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

    public int getWorkerId()
    {
	return this.workerId;
    }

    public void setWorkerId(int workerId)
    {
	this.workerId = workerId;
    }

    public boolean equals(Object other)
    {
	if (this == other)
	{
	    return true;
	}
	if (!(other instanceof HolidayPK))
	{
	    return false;
	}
	HolidayPK castOther = (HolidayPK) other;
	return (this.yearId == castOther.yearId)
		&& (this.workerId == castOther.workerId);
    }

    public int hashCode()
    {
	final int prime = 31;
	int hash = 17;
	hash = hash * prime + this.yearId;
	hash = hash * prime + this.workerId;

	return hash;
    }
}