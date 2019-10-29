package com.bulletpointgames.timedodge.game.systems.ecs;

import android.graphics.Canvas;

import com.bulletpointgames.timedodge.game.systems.ecs.annotations.RequiresComponent;
import com.bulletpointgames.timedodge.game.systems.ecs.annotations.Singleton;
import com.bulletpointgames.timedodge.game.systems.thread.GameManager;
import com.bulletpointgames.timedodge.utils.Tools;

import java.lang.annotation.Annotation;
import java.util.ArrayList;

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

        public void update()
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

        public static boolean isSingleton(Class componentClazz)
        {
            // return whether component is singleton
            return (componentClazz.getAnnotation(Singleton.class) != null);
        }

        public static ArrayList<Class> getRequiredComponents(Class inputClazz)
        {
            ArrayList<Class> components = new ArrayList<>();

            // Get all annotations for input component class
            Annotation[] annotations = inputClazz.getAnnotationsByType(RequiresComponent.class);
            for (Annotation annotation : annotations)
            {
                // Get component to check for
                Class componentClass = ((RequiresComponent) annotation).component();
                if (Component.class.isAssignableFrom(componentClass))
                {
                    if (!components.contains(componentClass))
                        components.add(componentClass);
                }
            }
            return components;
        }
}
