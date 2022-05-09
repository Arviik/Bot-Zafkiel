package org.arvik.zafkielbot;

import org.arvik.zafkielbot.utils.ConfigManager;
import org.arvik.zafkielbot.utils.commands.MessageManager;
import org.arvik.zafkielbot.utils.commands.VoiceChannelManager;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

import java.io.File;

public class Main {
    private static DiscordApi api;
    private static ConfigManager configManager;

    public static void main(String[] args) {
        configManager = new ConfigManager(new File(System.getProperty("user.dir"), "config.toml"));
        api = new DiscordApiBuilder().setToken(configManager.getToml().getString("bot.token")).login().join();
        api.addMessageCreateListener(MessageManager::create);
        api.addServerVoiceChannelMemberJoinListener(VoiceChannelManager::create);
    }

    public static ConfigManager getConfigManager() {
        return configManager;
    }

    public static DiscordApi getApi() {
        return api;
    }
}