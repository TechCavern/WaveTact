package com.techcavern.wavetact.consoleCommands.utils;

import com.techcavern.wavetact.annot.ConCMD;
import com.techcavern.wavetact.objects.CommandIO;
import com.techcavern.wavetact.objects.ConsoleCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.Registry;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

@ConCMD
public class FlushWhoisCache extends ConsoleCommand {

    public FlushWhoisCache() {
        super(GeneralUtils.toArray("flushwhois"), "flushwois)", "Flushes Whois Cache");
    }

    @Override
    public void onCommand(String command, String[] args, CommandIO commandIO) throws Exception {
        Registry.WhoisEventCache.clear();
    }

}
