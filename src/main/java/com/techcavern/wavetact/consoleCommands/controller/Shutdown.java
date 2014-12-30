package com.techcavern.wavetact.consoleCommands.controller;

import com.techcavern.wavetact.annot.CMDLine;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.Registry;
import com.techcavern.wavetact.objects.CommandIO;
import com.techcavern.wavetact.objects.ConsoleCommand;

@CMDLine
public class Shutdown extends ConsoleCommand {

	public Shutdown() {
		super(GeneralUtils.toArray("shutdown stop"),"stop", "Stops the bot.");
	}

	@Override
	public void onCommand(String[] args, CommandIO commandIO) {
		Registry.consoleServer.keepConsoleRunning = false;
		Registry.consoleServer.keepConnectionRunning = false;
		commandIO.getPrintStream().println("Shutting down...");
	}
}
