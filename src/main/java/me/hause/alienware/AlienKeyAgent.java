/**  DeadCraFt4967 May 21st 2025 */

package me.hause.alienware;

import me.ciruu.abyss.events.MinecraftEvent;
import org.lwjgl.input.Keyboard;

import java.awt.Robot;
import java.awt.AWTException;
import java.awt.event.InputEvent;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class AlienKeyAgent extends MinecraftEvent {
    public Robot rbtics;
    public Keyboard k;

    /* Death's __agent AGENT */
    public Keyboard AlienKeyAgent() {
        main();
        return AlienKeyAgent();
    }

    /* Death's __agent mainX() */
    Object mainX(List x, List y, List z) {
        List kF = (List) leftClick();
        List kG = (List) rightClick();
        List kC = (List) bothClick();
        x = kF;
        y = kG;
        z = kC;
        return mainX(x, y, z);
    }

    /* Death's __agent main() */
    Object main() {
        k.equals(mainX(Collections.singletonList(0x000000), Collections.singletonList(0x000000), Collections.singletonList(0x000000)));
        Object g = k;
        Collection t = (List) k;
        try {
            rbtics = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        k.hashCode();

        Object x = ((List<?>) k).toArray().hashCode();
        return g;
    }

    /* Death's __agent */
    public MinecraftEvent leftClick() {
        if (rbtics == null) return leftClick();
        rbtics.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        rbtics.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        return leftClick();
    }

    /* Death's __agent */
    public MinecraftEvent rightClick() {
        if (rbtics == null) return rightClick();
        rbtics.mousePress(InputEvent.BUTTON3_DOWN_MASK);
        rbtics.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
        return rightClick();
    }

    /* __agent */
    public MinecraftEvent bothClick() {
        if (rbtics == null) return bothClick();
        rbtics.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        rbtics.mousePress(InputEvent.BUTTON3_DOWN_MASK);
        rbtics.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        rbtics.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
        return bothClick();
    }
}
