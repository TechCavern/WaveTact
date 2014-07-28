package org.pircbotz.hooks;

import org.pircbotz.hooks.events.ActionEvent;
import org.pircbotz.hooks.events.ChannelInfoEvent;
import org.pircbotz.hooks.events.ConnectEvent;
import org.pircbotz.hooks.events.DisconnectEvent;
import org.pircbotz.hooks.events.FingerEvent;
import org.pircbotz.hooks.events.HalfOpEvent;
import org.pircbotz.hooks.events.IncomingChatRequestEvent;
import org.pircbotz.hooks.events.IncomingFileTransferEvent;
import org.pircbotz.hooks.events.InviteEvent;
import org.pircbotz.hooks.events.JoinEvent;
import org.pircbotz.hooks.events.KickEvent;
import org.pircbotz.hooks.events.MessageEvent;
import org.pircbotz.hooks.events.ModeEvent;
import org.pircbotz.hooks.events.MotdEvent;
import org.pircbotz.hooks.events.NickAlreadyInUseEvent;
import org.pircbotz.hooks.events.NickChangeEvent;
import org.pircbotz.hooks.events.NoticeEvent;
import org.pircbotz.hooks.events.OpEvent;
import org.pircbotz.hooks.events.OwnerEvent;
import org.pircbotz.hooks.events.PartEvent;
import org.pircbotz.hooks.events.PingEvent;
import org.pircbotz.hooks.events.PrivateMessageEvent;
import org.pircbotz.hooks.events.QuitEvent;
import org.pircbotz.hooks.events.RemoveChannelBanEvent;
import org.pircbotz.hooks.events.RemoveChannelKeyEvent;
import org.pircbotz.hooks.events.RemoveChannelLimitEvent;
import org.pircbotz.hooks.events.RemoveInviteOnlyEvent;
import org.pircbotz.hooks.events.RemoveModeratedEvent;
import org.pircbotz.hooks.events.RemoveNoExternalMessagesEvent;
import org.pircbotz.hooks.events.RemovePrivateEvent;
import org.pircbotz.hooks.events.RemoveSecretEvent;
import org.pircbotz.hooks.events.RemoveTopicProtectionEvent;
import org.pircbotz.hooks.events.ServerPingEvent;
import org.pircbotz.hooks.events.ServerResponseEvent;
import org.pircbotz.hooks.events.SetChannelBanEvent;
import org.pircbotz.hooks.events.SetChannelKeyEvent;
import org.pircbotz.hooks.events.SetChannelLimitEvent;
import org.pircbotz.hooks.events.SetInviteOnlyEvent;
import org.pircbotz.hooks.events.SetModeratedEvent;
import org.pircbotz.hooks.events.SetNoExternalMessagesEvent;
import org.pircbotz.hooks.events.SetPrivateEvent;
import org.pircbotz.hooks.events.SetSecretEvent;
import org.pircbotz.hooks.events.SetTopicProtectionEvent;
import org.pircbotz.hooks.events.SocketConnectEvent;
import org.pircbotz.hooks.events.SuperOpEvent;
import org.pircbotz.hooks.events.TimeEvent;
import org.pircbotz.hooks.events.TopicEvent;
import org.pircbotz.hooks.events.UnknownEvent;
import org.pircbotz.hooks.events.UserListEvent;
import org.pircbotz.hooks.events.UserModeEvent;
import org.pircbotz.hooks.events.VersionEvent;
import org.pircbotz.hooks.events.VoiceEvent;
import org.pircbotz.hooks.events.WhoisEvent;
import org.pircbotz.generics.GenericCTCPEvent;
import org.pircbotz.generics.GenericChannelEvent;
import org.pircbotz.generics.GenericChannelModeEvent;
import org.pircbotz.generics.GenericChannelUserEvent;
import org.pircbotz.generics.GenericDCCEvent;
import org.pircbotz.generics.GenericMessageEvent;
import org.pircbotz.generics.GenericUserEvent;
import org.pircbotz.generics.GenericUserModeEvent;

