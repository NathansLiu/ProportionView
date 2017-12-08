package com.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.myapplication.adapter.ProportionViewAdapter;
import com.myapplication.bean.ProportionViewBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {
    @InjectView(R.id.rv)
    RecyclerView mRv;
    protected ActionBar mActionBar;
    private static final double max = 100.0;
    private static final double min = 0.0;
    private Random random = new Random();
    private double mDouble;
    private List<ProportionViewBean> stringList = new ArrayList<>();
    private ProportionViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        mActionBar = getSupportActionBar();
        // mActionBar.hide(); //隐藏ActionBar
        initWidget();
        initData();

    }

    private void initWidget() {
        mActionBar.setDisplayOptions(
                ActionBar.DISPLAY_SHOW_CUSTOM
                        | ActionBar.DISPLAY_USE_LOGO
                        | ActionBar.DISPLAY_SHOW_TITLE
                        | ActionBar.DISPLAY_SHOW_HOME
        );

        //        mActionBar.setDisplayHomeAsUpEnabled(true);//显示返回键
        //        mActionBar.setHomeButtonEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //右侧图标和 showAsAction 有关
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //刷新点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                stringList.removeAll(stringList);
                adapter.notifyDataSetChanged();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initData();
                    }
                }, 500);
                break;
            case android.R.id.home:
                Log.d("返回键", "滴滴");
                break;
        }
        return true;
    }

    private void initData() {
        for (int i = 0; i < 30; i++) {
            mDouble = random.nextDouble() % (max - min + 1) + min;
            stringList.add(new ProportionViewBean("选择" + i + "", mDouble * 100));
            Log.d("数据长度", mDouble * 100 + "");
        }
        mRv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProportionViewAdapter(MainActivity.this, stringList);
        mRv.setAdapter(adapter);
    }
}
