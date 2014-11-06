package com.techcavern.wavetact.commands.utils;

import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.gson.JsonArray;
import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.GenCMD;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.io.IOException;
import java.util.List;

@CMD
@GenCMD
public class YoutubeCMD extends GenericCommand {

    public YoutubeCMD() {
        super(GeneralUtils.toArray("youtube yt"), 0, "youtube (result #) [string to search youtube]", "searches youtube for videos");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        if (GeneralRegistry.googleapikey == null) {
            IRCUtils.sendError(user, "Google API key is null - Contact Bot Controller to fix");
            return;
        }
        int ArrayIndex = 1;
        if (GeneralUtils.isInteger(args[0])) {
            ArrayIndex = Integer.parseInt(args[0]);
            args = ArrayUtils.remove(args, 0);
        }
        YouTube yt = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
            public void initialize(HttpRequest request) throws IOException {
            }

        }).setApplicationName("youtubesearch").build();
        YouTube.Search.List search = yt.search().list("id,snippet");
        search.setKey(GeneralRegistry.googleapikey);
        search.setQ(StringUtils.join(args, " "));
        search.setMaxResults(Integer.toUnsignedLong(ArrayIndex));
        List<SearchResult> results = search.execute().getItems();
        if (results.size() > 0) {
            if (results.size() >= ArrayIndex) {
                SearchResult result = results.get(ArrayIndex-1);
                String url = "http://youtube.com/watch?v=" + result.getId().getVideoId();
                String title = result.getSnippet().getTitle();
                String content = result.getSnippet().getDescription();
                IRCUtils.sendMessage(user, channel, title + " - " + content, isPrivate);
                IRCUtils.sendMessage(user, channel, url, isPrivate);
            } else {
                IRCUtils.sendError(user, "Search #" + ArrayIndex + " does not exist");
            }
        } else {
            IRCUtils.sendError(user, "Search returned no results");
        }
    }
}

