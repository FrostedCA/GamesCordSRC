package ca.tristan.leafstackbot.commands.music;

import ca.tristan.jdacommands.*;
import ca.tristan.leafstackbot.lavaplayer.*;
import com.sedmelluq.discord.lavaplayer.player.*;

public class CmdNowPlaying implements ICommand {
    @Override
    public void execute(ExecuteArgs event) {
        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(event.getGuild());
        final AudioPlayer audioPlayer = musicManager.audioPlayer;

        if(audioPlayer.getPlayingTrack() == null){
            event.getTextChannel().sendMessage("There is no track playing currently.").queue();
            return;
        }

        String title = audioPlayer.getPlayingTrack().getInfo().title;
        String author = audioPlayer.getPlayingTrack().getInfo().author;

        event.getTextChannel().sendMessage("Now playing: **`" + title + "`** by **`" + author + "`**.").queue();
    }

    @Override
    public String getName() {
        return "nowplaying";
    }

    @Override
    public String helpMessage() {
        return "Shows the current playing track.";
    }

    @Override
    public boolean needOwner() {
        return false;
    }
}
