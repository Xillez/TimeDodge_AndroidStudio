package com.bulletpointgames.timedodge.game.systems.UI;

import android.graphics.Canvas;

import com.bulletpointgames.timedodge.game.Public;
import com.bulletpointgames.timedodge.game.systems.UI.annotations.AttachUI;
import com.bulletpointgames.timedodge.game.systems.ecs.Component;

import java.util.ArrayList;

public class UIManager
{
    private ArrayList<UIElement> uiElements = new ArrayList<>();

    public ArrayList<UIElement> getUIElements()
    {
        return uiElements;
    }

    public void create()
    {
        for (UIElement element : this.uiElements)
        {
            element.create();
        }
    }

    public void update()
    {
        for (UIElement element : this.uiElements)
        {
            element.update();
        }
    }

    public void draw(Canvas canvas)
    {
        for (UIElement element : this.uiElements)
        {
            element.draw(canvas);
        }
    }

    public void destroy()
    {
        for (UIElement element : this.uiElements)
        {
            element.destroy();
        }
        this.uiElements.clear();
    }

    public void addUIElement(UIElement element)
    {
        this.uiElements.add(element);
    }

    public void removeUIElement(UIElement element)
    {
        this.uiElements.remove(element);
    }

    public static void handleAttachedUI(AttachUI annotation, Component component)
    {
        // Get ui class to add and field name to watch
        Class uiClass = annotation.uiClass();
        String fieldName = annotation.fieldName();

        // Input class is of type "UICompElement"
        if (UICompElement.class.isAssignableFrom(uiClass))
        {
            try
            {
                // Add new instance
                UICompElement element =  (UICompElement) uiClass.newInstance();
                element.setComponent(component);
                element.setFieldNameToWatch(fieldName);
                Public.uiManager.addUIElement(element);
            } catch (InstantiationException | IllegalAccessException e)
            {
                e.printStackTrace();
            }
        }
    }
}
