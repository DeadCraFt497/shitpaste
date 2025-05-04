package me.ciruu.abyss.modules.misc;

import java.util.Random;
import java.util.function.Predicate;

import me.ciruu.abyss.*;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.enums.Class190;
import me.ciruu.abyss.enums.Class333;
import me.ciruu.abyss.events.player.EventPlayerUpdateMoveState;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.modules.movement.NoSlowDown;
import me.ciruu.abyss.settings.Setting;
import me.hause.alienware.AlienKeyAgent;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.item.ItemFishingRod;

public class AntiAFK
extends Module {
    private final Setting main = new Setting("Main", "", this, new Class25("Main Settings"));
    private final Setting mode = new Setting("Mode", "", this, (Object)Class333.Normal);
    private final Setting walk = new Setting("Walk", "", this, true);
    private final Setting shift = new Setting("Shift", "", (Module)this, (Object)true, this.main, this::Method2910);
    private final Setting fakeFishing = new Setting("FakeFishing", "", this, true);
    private final Class22 Field2346 = new Class22();
    private final Class22 Field2347 = new Class22();
    private final Class22 Field2348 = new Class22();
    private final Class22 Field2349 = new Class22();
    private final Class22 Field2350 = new Class22();
    private final Class22 Field2351 = new Class22();
    private final Class22 Field2352 = new Class22();
    private boolean Field2353 = false;
    private boolean Field2354 = false;
    private final Random Field2355 = new Random();
    private Class190 Field2356 = Class190.None;
    @EventHandler
    private Listener Field2357 = new Listener<EventPlayerUpdateMoveState>(this::Method2911, new Predicate[0]);
    private boolean Field2358;
    private boolean Field2359;
    private boolean Field2360 = true;
    private int Field2361;
    private int Field2362;
    private int Field2363;
    private int Field2364;
    private int Field2365;
    private int Field2366;
    private int Field2367;
    private int Field2368;

    public AntiAFK() {
        super("AntiAFK", "", Class11.MISC, "");
        this.addSetting(this.mode);
        this.addSetting(this.walk);
        this.addSetting(this.shift);
        this.addSetting(this.fakeFishing);
    }

    public void Method2913() {
        super.getEnable();
        this.Field2360 = true;
        this.Field2359 = true;
        this.Field2358 = true;
        this.Field2353 = false;
        this.Field2354 = false;
        if (!Manager.moduleManager.isModuleEnabled(NoSlowDown.class)) {
            ((NoSlowDown)Manager.moduleManager.getModuleByClass(NoSlowDown.class)).enable();
        } else {
            this.Field2354 = true;
        }
        if (((Boolean)this.walk.getValue()).booleanValue() && this.mode.getValue() == Class333.Abyss2b2t) {
            Class547.printChatMessage("Stay between 4 chunks, F3 + G");
            Class547.printChatMessage("Those 4 chunks must be flat for it to work well");
        }
    }

    public void Method2915() {
        super.getDisable();
        if (Manager.moduleManager.isModuleEnabled(NoSlowDown.class) && !this.Field2354) {
            ((NoSlowDown)Manager.moduleManager.getModuleByClass(NoSlowDown.class)).enable();
        }
    }

    private void Method2916() {
        if (this.Field2348.getLongNanoTime(this.Field2355.nextInt(91) + 30000)) {
            Class155.Method1491(ItemFishingRod.class, false);
            AlienKeyAgent __agent = new AlienKeyAgent();
            __agent.rightClick();
            this.Field2348.getNanoTime();
        }
    }

    private void Method2917() {
        switch (this.Field2356) {
            case Forward: {
                Globals.mc.player.movementInput.moveForward += 1.0f;
                break;
            }
            case Back: {
                Globals.mc.player.movementInput.moveForward -= 1.0f;
                break;
            }
            case Right: {
                Globals.mc.player.movementInput.moveStrafe += 1.0f;
                break;
            }
            case Left: {
                Globals.mc.player.movementInput.moveStrafe -= 1.0f;
                break;
            }
        }
    }

    private void Method2918() {
        if (this.Field2349.getLongNanoTime(this.Field2355.nextInt(5) * 1000)) {
            int n = this.Field2355.nextInt(6);
            switch (n) {
                case 0: {
                    this.Field2356 = Class190.Forward;
                    break;
                }
                case 1: {
                    this.Field2356 = Class190.Right;
                    break;
                }
                case 2: {
                    this.Field2356 = Class190.Left;
                    break;
                }
                case 3: {
                    this.Field2356 = Class190.Back;
                    break;
                }
                case 4: 
                case 5: {
                    this.Field2356 = Class190.None;
                }
            }
            this.Field2349.getNanoTime();
        }
    }

    private void Method2919() {
        int n = (int)Globals.mc.player.posX % 16;
        int n2 = (int)Globals.mc.player.posZ % 16;
        if (n == 15 && n2 == 0 || n == 0 && n2 == 0 || n == 15 && n2 == -15 || n == 0 && n2 == -15 || n == 15 && n2 == 15 || n == -15 && n2 == -15 || n == -15 && n2 == 0 || n == 0 && n2 == 15) {
            if (this.Field2360) {
                Globals.mc.player.rotationYaw = 180.0f;
                this.Field2356 = Class190.Forward;
                this.Field2353 = true;
            }
        } else if (this.Field2360) {
            this.Method2920();
        } else {
            this.Method2921();
        }
    }

    private void Method2922() {
        if (this.Field2347.getLongNanoTime(1000L) && ((Boolean)this.walk.getValue()).booleanValue() && this.mode.getValue() == Class333.Abyss2b2t) {
            Globals.mc.gameSettings.keyBindSneak.pressed = true;
            this.Field2347.getNanoTime();
        }
    }

    private void Method2920() {
        int n = (int)Globals.mc.player.posX % 16;
        int n2 = (int)Globals.mc.player.posZ % 16;
        if (this.Field2351.getLongNanoTime(2000L)) {
            if (this.Field2358) {
                Globals.mc.player.rotationYaw = 90.0f;
                this.Field2358 = false;
            } else if (this.Field2359) {
                this.Field2361 = Globals.mc.player.chunkCoordX;
                this.Field2365 = Globals.mc.player.chunkCoordZ;
                this.Field2359 = false;
                Globals.mc.player.rotationYaw = 0.0f;
            } else if (this.Field2360) {
                this.Field2363 = Globals.mc.player.chunkCoordX;
                this.Field2367 = Globals.mc.player.chunkCoordZ;
                this.Field2360 = false;
                Globals.mc.player.rotationYaw = -90.0f;
                this.Field2362 = this.Field2361 + 1;
                this.Field2366 = this.Field2365;
                this.Field2364 = this.Field2363 + 1;
                this.Field2368 = this.Field2367;
                this.Field2356 = Class190.None;
            }
            this.Field2351.getNanoTime();
        }
    }

    private void Method2921() {
        if (this.Field2352.getLongNanoTime(3000L)) {
            this.Field2356 = Class190.Forward;
            if (Globals.mc.player.chunkCoordX == this.Field2363 && Globals.mc.player.chunkCoordZ == this.Field2367) {
                switch (this.Field2355.nextInt(3)) {
                    case 0: {
                        Globals.mc.player.rotationYaw = -90.0f;
                        break;
                    }
                    case 1: {
                        Globals.mc.player.rotationYaw = 180.0f;
                        break;
                    }
                    case 2: {
                        Globals.mc.player.rotationYaw = -135.0f;
                    }
                }
            }
            if (Globals.mc.player.chunkCoordX == this.Field2364 && Globals.mc.player.chunkCoordZ == this.Field2368) {
                switch (this.Field2355.nextInt(3)) {
                    case 0: {
                        Globals.mc.player.rotationYaw = 90.0f;
                        break;
                    }
                    case 1: {
                        Globals.mc.player.rotationYaw = 180.0f;
                        break;
                    }
                    case 2: {
                        Globals.mc.player.rotationYaw = 135.0f;
                    }
                }
            }
            if (Globals.mc.player.chunkCoordX == this.Field2361 && Globals.mc.player.chunkCoordZ == this.Field2365) {
                switch (this.Field2355.nextInt(3)) {
                    case 0: {
                        Globals.mc.player.rotationYaw = -90.0f;
                        break;
                    }
                    case 1: {
                        Globals.mc.player.rotationYaw = 0.0f;
                        break;
                    }
                    case 2: {
                        Globals.mc.player.rotationYaw = -45.0f;
                    }
                }
            }
            if (Globals.mc.player.chunkCoordX == this.Field2362 && Globals.mc.player.chunkCoordZ == this.Field2366) {
                switch (this.Field2355.nextInt(3)) {
                    case 0: {
                        Globals.mc.player.rotationYaw = 90.0f;
                        break;
                    }
                    case 1: {
                        Globals.mc.player.rotationYaw = 0.0f;
                        break;
                    }
                    case 2: {
                        Globals.mc.player.rotationYaw = 45.0f;
                    }
                }
            }
            this.Field2352.getNanoTime();
        }
    }

    private void Method2911(EventPlayerUpdateMoveState eventPlayerUpdateMoveState) {
        int n = (int)Globals.mc.player.posX % 16;
        int n2 = (int)Globals.mc.player.posZ % 16;
        if (this.Field2346.getLongNanoTime(3000L) && ((Boolean)this.shift.getValue()).booleanValue() && this.mode.getValue() == Class333.Normal) {
            Globals.mc.gameSettings.keyBindSneak.pressed = true;
            this.Field2346.getNanoTime();
        }
        if (((Boolean)this.fakeFishing.getValue()).booleanValue()) {
            this.Method2916();
        }
        if (((Boolean)this.walk.getValue()).booleanValue() && this.mode.getValue() == Class333.Normal) {
            this.Method2918();
            this.Method2917();
        }
        if (((Boolean)this.walk.getValue()).booleanValue() && this.mode.getValue() == Class333.Abyss2b2t && (n == 15 && n2 == 0 || n == 0 && n2 == 0 || n == 15 && n2 == -15 || n == 0 && n2 == -15 || n == 15 && n2 == 15 || n == -15 && n2 == -15 || n == -15 && n2 == 0 || n == 0 && n2 == 15 || this.Field2353)) {
            if (this.Field2350.getLongNanoTime(1000L)) {
                this.Method2919();
                this.Field2350.getNanoTime();
            }
            this.Method2917();
        }
    }

    private boolean Method2910() {
        return this.mode.getValue() == Class333.Normal;
    }
}
