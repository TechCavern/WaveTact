package com.techcavern.wavetact.consoleCommands.utils;

import com.techcavern.wavetact.Main;
import com.techcavern.wavetact.annot.CMDLine;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.objects.CommandIO;
import com.techcavern.wavetact.utils.objects.ConsoleCommand;

@CMDLine
public class Quit extends ConsoleCommand {

	public Quit() {
		super(GeneralUtils.toArray("quit exit"),"quit", "exits from console");
	}

	@Override
	public void onCommand(String[] args, CommandIO commandIO) {
		Main.consoleServer.keepConnectionRunning = false;
		commandIO.getPrintStream().println("Bye!");
	}
}