public abstract class ListenerAdapter implements Listener {

    @Override
    public void onEvent(Event event) throws Exception {
        if (event instanceof ActionEvent) {
            onAction((ActionEvent) event);
        } else if (event instanceof ChannelInfoEvent) {
            onChannelInfo((ChannelInfoEvent) event);
        } else if (event instanceof ConnectEvent) {
            onConnect((ConnectEvent) event);
        } else if (event instanceof DisconnectEvent) {
            onDisconnect((DisconnectEvent) event);
        } else if (event instanceof FingerEvent) {
            onFinger((FingerEvent) event);
        } else if (event instanceof HalfOpEvent) {
            onHalfOp((HalfOpEvent) event);
        } else if (event instanceof IncomingChatRequestEvent) {
            onIncomingChatRequest((IncomingChatRequestEvent) event);
        } else if (event instanceof IncomingFileTransferEvent) {
            onIncomingFileTransfer((IncomingFileTransferEvent) event);
        } else if (event instanceof InviteEvent) {
            onInvite((InviteEvent) event);
        } else if (event instanceof JoinEvent) {
            onJoin((JoinEvent) event);
        } else if (event instanceof KickEvent) {
            onKick((KickEvent) event);
        } else if (event instanceof MessageEvent) {
            onMessage((MessageEvent) event);
        } else if (event instanceof ModeEvent) {
            onMode((ModeEvent) event);
        } else if (event instanceof MotdEvent) {
            onMotd((MotdEvent) event);
        } else if (event instanceof NickAlreadyInUseEvent) {
            onNickAlreadyInUse((NickAlreadyInUseEvent) event);
        } else if (event instanceof NickChangeEvent) {
            onNickChange((NickChangeEvent) event);
        } else if (event instanceof NoticeEvent) {
            onNotice((NoticeEvent) event);
        } else if (event instanceof OpEvent) {
            onOp((OpEvent) event);
        } else if (event instanceof OwnerEvent) {
            onOwner((OwnerEvent) event);
        } else if (event instanceof PartEvent) {
            onPart((PartEvent) event);
        } else if (event instanceof PingEvent) {
            onPing((PingEvent) event);
        } else if (event instanceof PrivateMessageEvent) {
            onPrivateMessage((PrivateMessageEvent) event);
        } else if (event instanceof QuitEvent) {
            onQuit((QuitEvent) event);
        } else if (event instanceof RemoveChannelBanEvent) {
            onRemoveChannelBan((RemoveChannelBanEvent) event);
        } else if (event instanceof RemoveChannelKeyEvent) {
            onRemoveChannelKey((RemoveChannelKeyEvent) event);
        } else if (event instanceof RemoveChannelLimitEvent) {
            onRemoveChannelLimit((RemoveChannelLimitEvent) event);
        } else if (event instanceof RemoveInviteOnlyEvent) {
            onRemoveInviteOnly((RemoveInviteOnlyEvent) event);
        } else if (event instanceof RemoveModeratedEvent) {
            onRemoveModerated((RemoveModeratedEvent) event);
        } else if (event instanceof RemoveNoExternalMessagesEvent) {
            onRemoveNoExternalMessages((RemoveNoExternalMessagesEvent) event);
        } else if (event instanceof RemovePrivateEvent) {
            onRemovePrivate((RemovePrivateEvent) event);
        } else if (event instanceof RemoveSecretEvent) {
            onRemoveSecret((RemoveSecretEvent) event);
        } else if (event instanceof RemoveTopicProtectionEvent) {
            onRemoveTopicProtection((RemoveTopicProtectionEvent) event);
        } else if (event instanceof ServerPingEvent) {
            onServerPing((ServerPingEvent) event);
        } else if (event instanceof ServerResponseEvent) {
            onServerResponse((ServerResponseEvent) event);
        } else if (event instanceof SetChannelBanEvent) {
            onSetChannelBan((SetChannelBanEvent) event);
        } else if (event instanceof SetChannelKeyEvent) {
            onSetChannelKey((SetChannelKeyEvent) event);
        } else if (event instanceof SetChannelLimitEvent) {
            onSetChannelLimit((SetChannelLimitEvent) event);
        } else if (event instanceof SetInviteOnlyEvent) {
            onSetInviteOnly((SetInviteOnlyEvent) event);
        } else if (event instanceof SetModeratedEvent) {
            onSetModerated((SetModeratedEvent) event);
        } else if (event instanceof SetNoExternalMessagesEvent) {
            onSetNoExternalMessages((SetNoExternalMessagesEvent) event);
        } else if (event instanceof SetPrivateEvent) {
            onSetPrivate((SetPrivateEvent) event);
        } else if (event instanceof SetSecretEvent) {
            onSetSecret((SetSecretEvent) event);
        } else if (event instanceof SetTopicProtectionEvent) {
            onSetTopicProtection((SetTopicProtectionEvent) event);
        } else if (event instanceof SocketConnectEvent) {
            onSocketConnect((SocketConnectEvent) event);
        } else if (event instanceof SuperOpEvent) {
            onSuperOp((SuperOpEvent) event);
        } else if (event instanceof TimeEvent) {
            onTime((TimeEvent) event);
        } else if (event instanceof TopicEvent) {
            onTopic((TopicEvent) event);
        } else if (event instanceof UnknownEvent) {
            onUnknown((UnknownEvent) event);
        } else if (event instanceof UserListEvent) {
            onUserList((UserListEvent) event);
        } else if (event instanceof UserModeEvent) {
            onUserMode((UserModeEvent) event);
        } else if (event instanceof VersionEvent) {
            onVersion((VersionEvent) event);
        } else if (event instanceof VoiceEvent) {
            onVoice((VoiceEvent) event);
        } else if (event instanceof WhoisEvent) {
            onWhois((WhoisEvent) event);
        }
        if (event instanceof GenericCTCPEvent) {
            onGenericCTCP((GenericCTCPEvent) event);
        }
        if (event instanceof GenericUserModeEvent) {
            onGenericUserMode((GenericUserModeEvent) event);
        }
        if (event instanceof GenericChannelModeEvent) {
            onGenericChannelMode((GenericChannelModeEvent) event);
        }
        if (event instanceof GenericDCCEvent) {
            onGenericDCC((GenericDCCEvent) event);
        }
        if (event instanceof GenericMessageEvent) {
            onGenericMessage((GenericMessageEvent) event);
        }
        if (event instanceof GenericUserEvent) {
            onGenericUser((GenericUserEvent) event);
        }
        if (event instanceof GenericChannelEvent) {
            onGenericChannel((GenericChannelEvent) event);
        }
        if (event instanceof GenericChannelUserEvent) {
            onGenericChannelUser((GenericChannelUserEvent) event);
        }
    }

