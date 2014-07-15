package by.uniterra.system.main;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.eclipse.persistence.config.PersistenceUnitProperties;

import by.uniterra.dai.eao.WorkerrService;
import by.uniterra.dai.entity.Worker;

public class Main
{

    private static final String PERSISTENCE_UNIT_NAME = "WorkFit";
    public static EntityManagerFactory factory;
    public static int ID_DEL_WORKER = 6;

    public static EntityManagerFactory connectDB()
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
	return factory;
    }

    public static void main(String[] args)
    {

	// 2.1 Подключиться из приложения к базе данных.
	connectDB();

	// 2.2 Прочитать всех Worker и вывезти в консоль все детали (имена,
	// фамилии, ID, детали связанного Department и т.п.).

	// TODO codehack: add EntityManager to a WorkerrService class like a
	// member, initialize it in a constructor (like new WorkerrService(em))
	// and after that you can call "PrintAllWorkers" and "save" methods
	// without "em" in arguments (like service.PrintAllWorkers(),
	// service.save(work1)).

	WorkerrService service = new WorkerrService();
	service.PrintAllWorkers();

	// 2.3 Добавить нового Worker в базу данных.
	Worker work1 = new Worker();
	work1.setFirstName("worker6");
	work1.setSekondName("sekond_w6");
	work1.setThirdName("third_w6");
	work1.setTelNumber("888888888888");
	work1.setDepartment(null);
	service.save(work1);

	// TODO you should close EntityManager (em) before factory too

	// 2.4 Разорвать соединение с базой данных.
	service.emClose();
	factory.close();

	// 2.5 Опять подключиться и вывести данные о всех Worker (убедиться что
	// наша новая запись в базе).
	connectDB();
	WorkerrService service2 = new WorkerrService();
	service2.PrintAllWorkers();

	// 2.6 Удалить нашу запись из базы.
	service2.delete(ID_DEL_WORKER);

	// 2.7 Разорвать соединение с базой данных.
	service2.emClose();
	factory.close();

	// 2.8 Опять подключиться и вывести данные о всех Worker (убедиться что
	// наша новая запись была удалена из базы).
	connectDB();
	WorkerrService service3 = new WorkerrService();
	service3.PrintAllWorkers();

	// close connection
	service3.emClose();
	factory.close();
    }

}
