package by.uniterra.system.main;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.eclipse.persistence.config.PersistenceUnitProperties;

import by.uniterra.dai.eao.WorkerrService;
import by.uniterra.dai.entity.Worker;

public class Main
{

    private static final String PERSISTENCE_UNIT_NAME = "WorkFit";
    public static int ID_DEL_WORKER = 6;
    
    private static EntityManagerFactory emfFactory;
    private static EntityManager emManager;
    

    /**
     * Extablish a new onnection to target DB
     */
    public static void connectToDB()
    {
        Map<String, String> mapCustomProp = new HashMap<String, String>();
        // put system configuration properties
        mapCustomProp.put(PersistenceUnitProperties.JDBC_URL, "jdbc:mysql://192.168.1.19:3306/testDB");
        mapCustomProp.put(PersistenceUnitProperties.JDBC_USER, "testdb");
        mapCustomProp.put(PersistenceUnitProperties.JDBC_PASSWORD, "testdb");
        mapCustomProp.put(PersistenceUnitProperties.JDBC_DRIVER, "com.mysql.jdbc.Driver");
        // the correct way to disable the shared cache (L2 cache)
        mapCustomProp.put("eclipselink.cache.shared.default", "false");
        //a new connection (factory) to target DB
        emfFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, mapCustomProp);
        emManager = emfFactory.createEntityManager();
    }
    
    /**
     * Disconnect from target DB
     */
    public static void disconnectFromDb()
    {
        // close EntityManager
        if (emManager != null && emManager.isOpen())
        {
            emManager.close();
        }
        // close EntityManagerFactory
        if (emfFactory != null && emfFactory.isOpen())
        {
            emfFactory.close();
        }
    }
    

    public static void main(String[] args)
    {

        // 2.1 Подключиться из приложения к базе данных.
        connectToDB();

        // 2.2 Прочитать всех Worker и вывезти в консоль все детали (имена,
        // фамилии, ID, детали связанного Department и т.п.).

        WorkerrService service = new WorkerrService(emManager);
        service.printAllWorkers();

        // 2.3 Добавить нового Worker в базу данных.
        Worker work1 = new Worker();
        work1.setFirstName("worker6");
        work1.setSekondName("sekond_w6");
        work1.setThirdName("third_w6");
        work1.setTelNumber("888888888888");
        work1.setDepartment(null);
        service.save(work1);

        // 2.4 Разорвать соединение с базой данных.
        disconnectFromDb();

        // 2.5 Опять подключиться и вывести данные о всех Worker (убедиться что
        // наша новая запись в базе).
        connectToDB();
        WorkerrService service2 = new WorkerrService(emManager);
        service2.printAllWorkers();

        // 2.6 Удалить нашу запись из базы.
        service2.delete(ID_DEL_WORKER);

        // 2.7 Разорвать соединение с базой данных.
        disconnectFromDb();

        // 2.8 Опять подключиться и вывести данные о всех Worker (убедиться что
        // наша новая запись была удалена из базы).
        connectToDB();
        WorkerrService service3 = new WorkerrService(emManager);
        service3.printAllWorkers();

        // close connection
        disconnectFromDb();
    }

}
