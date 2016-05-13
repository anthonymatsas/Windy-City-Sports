package com.example.anthonymatsas.windycitysports;

import android.database.Cursor;
import android.database.CursorWrapper;

/**
 * Created by anthonymatsas on 4/12/16.
 */
public class TeamCursorWrapper extends CursorWrapper {
    //Ctor
    public TeamCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    //Get team from DB
    public Team getTeam() {
        String uuid = getString(getColumnIndexOrThrow(TeamTable.COL_UUID));
        String name = getString(getColumnIndexOrThrow(TeamTable.COL_NAME));
        String image = getString(getColumnIndexOrThrow(TeamTable.COL_IMAGE));
        String twitterName = getString(getColumnIndexOrThrow(TeamTable.COL_TWITTERHANDLE));
        String youtubeUsrName = getString(getColumnIndexOrThrow(TeamTable.COL_YOUTUBEUSRNAME));
        String history = getString(getColumnIndexOrThrow(TeamTable.COL_HISTORY));

        Team team = new Team(uuid, name, image, twitterName, youtubeUsrName, history);

        return team;
    }
}
