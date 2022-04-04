package ca.tristan.leafstackbot;

import ca.tristan.jdacommands.*;
import ca.tristan.leafstackbot.commands.*;
import ca.tristan.leafstackbot.commands.music.*;
import ca.tristan.leafstackbot.events.*;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.requests.*;
import net.dv8tion.jda.api.utils.cache.*;

import javax.security.auth.login.*;
import java.util.*;

public class Bot {

    public final static GatewayIntent[] INTENTS = {GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MESSAGE_REACTIONS, GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES};

    public static Database mysql;

    public static JDA jda;

    public static void main(String[] args) throws LoginException, InterruptedException {

        //mysql = new Database();

        JDACommands jdaCommands = new JDACommands("!");
        jdaCommands.registerCommand(new CmdWallet());
        jdaCommands.registerCommand(new CmdAddCoins());
        jdaCommands.registerCommand(new CmdRemoveCoins());
        jdaCommands.registerCommand(new CmdClear());
        jdaCommands.registerCommand(new CmdUpdateCount());

        jdaCommands.registerCommand(new CmdPlay());
        jdaCommands.registerCommand(new CmdStop());
        jdaCommands.registerCommand(new CmdSkip());
        jdaCommands.registerCommand(new CmdNowPlaying());
        jdaCommands.registerCommand(new CmdLyrics());

        jdaCommands.registerCommand(new CmdHelp(jdaCommands));

        jda = JDABuilder.create("-", Arrays.asList(INTENTS))
                .enableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE)
                .setActivity(Activity.streaming("TR!STAN", "https://www.twitch.tv/frostedca"))
                .setStatus(OnlineStatus.ONLINE)
                .addEventListeners(new EventJoin())
                .addEventListeners(new EventReactions())
                .addEventListeners(new EventNotif())
                .addEventListeners(new EventTicket())
                .addEventListeners(new EventLogs())
                .addEventListeners(jdaCommands)
                .build();

        jda.awaitReady();
        jda.getGuildById("951333457672437780").upsertCommand("notifs", "Notification").queue();
        jda.getGuildById("951333457672437780").upsertCommand("createticket", "Creating a ticket.").queue();
    }

    public static void sendPrivateMessageTo(User user, String message){
        user.openPrivateChannel().queue(privateChannel -> {
            privateChannel.sendMessage(message).queue();
        });
    }

    public static boolean isNumber(String str){
        try{
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }

}
