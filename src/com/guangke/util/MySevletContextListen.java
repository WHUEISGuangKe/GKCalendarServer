package com.guangke.util;

import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.guangke.dao.CalendarDao;
import com.guangke.dao.impl.CalendarDaoImpl;

/**
 * Application Lifecycle Listener implementation class MySevletContextListen
 *
 */
@WebListener
public class MySevletContextListen implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public MySevletContextListen() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  { 
         System.out.println("初始化操作");
         CalendarDao dao = new CalendarDaoImpl();
         dao.readVersionMap();
         System.out.println("初始化完成");
    }
}
