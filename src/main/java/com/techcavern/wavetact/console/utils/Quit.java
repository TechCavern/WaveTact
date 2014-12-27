package com.techcavern.wavetact.console.utils;

import com.techcavern.wavetact.Main;
import com.techcavern.wavetact.annot.CMDLine;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.objects.CommandLine;

@CMDLine
public class Quit extends CommandLine {

	public Quit() {
		super(GeneralUtils.toArray("quit exit disconnect"), "Disconnects you.", false);
	}

	@Override
	public void doAction(String[] args, CommandVariables commandVariables) {
		Main.consoleServer.keepConnectionRunning = false;
		commandVariables.getPrintStream().println("Bye!");
	}
}
