package com.myown.application.async_demo.felles;

import java.io.IOException;

import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;

public class MyAsyncListener implements AsyncListener {

	@Override
	public void onComplete(AsyncEvent arg0) throws IOException {
		System.out.println( "MyAsyncListener::onComplete" );
	}

	@Override
	public void onError(AsyncEvent arg0) throws IOException {
		System.out.println( "MyAsyncListener::onError" );
	}

	@Override
	public void onStartAsync(AsyncEvent arg0) throws IOException {
		System.out.println( "MyAsyncListener::onStartAsync" );
	}

	@Override
	public void onTimeout(AsyncEvent arg0) throws IOException {
		System.out.println( "MyAsyncListener::onTimeout" );
	}

}
