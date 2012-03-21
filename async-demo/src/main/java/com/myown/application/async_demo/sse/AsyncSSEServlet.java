package com.myown.application.async_demo.sse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.Executor;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.myown.application.async_demo.felles.MessageTool;
import com.myown.application.async_demo.felles.MyAsyncListener;

@WebServlet( urlPatterns="/AsyncSSEServlet", asyncSupported=true, name="AsyncSSEServlet")
public class AsyncSSEServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//response.setContentType("text/html");
		setNoCacheHeaders(response);

		request.setCharacterEncoding("UTF-8");

		String cmd = request.getParameter("cmd") != null ? request.getParameter("cmd") : "";

		switch (cmd) {
			case "sse":
				System.out.println("sse");
				request.getRequestDispatcher("/WEB-INF/pages/sse.jsp").forward(request, response);
				break;
			case "subscribeSSE":
				System.out.println("subscribeSSE");

				response.setContentType("text/event-stream");
				response.setCharacterEncoding("UTF-8");
				
				PrintWriter writer = response.getWriter();
				// payload to kickstart long connection
				// for Safari, Chrome, IE and Opera
				for (int i = 0; i < 10; i++) {
					writer.write(MessageTool.getJunk());
				}
				writer.flush();
				
				AsyncContext asyncCtx = request.startAsync();
				asyncCtx.addListener(new MyAsyncListener());
				asyncCtx.setTimeout(20000);
				Executor executor = (Executor) request.getServletContext().getAttribute("executor");
				
				// delegate long running process to an "async" thread
				executor.execute(new AsyncSSEProcessor(asyncCtx));
				
				break;
				default:
					System.out.println("ERROR? cmd is set to something unknown: " + cmd);
		}
			
		System.out.println("service done");
	}
	
	private void setNoCacheHeaders(HttpServletResponse response) {
		response.setHeader("pragma", "no-cache");
		response.setHeader("Cache-Control", "must-revalidate");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Expires", "Sat, 6 may 1995 12:00:00 GMT");
	}

}
