package com.example.timedodge.game.ecs;

import android.graphics.Canvas;
import android.hardware.SensorEvent;

public class Component
    {
        protected int id = 0;
        protected Entity parent = null;

        public Component()
        {

        }

        public void create(int id, Entity parent)
        {
            //Log.i(LOG_INFO_TAG, "Component create");

            this.id = id;
            this.parent = parent;
        }

        public void update(float dt, SensorEvent event)
        {
            //Log.i(LOG_INFO_TAG, "Component update");
        }

        public void draw(Canvas canvas)
        {
            //Log.i(LOG_INFO_TAG, "Component draw");
        }

        public void destroy()
        {
            //Log.i(LOG_INFO_TAG, "Component destroy");
        }

        public int getId()
        {
            return id;
        }

        public Entity getParent()
        {
            return parent;
        }
}
