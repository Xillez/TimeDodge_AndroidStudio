package com.example.timedodge.game.spawn;

import android.graphics.Canvas;
import android.os.CountDownTimer;
import android.util.Log;

import com.example.timedodge.game.Public;
import com.example.timedodge.game.ecs.Entity;
import com.example.timedodge.game.ecs.components.CollisionCircle;
import com.example.timedodge.game.ecs.components.Graphics;
import com.example.timedodge.game.ecs.components.Physics;
import com.example.timedodge.game.ecs.components.Transform;
import com.example.timedodge.utils.Logging;
import com.example.timedodge.utils.Vector;

import java.util.Random;

public class SpawnManager
{
    private float spawnDelaySec = 5.0f;
    private boolean shouldSpawn = true;
    private CountDownTimer cdt = new CountDownTimer((long) this.spawnDelaySec * 1000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            if (shouldSpawn)
                spawnEntity();
            cdt.cancel();
            cdt.start();
        }
    };

    private Random rnd = new Random();

    public SpawnManager()
    {
        //
    }

    public void create()
    {
        cdt.start();
    }

    public void update(float dt, Vector tiltValues) // CANVAS SYSTEM --> , SensorEvent event)
    {
        //
    }

    public void draw(Canvas canvas)
    {
        //
    }

    //OpenGL Version
    /*public void draw(int vertexBufferPosition, int colorPosition)
    {
        //
    }*/

    public void destroy()
    {
        //
    }

    private void spawnEntity()
    {
        Entity entity = new Entity();
        Transform entityTransform = new Transform();
        entity.addComponent(entityTransform);
        entity.addComponent(new Graphics());
        Physics entityPhysics = new Physics();
        entity.addComponent(entityPhysics);
        CollisionCircle collision = new CollisionCircle();
        collision.setBackgroundCollision(true);
        entity.addComponent(collision);

        float radius = 250;//Public.screenSize.x;
        float angle = (rnd.nextInt(360) + 1) * (180.0f / (float) Math.PI);
        Vector pos = new Vector((radius * (float) Math.cos(angle)) + (Public.screenSize.x / 2.0f), (radius * (float) Math.sin(angle)) + (Public.screenSize.y / 2.0f));
        entityTransform.setPosition(pos);
        entityPhysics.setVelocity((Public.screenSize.x / 2.0f) - pos.x, (Public.screenSize.y / 2.0f) - pos.y);
        Public.gameManager.addEntity(entity);
    }
}
