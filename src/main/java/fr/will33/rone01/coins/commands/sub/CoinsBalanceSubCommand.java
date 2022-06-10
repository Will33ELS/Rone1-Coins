package fr.will33.rone01.coins.commands.sub;

import fr.will33.rone01.coins.CoinsPlugin;
import fr.will33.rone01.coins.models.CoinsPlayer;
import fr.will33.rone01.coins.utils.StringUtil;
import org.bukkit.ChatColor;

public class CoinsBalanceSubCommand {

    public static void balance(CoinsPlayer coinsPlayer){
        coinsPlayer.getPlayer().sendMessage(
                ChatColor.translateAlternateColorCodes('&', CoinsPlugin.getInstance().getConfig().getString("message.balance")
                        .replace("{balance}", StringUtil.formatCurrency(coinsPlayer.getCoins()))
                )
        );
    }

}
