package org.arvik.zafkielbot.commands;

import org.arvik.zafkielbot.utils.commands.Command;
import org.arvik.zafkielbot.utils.commands.CommandExecutor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import java.awt.*;

public class CommandPing implements CommandExecutor {
    @Override
    public void run(MessageCreateEvent event, Command command, String[] args) {
        EmbedBuilder builder = new EmbedBuilder()
                .setTitle("**Ping :**")
                .setDescription("...")
                .setColor(Color.RED);

        event.getChannel().sendMessage(builder).thenAccept(message -> {
            long unixBot = message.getCreationTimestamp().toEpochMilli();
            long unixUser = event.getMessage().getCreationTimestamp().toEpochMilli();
            long ping = unixBot - unixUser;
            if (ping<100){
                builder.setColor(Color.GREEN);
            } else if (ping<250){
                builder.setColor(Color.ORANGE);
            } else {
                builder.setColor(Color.RED);
            }
            builder.setDescription(ping+"ms");
            message.edit(builder);
        });
    }
}