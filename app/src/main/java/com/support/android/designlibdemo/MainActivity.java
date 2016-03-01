package com.support.android.designlibdemo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //找到ToolBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        //找到抽屉布局
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        //找到导航抽屉
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        //找到viewpager
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            //设置viewpager 的数据
            setupViewPager(viewPager);
        }

        //找到FloatingActionButton
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //给按钮设置一个点击事件
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //类似土司  自动产生了一个向上的动画效果
                Snackbar.make(view, " 这是Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        //找到了tabLayout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        //给viewpager 关联一共tablayotu
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        //创建了一个viewpager的数据适配器
        Adapter adapter = new Adapter(getSupportFragmentManager());
        //Fragment作为viewpager 要展示的内容
        adapter.addFragment(new CheeseListFragment(), "tab1");
        adapter.addFragment(new CheeseListFragment(), "tab2");
        adapter.addFragment(new CheeseListFragment(), "tab3");

        //给viewpager设置数据适配器  viewpager的内容就显示出来 了
        viewPager.setAdapter(adapter);
    }

    private void setupDrawerContent(NavigationView navigationView) {

        //设置菜单项被点击时候的监听
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        //设置菜单选项被选中 状态
                        menuItem.setChecked(true);
                        //关闭抽屉
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    //就是vewpager 要展示的数据适配器
    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            //添加Fragment
            mFragments.add(fragment);
            //添加标题
            mFragmentTitles.add(title);
        }

        //返回viewpager 中每个条目展示的内容
        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        //返回viewpager中一共要展示的条目
        @Override
        public int getCount() {
            //集合的大小
            return mFragments.size();

        }

        //返回内容对应的标题  标题的内容来自于mFragmentTitles 集合
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
