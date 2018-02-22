package com.linkedin.replica.serachEngine.commands;

import java.util.HashMap;
/**
 * Command is an abstract class responsible for handling specific request and it communicates between
 * external input and internal functionality implementation
 */
public abstract class Command {
    protected HashMap<String, String> args;

    public Command(HashMap<String, String> args) {
        this.args = args;
    }

    /**
     * Execute the command
     * @return The output (if any) of the command
     */
    public abstract String execute();
}