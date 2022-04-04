package ca.tristan.leafstackbot.events;

import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.events.message.*;
import net.dv8tion.jda.api.hooks.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class EventLogs extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if(event.getAuthor().isBot()){
            return;
        }

        if(event.isFromGuild()){
            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle("Message received log");
            builder.addField("Content", event.getMessage().getContentRaw(), false);
            builder.addField("Member", event.getMember().getAsMention(), false);
            builder.setFooter(Calendar.getInstance().getTime().toString());
            event.getGuild().getTextChannelById("957288354351841290").sendMessageEmbeds(builder.build()).queue();
        }else{
            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle("Message received log (Private message)");
            builder.addField("Content", event.getMessage().getContentRaw(), false);
            builder.addField("Member", event.getAuthor().getName(), false);
            builder.setFooter(Calendar.getInstance().getTime().toString());
            event.getJDA().getGuildById("951333457672437780").getTextChannelById("957288354351841290").sendMessageEmbeds(builder.build()).queue();
        }
    }

}
