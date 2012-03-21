package com.myown.application.async_demo.iframe;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myown.application.async_demo.felles.MessageTool;

public class AsyncIframeProcessor implements Runnable {
	
	Logger logger = LoggerFactory.getLogger("com.myown.application.async_demo.iframe.AsyncIframeProcessor");

	private final AsyncContext asyncContext;
	
	public AsyncIframeProcessor(AsyncContext asyncContext ) {
		this.asyncContext = asyncContext;
	}

	@Override
	public void run() {

		MessageTool tool = new MessageTool();
		
		boolean done = false;
		int count = 0;
		while (!Thread.currentThread().isInterrupted() && !done ) {
			logger.debug("Thread running...");

			if( count > 10 )
			{
				done = true;
				continue;
			}
			
			String msg = "Sending from server the count: " + count++;
			
			try {
				HttpServletResponse response = (HttpServletResponse) asyncContext.getResponse();
				if( response != null )
				{
					logger.debug("writing..." + count);
					
					response.setContentType("text/html"); 
					PrintWriter acWriter = response.getWriter();
	                acWriter.write(tool.asScript(tool.toJsonp("window.parent.app.update", "t", msg)));
	                acWriter.flush();
				}
				else
					logger.error( "response is null" );
                
            } catch(IOException ex) {
            	logger.error( "failure in writing to response", ex );
                done = true;
            }
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				done = true;
			}
		}
		
		asyncContext.complete();
	}
}
