package com.matthew.combatLog;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class CombatLog extends JavaPlugin implements Listener {

    private static CombatLog instance;

    @Override
    public void onEnable() {
        instance = this;
        CombatLogManager combatLogManager = CombatLogManager.INSTANCE;

        Bukkit.getPluginManager().registerEvents(new combatEvents(), this);
    }

    public static CombatLog getInstance() { return instance; }
}
