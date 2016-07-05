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

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.team.TeamService;
import com.netease.nimlib.sdk.team.model.Team;
import com.vickee.wetalk.R;
import com.vickee.wetalk.talkUser.TalkGroupActivity;
import com.vickee.wetalk.widget.DividerDecoration;

import java.util.List;


public class TeamFragment extends Fragment {

    private RecyclerView recyclerView;
    private TeamListAdapter teamListAdapter;

    public TeamFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        recyclerView.addItemDecoration(new DividerDecoration(getActivity()));
        recyclerView.setAdapter(teamListAdapter);

        final List<Team> teamList1 = NIMClient.getService(TeamService.class).queryTeamListBlock();
        teamListAdapter.UpdateAdapterData(teamList1);
        teamListAdapter.setOnItemClickListener(new TeamListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), TalkGroupActivity.class);
                intent.putExtra("TalkTeamId", teamList1.get(position).getId());
                intent.putExtra("TalkTeamName", teamList1.get(position).getName());
                startActivity(intent);
            }
        });
    }

}
