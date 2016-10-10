package com.cj.recyclerview;

import android.content.Context;
import android.graphics.Color;
import android.os.SystemClock;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cj.recyclerview.adapter.CommonAdapter;
import com.cj.recyclerview.adapter.ViewHolder;
import com.cj.recyclerview.refresh.DividerItemDecoration;
import com.cj.recyclerview.refresh.PullRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecycler;
    private SwipeRefreshLayout mSwipe;
    private PullRefreshLayout mPull;
    private ArrayList<String> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecycler = (RecyclerView) findViewById(R.id.recycler);
        mSwipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
        mPull = (PullRefreshLayout) findViewById(R.id.pull);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPull.setRefreshing(true);
            }
        });

        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPull.setAllowLoadMore(!mPull.isAllowLoadMore());
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecycler.setLayoutManager(layoutManager);

        mRecycler.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));

        mPull.setColorSchemeColors(Color.BLACK,Color.CYAN,Color.YELLOW);

        mDatas = new ArrayList<>();
        for(int i=0;i<220;i++){
            mDatas.add(i+"");
        }

        final MyAdapter adapter = new MyAdapter(this,R.layout.recyclercview_item,mDatas);
        mRecycler.setAdapter(adapter);

        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(2000);
                        mDatas.clear();
                        for(int i=0;i<220;i++){
                            mDatas.add(i+"");
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                                mSwipe.setRefreshing(false);
                            }
                        });
                    }
                }).start();
            }
        });

        mPull.setOnPullListener(new PullRefreshLayout.OnPullListener() {
            @Override
            public void onLoadMore(final PullRefreshLayout pullRefreshLayout) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(2000);
                        mDatas.add(mDatas.size() + "");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                                pullRefreshLayout.setRefreshing(false);
                            }
                        });
                    }
                }).start();
            }
        });
    }

    class MyAdapter extends CommonAdapter<String> {

        public MyAdapter(Context context, int layoutId, List<String> datas) {
            super(context, layoutId, datas);
        }

        @Override
        public void convert(ViewHolder viewHolder, String item, int position) {
            viewHolder.setText(R.id.recyclerView_tv,item);

        }
    }
}
