package org.arvik.zafkielbot.utils.commands;

public class Command {
    private String id, description;
    private String[] aliases;
    private CommandExecutor executor;

    private VcCommandExecutor vcExecutor;

    public Command(String id, String description, CommandExecutor executor, String... aliases) {
        this.id = id;
        this.description = description;
        this.aliases = aliases;
        this.executor = executor;
    }

    public Command(String id, String description, VcCommandExecutor vcExecutor, String... aliases) {
        this.id = id;
        this.description = description;
        this.aliases = aliases;
        this.vcExecutor = vcExecutor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getAliases() {
        return aliases;
    }

    public void setAliases(String[] aliases) {
        this.aliases = aliases;
    }

    public CommandExecutor getExecutor() {
        return executor;
    }

    public void setExecutor(CommandExecutor executor) {
        this.executor = executor;
    }

    public VcCommandExecutor getVcExecutor() {
        return vcExecutor;
    }

    public void setVcExecutor(VcCommandExecutor vcExecutor) {
        this.vcExecutor = vcExecutor;
    }
}