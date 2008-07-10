package com.idega.app.eplatform.headless;

import org.eclipse.core.runtime.adaptor.EclipseStarter;
public class Main {
	public static void main(String[] args) {
		String exitCode = "-1";
		
		try {
			EclipseStarter.run(args, null);
			exitCode = System.getProperty("eclipse.exitcode"); //$NON-NLS-1$
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.exit(Integer.parseInt(exitCode));
	}
}