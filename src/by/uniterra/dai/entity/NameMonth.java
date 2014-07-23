package by.uniterra.dai.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the name_month database table.
 * 
 */
@Entity
@Table(name = "name_month")
@NamedQuery(name = "NameMonth.findAll", query = "SELECT n FROM NameMonth n")
public class NameMonth implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "NAME_MONTH_ID")
	private int nameMonthId;

	private String name;

	// bi-directional many-to-one association to Month
	@OneToMany(mappedBy = "nameMonth")
	private List<Month> months;

	public NameMonth()
	{
	}

	public int getNameMonthId()
	{
		return this.nameMonthId;
	}

	public void setNameMonthId(int nameMonthId)
	{
		this.nameMonthId = nameMonthId;
	}

	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name = name;
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
		month.setNameMonth(this);

		return month;
	}

	public Month removeMonth(Month month)
	{
		getMonths().remove(month);
		month.setNameMonth(null);

		return month;
	}

	@Override
	public String toString()
	{
		return "NameMonth [nameMonthId=" + nameMonthId + ", name=" + name + "]";
	}

}