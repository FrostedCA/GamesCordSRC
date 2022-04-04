package ca.tristan.leafstackbot.commands;

import ca.tristan.jdacommands.*;
import ca.tristan.leafstackbot.*;
import net.dv8tion.jda.api.entities.*;

public class CmdRemoveCoins implements ICommand {

    @Override
    public String getName() {
        return "removecoins";
    }

    @Override
    public boolean needOwner() {
        return true;
    }

    @Override
    public void execute(ExecuteArgs event) {
        String[] args = event.getMessage().getContentRaw().split(" ");
        if(args.length == 3){
            if(event.getMessage().getMentionedMembers().size() == 1){
                Member member = event.getMessage().getMentionedMembers().get(0);
                if(Bot.isNumber(args[2])){
                    Bot.mysql.removeCoinsFromMember(member, Integer.parseInt(args[2]));
                    event.getTextChannel().sendMessage("Removed " + args[2] + " coins from member " + event.getMember().getAsMention()).queue();
                }else{
                    event.getTextChannel().sendMessage("This doesn't looks like a valid number.").queue();
                }
            }else{
                event.getTextChannel().sendMessage("You need to specify a member or you specify multiple members and I can only process one at a time.").queue();
            }
        }
    }

    @Override
    public String helpMessage() {
        return null;
    }
}
