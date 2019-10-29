package com.bulletpointgames.timedodge.game.systems.ecs.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresComponentsContainer
{
    RequiresComponent[] value() default{};
}
