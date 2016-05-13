package com.example.anthonymatsas.windycitysports;

import android.app.Fragment;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.UUID;

/**
 * Created by anthonymatsas on 4/24/16.
 */
public class HistoryFragment extends android.support.v4.app.Fragment {
    //Class level variables
    private TextView testView;
    private Team team;

    public HistoryFragment() {
        //empty ctor
    }

    public static HistoryFragment newInstance(UUID id) {
        //Initialize fragment and grab passed team id
        HistoryFragment historyFragment = new HistoryFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable("id", id);
        historyFragment.setArguments(bundle);

        return historyFragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Set layout components and inflate fragment
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);

        UUID id = (UUID)getArguments().getSerializable("id");

        team = TeamList.get(getContext()).getTeam(id);

        testView = (TextView)rootView.findViewById(R.id.history_text_view);
        testView.setText(team.getHistory());

        return rootView;
    }
}
