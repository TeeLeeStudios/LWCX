package com.griefcraft.modules.admin;

import com.griefcraft.cache.BlockCache;
import com.griefcraft.lwc.LWC;
import com.griefcraft.model.Flag;
import com.griefcraft.model.Protection;
import com.griefcraft.scripting.JavaModule;
import com.griefcraft.scripting.event.LWCCommandEvent;
import com.griefcraft.util.matchers.DoorHelper;
import org.bukkit.command.CommandSender;

public class AdminMigrate extends JavaModule {

  @Override
  public void onCommand(LWCCommandEvent event) {
    if (event.isCancelled()) return;
    if (!event.hasFlag("migrate")) return;

    CommandSender sender = event.getSender();
    LWC lwc = event.getLWC();

    if (!lwc.isAdmin(sender)) {
      sender.sendMessage("You do not have permission to run this command");
      return;
    }
    sender.sendMessage("Running LWCX to LoTC Migration Tool by Cheezboi9");

    int total = 0, updated = 0;
    BlockCache blockCache = BlockCache.getInstance();

    for (Protection protection : lwc.getPhysicalDatabase().loadProtections()) {
      total ++;

      boolean isDoor = DoorHelper.isDoorType(protection.getBlock().getType());

      if (isDoor && !protection.hasFlag(Flag.Type.AUTOCLOSE)) {
        protection.addFlag(new Flag(Flag.Type.AUTOCLOSE));
        protection.saveNow();
        updated++;
      }
    }

    sender.sendMessage("Migration results:\n  Tagged autoclose on for " + updated + " out of " + total + " protections");
    event.setCancelled(true);
  }
}
