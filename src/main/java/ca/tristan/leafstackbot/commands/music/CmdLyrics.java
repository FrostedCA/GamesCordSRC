package ca.tristan.leafstackbot.commands.music;

import ca.tristan.jdacommands.*;
import ca.tristan.leafstackbot.lavaplayer.*;
import com.sedmelluq.discord.lavaplayer.player.*;
import core.*;

import java.io.*;
import java.util.*;

public class CmdLyrics implements ICommand {
    @Override
    public void execute(ExecuteArgs event) {
        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(event.getGuild());
        final AudioPlayer audioPlayer = musicManager.audioPlayer;

        if(audioPlayer.getPlayingTrack() == null){
            event.getTextChannel().sendMessage("There is no track playing currently.").queue();
            return;
        }

        String title = audioPlayer.getPlayingTrack().getInfo().title;

        GLA gla = new GLA();
        try {
            String url = gla.search(title.toLowerCase().replaceAll("official", "").replaceAll("music", "").replaceAll("video", "")).getHits().isEmpty() ? gla.search(title).getHits().getFirst().getUrl() : "";
            if(url.isEmpty()){
                event.getTextChannel().sendMessage("Sorry, I didn't found anything.").queue();
                return;
            }
            event.getTextChannel().sendMessage("Here's what I've found: " + url).queue();
        } catch (IOException e) {
            event.getTextChannel().sendMessage("Sorry, I didn't found anything.").queue();
        }
    }

    @Override
    public String getName() {
        return "lyrics";
    }

    @Override
    public String helpMessage() {
        return "Searches the lyrics on Genius.";
    }

    @Override
    public boolean needOwner() {
        return false;
    }
}
