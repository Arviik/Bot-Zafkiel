package org.arvik.zafkielbot.utils.commands;

import org.javacord.api.event.channel.server.voice.ServerVoiceChannelMemberJoinEvent;

public interface VcCommandExecutor {
    void run(ServerVoiceChannelMemberJoinEvent event, Command command);
}
