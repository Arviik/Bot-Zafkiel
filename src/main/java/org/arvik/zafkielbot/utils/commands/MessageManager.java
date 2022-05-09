package org.arvik.zafkielbot.utils.commands;

import org.arvik.zafkielbot.Main;
import org.arvik.zafkielbot.commands.CommandMusic;
import org.arvik.zafkielbot.commands.CommandPing;
import org.arvik.zafkielbot.commands.connectionMusic.AddUserMusic;
import org.javacord.api.event.message.MessageCreateEvent;
import java.util.Arrays;

public class MessageManager {
    private static final CommandRegistry registry = new CommandRegistry();
    private static final String PREFIX = Main.getConfigManager().getToml().getString("bot.prefix");

    static {
        registry.addCommand(new Command("ping","Pings the bot",new CommandPing(),"ping","p"));
        registry.addCommand(new Command("music","Plays music",new CommandMusic(),"music","m"));
        registry.addCommand(new Command("addUserMusic","Add an user music",new AddUserMusic(),"addUserMusic","aum"));
    }
    public static void create(MessageCreateEvent event) {
        if (event.getMessageContent().startsWith(PREFIX)) {
            String[] args = event.getMessageContent().split(" ");
            String commandName = args[0].substring(PREFIX.length());
            args = args.length == 1 ? new String[0] : Arrays.copyOfRange(args, 1, args.length);
            String[] finalArgs = args;
            registry.getByAlias(commandName).ifPresent((cmd) -> cmd.getExecutor().run(event, cmd, finalArgs));
        }
    }
}