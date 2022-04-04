package ca.tristan.leafstackbot.commands.music;

import ca.tristan.jdacommands.*;
import ca.tristan.leafstackbot.lavaplayer.*;

public class CmdPause implements ICommand {
    @Override
    public void execute(ExecuteArgs event) {
        if(PlayerManager.getInstance().getMusicManager(event.getGuild()).scheduler.player.isPaused()){
            PlayerManager.getInstance().getMusicManager(event.getGuild()).scheduler.player.setPaused(false);
        }else{
            PlayerManager.getInstance().getMusicManager(event.getGuild()).scheduler.player.setPaused(true);
        }
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String helpMessage() {
        return null;
    }

    @Override
    public boolean needOwner() {
        return false;
    }
}
