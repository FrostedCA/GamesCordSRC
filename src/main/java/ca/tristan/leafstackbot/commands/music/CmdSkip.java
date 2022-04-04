package ca.tristan.leafstackbot.commands.music;

import ca.tristan.jdacommands.*;
import ca.tristan.leafstackbot.lavaplayer.*;
import com.sedmelluq.discord.lavaplayer.player.*;
import net.dv8tion.jda.api.entities.*;

public class CmdSkip implements ICommand {

    @Override
    public void execute(ExecuteArgs event) {
        final TextChannel channel = event.getTextChannel();
        final Member self = event.getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();

        if(!selfVoiceState.inAudioChannel()){
            channel.sendMessage("I need to be in a voice channel for this to work.").queue();
            return;
        }

        final Member member = event.getMember();
        final GuildVoiceState memberVoiceState = event.getMemberVoiceState();

        if(!memberVoiceState.inAudioChannel()){
            channel.sendMessage("You need to be in a voice channel for this command to work.").queue();
            return;
        }

        if(!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())){
            channel.sendMessage("You need to be in the same voice channel as me for this to work.").queue();
            return;
        }

        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(event.getGuild());
        final AudioPlayer audioPlayer = musicManager.audioPlayer;

        if(audioPlayer.getPlayingTrack() == null){
            channel.sendMessage("There is no track playing currently.").queue();
            return;
        }

        musicManager.scheduler.nextTrack();
        channel.sendMessage("Skipped the current track. Now playing **`" + musicManager.audioPlayer.getPlayingTrack().getInfo().title + "`** by **`" + musicManager.audioPlayer.getPlayingTrack().getInfo().author + "`**.").queue();
    }

    @Override
    public String getName() {
        return "skip";
    }

    @Override
    public String helpMessage() {
        return "Skips the current track to the next one.";
    }

    @Override
    public boolean needOwner() {
        return false;
    }

}
