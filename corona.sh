#!/bin/bash
####################################################################
# Copyright (c) 2007-2008 Compuware Corporation and others.
# 
# All rights reserved. This program and the accompanying 
# materials are made available under the terms of the 
# Eclipse Public License v1.0 which accompanies this 
# distribution, and is available at:
# http://www.eclipse.org/legal/epl-v10.html 
# 
# Contributors:
#     Compuware Corporation - initial API and implementation
####################################################################
echo
echo Corona - Eclipse Tool Services Framework

#====================================================================
# Command: Run the Corona server-side environment
#====================================================================
function cmdRun() {
	echo Running the Corona server-side platform

	$jvm $jvmProperties -jar $launcher -console -clean -application org.eclipse.corona.application
}

#====================================================================
# Command: Test the Corona server-side environment
#====================================================================
function cmdTest() {
	echo Running the Corona Test Framework

	jvmProperties="$jvmProperties  "-Dosgi.noShutdown=true
	
	$jvm $jvmProperties -jar $launcher -console -clean -application org.eclipse.corona.test.application
}

#====================================================================
# Command: Install a feature into the Corona server-side environment
#====================================================================
function cmdInstall() {
	echo Install feature into Corona

	updateSite=$1
	featureId=$2
	featureVersion=$3

	echo update site: $updateSite
	echo  feature id: $featureId
	echo     version: $featureVersion
	
	echo "*** not yet implemented ***"
	return
		
	$jvm $jvmProperties -jar $launcher -console -clean -application org.eclipse.update.core.standaloneUpdate -command install -from $updateSite -featureId $featureId -version $featureVersion 	
}

#====================================================================
# display help
#====================================================================
function help() {
	echo
	echo "syntax: corona <cmd> [args]"
	echo "*	cmd = one of the following commands"
	echo "*		run     - run the Corona server application"
	echo "*		test    - run the Corona test framework application"
	echo "*		install - install a feature into Corona"
	echo
}

#====================================================================
# Error: could not find Eclipse Equinox launcher
#====================================================================
function errLauncher() {
	echo
	echo Error: cound not find Eclipse Equinox launcher 
}

#====================================================================
# Error: invalid command line syntax
#====================================================================
function errSyntax() {
	echo
	echo Error: invalid command line syntax
	help 
}

#--------------------------------------------------------------------
# /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\
#--------------------------------------------------------------------

#--------------------------------------------------------------------
# Identify the Eclipse Equinox launcher to use
#--------------------------------------------------------------------
launcher=`ls plugins/org.eclipse.equinox.launcher_*.jar`
if [ "$launcher" = "" ]; then
	errLauncher
	exit 2
fi

# define the jvm to use
jvm=java

cmd=$1
shift

case "$cmd" in
	run)		cmdRun;;
	test) 		cmdTest;;
	install)	cmdInstall $1 $2 $3;;
	*)		errSyntax;;
esac

exit 0

