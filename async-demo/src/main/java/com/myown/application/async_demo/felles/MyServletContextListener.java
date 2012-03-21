package com.myown.application.async_demo.felles;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class MyServletContextListener implements ServletContextListener {

	public MyServletContextListener() {
	}

	public void contextInitialized(ServletContextEvent arg0) {
		Executor executor = new ThreadPoolExecutor(10, 10, 50000L, TimeUnit.MILLISECONDS, 
		new LinkedBlockingQueue<Runnable>(100));

		arg0.getServletContext().setAttribute("executor", executor);
	}

	public void contextDestroyed(ServletContextEvent arg0) {
		ThreadPoolExecutor t = (ThreadPoolExecutor)arg0.getServletContext().getAttribute("executor");
		t.shutdownNow();
	}

}
