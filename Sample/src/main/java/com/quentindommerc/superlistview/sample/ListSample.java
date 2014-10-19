package com.quentindommerc.superlistview.sample;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.quentindommerc.superlistview.OnMoreListener;
import com.quentindommerc.superlistview.SuperListview;
import com.quentindommerc.superlistview.SwipeDismissListViewTouchListener;

import java.util.ArrayList;


public class ListSample extends Activity implements SwipeRefreshLayout.OnRefreshListener, OnMoreListener {

    public static final int ITEMS_PER_PAGE = 20;
    private SuperListview mList;
    private ArrayAdapter<String> mAdapter;

    @SuppressWarnings("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_sample);

        // Empty list view demo, just pull to add more items
        ArrayList<String> lst = new ArrayList<String>();
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, lst);


        // This is what you're looking for
        mList = (SuperListview)findViewById(R.id.list);

        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                for (int i = 1; i <= ITEMS_PER_PAGE; i++) {
                    mAdapter.add("Item " + i);
                }

                mList.setAdapter(mAdapter);
            }
        }, 2000);

        // Setting the refresh listener will enable the refresh progressbar
        mList.setRefreshListener(this);

        // Wow so beautiful
        mList.setRefreshingColor(android.R.color.holo_orange_light, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_red_light);

        // I want to get loadMore triggered if I see the last item (1)
        mList.setupMoreListener(this, 1);

        mList.setupSwipeToDismiss(new SwipeDismissListViewTouchListener.DismissCallbacks() {
            @Override
            public boolean canDismiss(int position) {
                return true;
            }

            @Override
            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
            }
        }, true);
    }

    @Override
    public void onRefresh() {
        Toast.makeText(this, "Refresh", Toast.LENGTH_LONG).show();

        // enjoy the beaty of the progressbar
        new Handler().postDelayed(new Runnable() {
            public void run() {
                // demo purpose, adding to the top so you can see it
                mAdapter.insert("New stuff", 0);

            }
        }, 2000);
    }

    @Override
    public void onMoreAsked(final int numberOfItems, int numberBeforeMore, int currentItemPos) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (numberOfItems < 60) {
                    //demo purpose, adding to the bottom
                    for (int i = 1; i <= ITEMS_PER_PAGE; i++) {
                        mAdapter.add("Item " + (numberOfItems + i));
                    }
                    mList.hideMoreProgress(true);
                } else {
                    mList.hideMoreProgress(false);
                }
            }
        }, 2000);

    }
}
