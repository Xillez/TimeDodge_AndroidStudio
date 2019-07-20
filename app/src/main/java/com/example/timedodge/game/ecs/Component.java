package com.example.timedodge.game.ecs;

import com.example.timedodge.game.GameManager;
import com.example.timedodge.utils.Vector;

public class Component implements GameManager.GameLifecycle
    {
        public static int NO_ID = -1;

        protected int id = 0;
        protected Entity parent = null;

        private boolean created = false;

        public Component()
        {
            this.id = Component.NO_ID;
        }

        public Component(int id)
        {
            this.id = id;
        }

        public void create()
        {
            //Log.i(LOG_INFO_TAG, "Component create");
            this.created = true;
        }

        public void update(float dt, Vector tiltValues)
        {
            if (!this.isCreated())
                this.create();
            //Log.i(LOG_INFO_TAG, "Component update");
        }

        public void draw()
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

        public void setId(int id) {
            this.id = id;
        }

        public Entity getParent()
        {
            return parent;
        }

        public void setParent(Entity parent) {
            this.parent = parent;
        }

        public boolean isCreated()
        {
            return created;
        }
}
