package com.itheima.listener;


import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;

public class MyContextListener implements ServletContextListener{

    public void contextInitialized(ServletContextEvent servletContextEvent) {

    }

    public void contextDestroyed(ServletContextEvent sce) {
        // Clear mysql thread
        try {
            AbandonedConnectionCleanupThread.shutdown();
        } catch (Throwable t) {

        }

        // This manually deregisters JDBC driver, which prevents Tomcat 7 from complaining about memory leaks
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
            } catch (Throwable t) {
            }
        }

      /*  // Clear C3P0 data source
        for (Object o : C3P0Registry.getPooledDataSources()) {
            try {
                ((PooledDataSource) o).close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/
    }
}
