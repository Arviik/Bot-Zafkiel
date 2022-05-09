package org.arvik.zafkielbot.commands.event.connectionMusic;

public class UserMusic {
    private String idUser;
    private String music;
    private long playtime;

    public UserMusic(String idUser, String music, long playtime) {
        this.idUser = idUser;
        this.music = music;
        this.playtime = playtime;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getMusic() {
        return music;
    }

    public void setMusic(String music) {
        this.music = music;
    }

    public long getPlaytime() {
        return playtime;
    }

    public void setPlaytime(long playtime) {
        this.playtime = playtime;
    }
}
