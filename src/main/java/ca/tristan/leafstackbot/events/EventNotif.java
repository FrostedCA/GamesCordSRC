package ca.tristan.leafstackbot.events;

import ca.tristan.leafstackbot.*;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.events.interaction.command.*;
import net.dv8tion.jda.api.events.interaction.component.*;
import net.dv8tion.jda.api.hooks.*;
import net.dv8tion.jda.api.interactions.components.buttons.*;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.jetbrains.annotations.*;

import java.awt.*;
import java.util.*;

public class EventNotif extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if(event.getName().equals("notifs") && event.getMember().isOwner()){

            EmbedBuilder builder = new EmbedBuilder();
            builder.setColor(new Color(185, 60, 60));
            builder.setThumbnail(event.getGuild().getIconUrl());
            builder.setTitle("Enable custom notifications?");
            builder.setDescription("By turning on the notifications, you will now receive notifications from my YouTube videos and Twitch streams on Discord.");

            event.replyEmbeds(builder.build()).addActionRow(Button.success("addNotif", "Turn on"), Button.danger("removeNotif", "Turn off")).queue();
        }
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        if(event.getComponentId().equals("addNotif")){
            event.getGuild().addRoleToMember(Objects.requireNonNull(event.getMember()), Objects.requireNonNull(event.getGuild().getRoleById("955505502501077074"))).queue();
            event.getInteraction().reply("You have been granted the role " + event.getGuild().getRoleById("955505502501077074").getAsMention() + ".").setEphemeral(true).queue();
        }
        if(event.getComponentId().equals("removeNotif")){
            if(event.getMember().getRoles().contains(event.getGuild().getRoleById("955505502501077074"))){
                event.getGuild().removeRoleFromMember(Objects.requireNonNull(event.getMember()), Objects.requireNonNull(event.getGuild().getRoleById("955505502501077074"))).queue();
                event.getInteraction().reply("You have been removed the role " + event.getGuild().getRoleById("955505502501077074").getAsMention() + ".").setEphemeral(true).queue();
            }else{
                event.getInteraction().reply("I couldn't removed you the role " + event.getGuild().getRoleById("955505502501077074").getAsMention() + " since you didn't have it.").setEphemeral(true).queue();
            }
        }
    }
}
