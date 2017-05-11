package com.zhonghong.hlbelogistics.core.home.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.zhonghong.hlbelogistics.R;
import com.zhonghong.hlbelogistics.base.Application.MyApplication;
import com.zhonghong.hlbelogistics.bean.IngPlanOrder;
import com.zhonghong.hlbelogistics.callback.IngPlanFragmentCallBack;
import com.zhonghong.hlbelogistics.core.home.Adapter.IngPlanrAdapter;
import com.zhonghong.hlbelogistics.ui.PullToRefreshBase;
import com.zhonghong.hlbelogistics.ui.PullToRefreshListView;
import com.zhonghong.hlbelogistics.utils.NetWorkStatte;
import com.zhonghong.hlbelogistics.utils.OkHttpUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.Call;

public class IngPlanFragment extends Fragment {
    private View view;
    public ListView mListView;
    private PullToRefreshListView listView;
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("MM-dd HH:mm");
    private List<IngPlanOrder> list;
    private IngPlanrAdapter adapter;
    private boolean mIsStart = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        getAllOrder();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tastcenter, container, false);
        initView();
        return view;
    }

    private void initView() {
        listView = (PullToRefreshListView) view.findViewById(R.id.list_view);
        listView.setPullLoadEnabled(false);
        listView.setScrollLoadEnabled(true);
        mListView = listView.getRefreshableView();
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
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

    //获取进行中计划
    public void getAllOrder() {
        if (NetWorkStatte.networkConnected(getActivity())) {
            String deliveryId = MyApplication.getInstance().getDeliveryId();
            OkHttpUtils.get()
                    .url(OkHttpUtil.URL)
                    .addParams("config", "planOrder")
                    .addParams("type", "1")
                    .addParams("deliveryId", deliveryId)
                    .addParams("status", "PSAT001003")
                    .build()
                    .execute(new IngPlanFragmentCallBack() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                        }

                        @Override
                        public void onResponse(List<IngPlanOrder> response, int id) {
                            adapter = new IngPlanrAdapter(getActivity());
                            if (response != null) {
                                list = response;
                                for (int i = 0; i < list.size(); i++) {
                                    if (list.get(i).getOrderNum() == 0) {
                                        list.remove(i);
                                        adapter.notifyDataSetChanged();
                                    }
                                }
                                adapter.setItems(list);
                                mListView.setAdapter(adapter);
                                if (list.size() == 0) {
                                    return;
                                }
                                if (list.get(0).getProId() == "0") {
                                } else if (list.get(0).getProId() == "-52") {
                                }
                            }
                        }
                    });
        } else {
            Toast.makeText(getActivity(), "请连接网络", Toast.LENGTH_SHORT).show();
        }
    }

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
                adapter = null;
                getAllOrder();
            } else {
            }
            listView.onPullDownRefreshComplete();
            listView.onPullUpRefreshComplete();
            listView.setHasMoreData(hasMoreData);
            setLastUpdateTime();
            super.onPostExecute(result);
        }
    }

    private void setLastUpdateTime() {
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String time = mDateFormat.format(curDate);
        listView.setLastUpdatedLabel(time);
    }

    @Override
    public void onStart() {
        list = null;
        getAllOrder();
        super.onStart();
    }
}