package com.vickee.wetalk.main.teamList;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vickee.wetalk.R;
import com.vickee.wetalk.main.talkGroup.TalkGroupActivity;

import java.util.ArrayList;
import java.util.List;


public class TeamFragment extends Fragment {

    private List<String> teams;
    private RecyclerView recyclerView;
    private TeamListAdapter teamListAdapter;


    public TeamFragment() { }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        teams = new ArrayList<>();
        teamListAdapter = new TeamListAdapter(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_team, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.teamList_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(teamListAdapter);

//        List<Team> teamList1 = NIMClient.getService(TeamService.class).queryTeamListBlock();
//        if (teamList1 != null) {
//            for (Team eachTeam : teamList1) {
//                teams.add(eachTeam.getId());
//            }
//        }
        for (int i=0; i<6;i++){
            teams.add("TestGroup");
        }

        teamListAdapter.UpdateAdapterData(teams);
        teamListAdapter.setOnItemClickListener(new TeamListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String id = teams.get(position);
                Intent intent = new Intent(getActivity(), TalkGroupActivity.class);
                intent.putExtra("TalkGroup", id);
                startActivity(intent);
            }
        });
    }

}
