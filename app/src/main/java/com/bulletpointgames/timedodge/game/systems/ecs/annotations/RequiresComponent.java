package com.bulletpointgames.timedodge.game.systems.ecs.annotations;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Tells {@link com.bulletpointgames.timedodge.game.systems.ecs.Entity} that the {@link com.bulletpointgames.timedodge.game.systems.ecs.Component} requires another and should be added if non-existent.
 * <b>Note:</b> Two components that requires each other might produce undefined behaviour.
 */
@Retention(RetentionPolicy.RUNTIME)
//@Target(ElementType.TYPE)
@Repeatable(RequiresComponentsContainer.class)
public @interface RequiresComponent
{
    Class component();
}
