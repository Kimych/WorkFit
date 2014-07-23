package by.uniterra.dai.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the spent_holiday database table.
 * 
 */
@Embeddable
public class SpentHolidayPK implements Serializable
{
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "MONTH_ID", insertable = false, updatable = false)
	private int monthId;

	@Column(name = "WORKER_ID", insertable = false, updatable = false)
	private int workerId;

	public SpentHolidayPK()
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
		if (!(other instanceof SpentHolidayPK))
		{
			return false;
		}
		SpentHolidayPK castOther = (SpentHolidayPK) other;
		return (this.monthId == castOther.monthId)
				&& (this.workerId == castOther.workerId);
	}

	public int hashCode()
	{
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.monthId;
		hash = hash * prime + this.workerId;

		return hash;
	}
}