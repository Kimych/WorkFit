package by.uniterra.dai.entity;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.eclipse.persistence.config.PersistenceUnitProperties;

public class Main
{

    private static final String PERSISTENCE_UNIT_NAME = "WorkFit";
    private static EntityManagerFactory factory;

    private static EntityManager connectDB()
    {
	Map<String, String> mapCustomProp = new HashMap<String, String>();
	// put system configuration properties
	mapCustomProp.put(PersistenceUnitProperties.JDBC_URL,
		"jdbc:mysql://192.168.1.19:3306/testDB");
	mapCustomProp.put(PersistenceUnitProperties.JDBC_USER, "testdb");
	mapCustomProp.put(PersistenceUnitProperties.JDBC_PASSWORD, "testdb");
	mapCustomProp.put(PersistenceUnitProperties.JDBC_DRIVER,
		"com.mysql.jdbc.Driver");
	// the correct way to disable the shared cache (L2 cache)
	mapCustomProp.put("eclipselink.cache.shared.default", "false");

	factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME,
		mapCustomProp);
	EntityManager em = factory.createEntityManager();
	return em;
    }

    public static void main(String[] args)
    {
	
	//2.1 Подключиться из приложения к базе данных.
	EntityManager em = connectDB();
	
	//2.2 Прочитать всех Worker и вывезти в консоль все детали (имена, фамилии, ID, детали связанного Department и т.п.).
	WorkerrService service = new WorkerrService();
	service.PrintAllWorkers(em);
	
	//2.3 Добавить нового Worker в базу данных.
	Worker work1 = new Worker();
	work1.setFirstName("worker6");
	work1.setSekondName("sekond_w6");
	work1.setThirdName("third_w6");
	work1.setTelNumber("888888888888");
	work1.setDepartment(null);
	service.save(em, work1);
	
	//2.4 Разорвать соединение с базой данных.
	factory.close();

	// dell line
	// Department dep = service.delete(em, 8); System.out.println(dep);	 
    }

}
