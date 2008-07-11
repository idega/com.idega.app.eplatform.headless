package com.idega.app.eplatform.headless;

import java.util.Iterator;
import java.util.Map;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

import com.idega.app.eplatform.appservermanager.AppserverManager;
import com.idega.app.eplatform.appservermanager.AppservermanagerPlugin;


public class MainRunner implements IApplication {

	boolean run=true;
	
	public Object start(IApplicationContext args) throws Exception {
		Map arguments = args.getArguments();
		//String[] cmdArgs = (String[])args;
		if(arguments == null || arguments.isEmpty()){
			System.out.println("hello, world.");
		}
		else{
			for (Iterator iterator = arguments.keySet().iterator(); iterator.hasNext();) {
				Object key = iterator.next();
				String sKey = key.toString();
				String value = arguments.get(key).toString();
				System.out.println("hello, "+sKey+"="+value);
			}
			
			//for(int i=0; i<cmdArgs.length;i++){
			//	
			//}
		}
		String status = null;
		while(run){
			String newStatus = getManager().getMainWebapp().getStatus();
			if(status!=null){
				if(!status.equals(newStatus)){
					System.out.println("[Eplatform] : "+status);
				}
			}
			status=newStatus;
			Thread.sleep(1000);
		}
		return EXIT_OK;
	}

	public void stop() {
		// TODO Auto-generated method stub
		
	}
	
	private AppserverManager getManager() {
		return AppservermanagerPlugin.getPlugin().getManager();
	}

}
