package ca.tristan.leafstackbot.commands;

import ca.tristan.jdacommands.*;
import net.dv8tion.jda.api.*;

import java.util.*;

public class CmdHelp implements ICommand {

    private List<ICommand> commands;
    private String prefix;

    public CmdHelp(JDACommands jdaCommands) {
        this.commands = jdaCommands.getCommands();
        this.prefix = jdaCommands.getPrefix();
    }

    @Override
    public void execute(ExecuteArgs event) {

        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Help");
        builder.setThumbnail(event.getGuild().getIconUrl());
        builder.setDescription("Here's a list of every commands on the server.");
        commands.forEach(iCommand -> {
            if(iCommand.helpMessage() != null && !iCommand.helpMessage().isEmpty()){
                builder.addField(this.prefix + iCommand.getName(), iCommand.helpMessage(), false);
            }
        });
        event.getTextChannel().sendMessageEmbeds(builder.build()).queue();
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String helpMessage() {
        return "Displays this message.";
    }

    @Override
    public boolean needOwner() {
        return false;
    }

}