    public void onAction(ActionEvent event) throws Exception {
    }

    public void onChannelInfo(ChannelInfoEvent event) throws Exception {
    }

    public void onConnect(ConnectEvent event) throws Exception {
    }

    public void onDisconnect(DisconnectEvent event) throws Exception {
    }

    public void onFinger(FingerEvent event) throws Exception {
    }

    public void onHalfOp(HalfOpEvent event) throws Exception {
    }

    public void onIncomingChatRequest(IncomingChatRequestEvent event) throws Exception {
    }

    public void onIncomingFileTransfer(IncomingFileTransferEvent event) throws Exception {
    }

    public void onInvite(InviteEvent event) throws Exception {
    }

    public void onJoin(JoinEvent event) {
    }

    public void onKick(KickEvent event) throws Exception {
    }

    public void onMessage(MessageEvent event) {
    }

    public void onMode(ModeEvent event) throws Exception {
    }

    public void onMotd(MotdEvent event) throws Exception {
    }

    public void onNickAlreadyInUse(NickAlreadyInUseEvent event) throws Exception {
    }

    public void onNickChange(NickChangeEvent event) throws Exception {
    }

    public void onNotice(NoticeEvent event) throws Exception {
    }

    public void onOp(OpEvent event) throws Exception {
    }

    public void onOwner(OwnerEvent event) throws Exception {
    }

