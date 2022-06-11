package fr.will33.rone01.coins.commands;

import fr.will33.rone01.coins.CoinsPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ConfirmCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(commandSender instanceof Player player){
            if(!CoinsPlugin.getInstance().getConfirm().containsKey(player)){
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', CoinsPlugin.getInstance().getConfig().getString("message.confirm.noConfirm")));
            } else {
                CoinsPlugin.getInstance().getConfirm().get(player).run();
            }
        }
        return false;
    }
}
