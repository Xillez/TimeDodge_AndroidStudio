package com.example.timedodge.game.systems.spawn;

import android.content.Context;

import com.example.timedodge.game.systems.ecs.Entity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class SpawnConfigLoader
{
    private Context context;

    public SpawnConfigLoader(Context context)
    {
        this.context = context;
    }

    /*private void configure()
    {
        XmlPullParserFactory factory = null;
        try {
            factory = XmlPullParserFactory.newInstance();

            XmlPullParser parser = factory.newPullParser();
            InputStream is = context.getAssets().open("spawn/config/spawnmanager_config.xml");
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(is, null);


            ArrayList<Entity> entities = new ArrayList<>();

            int eventType = parser.getEventType();
            Entity tempEntity = null;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String eltName = null;

                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        eltName = parser.getName();

                        if (eltName.equals("behaviour")) continue; // <-- Just a boring root element, ignore it.
                    else if (eltName.equals("entity")) {
                        tempEntity = new Entity();
                        players.add(currentPlayer);
                    } else if (currentPlayer != null) {
                        if ("name".equals(eltName)) {
                            currentPlayer.name = parser.nextText();
                        } else if ("age".equals(eltName)) {
                            currentPlayer.age = parser.nextText();
                        } else if ("position".equals(eltName)) {
                            currentPlayer.position = parser.nextText();
                        }
                    }
                        break;
                        case XmlPullParser.
                }

                eventType = parser.next();

            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
    }*/
}
