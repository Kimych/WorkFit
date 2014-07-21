package by.uniterra.dai.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the spent_holiday database table.
 * 
 */
@Entity
@Table(name = "spent_holiday")
@NamedQuery(name = "SpentHoliday.findAll", query = "SELECT s FROM SpentHoliday s")
public class SpentHoliday implements Serializable
{
    private static final long serialVersionUID = 1L;

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

}