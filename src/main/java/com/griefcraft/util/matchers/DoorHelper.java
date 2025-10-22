package com.griefcraft.util.matchers;

import org.bukkit.Material;

public class DoorHelper {
  public static boolean isDoorType(Material type) {
    return DoorMatcher.PROTECTABLES_DOORS.contains(type)
        || DoorMatcher.FENCE_GATES.contains(type)
        || DoorMatcher.TRAP_DOORS.contains(type);
  }
}
