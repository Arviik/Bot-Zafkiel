package org.arvik.zafkielbot.commands.event.connectionMusic;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import org.arvik.zafkielbot.Main;
import org.arvik.zafkielbot.database.Database;
import org.arvik.zafkielbot.utils.commands.Command;
import org.arvik.zafkielbot.utils.commands.LavaplayerAudioSource;
import org.arvik.zafkielbot.utils.commands.VcCommandExecutor;
import org.javacord.api.audio.AudioSource;
import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.event.channel.server.voice.ServerVoiceChannelMemberJoinEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConnectionMusic implements VcCommandExecutor {
    private final ArrayList<UserMusic> listUserMusic = new ArrayList<>();
    public ConnectionMusic() {
        Database database = new Database();
        PreparedStatement req;
        try {
            req = database.getCnx().prepareStatement("SELECT * FROM usermusic");
            ResultSet res = req.executeQuery();
            while (res.next()){
                listUserMusic.add(new UserMusic(
                        res.getString(1),
                        res.getString(2),
                        Long.parseLong(res.getString(3))
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run(ServerVoiceChannelMemberJoinEvent event, Command command) {
        String eventUser = String.valueOf(event.getUser().getId());
        for (UserMusic userMusic : listUserMusic) {
            if (userMusic.getIdUser().equals(eventUser)) {
                ServerVoiceChannel channel = event.getChannel();
                channel.connect().thenAccept(audioConnection -> {
                    AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
                    playerManager.registerSourceManager(new YoutubeAudioSourceManager());
                    AudioPlayer player = playerManager.createPlayer();
                    AudioSource source = new LavaplayerAudioSource(Main.getApi(), player);
                    audioConnection.setAudioSource(source);
                    playerManager.loadItem(userMusic.getMusic(), new AudioLoadResultHandler() {
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
                    while (true) {
                        if (player.getPlayingTrack() != null) {
                            break;
                        }
                    }
                    while (true) {
                        if (player.getPlayingTrack().getPosition() >= userMusic.getPlaytime()) {
                            break;
                        }
                    }
                    player.stopTrack();
                }).exceptionally(e -> {
                    // Failed to connect to voice channel (no permissions?)
                    e.printStackTrace();
                    return null;
                });
            }
        }
    }
}