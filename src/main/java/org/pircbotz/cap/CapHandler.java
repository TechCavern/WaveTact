package org.pircbotz.cap;

import java.util.List;
import org.pircbotz.PircBotZ;
import org.pircbotz.exception.CAPException;

public interface CapHandler {

    public boolean handleLS(PircBotZ bot, List<String> capabilities) throws CAPException;

    public boolean handleACK(PircBotZ bot, List<String> capabilities) throws CAPException;

    public boolean handleNAK(PircBotZ bot, List<String> capabilities) throws CAPException;

    public boolean handleUnknown(PircBotZ bot, String rawLine) throws CAPException;
}
