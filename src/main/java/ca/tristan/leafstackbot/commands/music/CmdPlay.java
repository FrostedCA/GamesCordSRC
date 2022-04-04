package ca.tristan.leafstackbot.commands.music;

import ca.tristan.jdacommands.*;
import ca.tristan.leafstackbot.lavaplayer.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.managers.*;

import java.net.*;

public class CmdPlay implements ICommand {

    @Override
    public void execute(ExecuteArgs event) {

        if(!event.getMemberVoiceState().inAudioChannel()){
            event.getTextChannel().sendMessage("You need to be in a voice channel for this command to work.").queue();
            return;
        }

        if(!event.getSelfVoiceState().inAudioChannel()){
            final AudioManager audioManager = event.getGuild().getAudioManager();
            final VoiceChannel memberChannel = (VoiceChannel) event.getMemberVoiceState().getChannel();

            audioManager.openAudioConnection(memberChannel);
        }

        String link = event.getArgs()[1];

        if(!isUrl(link)){
            link = "ytsearch:" + String.join(" ", event.getArgs()) + " audio";
        }

        PlayerManager.getInstance().loadAndPlay(event.getTextChannel(), link);

    }

    private boolean isUrl(String url){
        try{
            new URI(url);
            return true;
        } catch (URISyntaxException e){
            return false;
        }
    }

    @Override
    public String getName() {
        return "play";
    }

    @Override
    public String helpMessage() {
        return "Plays the specified track or adds it to the queue if music is already playing.";
    }

    @Override
    public boolean needOwner() {
        return false;
    }
}
