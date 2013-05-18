package com.choujone.eclipse.ftp.preferences.projectresource;

import java.util.Observer;

public class VariableChangeLinster {

	private Observer observer;
	public VariableChangeLinster(
			Observer observer) {
		this.observer=observer;
	}

	public void update(Object object) {
		if(null!=observer){
			observer.update(null, object);
		}
		
	}
	

}
