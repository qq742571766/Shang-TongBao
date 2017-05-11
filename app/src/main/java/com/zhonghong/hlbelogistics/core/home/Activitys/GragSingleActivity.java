package com.zhonghong.hlbelogistics.core.home.Activitys;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.zhonghong.hlbelogistics.R;
import com.zhonghong.hlbelogistics.bean.GrapSingleOrder;
import com.zhonghong.hlbelogistics.callback.GrapSingleOrderCallBack;
import com.zhonghong.hlbelogistics.core.home.Adapter.GrapSingleAdapter;
import com.zhonghong.hlbelogistics.ui.PullToRefreshBase;
import com.zhonghong.hlbelogistics.ui.PullToRefreshListView;
import com.zhonghong.hlbelogistics.utils.NetWorkStatte;
import com.zhonghong.hlbelogistics.utils.OkHttpUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import okhttp3.Call;
/**
 * 抢单
 */



public class GragSingleActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView mListView;
    private PullToRefreshListView lisview;
    private GrapSingleAdapter adapter;
    private ImageView grap_iv;
    private List<GrapSingleOrder> list;
    private boolean mIsStart = true;

    private SimpleDateFormat mDateFormat = new SimpleDateFormat("MM-dd HH:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grag_single);
        initView();
        getPlanOrderList();
    }
    //初始化控件
    private void initView() {
        lisview= (PullToRefreshListView) findViewById(R.id.grag_single_lv);
        lisview.setPullLoadEnabled(false);
        lisview.setScrollLoadEnabled(true);
        mListView = lisview.getRefreshableView();
        lisview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
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
        grap_iv= (ImageView) findViewById(R.id.grap_iv);
        grap_iv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.grap_iv:
                Intent intent = new Intent(GragSingleActivity.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
//                finish();
                break;
        }
    }


    //获取可抢订单list
    private void getPlanOrderList(){
        if (NetWorkStatte.networkConnected(GragSingleActivity.this)){
                OkHttpUtils.get()
                        .url(OkHttpUtil.URL)
                        .addParams("config","order")
                        .addParams("type","3")
                        .addParams("status","OSAT001001")
                        .build()
                        .execute(new GrapSingleOrderCallBack() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Toast.makeText(GragSingleActivity.this,"异常"+e.getMessage(),Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(List<GrapSingleOrder> response, int id) {
                                adapter=new GrapSingleAdapter(GragSingleActivity.this);
                                if(response!=null){
                                    list=response;
                                    adapter.setItems(list);
                                    mListView.setAdapter(adapter);
                                    if(list.size()==0){
//                                        Toast.makeText(GragSingleActivity.this,"未查找到数据！",Toast.LENGTH_SHORT).show();
                                        return;
                                    }else{
                                        if(list.get(0).getProId()=="-52"){
//                                            Toast.makeText(GragSingleActivity.this,list.get(0).getInfo(),Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                }else {
                                    Toast.makeText(GragSingleActivity.this,"空的响应",Toast.LENGTH_SHORT).show();
                                }

                            }
                        });


        }else {
            Toast.makeText(GragSingleActivity.this,"请连接网络",Toast.LENGTH_SHORT).show();
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
                adapter=null;
                getPlanOrderList();

            } else {

            }
            lisview.onPullDownRefreshComplete();
            lisview.onPullUpRefreshComplete();
            lisview.setHasMoreData(hasMoreData);
            setLastUpdateTime();
            super.onPostExecute(result);
        }
    }
    private void setLastUpdateTime() {
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String time = mDateFormat.format(curDate);
        lisview.setLastUpdatedLabel(time);
    }

}
