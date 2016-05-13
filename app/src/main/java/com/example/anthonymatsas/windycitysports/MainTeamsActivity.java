package com.example.anthonymatsas.windycitysports;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;
import java.util.UUID;

public class MainTeamsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TeamAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_team_list);

        recyclerView = (RecyclerView)findViewById(R.id.team_list_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TeamAdapter();
        recyclerView.setAdapter(adapter);
    }

    @Override
    //Create and inflate menu
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.about_menu, menu);

        return true;
    }

    @Override
    //Launch about intent when clicking on About menu item
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.about:
                Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class TeamHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageButton imageBtn;
        Team team;
        private int image = R.drawable.wcs_logo; //Test

        public TeamHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            imageBtn = (ImageButton)itemView.findViewById(R.id.row_image_btn);
            imageBtn.setOnClickListener(this);
        }

        public void bindTeam(Team team) {
            this.team = team;

            //Set image for each team
            if(team.getImage().equals("bulls.png")) {
                image = R.drawable.bullsbtn;
            } else if(team.getImage().equals("sky.png")) {
                image = R.drawable.skybtn;
            } else if(team.getImage().equals("cubs.png")) {
                image = R.drawable.cubsbtn;
            } else if(team.getImage().equals("sox.png")) {
                image = R.drawable.soxbtn;
            } else if(team.getImage().equals("bears.png")) {
                image = R.drawable.bearsbtn;
            } else if(team.getImage().equals("hawks.png")) {
                image = R.drawable.hawksbtn;
            }
            imageBtn.setImageResource(image);
        }

        @Override
        public void onClick(View v){
            Intent intent = new Intent(MainTeamsActivity.this, TabTeamDetail.class);
            intent.putExtra("id", team.getId());
            startActivity(intent);
            //Debug purpose
            //Log.i("ID of team", team.getTwitterName() + "'s page");
        }
    }

    private class TeamAdapter extends RecyclerView.Adapter<TeamHolder> {
        private List<Team> teams;
        private TeamHolder holder;

        public TeamAdapter() {
            setTeams(TeamList.get(MainTeamsActivity.this).getTeams());
        }

        public void setTeams(List<Team> teams) {
            this.teams = teams;
        }

        @Override
        public TeamHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(MainTeamsActivity.this);
            View view = layoutInflater.inflate(R.layout.row_item, parent, false);
            holder = new TeamHolder(view);

            return holder;
        }

        @Override
        public void onBindViewHolder(TeamHolder holder, int position) {
            Team team = teams.get(position);
            holder.bindTeam(team);
        }

        @Override
        public int getItemCount() {
            return teams.size();
        }
    }
}
