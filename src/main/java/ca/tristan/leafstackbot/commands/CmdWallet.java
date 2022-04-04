package ca.tristan.leafstackbot.commands;

import ca.tristan.jdacommands.*;
import ca.tristan.leafstackbot.*;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.*;

import java.awt.*;

public class CmdWallet implements ICommand {
    @Override
    public String getName() {
        return "wallet";
    }

    @Override
    public boolean needOwner() {
        return false;
    }

    @Override
    public void execute(ExecuteArgs event) {
        if(!event.getTextChannel().getId().equals("956805024401461298")){
            Bot.sendPrivateMessageTo(event.getMember().getUser(), "Please use this command in the #commands channel. Thanks :)");
            return;
        }

        String[] args = event.getArgs();

        if(args.length == 1){
            EmbedBuilder builder = new EmbedBuilder();
            builder.setColor(new Color(43, 161, 76));
            builder.setDescription("**" + event.getMember().getAsMention() + " wallet**");
            builder.addField("Coins", Bot.mysql.getCoinsOfMember(event.getMember()) + "", true);
            builder.addField("Level", Bot.mysql.getLevelOfMember(event.getMember()) + "", true);
            builder.setImage(event.getMember().getEffectiveAvatarUrl());
            event.getTextChannel().sendMessageEmbeds(builder.build()).queue();
        }else if(args.length == 2){
            if(event.getMessage().getMentionedMembers().size() == 1){
                Member member = event.getMessage().getMentionedMembers().get(0);
                EmbedBuilder builder = new EmbedBuilder();
                builder.setColor(new Color(43, 161, 76));
                builder.setDescription("**" + member.getEffectiveName() + " wallet**");
                builder.addField("Coins", Bot.mysql.getCoinsOfMember(member) + "", true);
                builder.addField("Level", Bot.mysql.getLevelOfMember(member) + "", true);
                builder.setImage(member.getEffectiveAvatarUrl());
                event.getTextChannel().sendMessageEmbeds(builder.build()).queue();
            }else{
                event.getTextChannel().sendMessage("I can't find members by their names. Please try using `!wallet <@member>`").queue();
            }
        }else{
            event.getTextChannel().sendMessage("I don't know what you were trying to execute here... Try `!help`").queue();
        }
    }

    @Override
    public String helpMessage() {
        return "Shows your current wallet balance and level.";
    }

}
