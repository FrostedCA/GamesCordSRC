package ca.tristan.leafstackbot.commands;

import ca.tristan.jdacommands.*;
import ca.tristan.leafstackbot.*;

public class CmdClear implements ICommand {

    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public boolean needOwner() {
        return true;
    }

    @Override
    public void execute(ExecuteArgs event) {
        String[] args = event.getMessage().getContentRaw().split(" ");

        if(args.length == 2){
            if(Bot.isNumber(args[1])){
                event.getTextChannel().getHistory().retrievePast(Integer.parseInt(args[1]) + 1).queue(messages -> {
                    messages.forEach(message -> {
                        message.delete().queue();
                    });
                });
            }
        }
    }

    @Override
    public String helpMessage() {
        return null;
    }
}
