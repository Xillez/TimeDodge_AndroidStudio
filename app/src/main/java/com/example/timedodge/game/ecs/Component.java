package com.example.timedodge.game.ecs;

import android.graphics.Canvas;

import com.example.timedodge.game.Public;
import com.example.timedodge.game.thread.GameManager;
import com.example.timedodge.utils.Tools;
import com.example.timedodge.utils.Vector;

public class Component implements GameManager.GameLifecycle
    {
        protected int id = 0;
        protected Entity parent = null;

        private boolean created = false;

        public Component()
        {
            this.id = Tools.getNewUID();
        }

        public void create()
        {
            //Log.i(LOG_INFO_TAG, "Component create");
            this.created = true;
        }

        public void update(float dt, Vector tiltValues)
        {
            //Log.i(LOG_INFO_TAG, "Component update");
            if (!this.isCreated())
                this.create();
        }

        public void draw(Canvas canvas)
        {
            //Log.i(LOG_INFO_TAG, "Component draw");
        }

        //OpenGL Version
        /*public void draw(int vertexBufferPosition, int colorPosition)
        {
            //Log.i(LOG_INFO_TAG, "Component draw");
        }*/

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

        public void setParent(Entity parent) {
            this.parent = parent;
        }

        public boolean isCreated()
        {
            return created;
        }
}
