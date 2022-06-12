package fr.will33.rone01.coins.commands;

import fr.will33.rone01.coins.CoinsPlugin;
import fr.will33.rone01.coins.commands.sub.CoinsBalanceSubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BalanceSubCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(commandSender instanceof Player player){
            CoinsBalanceSubCommand.balance(CoinsPlugin.getInstance().getCoinsPlayers().get(player));
        }
        return false;
    }
}
