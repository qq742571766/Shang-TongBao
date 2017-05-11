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
import com.zhonghong.hlbelogistics.bean.PlanOrder;
import com.zhonghong.hlbelogistics.callback.GetAllWaitPlanCallBack;
import com.zhonghong.hlbelogistics.core.home.Activitys.WaitPlanOnderActivity;
import com.zhonghong.hlbelogistics.core.home.Adapter.WaitPlanCenterAdapter;
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
 * Created by Administrator on 2017/2/10.
 */

public class PlanCenterFragment extends Fragment{
    private ListView mListview;
    private PullToRefreshListView listView;
    private WaitPlanCenterAdapter centerAdapter;
    private List<PlanOrder> list;
    private View view;
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("MM-dd HH:mm");
    private boolean mIsStart = true;

    private PlanCenterListener planCenterListener;//监听




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
//        centerAdapter=new WaitPlanCenterAdapter(getActivity());
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_plancenter,container,false);
        listView= (PullToRefreshListView) view.findViewById(R.id.planCenter_listview);
        mListview=listView.getRefreshableView();
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
        getAllOrder();
        centerAdapter=new WaitPlanCenterAdapter(getActivity());
        centerAdapter.setModifyPlanListener(new WaitPlanCenterAdapter.MotifyPlanListener(){
            @Override
            public void onSucess() {
                if(planCenterListener!=null){
                    //调用监听
                    planCenterListener.onPlanOrderUnderway();
                    //                  ((MainActivity)getActivity()).onPlanOrderUnderway();//主界面直接设置方法
                }
            }
        });
        return view;
    }
    public PlanCenterListener getPlanCenterListener() {
        return planCenterListener;
    }

    public void setPlanCenterListener(PlanCenterListener planCenterListener) {
        this.planCenterListener = planCenterListener;
    }

    public interface PlanCenterListener{
        void onPlanOrderUnderway();//订单已接收，处于进行中状态
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }



   // 请求所有订单信息
    private void getAllOrder(){
        if (NetWorkStatte.networkConnected(getActivity())){
            String deliveryId=MyApplication.getInstance().getDeliveryId();
            OkHttpUtils.get()
                    .url(OkHttpUtil.URL)
                    .addParams("config","planOrder")
                    .addParams("type","1")
                    .addParams("deliveryId",deliveryId)
                    .addParams("status","PSAT001002")
                    .build()
                    .execute(new GetAllWaitPlanCallBack() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
//                            Toast.makeText(getActivity(),"异常"+e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onResponse(List<PlanOrder> response, int id) {
                            centerAdapter=new WaitPlanCenterAdapter(getActivity());
                           if(response!=null){
                               list=response;
                               centerAdapter.setItems(list);
                               mListview.setAdapter(centerAdapter);
                               if(list.size()==0){
//                                   Toast.makeText(getActivity(),"未查找到数据！",Toast.LENGTH_SHORT).show();
                                   return;
                               }
                               if(list.get(0).getProId() == "0"){
                              } else if(list.get(0).getProId()=="-52"){
//                                   Toast.makeText(getActivity(),list.get(0).getInfo(),Toast.LENGTH_SHORT).show();
                               }

                           }else{
//                               Toast.makeText(getActivity(),"空的响应",Toast.LENGTH_SHORT).show();
                           }

                        }
                    });

        }else {
            Toast.makeText(getActivity(),"请连接网络",Toast.LENGTH_SHORT).show();
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
                centerAdapter=null;
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


}
