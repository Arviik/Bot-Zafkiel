package org.arvik.zafkielbot.utils.commands;

import org.arvik.zafkielbot.commands.event.connectionMusic.ConnectionMusic;
import org.javacord.api.event.channel.server.voice.ServerVoiceChannelMemberJoinEvent;

public class VoiceChannelManager {
    private static final CommandRegistry registry = new CommandRegistry();

    static {
        registry.addCommand(new Command("connectionMusic","Plays music when someone join the VC",new ConnectionMusic(),"connectionMusic","cm"));
    }

    public static void create(ServerVoiceChannelMemberJoinEvent event) {
        registry.getByAlias("connectionMusic").ifPresent((cmd) -> cmd.getVcExecutor().run(event, cmd));
    }
}