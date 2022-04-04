package ca.tristan.leafstackbot.commands.music;

import ca.tristan.jdacommands.*;
import ca.tristan.leafstackbot.lavaplayer.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.managers.*;

public class CmdStop implements ICommand {

    @Override
    public void execute(ExecuteArgs event) {

        if(!event.getMemberVoiceState().inAudioChannel()){
            event.getTextChannel().sendMessage("You need to be in a voice channel for this command to work.").queue();
            return;
        }

        if(!event.getSelfVoiceState().inAudioChannel()){
            event.getTextChannel().sendMessage("I need to be in a voice channel or I need to be playing music for this command to work.").queue();
            return;
        }

        if(event.getMemberVoiceState().getChannel().equals(event.getSelfVoiceState().getChannel())){
            PlayerManager.getInstance().getMusicManager(event.getGuild()).scheduler.player.stopTrack();
            PlayerManager.getInstance().getMusicManager(event.getGuild()).scheduler.queue.clear();
            event.getGuild().getAudioManager().closeAudioConnection();
            event.getTextChannel().sendMessage("The player has been stopped and the queue has been cleared.").queue();
        }

    }

    @Override
    public String getName() {
        return "stop";
    }

    @Override
    public String helpMessage() {
        return "Stops the music player and clears the queue.";
    }

    @Override
    public boolean needOwner() {
        return false;
    }
}
