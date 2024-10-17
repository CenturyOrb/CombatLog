package com.matthew.combatLog;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public enum CombatLogManager {
    INSTANCE;

    private Cache<Player, Long> cooldown = CacheBuilder.newBuilder().expireAfterWrite(15, TimeUnit.SECONDS).build();
    private HashMap<Player, BukkitTask> cooldownIndicator = new HashMap<>();

    /**
     * Starts combat for a player
     * @param player the player to start combat for
     */
    public void startCombat(Player player) {
        if (!cooldown.asMap().containsKey(player)) { // 5 Minute Cooldown
            player.sendMessage(ChatColor.RED + "You are now in combat.");
            cooldown.put(player, System.currentTimeMillis() + (15 * 20));
        }

        BukkitTask task = Bukkit.getScheduler().runTaskLater(CombatLog.getInstance(), () -> {
            player.sendMessage(ChatColor.GREEN + "You are no longer in combat.");
        }, 15 * 20);
        cooldownIndicator.put(player, task);
    }
    /**
     * Checks to see if the player is in active combat
     * @param player the player to check and see if they are in combat
     */
    public boolean isInCombat(Player player) {
        return cooldown.asMap().containsKey(player);
    }

    /**
     * Remove player from the cooldown
     * @param player the player to check and see if they are in combat
     */
    public void endCombat(Player player) {
        cooldown.invalidate(player);
        cooldownIndicator.remove(player);
    }
}