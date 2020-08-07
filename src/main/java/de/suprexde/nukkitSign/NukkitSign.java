package de.suprexde.nukkitSign;

import cn.nukkit.Player;
import cn.nukkit.plugin.PluginBase;
import de.suprexde.nukkitSign.command.SignCommand;
import lombok.Getter;
import java.time.LocalDate;

public class NukkitSign extends PluginBase {

    private Double currentlyConfigVersion = 1.0;

    private @Getter Double configVersion;
    private @Getter Integer maxSignlenght;
    private @Getter String prefix;

    private @Getter Boolean allowMultiSign;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        this.configVersion = this.getConfig().getDouble("configVersion");
        if (!configVersion.equals(currentlyConfigVersion)) {
            this.getServer().getLogger().critical("");
            this.getServer().getLogger().critical("NukkitSign: config.yml is not up to date. Please delete config.yml");
            this.getServer().getLogger().critical("");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        this.prefix = this.getConfig().getString("settings.prefix") + " ";
        this.allowMultiSign = this.getConfig().getBoolean("settings.allowMultiSign");
        this.maxSignlenght = this.getConfig().getInt("settings.maxSignlenght");

        this.getServer().getCommandMap().register("sign", new SignCommand(this));
        this.getServer().getPluginManager().registerEvents(new EventListener(this), this);

    }

    @Override
    public void onDisable() {

    }

    public String getMessage(String key) {
        return getConfig().getString("messages." + key).replace("&", "§");
    }

    public String getSignMessage(Player player, String text, boolean hasText){
        if(hasText){
            return getMessage("signText").replace("%player%", player.getDisplayName()).replace("%time%", formatDate()).replace("%text%", text).replace("%line%", "\n");
        } else {
            return getMessage("signNoText").replace("%player%", player.getDisplayName()).replace("%time%", formatDate()).replace("%line%", "\n");
        }
    }

    public String formatDate() {
        LocalDate localDate = LocalDate.now();
        int dd = localDate.getDayOfMonth();
        int mm = localDate.getMonthValue();
        int yyyy = localDate.getYear();
        String date = dd + "." + mm + "." + yyyy;
        if (dd < 10) {
            date = "0" + dd + "." + mm + "." + yyyy;
        }
        if (mm < 10) {
            date = dd + ".0" + mm + "." + yyyy;
        }
        if ((dd < 10) && (mm < 10)) {
            date = "0" + dd + ".0" + mm + "." + yyyy;
        }
        return date;
    }
}
