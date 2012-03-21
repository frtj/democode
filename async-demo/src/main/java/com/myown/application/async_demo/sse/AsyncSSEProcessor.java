package com.myown.application.async_demo.sse;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AsyncSSEProcessor implements Runnable {
	
	Logger logger = LoggerFactory.getLogger("com.myown.application.async_demo.sse.AsyncSSEProcessor");

	private final AsyncContext asyncContext;

	public AsyncSSEProcessor(AsyncContext asyncContext) {
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
				try {
					sendStreamClose((HttpServletResponse) asyncContext.getResponse());
				} catch (IOException e) {
					e.printStackTrace();
				}
				continue;
			}
			
			String msg = "Sending from server the count -  " + count;
			
			try {
				HttpServletResponse response = (HttpServletResponse) asyncContext.getResponse();
				if( response != null )
				{
					logger.debug("writing..." + count);
					
					response.setContentType("text/event-stream"); 
					PrintWriter acWriter = response.getWriter();
										
					acWriter.write("data:" + msg + "\n\n");
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
	
	private void sendStreamClose(HttpServletResponse response) throws IOException
	{
		if( response != null )
		{
			logger.info("sending closeStream...");

			response.setContentType("text/event-stream"); 
			PrintWriter acWriter = response.getWriter();

			acWriter.write("event:closeStream\n");
			acWriter.write("data: closing shit\n\n");
            acWriter.flush();
		}
		else
			logger.error( "closing : response is null" );
	}
}
