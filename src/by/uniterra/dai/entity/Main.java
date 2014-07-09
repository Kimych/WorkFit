package by.uniterra.dai.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.eclipse.persistence.config.PersistenceUnitProperties;

public class Main
{

    private static final String PERSISTENCE_UNIT_NAME = "WorkFit";
    private static EntityManagerFactory factory;

    public static void main(String[] args)
    {
	 //factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	Map<String, String> mapCustomProp = new HashMap<String, String>();
        // put system configuration properties
        mapCustomProp.put(PersistenceUnitProperties.JDBC_URL, "jdbc:mysql://192.168.1.19:3306/testDB");
        mapCustomProp.put(PersistenceUnitProperties.JDBC_USER, "testdb");
        mapCustomProp.put(PersistenceUnitProperties.JDBC_PASSWORD, "testdb");
        mapCustomProp.put(PersistenceUnitProperties.JDBC_DRIVER, "com.mysql.jdbc.Driver");
//the correct way to disable the shared cache (L2 cache)
        mapCustomProp.put("eclipselink.cache.shared.default", "false");

        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, mapCustomProp);
	    EntityManager em = factory.createEntityManager();
	    // read the existing entries and write to console
	    Query q = em.createQuery("select w from Department w");
	    @SuppressWarnings("unchecked")
	    List<Department> depList = q.getResultList();
	    for (Department department : depList) {
	      System.out.println(department);
	    }
	    System.out.println("Size: " + depList.size());

	 // create new department
	   /* em.getTransaction().begin();
	    Department department = new Department();
	    department.setDepartmentName("IT");
	    department.setNote("test update");
	    em.persist(department);
	    em.getTransaction().commit();
	    em.close();*/

    }
    
    public void delLine( int id, EntityManager em)
    {
	//Query q = em.createQuery("DELETE  from Department WHERE departmentId = " + id);
	em.getTransaction().begin();

	
	
	em.getTransaction().commit();
    }
}
