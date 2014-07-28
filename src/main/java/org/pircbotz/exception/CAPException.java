package org.pircbotz.exception;

import org.apache.commons.lang3.Validate;

public class CAPException extends RuntimeException {

    private static final long serialVersionUID = 393282L;

    public CAPException(Reason reason, String detail) {
        this(reason, detail, null);
    }

    private CAPException(Reason reason, String detail, Throwable cause) {
        super(reason + ": " + detail, cause);
        Validate.notNull(reason, "Reason cannot be null");
        Validate.notNull(detail, "Detail cannot be null");
    }

    public static enum Reason {

        UnsupportedCapability,
        SASLFailed,
        Other
    }
}
