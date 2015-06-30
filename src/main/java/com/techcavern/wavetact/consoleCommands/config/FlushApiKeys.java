package com.techcavern.wavetact.consoleCommands.config;

import com.techcavern.wavetact.annot.ConCMD;
import com.techcavern.wavetact.objects.CommandIO;
import com.techcavern.wavetact.objects.ConsoleCommand;
import com.techcavern.wavetact.utils.ConfigUtils;
import com.techcavern.wavetact.utils.GeneralUtils;

@ConCMD
public class FlushApiKeys extends ConsoleCommand{
    public FlushApiKeys() {
        super(GeneralUtils.toArray("flushapikeys"), "flushapikeys", "Flushes the API Keys");
    }

    @Override
    public void onCommand(String[] args, CommandIO commandIO) throws Exception {
        ConfigUtils.flushApiKeys();
    }
}
