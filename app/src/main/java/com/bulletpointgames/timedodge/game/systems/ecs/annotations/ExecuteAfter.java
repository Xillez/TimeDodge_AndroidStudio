package com.bulletpointgames.timedodge.game.systems.ecs.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Tells components to execute after some other {@link com.bulletpointgames.timedodge.game.systems.ecs.Component}.
 * This should only be used in conjunction with {@link RequiresComponent} and only if absolutely required.
 * <b>Note:</b> Two components set to execute after each other will produce undefined behaviour in the execution order.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ExecuteAfter
{
    // TODO: MAKE A ARRAY!! Is this possible?
    Class component();
}
