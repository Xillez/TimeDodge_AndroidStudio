package com.bulletpointgames.timedodge.game.systems.ecs.components;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;

import com.bulletpointgames.timedodge.game.Public;
import com.bulletpointgames.timedodge.game.systems.ecs.Component;
import com.bulletpointgames.timedodge.utils.Time;
import com.bulletpointgames.timedodge.utils.Tools;
import com.bulletpointgames.timedodge.utils.Vector;

public final class RespawnTrigger extends Component
{
    private final float RESPAWN_TIME_SEC = 5.0f;

    private boolean visible = false;
    private boolean enteredScreen = false;
    private boolean timeExpiration = false;

    private float timeSoFar = 0.0f;

    private Transform parentTransform = null;
    private Graphics parentGraphics = null;

    public RespawnTrigger()
    {
        super();
    }

    @Override
    public void create()
    {
        super.create();

        this.parentTransform = (Transform) this.parent.getComponentByType(Transform.class);
        this.parentGraphics = (Graphics) this.parent.getComponentByType(Graphics.class);
    }

    @Override
    public void update()
    {
        super.update();

        // No parent transform nor graphics found, abort
        if (this.parentTransform == null || this.parentGraphics == null)
            return;

        Vector pos = this.parentTransform.getPosition();
        Vector size = this.parentGraphics.getActualSize();
        Rect objRect = new Rect((int) (pos.x - (size.x / 2.0f)), (int) (pos.y - (size.y / 2.0f)), (int) (pos.x + (size.x / 2.0f)), (int) (pos.y + (size.y / 2.0f)));

        // Continuously track whether visible or not
        this.visible = Tools.isVisibleOnScreen(objRect);

        // Entered screen once visible
        if (this.visible)
        {
            this.enteredScreen = true;
        }

        this.timeSoFar += Time.getDeltaTime();

        this.timeExpiration = (this.timeSoFar >= this.RESPAWN_TIME_SEC);
    }

    @Override
    public void draw(Canvas canvas)
    {
        super.draw(canvas);

        // No parent transform nor graphics found, abort
        if (this.parentTransform == null || this.parentGraphics == null)
            return;

        if (Public.DEBUG_MODE)
        {
            Vector pos = this.parentTransform.getPosition();
            Vector size = this.parentGraphics.getActualSize();

            ShapeDrawable detectCircle = new ShapeDrawable(new RectShape());
            detectCircle.getPaint().setColor(0x880000FF);
            detectCircle.setBounds(new Rect((int) (pos.x - (size.x / 2.0f)), (int) (pos.y - (size.y / 2.0f)), (int) (pos.x + (size.x / 2.0f)), (int) (pos.y + (size.y / 2.0f))));
            detectCircle.draw(canvas);

            Paint paint = new Paint();
            paint.setColor(0xFFCC7832);
            canvas.drawLine(Public.screenSize.x / 2.0f, Public.screenSize.y / 2.0f, pos.x, pos.y, paint);
        }
    }

    @Override
    public void destroy()
    {
        super.destroy();
    }

    public boolean isVisible()
    {
        return visible;
    }

    public boolean hasEnteredScreen()
    {
        return enteredScreen;
    }

    public boolean hasTimeExpired()
    {
        return timeExpiration;
    }

    public void reset()
    {
        this.visible = false;
        this.enteredScreen = false;
        this.timeSoFar = 0.0f;
    }
}
