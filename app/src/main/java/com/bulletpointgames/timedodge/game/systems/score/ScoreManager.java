package com.bulletpointgames.timedodge.game.systems.score;

import com.bulletpointgames.timedodge.game.Public;

public class ScoreManager
{
    private long points = 0L;
    private long bonuses = 0L;

    public ScoreManager()
    {

    }

    public void create()
    {
        Public.timerManager.registerTimer(0, 1000, ()->{ScoreManager.AddPoints(10);});
    }

    public void update()
    {
        //
    }

    public void destroy()
    {
        //
    }
    public static long GetPoints()
    {
        return Public.scoreManager.points;
    }

    public static void AddPoints(long pointsToAdd)
    {
        Public.scoreManager.points += pointsToAdd;
    }

    public static void RemovePoints(long pointsToRemove)
    {
        Public.scoreManager.points -= pointsToRemove;
    }

    public static long GetBonuses()
    {
        return Public.scoreManager.bonuses;
    }

    public static void AddBonuses(long pointsToAdd)
    {
        Public.scoreManager.bonuses += pointsToAdd;
    }

    public static void RemoveBonuses(long pointsToRemove)
    {
        Public.scoreManager.bonuses -= pointsToRemove;
    }
}
