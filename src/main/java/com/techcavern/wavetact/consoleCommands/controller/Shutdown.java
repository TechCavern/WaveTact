package com.techcavern.wavetact.consoleCommands.controller;

import com.techcavern.wavetact.Main;
import com.techcavern.wavetact.annot.CMDLine;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.objects.CommandIO;
import com.techcavern.wavetact.utils.objects.ConsoleCommand;

@CMDLine
public class Shutdown extends ConsoleCommand {

	public Shutdown() {
		super(GeneralUtils.toArray("shutdown stop"),"stop", "Stops the bot.");
	}

	@Override
	public void onCommand(String[] args, CommandIO commandIO) {
		Main.consoleServer.keepConsoleRunning = false;
		Main.consoleServer.keepConnectionRunning = false;
		commandIO.getPrintStream().println("Shutting down...");
	}
}
