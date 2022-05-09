package org.arvik.zafkielbot.commands;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import org.arvik.zafkielbot.Main;
import org.arvik.zafkielbot.utils.commands.Command;
import org.arvik.zafkielbot.utils.commands.CommandExecutor;
import org.arvik.zafkielbot.utils.commands.LavaplayerAudioSource;
import org.javacord.api.audio.AudioSource;
import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.event.message.MessageCreateEvent;

public class CommandMusic implements CommandExecutor {
    @Override
    public void run(MessageCreateEvent event, Command command, String[] args) {
        if (event.getMessageAuthor().getConnectedVoiceChannel().isPresent()){
            ServerVoiceChannel channel = event.getMessageAuthor().getConnectedVoiceChannel().get();
            channel.connect().thenAccept(audioConnection -> {
                StringBuilder msg = new StringBuilder(event.getMessageContent());
                msg.delete(0,msg.indexOf(" ")+1);
                AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
                playerManager.registerSourceManager(new YoutubeAudioSourceManager());
                AudioPlayer player = playerManager.createPlayer();
                AudioSource source = new LavaplayerAudioSource(Main.getApi(), player);
                audioConnection.setAudioSource(source);
                playerManager.loadItem(String.valueOf(msg), new AudioLoadResultHandler() {
                    @Override
                    public void trackLoaded(AudioTrack track) {
                        player.playTrack(track);
                    }
                    @Override
                    public void playlistLoaded(AudioPlaylist playlist) {
                        for (AudioTrack track : playlist.getTracks()) {
                            player.playTrack(track);
                        }
                    }
                    @Override
                    public void noMatches() {
                        // Notify the user that we've got nothing
                    }
                    @Override
                    public void loadFailed(FriendlyException throwable) {
                        // Notify the user that everything exploded
                    }
                });
            }).exceptionally(e -> {
                // Failed to connect to voice channel (no permissions?)
                e.printStackTrace();
                return null;
            });
        } else {
            event.getChannel().sendMessage("You aren't connected to any voice channel.");
        }
    }
}