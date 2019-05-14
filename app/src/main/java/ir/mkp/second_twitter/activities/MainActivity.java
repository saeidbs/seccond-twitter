package ir.mkp.second_twitter.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import ir.mkp.second_twitter.R;
import ir.mkp.second_twitter.fragment.AnalyzerShowUsers;
import ir.mkp.second_twitter.fragment.BioFragment;
import ir.mkp.second_twitter.fragment.FollowingPostFragment;
import ir.mkp.second_twitter.fragment.HotPostFragment;
import ir.mkp.second_twitter.fragment.ManagerDetectCheatAndHotUsers;
import ir.mkp.second_twitter.fragment.SearchFragment;
import ir.mkp.second_twitter.utility.Values;
import ir.mkp.second_twitter.widget.UTab;

public class MainActivity extends BaseActivity {
    private TabLayout tabLayout;
    private FloatingActionButton floatingActionButton;
    private ViewPager viewPager;
     UTab uTab;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initial();
        action();
    }


    private void initial(){
     tabLayout=findViewById(R.id.main_tab_layout);
     floatingActionButton=findViewById(R.id.main_floating_action_button);
     floatingActionButton.setImageResource(R.drawable.ic_blog);


     viewPager=findViewById(R.id.main_view_pager);
        uTab=new UTab(this,R.id.main_view_pager,R.id.main_tab_layout);
    }

    private void action(){
        switch (Values.loginUser.getRole()){
            case ADMIN:
                uTab.add(ManagerDetectCheatAndHotUsers.class,R.drawable.ic_admin);//detect cheaters and show hot users
            case ANALYSER:
                uTab.add(AnalyzerShowUsers.class,R.drawable.ic_analyze);//analyzer
        }
        uTab.add(HotPostFragment.class,R.drawable.ic_explore);//explorer
        uTab.add(SearchFragment.class,R.drawable.ic_maps_and_flags);//search
        uTab.add(FollowingPostFragment.class,R.drawable.ic_home);//main
        uTab.add(BioFragment.class,R.drawable.ic_bio);//bio
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreatePost.class);
                startActivity(intent);
            }
        });


    }
}
