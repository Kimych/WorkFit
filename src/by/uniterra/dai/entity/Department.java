package by.uniterra.dai.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the department database table.
 * 
 */
@Entity
@NamedQuery(name = "Department.findAll", query = "SELECT d FROM Department d")
public class Department implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DEPARTMENT_ID", unique = true, nullable = false)
    private int departmentId;

    @Column(name = "DEPARTMENT_NAME")
    private String departmentName;

    private String note;

    // bi-directional many-to-one association to Worker
    // @OneToMany(mappedBy = "department")
    // private List<Worker> workers;

    public Department()
    {
    }

    public int getDepartmentId()
    {
        return this.departmentId;
    }

    public void setDepartmentId(int departmentId)
    {
        this.departmentId = departmentId;
    }

    public String getDepartmentName()
    {
        return this.departmentName;
    }

    public void setDepartmentName(String departmentName)
    {
        this.departmentName = departmentName;
    }

    public String getNote()
    {
        return this.note;
    }

    public void setNote(String note)
    {
        this.note = note;
    }

    /*
     * public List<Worker> getWorkers() { return this.workers; }
     * 
     * public void setWorkers(List<Worker> workers) { this.workers = workers; }
     * 
     * public Worker addWorker(Worker worker) { getWorkers().add(worker);
     * worker.setDepartment(this);
     * 
     * return worker; }
     * 
     * public Worker removeWorker(Worker worker) { getWorkers().remove(worker);
     * worker.setDepartment(null);
     * 
     * return worker; }
     */

    @Override
    public String toString()
    {
        return "Department [departmentId=" + departmentId + ", departmentName=" + departmentName + ", note=" + note + "]";
    }

}