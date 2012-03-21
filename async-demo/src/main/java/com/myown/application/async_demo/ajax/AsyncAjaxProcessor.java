package com.myown.application.async_demo.ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AsyncAjaxProcessor implements Runnable {
	
	Logger logger = LoggerFactory.getLogger("com.myown.application.async_demo.ajax.AsyncAjaxProcessor");

	private final AsyncContext asyncContext;

	public AsyncAjaxProcessor(AsyncContext asyncContext) {
		this.asyncContext = asyncContext;
	}

	@Override
	public void run() {

		boolean done = false;
		int count = 0;
		while (!Thread.currentThread().isInterrupted() && !done ) {
			logger.debug("Thread running...");

			if( count > 10 )
			{
				done = true;
				continue;
			}
			
			String msg = "Sending from server the count -  " + count;
			
			try {
				HttpServletResponse response = (HttpServletResponse) asyncContext.getResponse();
				if( response != null )
				{
					logger.debug("writing..." + count);
					
					PrintWriter acWriter = response.getWriter();
					// using | as delimiter
					acWriter.write( "|" + msg );
	                acWriter.flush();
				}
				else
					logger.error( "response is null" );
                
            } catch(IOException ex) {
            	logger.error( "failure in writing to response", ex );
                done = true;
            }
			
			count++;
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				done = true;
			}
		}
		
		asyncContext.complete();
	}
	
	
}
