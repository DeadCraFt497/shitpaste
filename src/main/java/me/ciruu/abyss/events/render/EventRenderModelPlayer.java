package me.ciruu.abyss.events.render;

import me.ciruu.abyss.enums.Class53;
import me.ciruu.abyss.events.MinecraftEvent;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;

public class EventRenderModelPlayer
extends MinecraftEvent {
    public ModelBase model_val;
    public Entity entity;
    public float fl_1;
    public float fl_2;
    public float fl_3;
    public float fl_4;
    public float fl_5;
    public float fl_6;

    public EventRenderModelPlayer(ModelBase modelBase, Entity entity, float f, float f2, float f3, float f4, float f5, float f6) {
        this.model_val = modelBase;
        this.entity = entity;
        this.fl_1 = f;
        this.fl_2 = f2;
        this.fl_3 = f3;
        this.fl_4 = f4;
        this.fl_5 = f5;
        this.fl_6 = f6;
    }

    public void getClass53(Class53 class53) {
        
    }
}
