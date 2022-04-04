package ca.tristan.leafstackbot.events;

import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.command.*;
import net.dv8tion.jda.api.events.interaction.component.*;
import net.dv8tion.jda.api.events.message.react.*;
import net.dv8tion.jda.api.hooks.*;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.jetbrains.annotations.*;

import java.awt.*;
import java.util.*;

public class EventTicket extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if(event.getName().equals("createticket") && event.getMember().isOwner()){
            EmbedBuilder builder = new EmbedBuilder();
            builder.setColor(new Color(185, 60, 60));
            builder.setThumbnail(event.getGuild().getIconUrl());
            builder.setTitle("Need to get in touch with us?");
            builder.setDescription("By clicking on the button down bellow you will be creating a new ticket channel. You will therefore be able to talk to our staff privately.");

            event.replyEmbeds(builder.build()).addActionRow(net.dv8tion.jda.api.interactions.components.buttons.Button.success("createTicket", "Create")).queue();
        }
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        if(event.getComponentId().equals("createTicket")){
            Collection<Permission> permissionsAllow = new ArrayList<>();
            Collection<Permission> permissionsDeny = new ArrayList<>();
            permissionsAllow.add(Permission.VIEW_CHANNEL);
            event.getGuild().getCategoryById("955095097266044928").createTextChannel("ticket-" + event.getMember().getId()).addMemberPermissionOverride(Long.parseLong(event.getMember().getId()), permissionsAllow, permissionsDeny).queue(textChannel -> {
                EmbedBuilder builder = new EmbedBuilder();
                builder.setColor(new Color(40, 145, 69));
                builder.addField("Ask your question here, it's staying between you and us.", "To close this ticket you can add a reaction to this embed.", false);
                textChannel.sendMessageEmbeds(builder.build()).queue(message -> {
                    message.addReaction("\uD83D\uDD12").queue();
                    event.getInteraction().reply("Your ticket as been create here -> " + textChannel.getAsMention() + ".").setEphemeral(true).queue();

                    EmbedBuilder builder1 = new EmbedBuilder();
                    builder1.setTitle("Ticket creation log");
                    builder1.addField("By", event.getMember().getUser().getAsMention(), false);
                    builder1.addField("Channel", textChannel.getAsMention(), false);
                    builder1.setFooter(Calendar.getInstance().getTime().toString());
                    event.getGuild().getTextChannelById("957288354351841290").sendMessageEmbeds(builder1.build()).queue();
                });
            });
        }
    }

    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {
        if(event.getChannel().getName().contains(event.getMember().getId())){
            event.getChannel().getHistoryFromBeginning(1).queue(messageHistory -> {
                if(event.getMessageId().equals(messageHistory.getRetrievedHistory().get(0).getId())){
                    event.getChannel().delete().queue();
                }
            });
        }
    }
}
