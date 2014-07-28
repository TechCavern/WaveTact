package org.pircbotz.exception;

import org.pircbotz.hooks.Event;
import org.apache.commons.lang3.Validate;

class UnknownEventException extends RuntimeException {

    private static final long serialVersionUID = 40292L;

    private UnknownEventException(Event event, Throwable cause) {
        super("Unknown Event " + (event == null ? null : event.getClass().toString()), cause);
        Validate.notNull(event, "Event cannot be null");
    }

    public UnknownEventException(Event event) {
        this(event, null);
    }
}
