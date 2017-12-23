package me.yhl;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class OnDeath implements Listener {

    public static HashMap<Block, Inventory> DeathChest = new HashMap<Block, Inventory>();
	
    @EventHandler
    public void onDeath(PlayerDeathEvent event){
            Player player = (Player)event.getEntity();
           
        Block blockchest = player.getWorld().getBlockAt(player.getLocation().add(0,0.5,0));
        blockchest.setType(Material.CHEST);
       
        Inventory inv = Bukkit.createInventory(null, 45,player.getDisplayName() + "'s Inventory");
        inv.clear();
        
        inv.setContents(player.getInventory().getContents());

        if(player.getInventory().getBoots() != null) {
        	inv.setItem(44, player.getInventory().getBoots());
        	}
        if(player.getInventory().getLeggings() != null) {
        	inv.setItem(43, player.getInventory().getLeggings());
        	}
        if(player.getInventory().getChestplate() != null) {
        	inv.setItem(42, player.getInventory().getChestplate());
        	}
        if(player.getInventory().getHelmet() != null) {
        	inv.setItem(41, player.getInventory().getHelmet());
        	}
        
        DeathChest.put(blockchest, inv);
       
        event.getDrops().clear();
       
    }
	
    
    @EventHandler
    public void onOpen(PlayerInteractEvent event){
            if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
                    if(event.getClickedBlock().getType() == Material.CHEST){
                            Block block = event.getClickedBlock();
                           
                            for(Block blocks : DeathChest.keySet()){
                                    if(blocks.getLocation().equals(block.getLocation())){
                                    event.setCancelled(true);
                                    event.getPlayer().openInventory(DeathChest.get(blocks));
                                    }
                            }
                    }
                    
            }
    }
    
    @EventHandler
    public void onBreak(BlockBreakEvent e) {
    	
    	Player p = e.getPlayer();
    	Block block = e.getBlock();
    	
        for(Block blocks : DeathChest.keySet()){
            if(blocks.getLocation().equals(block.getLocation())){
        		p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 1.0f, 250f);
        		String[] name = DeathChest.get(blocks).getName().split("'");
        		p.sendMessage("§aYou have recieved " + name[0]+ "'s death items!");
            	for(ItemStack items : DeathChest.get(blocks)) {
            		try {
            		p.getInventory().addItem(items);
            		}catch(Exception ex) {
            			
            		}
            		e.setCancelled(true);
            		e.getBlock().setType(Material.AIR);
            	}
            	
            }
    }
    }
    
	private static void setType(Location cursor, Material m) {
		cursor.getBlock().setType(m);
	}
	
}
