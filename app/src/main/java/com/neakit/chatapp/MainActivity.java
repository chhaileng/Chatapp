package com.neakit.chatapp;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

/**
 * Created by Chhaileng on 2/27/17.
 */

public class MainActivity extends AppCompatActivity {


    BottomBar mBottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBottomBar = BottomBar.attach(this,savedInstanceState);
        mBottomBar.setItemsFromMenu(R.menu.menu_main, new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.BottomBarItemOne) {
                    ChatFragment f = new ChatFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, f).commit();
                    mBottomBar.setActiveTabColor("#3e47b8");

                }
                else if (menuItemId == R.id.BottomBarItemTwo) {
                    SettingFragment f = new SettingFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, f).commit();
                    mBottomBar.setActiveTabColor("#3e47b8");
                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
//                Toast.makeText(MainActivity.this, "Aww bong eng select 2 dong aii bong", Toast.LENGTH_SHORT).show();
            }
        });


        //mBottomBar.mapColorForTab(0,"#ff0000");
        //mBottomBar.mapColorForTab(1,"#ff0000");

//        BottomBarBadge unread;
//        unread = mBottomBar.makeBadgeForTabAt(0,"#ff0000",10);
//        unread.show();

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        moveTaskToBack(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
