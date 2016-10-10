# RecyclerView
RecyclerView的刷新辅助类 http://blog.csdn.net/cj_286/article/details/52767070

使用
<com.cj.recyclerview.refresh.PullRefreshLayout
        android:id="@+id/pull"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <android.support.v4.widget.SwipeRefreshLayout
            android:id="@+id/swipe"
            android:layout_width="match_parent"
            android:layout_height="match_parent" >

            <android.support.v7.widget.RecyclerView
                android:id="@+id/recycler"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:cacheColorHint="@null"
                android:scrollbars="vertical" />

        </android.support.v4.widget.SwipeRefreshLayout>

    </com.cj.recyclerview.refresh.PullRefreshLayout>
