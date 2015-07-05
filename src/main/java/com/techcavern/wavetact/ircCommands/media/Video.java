package com.techcavern.wavetact.ircCommands.media;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchResult;
import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.ErrorUtils;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.Registry;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.util.List;

@IRCCMD
public class Video extends IRCCommand {

    public Video() {
        super(GeneralUtils.toArray("video vid youtube yt"), 0, "video (result #) [string to search for]", "Searches youtube for videos", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        if (Registry.googleapikey == null) {
            ErrorUtils.sendError(user, "Google api key is null - contact bot controller to fix");
            return;
        }
        int ArrayIndex = 1;
        if (GeneralUtils.isInteger(args[0])) {
            ArrayIndex = Integer.parseInt(args[0]);
            args = ArrayUtils.remove(args, 0);
        }
        YouTube yt = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), request -> {
        }).setApplicationName("youtubesearch").build();
        YouTube.Search.List search = yt.search().list("id,snippet");
        search.setKey(Registry.googleapikey);
        search.setQ(StringUtils.join(args, " "));
        search.setMaxResults(Integer.toUnsignedLong(ArrayIndex));
        List<SearchResult> results = search.execute().getItems();
        if (results.size() > 0) {
            if (results.size() >= ArrayIndex) {
                SearchResult result = results.get(ArrayIndex - 1);
                String url = "";
                if (result.getId().getKind().equalsIgnoreCase("youtube#video")) {
                    url = "http://youtube.com/watch?v=" + result.getId().getVideoId();
                } else if (result.getId().getKind().equalsIgnoreCase("youtube#channel")) {
                    url = "http://youtube.com/" + result.getSnippet().getChannelTitle();
                }
                String title = result.getSnippet().getTitle();
                String content = result.getSnippet().getDescription();
                IRCUtils.sendMessage(user, network, channel, title + " - " + content, prefix);
                if (!url.isEmpty())
                    IRCUtils.sendMessage(user, network, channel, url, prefix);
            } else {
                ErrorUtils.sendError(user, "Search #" + ArrayIndex + " does not exist");
            }
        } else {
            ErrorUtils.sendError(user, "Search returned no results");
        }
    }
}

