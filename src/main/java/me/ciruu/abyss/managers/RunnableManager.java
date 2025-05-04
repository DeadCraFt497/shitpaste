package me.ciruu.abyss.managers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import me.ciruu.abyss.AbyssMod;
import me.ciruu.abyss.Class261;
import me.ciruu.abyss.Globals;

public class RunnableManager {
    private static final List runnableList = Collections.synchronizedList(new ArrayList());
    private static final Thread thread = new Thread(RunnableManager::threadCode);
    private static boolean running = false;
    private static final Class261 clazz = new Class261();

    public static void runRunnable(Runnable runnable) {
        if (running) {
            try {
                runnable.run();
            }
            catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        } else if (runnableList.size() < 10000) {
            runnableList.add(runnable);
        }
    }

    public static void forceRunRunnable(Runnable runnable) {
        running = true;
        runnable.run();
        running = false;
    }

    public static void addRunnableAsTask(Runnable runnable) {
        Globals.mc.addScheduledTask(runnable);
    }

    private static void threadCode() {
        while (true) {
            if (!runnableList.isEmpty()) {
                try {
                    Runnable runnable = (Runnable)runnableList.remove(0);
                    runnable.run();
                }
                catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
                continue;
            }
            try {
                AbyssMod.EVENT_BUS.post(clazz);
            }
            catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            try {
                Thread.sleep(10L);
                continue;
            }
            catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
                continue;
            }
        }
    }

    static {
        thread.start();
    }
}
