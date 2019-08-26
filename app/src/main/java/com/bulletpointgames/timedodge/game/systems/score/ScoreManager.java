package com.bulletpointgames.timedodge.game.systems.score;

public class ScoreManager
{
    private static long points = 0L;
    private static long bonuses = 0L;

    public static long GetPoints()
    {
        return points;
    }

    public static void AddPoints(long pointsToAdd)
    {
        points += pointsToAdd;
    }

    public static void RemovePoints(long pointsToRemove)
    {
        points -= pointsToRemove;
    }

    public static long GetBonuses()
    {
        return points;
    }

    public static void AddBonuses(long pointsToAdd)
    {
        points += pointsToAdd;
    }

    public static void RemoveBonuses(long pointsToRemove)
    {
        points -= pointsToRemove;
    }
}
