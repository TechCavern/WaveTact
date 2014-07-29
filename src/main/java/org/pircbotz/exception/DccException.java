package org.pircbotz.exception;

import org.pircbotz.User;
import org.apache.commons.lang3.Validate;

public class DccException extends RuntimeException {

    private static final long serialVersionUID = 60382L;
    private final Reason ourReason;
    private final User user;

    public DccException(Reason reason, User user, String detail, Throwable cause) {
        super(reason + " from user " + (user == null ? null : user.getNick()) + ": " + detail, cause);
        Validate.notNull(reason, "Reason cannot be null");
        Validate.notNull(user, "User cannot be null");
        this.ourReason = reason;
        this.user = user;
    }

    public DccException(Reason reason, User user, String detail) {
        this(reason, user, detail, null);
    }

    public Reason getOurReason() {
        return ourReason;
    }

    public User getUser() {
        return user;
    }

    public static enum Reason {

        UnknownFileTransferResume,
        ChatNotConnected,
        ChatCancelled,
        ChatTimeout,
        FileTransferCancelled,
        FileTransferTimeout,
        FileTransferResumeTimeout,
        FileTransferResumeCancelled,
        DccPortsInUse
    }
}
