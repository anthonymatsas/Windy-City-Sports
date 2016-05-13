package com.example.anthonymatsas.windycitysports;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TeamList {
    //Class level variables
    private static TeamList teamList;
    private SQLiteDatabase database;

    public static TeamList get(Context context) {
        if(teamList == null) {
            teamList = new TeamList(context);
        }
        return teamList;
    }

    private TeamList(Context context) {
        database = new DBHelper(context).getWritableDatabase();
    }

    //Get list of teams
    public List getTeams() {
        ArrayList<Team> teams = new ArrayList<>();
        Cursor c = database.query(TeamTable.TABLE_TEAM, null, null, null, null, null, null);
        TeamCursorWrapper teamCursorWrapper = new TeamCursorWrapper(c);
        Team team;

        try {
            teamCursorWrapper.moveToFirst();
            while(!teamCursorWrapper.isAfterLast()) {
                team = teamCursorWrapper.getTeam();
                teams.add(team);
                teamCursorWrapper.moveToNext();
            }
        } finally {
            teamCursorWrapper.close();
        }

        return teams;
    }

    //Get a team using uuid
    public Team getTeam(UUID id) {
        Team team = null;

        String selection = TeamTable.COL_UUID + "=?";
        String[] selectionArgs = {id.toString()};

        Cursor c = database.query(TeamTable.TABLE_TEAM, null, selection, selectionArgs, null, null, null);
        TeamCursorWrapper cursorWrapper = new TeamCursorWrapper(c);

        try {
            if(cursorWrapper.getCount() == 0) {
                return null;
            }
            cursorWrapper.moveToFirst();
            team = cursorWrapper.getTeam();
        } finally {
            cursorWrapper.close();
        }
        return team;
    }
}
