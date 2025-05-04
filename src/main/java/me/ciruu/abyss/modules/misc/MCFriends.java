package me.ciruu.abyss.modules.misc;

import java.util.function.Predicate;

import me.ciruu.abyss.Class547;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.Manager;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.events.player.EventPlayerUpdate;
import me.ciruu.abyss.modules.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import org.lwjgl.input.Mouse;

public class MCFriends
extends Module {
    private boolean Field1505 = false;
    @EventHandler
    private Listener Field1506 = new Listener<EventPlayerUpdate>(this::Method1923, new Predicate[0]);

    public MCFriends() {
        super("MCFriends", "Add friends by clicking the wheel button in mouse.", Class11.MISC, "");
    }

    private void Method1923(EventPlayerUpdate eventPlayerUpdate) {
        if (Globals.mc.currentScreen != null) {
            return;
        }
        if (!Mouse.isButtonDown((int)2)) {
            this.Field1505 = false;
            return;
        }
        if (!this.Field1505) {
            this.Field1505 = true;
            RayTraceResult rayTraceResult = Globals.mc.objectMouseOver;
            if (rayTraceResult == null || rayTraceResult.typeOfHit != RayTraceResult.Type.ENTITY) {
                return;
            }
            Entity entity = rayTraceResult.entityHit;
            if (entity == null || !(entity instanceof EntityPlayer)) {
                return;
            }
            if (Manager.Field223.Method711(entity)) {
                Manager.Field223.Method1542(entity.getName());
                Class547.printChatMessage(String.format("%s has been removed.", entity.getName()));
            } else {
                Manager.Field223.Method1540(entity.getName());
                Class547.printChatMessage(String.format("%s has been added.", entity.getName()));
            }
        }
    }
}
