package com.myown.application.async_demo.iframe;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.Executor;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myown.application.async_demo.felles.MessageTool;
import com.myown.application.async_demo.felles.MyAsyncListener;

@WebServlet(urlPatterns = "/AsyncIframeServlet", asyncSupported = true, name = "AsyncIframeServlet")
public class AsyncIframeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	Logger logger = LoggerFactory.getLogger("com.myown.application.async_demo.iframe.AsyncIframeServlet");

	protected void service(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		setNoCacheHeaders(response);

		String cmd = request.getParameter("cmd") != null ? request.getParameter("cmd") : "";

		switch (cmd) {
			case "iframe":
				request.getRequestDispatcher("/WEB-INF/pages/iframe.jsp").forward(request, response);
				break;
			case "subscribeIframe":
				response.setContentType("text/html");
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

				executor.execute(new AsyncIframeProcessor(asyncCtx));
				break;
			default:
				logger.error("cmd is set to something unknown: " + cmd);
		}

		logger.debug("service is done");
	}

	private void setNoCacheHeaders(HttpServletResponse response) {
		response.setHeader("pragma", "no-cache");
		response.setHeader("Cache-Control", "must-revalidate");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Expires", "Sat, 6 may 1995 12:00:00 GMT");
	}
}
