package ca.tristan.leafstackbot.commands;

import ca.tristan.jdacommands.*;

public class CmdUpdateCount implements ICommand {

    @Override
    public void execute(ExecuteArgs event) {
        event.getGuild().getVoiceChannelById("954752522348789821").getManager().setName("Members: " + event.getGuild().getMemberCount()).queue();
    }

    @Override
    public String getName() {
        return "updcount";
    }

    @Override
    public String helpMessage() {
        return null;
    }

    @Override
    public boolean needOwner() {
        return true;
    }
}
