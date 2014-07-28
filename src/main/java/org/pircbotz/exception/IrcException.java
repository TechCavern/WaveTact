package org.pircbotz.exception;

import org.apache.commons.lang3.Validate;

public class IrcException extends Exception {

    private static final long serialVersionUID = 503932L;

    public IrcException(Reason reason, String detail) {
        super(reason + ": " + detail);
        Validate.notNull(reason, "Reason cannot be null");
        Validate.notNull(detail, "Detail cannot be null");
    }

    public static enum Reason {

        AlreadyConnected,
        CannotLogin,
        ReconnectBeforeConnect,
    }
}
