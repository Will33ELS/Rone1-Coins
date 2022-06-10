package fr.will33.rone01.coins.commands.sub;

import fr.will33.rone01.coins.CoinsPlugin;
import fr.will33.rone01.coins.models.CoinsPlayer;
import fr.will33.rone01.coins.utils.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CoinsPaySubCommand {

    public static void pay(CoinsPlayer origin, String[] args){
        CoinsPlugin instance = CoinsPlugin.getInstance();
        if(args.length != 3){
            origin.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', instance.getConfig().getString("message.help.pay")));
        }else {
            int amount;
            try {
                amount = Integer.parseInt(args[2]);
                if (amount <= 0) {
                    origin.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', instance.getConfig().getString("message.pay.amountNotNegative")));
                    return;
                }
            } catch (NumberFormatException err) {
                origin.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', instance.getConfig().getString("message.pay.amountIsNotANumber")));
                return;
            }

            if(amount > origin.getCoins()){
                origin.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', instance.getConfig().getString("message.pay.noEnoughCoins")));
            }else {
                origin.takeCoins(amount);
                instance.getCoinsStockage().save(origin);
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    instance.getCoinsStockage().addAmount(Bukkit.getOfflinePlayer(args[1]).getUniqueId(), amount);
                } else {
                    CoinsPlayer coinsTarget = instance.getCoinsPlayers().get(target);
                    coinsTarget.addCoins(amount);
                    instance.getCoinsStockage().save(coinsTarget);
                    target.sendMessage(ChatColor.translateAlternateColorCodes('&', instance.getConfig().getString("message.pay.received")
                            .replace("{amount}", StringUtil.formatCurrency(amount))));
                }
                origin.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', instance.getConfig().getString("message.pay.send")
                        .replace("{amount}", StringUtil.formatCurrency(amount))));
            }
        }
    }

}
