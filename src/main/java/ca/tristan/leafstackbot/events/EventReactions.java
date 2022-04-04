package ca.tristan.leafstackbot.events;

import net.dv8tion.jda.api.events.message.react.*;
import net.dv8tion.jda.api.hooks.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class EventReactions extends ListenerAdapter {

    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {
        if(event.getChannel().getId().equals("955833528510197800")){
            event.getGuild().addRoleToMember(Objects.requireNonNull(event.getMember()), Objects.requireNonNull(event.getGuild().getRoleById("955505502501077074"))).queue();
        }
    }

    @Override
    public void onMessageReactionRemove(@NotNull MessageReactionRemoveEvent event) {
        if(event.getChannel().getId().equals("955833528510197800") && Objects.requireNonNull(event.getMember()).getRoles().contains(event.getGuild().getRoleById("955505502501077074"))){
            event.getGuild().removeRoleFromMember(Objects.requireNonNull(event.getMember()), Objects.requireNonNull(event.getGuild().getRoleById("955505502501077074"))).queue();
        }
    }
}
