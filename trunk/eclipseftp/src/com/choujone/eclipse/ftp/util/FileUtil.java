package com.choujone.eclipse.ftp.util;

import org.eclipse.jface.preference.PreferenceStore;

public class FileUtil {
	public void saveFile(){
		   PreferenceStore preferenceStore = new PreferenceStore("F:\\myPreference.properties"); 
		   preferenceStore.setValue("Database", "mysql");
		   try {
			   preferenceStore.save();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void readFile(){
		PreferenceStore preferenceStore = new PreferenceStore("F:\\myPreference.properties"); 
		try {
			preferenceStore.load();
		} catch (Exception e) {
			// TODO: handle exception
		}
		   String database = preferenceStore.getString("Database"); 
	}
}
