package fr.will33.rone01.coins.commands;

import fr.will33.rone01.coins.CoinsPlugin;
import fr.will33.rone01.coins.commands.sub.CoinsBalanceSubCommand;
import fr.will33.rone01.coins.commands.sub.CoinsBaltopSubCommand;
import fr.will33.rone01.coins.commands.sub.CoinsPaySubCommand;
import fr.will33.rone01.coins.models.CoinsPlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CoinsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(commandSender instanceof Player player){
            if(strings.length == 0){
                this.sendHelpMessage(player);
            } else {
                CoinsPlayer coinsPlayer = CoinsPlugin.getInstance().getCoinsPlayers().get(player);
                if(strings[0].equalsIgnoreCase("balance")){
                    CoinsBalanceSubCommand.balance(coinsPlayer);
                } else if(strings[0].equalsIgnoreCase("pay")){
                    CoinsPaySubCommand.pay(coinsPlayer, strings);
                } else if(strings[0].equalsIgnoreCase("baltop")){
                    CoinsBaltopSubCommand.baltop(player);
                } else {
                    this.sendHelpMessage(player);
                }
            }
        }
        return false;
    }

    private void sendHelpMessage(Player player){
        CoinsPlugin coinsPlugin = CoinsPlugin.getInstance();
        player.sendMessage(this.translateColor(coinsPlugin.getConfig().getString("message.help.balance")));
        player.sendMessage(this.translateColor(coinsPlugin.getConfig().getString("message.help.pay")));
        player.sendMessage(this.translateColor(coinsPlugin.getConfig().getString("message.help.baltop")));
    }

    private String translateColor(String origin){
        return ChatColor.translateAlternateColorCodes('&', origin);
    }

}