    public void onPart(PartEvent event) {
    }

    public void onPing(PingEvent event) throws Exception {
    }

    public void onPrivateMessage(PrivateMessageEvent event) {
    }

    public void onQuit(QuitEvent event) throws Exception {
    }

    public void onRemoveChannelBan(RemoveChannelBanEvent event) throws Exception {
    }

    public void onRemoveChannelKey(RemoveChannelKeyEvent event) throws Exception {
    }

    public void onRemoveChannelLimit(RemoveChannelLimitEvent event) throws Exception {
    }

    public void onRemoveInviteOnly(RemoveInviteOnlyEvent event) throws Exception {
    }

    public void onRemoveModerated(RemoveModeratedEvent event) throws Exception {
    }

    public void onRemoveNoExternalMessages(RemoveNoExternalMessagesEvent event) throws Exception {
    }

    public void onRemovePrivate(RemovePrivateEvent event) throws Exception {
    }

    public void onRemoveSecret(RemoveSecretEvent event) throws Exception {
    }

    public void onRemoveTopicProtection(RemoveTopicProtectionEvent event) throws Exception {
    }

    public void onServerPing(ServerPingEvent event) {
    }

    public void onServerResponse(ServerResponseEvent event) throws Exception {
    }

    public void onSetChannelBan(SetChannelBanEvent event) throws Exception {
    }

    public void onSetChannelKey(SetChannelKeyEvent event) throws Exception {
    }

    public void onSetChannelLimit(SetChannelLimitEvent event) throws Exception {
    }

    public void onSetInviteOnly(SetInviteOnlyEvent event) throws Exception {
    }

    public void onSetModerated(SetModeratedEvent event) throws Exception {
    }

    public void onSetNoExternalMessages(SetNoExternalMessagesEvent event) throws Exception {
    }

    public void onSetPrivate(SetPrivateEvent event) throws Exception {
    }

    public void onSetSecret(SetSecretEvent event) throws Exception {
    }

    public void onSetTopicProtection(SetTopicProtectionEvent event) throws Exception {
    }

    public void onSocketConnect(SocketConnectEvent event) throws Exception {
    }

    public void onSuperOp(SuperOpEvent event) throws Exception {
    }

    public void onTime(TimeEvent event) throws Exception {
    }

    public void onTopic(TopicEvent event) throws Exception {
    }

    public void onUnknown(UnknownEvent event) throws Exception {
    }

    public void onUserList(UserListEvent event) throws Exception {
    }

    public void onUserMode(UserModeEvent event) throws Exception {
    }

    public void onVersion(VersionEvent event) throws Exception {
    }

    public void onVoice(VoiceEvent event) throws Exception {
    }

    public void onWhois(WhoisEvent event) throws Exception {
    }

    public void onGenericCTCP(GenericCTCPEvent event) {
    }

    public void onGenericUserMode(GenericUserModeEvent event) throws Exception {
    }

    public void onGenericChannelMode(GenericChannelModeEvent event) throws Exception {
    }

    public void onGenericDCC(GenericDCCEvent event) throws Exception {
    }

    public void onGenericMessage(GenericMessageEvent event) throws Exception {
    }

    public void onGenericChannel(GenericChannelEvent event) throws Exception {
    }

    public void onGenericUser(GenericUserEvent event) throws Exception {
    }

    public void onGenericChannelUser(GenericChannelUserEvent event) throws Exception {
    }
}
