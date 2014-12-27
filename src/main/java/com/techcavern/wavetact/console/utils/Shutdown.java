package com.techcavern.wavetact.console.utils;

import com.techcavern.wavetact.Main;
import com.techcavern.wavetact.annot.CMDLine;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.objects.CommandLine;

@CMDLine
public class Shutdown extends CommandLine {

	public Shutdown() {
		super(GeneralUtils.toArray("shutdown stop"), "Stops the server.", false);
	}

	@Override
	public void doAction(String[] args, CommandVariables commandVariables) {
		Main.consoleServer.keepConsoleRunning = false;
		Main.consoleServer.keepConnectionRunning = false;
		commandVariables.getPrintStream().println("Shutting down...");
	}
}
