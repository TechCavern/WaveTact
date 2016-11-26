package com.techcavern.wavetact.ircCommands.media;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchResult;
import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.DatabaseUtils;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.util.List;

import static com.techcavern.wavetactdb.Tables.CONFIG;

@IRCCMD
public class Video extends IRCCommand {

    public Video() {
        super(GeneralUtils.toArray("video vid v youtube yt"), 1, "video (result #) [query for]", "Searches youtube for videos", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        String googleapikey;
        if (DatabaseUtils.getConfig("googleapikey") != null)
            googleapikey = DatabaseUtils.getConfig("googleapikey").getValue(CONFIG.VALUE);
        else {
            IRCUtils.sendError(user, network, channel, "Google api key is null - contact bot controller to fix", prefix);
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
        search.setKey(googleapikey);
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
                if (!url.isEmpty())
                    IRCUtils.sendMessage(user, network, channel, title + " - " + content + " - " + GeneralUtils.shortenURL(url), prefix);
                else
                    IRCUtils.sendMessage(user, network, channel, title + " - " + content, prefix);
            } else {
                IRCUtils.sendError(user, network, channel, "Search #" + ArrayIndex + " does not exist", prefix);
            }
        } else {
            IRCUtils.sendError(user, network, channel, "Search returned no results", prefix);
        }
    }
}

