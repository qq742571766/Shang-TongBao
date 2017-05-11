package com.zhonghong.hlbelogistics.core.home.Fragments;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


import com.zhonghong.hlbelogistics.R;
import com.zhonghong.hlbelogistics.bean.Buyer;
import com.zhonghong.hlbelogistics.core.home.Activitys.Buyers_details_Activity;
import com.zhonghong.hlbelogistics.core.home.Adapter.BuyerInfoAdapter;
import com.zhonghong.hlbelogistics.ui.PullToRefreshBase;
import com.zhonghong.hlbelogistics.ui.PullToRefreshListView;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

/**
 * Created by Administrator on 2017/3/1.
 */

public class BuyerInfoFragment extends Fragment {

    private ListView mListView;
    private PullToRefreshListView pull_refresh_listView;
    private BuyerInfoAdapter BIAdapter;
    private ArrayList<Buyer> buyerArrayList ;
    private int page_num = 1;
    private LinkedList<String> mListItems;
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("MM-dd HH:mm");
    private boolean mIsStart = true;
    private int mCurIndex = 0;
    private static final int mLoadDataCount = 100;

    @Override
    public void onCreate(@Nullable Bundle  savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

      View view = inflater.inflate(R.layout.fragment_buyerinfo,container,false);
      init(view);
      return view;
    }

  private void init(View view) {
      pull_refresh_listView = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_listView);
      pull_refresh_listView.setPullLoadEnabled(false);
      pull_refresh_listView.setScrollLoadEnabled(true);
      mListView = pull_refresh_listView.getRefreshableView();

      mCurIndex = mLoadDataCount;
        buyerArrayList = new ArrayList<>();
        for (int i=0 ;i<10;i++)
        {
            Buyer buyer = new Buyer();
            buyer.setId("i"+i);
            buyer.setDealed_num("i"+i);
            buyer.setGd_title("i"+i);
            buyer.setPrice("i"+i);
            buyer.setPrice_type("i"+i);
            buyer.setReleas_time("i"+i);
            buyer.setTotal("i"+i);
            buyerArrayList.add(buyer);
        }
        BIAdapter = new BuyerInfoAdapter(buyerArrayList);
      mListView.setAdapter(BIAdapter);
//      mListView.setOnItemClickListener(new MyOnItemClickListener());
      pull_refresh_listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
          @Override
          public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
              mIsStart = true;
              new GetDataTask().execute();
          }
          @Override
          public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
              mIsStart = false;
              new GetDataTask().execute();
          }
      });

    }
//    private class MyOnItemClickListener implements AdapterView.OnItemClickListener{
//
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            Intent intent=new Intent(getActivity(), Buyers_details_Activity.class);
//            startActivity(intent);
//        }
//    }



    private class GetDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            boolean hasMoreData = true;
            if (mIsStart) {
                Buyer buyer = new Buyer();
                buyer.setId("i"+page_num);
                buyer.setDealed_num("i"+page_num);
                buyer.setGd_title("i"+page_num);
                buyer.setPrice("i"+page_num);
                buyer.setPrice_type("i"+page_num);
                buyer.setReleas_time("i"+page_num);
                buyer.setTotal("i"+page_num);
                buyerArrayList.add(0,buyer);
            } else {
                if (page_num<5)
                {

                        Buyer buyer = new Buyer();
                        buyer.setId("i"+page_num);
                        buyer.setDealed_num("i"+page_num);
                        buyer.setGd_title("i"+page_num);
                        buyer.setPrice("i"+page_num);
                        buyer.setPrice_type("i"+page_num);
                        buyer.setReleas_time("i"+page_num);
                        buyer.setTotal("i"+page_num);
                        buyerArrayList.add(buyer);

                }else
                {
                    hasMoreData = false;
                }
            }
                BIAdapter.notifyDataSetChanged();
                pull_refresh_listView.onPullDownRefreshComplete();
                pull_refresh_listView.onPullUpRefreshComplete();
                pull_refresh_listView.setHasMoreData(hasMoreData);
                setLastUpdateTime();
            page_num++;
            super.onPostExecute(result);
        }
    }
    private void setLastUpdateTime() {
        String text = formatDateTime(System.currentTimeMillis());
        pull_refresh_listView.setLastUpdatedLabel(text);
    }
    private String formatDateTime(long time) {
        if (0 == time) {
            return "";
        }
        return mDateFormat.format(new Date(time));
    }
}
