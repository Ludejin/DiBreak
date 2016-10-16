package com.zero.dibreak.module.home;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.zero.dibreak.R;
import com.zero.dibreak.adapter.SingleTypeAdapter;
import com.zero.dibreak.data.RepositoryUtils;
import com.zero.dibreak.databinding.ActivityMainBinding;
import com.zero.dibreak.domain.interactor.DefaultSubscriber;
import com.zero.dibreak.domain.model.base.BaseResponse;
import com.zero.dibreak.domain.model.response.Sister;
import com.zero.dibreak.module.category.CategoryActivity;
import com.zero.dibreak.view.base.BaseActivity;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, SwipeRefreshLayout.OnRefreshListener {

    private ActivityMainBinding mMainBinding;

    private SingleTypeAdapter<Sister> adapter;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        init();

        getData(15);
    }

    private void init() {
        Toolbar toolbar = mMainBinding.barMain.toolbar;
        setSupportActionBar(toolbar);

        mDrawerLayout = mMainBinding.drawerLayout;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mDrawerLayout = mMainBinding.drawerLayout;
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = mMainBinding.navView;
        navigationView.setNavigationItemSelectedListener(this);

        mMainBinding.barMain.contentMain.recycleView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SingleTypeAdapter<Sister>(this, R.layout.item_sister);
        mMainBinding.barMain.contentMain.recycleView.setAdapter(adapter);

        mMainBinding.barMain.contentMain.refLayout.setOnRefreshListener(this);
    }

    /**
     * 获取数据
     * @see com.zero.dibreak.api.DiApi#getSister(int)
     * @param pageSize  一页显示的数据
     */
    private void getData(int pageSize) {
        getApplicationComponent().getDiService().getDiApi().getSister(pageSize)
                .compose(RepositoryUtils.<BaseResponse<Sister>>handleData())
                .subscribe(new DefaultSubscriber<BaseResponse<Sister>>(getContext()) {
                    @Override
                    public void onNext(BaseResponse<Sister> sisterBaseResponse) {
                        adapter.set(sisterBaseResponse.getData());
                        mMainBinding.barMain.contentMain.refLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mMainBinding.barMain.contentMain.refLayout.setRefreshing(false);
                    }
                });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        mDrawerLayout.closeDrawer(GravityCompat.START);
        if (id == R.id.nav_category) {
            Intent intent = new Intent();
            intent.setClass(this, CategoryActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_collection) {

        }
        return false;
    }

    @Override
    public void onRefresh() {
        getData(15);
    }
}
