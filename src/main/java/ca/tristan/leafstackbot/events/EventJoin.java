package ca.tristan.leafstackbot.events;

import ca.tristan.leafstackbot.*;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.guild.member.*;
import net.dv8tion.jda.api.events.message.*;
import net.dv8tion.jda.api.hooks.*;
import org.jetbrains.annotations.*;

import java.awt.*;
import java.sql.*;
import java.util.*;

public class EventJoin extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        Member member = event.getMember();

        event.getGuild().addRoleToMember(event.getMember(), Objects.requireNonNull(event.getGuild().getRoleById("951359444309119036"))).queue();

        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(new Color(24, 169, 55));
        builder.setTitle("Welcome to GamesCord");
        builder.setDescription("Members: " + event.getGuild().getMemberCount());
        builder.setThumbnail(member.getEffectiveAvatarUrl());
        builder.setFooter(Calendar.getInstance().getTime().toString(), event.getGuild().getIconUrl());
        builder.addField("Member ->", member.getAsMention(), true);
        Objects.requireNonNull(event.getGuild().getTextChannelById("954749528572059718")).sendMessageEmbeds(builder.build()).queue();

        try {
            PreparedStatement statement = Bot.mysql.getConn().prepareStatement("INSERT INTO discord.members (id, username, coins) VALUES (?, ?, ?)");
            statement.setString(1, event.getUser().getId());
            statement.setString(2, event.getUser().getName());
            statement.setInt(3, 0);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        event.getGuild().getVoiceChannelById("954752522348789821").getManager().setName("Members: " + event.getGuild().getMemberCount()).queue();

    }

    @Override
    public void onGuildMemberRemove(@NotNull GuildMemberRemoveEvent event) {
        User member = event.getUser();

        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(new Color(187, 29, 29));
        builder.setTitle("Goodbye's from GamesCord");
        builder.setDescription("Members: " + event.getGuild().getMemberCount());
        builder.setThumbnail(member.getEffectiveAvatarUrl());
        builder.setFooter(Calendar.getInstance().getTime().toString(), event.getGuild().getIconUrl());
        builder.addField("Member ->", member.getAsMention(), true);
        Objects.requireNonNull(event.getGuild().getTextChannelById("954749528572059718")).sendMessageEmbeds(builder.build()).queue();

        try {
            PreparedStatement statement = Bot.mysql.getConn().prepareStatement("DELETE FROM discord.members where (id, username, coins) = (?, ?, ?)");
            statement.setString(1, member.getId());
            statement.setString(2, member.getName());
            statement.setInt(3, 0);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        event.getGuild().getVoiceChannelById("954752522348789821").getManager().setName("Members: " + event.getGuild().getMemberCount()).queue();

    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if(!event.isFromGuild()){
            return;
        }
        if(!event.getMember().isOwner()){
            return;
        }
        if(event.getMessage().getContentRaw().startsWith("!rules")){
            event.getMessage().delete().queue();
            EmbedBuilder builder = new EmbedBuilder();
            builder.setColor(new Color(194, 31, 31));
            builder.setTitle("Server Laws");
            builder.addField("Discord TOS & Guidelines", "GamesCord follows **Discord's TOS and Guidelines**. If you are caught doing anything against Discord's TOS and Guidelines, your account will be reported to Discord.", false);
            builder.addField("NSFW", "Any **NSFW** content posted in GamesCord will result in a **INSTANT BAN**. Usernames and profile pictures must stay safe for all ages. Any songs played in voice channels and messages sent in text channels that are **sexually suggestive, and have illegal contect/subjects**, follow the same punishment.", false);
            builder.addField("Harassment", "**Harassing** in the server, or in **DM's with the servers in mutual server's** will get you kicked from the server. Depending on how bad it is, you may get **banned**.", false);
            builder.addField("Hate Speech & Racism", "GamesCord does not take lightly to **Hate Speech & Racism**. Any of that will result in a **INSTANT BAN**. This goes for DM's as will, with GamesCord as a mutual server.", false);
            builder.addField("Slurs", "Any slurs under any circumstance will result in an instant ban. This includes racial slurs, or other extremely offensive terms. General swearing is fine.", false);
            builder.addField("Political & Religious Topics", "Keep Religious and Political out of the server. Talking about these in DM's is fine. If anything happens in DM's that go against Discord's TOS, will be dealt with through reporting the situation.", false);
            builder.addField("Do not spam", "If you Spam, you may be kicked depending on how much. Mass Mentioning/Mention Spamming will get you Banned from the server.", false);
            builder.addField("Private Info", "Phone numbers, address, etc. If you give out the private info of another person you will be instantly banned. If a person wants to list their age or name that's fine, but other info must be kept private.", false);
            builder.addField("Begging", "It's annoying for the staff when all they deal with is 'giVe mE a roLE' or 'kAn I hAvE MoD'.", false);
            builder.addField("English and French only", "Keep chat readable for everyone. This makes things easier on the mods, and generally easier for everyone in the server.", false);
            builder.addField("Acting dumb", "Don't be an Idiot. Acting like an idiot will get you banned, this goes along side with acting dumb.", false);
            event.getChannel().sendMessageEmbeds(builder.build()).queue();
        }
    }
}
