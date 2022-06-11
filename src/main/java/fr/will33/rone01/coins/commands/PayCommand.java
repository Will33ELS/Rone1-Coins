package fr.will33.rone01.coins.commands;

import fr.will33.rone01.coins.CoinsPlugin;
import fr.will33.rone01.coins.commands.sub.CoinsPaySubCommand;
import fr.will33.rone01.coins.models.CoinsPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PayCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(commandSender instanceof Player player) {
            CoinsPlayer coinsPlayer = CoinsPlugin.getInstance().getCoinsPlayers().get(player);
            CoinsPaySubCommand.pay(coinsPlayer, strings);
        }
        return false;
    }
}
