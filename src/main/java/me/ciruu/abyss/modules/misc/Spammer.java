package me.ciruu.abyss.modules.misc;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;

import me.ciruu.abyss.*;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.enums.Class573;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;

public class Spammer
extends Module {
    private final Setting mainwindow = new Setting("Main", "", this, new Class25("Main Settings"));
    public final Setting mode = new Setting("Mode", "", this, (Object)Class573.Abyss2b2t);
    private final Setting delay = new Setting("Delay", "", (Module)this, (Object)Float.valueOf(1.0f), Float.valueOf(0.2f), Float.valueOf(200.0f));
    private final Setting randomdelay = new Setting("RandomDelay", "", (Module)this, (Object)true, this.mainwindow, Spammer::Method3293);
    private final Setting randomdelaymin = new Setting("RandomDelayMin", "", this, 1, 1, 249, this.mainwindow, this.randomdelay::getValue);
    private final Setting randomdelaymax = new Setting("RandomDelayMax", "", this, 10, 2, 250, this.mainwindow, this.randomdelay::getValue);
    private final Setting filename = new Setting("Filename", "", this, "spammer");
    private final Setting randomorder = new Setting("RandomOrder", "", this, false);
    private final Setting randomletters = new Setting("RandomLetters", "Random letters in the sentences", (Module)this, (Object)true, this.mainwindow, Spammer::Method3294);
    private final Setting numberrandomletters = new Setting("NumberRandomLetters", "", this, 3, 0, 10, this.mainwindow, this.randomletters::getValue);
    private final Setting randomsentences = new Setting("RandomSentences", "", this, false);
    private final Class22 Field2661 = new Class22();
    private Random Field2662 = new Random();
    private List Field2663 = new ArrayList();
    @EventHandler
    private Listener Field2664 = new Listener<Class261>(this::Method3295, new Predicate[0]);
    private int Field2665 = 0;

    public Spammer() {
        super("Spammer", "A random spammer to bypass 2b2t automute", Class11.MISC, "");
        this.addSetting(this.delay);
        this.addSetting(this.randomdelay);
        this.addSetting(this.randomdelaymin);
        this.addSetting(this.randomdelaymax);
        this.addSetting(this.filename);
        this.addSetting(this.randomorder);
        this.addSetting(this.randomletters);
        this.addSetting(this.numberrandomletters);
        this.addSetting(this.randomsentences);
    }

    public void Method3297() {
        super.getEnable();
        this.Method3298();
        try {
            File file = new File((String)this.filename.getValue() + ".txt");
            this.Field2663 = FileUtils.readLines(file, StandardCharsets.UTF_8);
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
            Class547.printChatMessage("File not found");
            this.module.disable();
        }
    }

    private void Method3300() {
        if (this.Field2665 >= this.Field2663.size()) {
            this.Field2665 = 0;
        }
        if (!this.Field2663.isEmpty()) {
            if (this.Field2662.nextInt(10) > 5 && ((Boolean)this.randomsentences.getValue()).booleanValue()) {
                Globals.mc.player.sendChatMessage(RandomStringUtils.random(this.Field2662.nextInt(4) + 1, 65, 116, false, false) + "" + RandomStringUtils.random(this.Field2662.nextInt(4) + 1, 65, 116, false, false) + "" + RandomStringUtils.random(this.Field2662.nextInt(4) + 1, 65, 116, false, false));
            } else if (((Boolean)this.randomletters.getValue()).booleanValue() && ((Boolean)this.randomorder.getValue()).booleanValue()) {
                Globals.mc.player.sendChatMessage((String)this.Field2663.get(this.Field2662.nextInt(this.Field2663.size())) + "" + RandomStringUtils.random((int)((Integer)this.numberrandomletters.getValue()), 65, 122, false, false));
            } else if (!((Boolean)this.randomletters.getValue()).booleanValue() && ((Boolean)this.randomorder.getValue()).booleanValue()) {
                Globals.mc.player.sendChatMessage((String)this.Field2663.get(this.Field2662.nextInt(this.Field2663.size())));
            } else if (((Boolean)this.randomletters.getValue()).booleanValue() && !((Boolean)this.randomorder.getValue()).booleanValue()) {
                Globals.mc.player.sendChatMessage((String)this.Field2663.get(this.Field2665) + "" + RandomStringUtils.random((int)((Integer)this.numberrandomletters.getValue()), 65, 122, false, false));
            } else if (!((Boolean)this.randomletters.getValue()).booleanValue() && !((Boolean)this.randomorder.getValue()).booleanValue()) {
                Globals.mc.player.sendChatMessage((String)this.Field2663.get(this.Field2665));
            }
        }
        ++this.Field2665;
    }

    private void Method3298() {
        try {
            File file = new File((String)this.filename.getValue() + ".txt");
            file.createNewFile();
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    private void Method3295(Class261 class261) {
        int n = ThreadLocalRandom.current().nextInt((Integer)this.randomdelaymin.getValue(), (Integer)this.randomdelaymax.getValue() + 1);
        if (((Boolean)this.randomdelay.getValue()).booleanValue() && this.Field2661.getLongNanoTime(n * 1000)) {
            this.Method3300();
            this.Field2661.getNanoTime();
        }
        if (!((Boolean)this.randomdelay.getValue()).booleanValue() && this.Field2661.getLongNanoTime((long)(((Float)this.delay.getValue()).floatValue() * 1000.0f))) {
            this.Method3300();
            this.Field2661.getNanoTime();
        }
    }

    private static boolean Method3294() {
        return true;
    }

    private static boolean Method3293() {
        return true;
    }
}
