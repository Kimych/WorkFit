package by.uniterra.dai.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the workers database table.
 * 
 */
@Entity
@Table(name = "workers")
@NamedQuery(name = "Worker.findAll", query = "SELECT w FROM Worker w")
public class Worker implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WORKERS_ID")
    private int workersId;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "SEKOND_NAME")
    private String sekondName;

    @Column(name = "TEL_NUMBER")
    private String telNumber;

    @Column(name = "THIRD_NAME")
    private String thirdName;

    // bi-directional many-to-one association to Department
    @ManyToOne
    @JoinColumn(name = "DEPARTMENT_ID")
    private Department department;

    public Worker()
    {
    }

    public int getWorkersId()
    {
	return this.workersId;
    }

    public void setWorkersId(int workersId)
    {
	this.workersId = workersId;
    }

    public String getFirstName()
    {
	return this.firstName;
    }

    public void setFirstName(String firstName)
    {
	this.firstName = firstName;
    }

    public String getSekondName()
    {
	return this.sekondName;
    }

    public void setSekondName(String sekondName)
    {
	this.sekondName = sekondName;
    }

    public String getTelNumber()
    {
	return this.telNumber;
    }

    public void setTelNumber(String telNumber)
    {
	this.telNumber = telNumber;
    }

    public String getThirdName()
    {
	return this.thirdName;
    }

    public void setThirdName(String thirdName)
    {
	this.thirdName = thirdName;
    }

    public Department getDepartment()
    {
	return this.department;
    }

    public void setDepartment(Department department)
    {
	this.department = department;
    }

    @Override
    public String toString()
    {
	return "Worker [workersId=" + workersId + ", firstName=" + firstName
		+ ", sekondName=" + sekondName + ", telNumber=" + telNumber
		+ ", thirdName=" + thirdName + ", department=" + department
		+ "]";
    }

    
}