package fr.will33.rone01.coins.commands.sub;

import fr.will33.rone01.coins.CoinsPlugin;
import fr.will33.rone01.coins.utils.MapUtil;
import fr.will33.rone01.coins.utils.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class CoinsBaltopSubCommand {

    public static void baltop(Player player){
        CoinsPlugin coinsPlugin = CoinsPlugin.getInstance();
        for(Map.Entry<UUID, Long> top : MapUtil.sortByValue(CoinsPlugin.getInstance().getCoinsStockage().getTop()).entrySet()){
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', coinsPlugin.getConfig().getString("message.top")
                    .replace("{pseudo}", Bukkit.getOfflinePlayer(top.getKey()).getName())
                    .replace("{balance}", StringUtil.formatCurrency(top.getValue()))
            ));
        }
    }

}
