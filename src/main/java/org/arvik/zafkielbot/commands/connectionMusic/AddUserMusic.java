package org.arvik.zafkielbot.commands.connectionMusic;

import org.arvik.zafkielbot.database.Database;
import org.arvik.zafkielbot.utils.commands.Command;
import org.arvik.zafkielbot.utils.commands.CommandExecutor;
import org.javacord.api.event.message.MessageCreateEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddUserMusic implements CommandExecutor {
    @Override
    public void run(MessageCreateEvent event, Command command, String[] args) {
        StringBuilder msg = new StringBuilder(event.getMessageContent());
        msg.delete(0,msg.indexOf(" ")+1);
        String music = String.valueOf(msg.delete(msg.indexOf(" "),msg.length()));

        msg = new StringBuilder(event.getMessageContent());
        msg.delete(0,msg.indexOf(" ")+1);
        String playtime = String.valueOf(msg.delete(0,msg.indexOf(" ")+1));

        Database database = new Database();
        PreparedStatement req;
        try {
            req = database.getCnx().prepareStatement("SELECT userId FROM usermusic WHERE userId = ?");
            req.setString(1, String.valueOf(event.getMessageAuthor().getId()));
            ResultSet res = req.executeQuery();
            if (res.next()) {
                req = database.getCnx().prepareStatement("UPDATE usermusic SET music = ?, playtime = ? WHERE userId = ?");
                req.setString(1, music);
                req.setString(2, playtime);
                req.setString(3, String.valueOf(event.getMessageAuthor().getId()));
                req.executeUpdate();
                event.getChannel().sendMessage("Music updated");
            } else {
                req = database.getCnx().prepareStatement("INSERT INTO usermusic(userId, music, playtime) VALUES (?,?,?)");
                req.setString(1, String.valueOf(event.getMessageAuthor().getId()));
                req.setString(2, music);
                req.setString(3, playtime);
                req.executeUpdate();
                event.getChannel().sendMessage("Music added");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}