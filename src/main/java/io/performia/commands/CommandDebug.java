package io.performia.commands;

import io.performia.fixes.PerformiaTextureManager;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class CommandDebug extends CommandBase {
    @Override
    public String getCommandName() {
        return "test";
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "test";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        PerformiaTextureManager.INSTANCE.print();
    }
}
