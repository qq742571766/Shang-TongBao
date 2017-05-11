package com.zhonghong.hlbelogistics.core.home.Activitys;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhonghong.hlbelogistics.R;
import com.zhonghong.hlbelogistics.base.activitys.BaseActivity;
import com.zhonghong.hlbelogistics.core.home.Adapter.ImageAdapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class Sellers_details_Activity extends BaseActivity implements View.OnClickListener {

    private ImageView sellers_details_Activity_iv;
    private ViewPager sellers_details_vp;
    private TextView sellers_details_title_tv;
    private TextView guadan_title;
    private TextView guadan_total;
    private TextView price_type;
    private TextView aleady_deal;
    private TextView offer;
    private TextView ralease_time;
    private TextView type;
    private TextView packing_type;
    private TextView origin;
    private TextView year;
    private TextView guadan_remarks;
    private TextView quality;
    private TextView guadan_total2;
    private TextView price_type2;
    private TextView min;
    private TextView duanyizhuang;
    private TextView transaction_start;
    private TextView transaction_end;
    private TextView warehouse;
    private TextView inventory_place;
    private TextView number_check_type;
    private TextView vat_invoice_type;
    private TextView quality_check_type;

    private LinearLayout rec_liner;

    Sellers_details_Activity.ImageHandler handler = new Sellers_details_Activity.ImageHandler(new WeakReference<>(this));
    private int [] drawabels = {
            R.drawable.one_viewpager,
            R.drawable.two_viewpager,
            R.drawable.three_viewpager
    };
    ArrayList<ImageView> views;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sellers_details_);
        initview();
        startViewPager();
    }
     //初始化控件
    private void initview() {
        sellers_details_Activity_iv= (ImageView) findViewById(R.id.sellers_details_Activity_iv);
        sellers_details_title_tv= (TextView) findViewById(R.id.sellers_details_title_tv);
        guadan_title= (TextView) findViewById(R.id.guadan_title);
        guadan_total= (TextView) findViewById(R.id.guadan_total);
        price_type= (TextView) findViewById(R.id.price_type);
        aleady_deal= (TextView) findViewById(R.id.aleady_deal);
        offer= (TextView) findViewById(R.id.offer);
        ralease_time= (TextView) findViewById(R.id.ralease_time);
        type= (TextView) findViewById(R.id.type);
        packing_type= (TextView) findViewById(R.id.packing_type);
        origin= (TextView) findViewById(R.id.origin);
        year= (TextView) findViewById(R.id.year);
        guadan_remarks= (TextView) findViewById(R.id.guadan_remarks);
        quality= (TextView) findViewById(R.id.quality);
        guadan_total2= (TextView) findViewById(R.id.guadan_total2);
        price_type2= (TextView) findViewById(R.id.price_type2);
        min= (TextView) findViewById(R.id.min);
        duanyizhuang= (TextView) findViewById(R.id.duanyizhuang);
        transaction_start= (TextView) findViewById(R.id.transaction_start);
        transaction_end= (TextView) findViewById(R.id.transaction_end);
        warehouse= (TextView) findViewById(R.id.warehouse);
        inventory_place= (TextView) findViewById(R.id.inventory_place);
        number_check_type= (TextView) findViewById(R.id.number_check_type);
        vat_invoice_type= (TextView) findViewById(R.id.vat_invoice_type);
        quality_check_type= (TextView) findViewById(R.id.quality_check_type);
        sellers_details_Activity_iv.setOnClickListener(this);
    }

    /**
     * 初始化viewpager，并向自定义handler发送消息
     */
    private void  startViewPager(){
        sellers_details_vp= (ViewPager) findViewById(R.id.sellers_details_vp);
        rec_liner = (LinearLayout) findViewById(R.id.rec_liner);

        views = new ArrayList<>();
        for (int i = 0;i<drawabels.length;i++)
        {
            ImageView img = new ImageView(Sellers_details_Activity.this);
            img.setImageResource(drawabels[i]);
            views.add(img);
            View re_view = new View(Sellers_details_Activity.this);
            LinearLayout.LayoutParams lp =  new LinearLayout.LayoutParams(30,30);
            lp.setMargins(10,0,10,0);
            re_view.setLayoutParams(lp);
            re_view.setBackgroundResource(R.drawable.dot);
            rec_liner.addView(re_view);
        }

        sellers_details_vp.setAdapter(new ImageAdapter(views));
        sellers_details_vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageSelected(int arg0) {
                handler.sendMessage(Message.obtain(handler, Sellers_details_Activity.ImageHandler.MSG_PAGE_CHANGED, arg0, 0));


                for (int i = 0 ;i<drawabels.length;i++)
                {
                    if (i==arg0%drawabels.length)
                    {
                        View v = rec_liner.getChildAt(i);
                        v.setBackgroundResource(R.drawable.dot_selected);
                    }  else
                    {
                        View v = rec_liner.getChildAt(i);
                        v.setBackgroundResource(R.drawable.dot);
                    }
                }

            }

            //覆写该方法实现轮播效果的暂停和恢复
            @Override
            public void onPageScrollStateChanged(int arg0) {
                switch (arg0) {
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        handler.sendEmptyMessage(Sellers_details_Activity.ImageHandler.MSG_KEEP_SILENT);
                        break;
                    case ViewPager.SCROLL_STATE_IDLE:
                        handler.sendEmptyMessageDelayed(Sellers_details_Activity.ImageHandler.MSG_UPDATE_IMAGE, Sellers_details_Activity.ImageHandler.MSG_DELAY);
                        break;
                    default:
                        break;
                }
            }
        });
        sellers_details_vp.setCurrentItem(Integer.MAX_VALUE/2);//默认在中间，使用户看不到边界
        //开始轮播效果
        handler.sendEmptyMessageDelayed(Sellers_details_Activity.ImageHandler.MSG_UPDATE_IMAGE, Sellers_details_Activity.ImageHandler.MSG_DELAY);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sellers_details_Activity_iv:
                finish();
            break;
        }
    }


    /**
     * 自动轮播封装类
     */
    private static class ImageHandler extends Handler {

        /**
         * 请求更新显示的View。
         */
        protected static final int MSG_UPDATE_IMAGE  = 1;
        /**
         * 请求暂停轮播。
         */
        protected static final int MSG_KEEP_SILENT   = 2;
        /**
         * 请求恢复轮播。
         */
        protected static final int MSG_BREAK_SILENT  = 3;
        /**
         * 记录最新的页号，当用户手动滑动时需要记录新页号，否则会使轮播的页面出错。
         * 例如当前如果在第一页，本来准备播放的是第二页，而这时候用户滑动到了末页，
         * 则应该播放的是第一页，如果继续按照原来的第二页播放，则逻辑上有问题。
         */
        protected static final int MSG_PAGE_CHANGED  = 4;

        //轮播间隔时间
        protected static final long MSG_DELAY = 3000;

        //使用弱引用避免Handler泄露.这里的泛型参数可以不是Activity，也可以是Fragment等
        private WeakReference<Sellers_details_Activity> weakReference;
        private int currentItem = 0;

        protected ImageHandler(WeakReference<Sellers_details_Activity> wk){
            weakReference = wk;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Sellers_details_Activity activity = weakReference.get();
            if (activity==null){
                //Activity已经回收，无需再处理UI了
                return ;
            }
            //检查消息队列并移除未发送的消息，这主要是避免在复杂环境下消息出现重复等问题。

            switch (msg.what) {

                case MSG_UPDATE_IMAGE:
                    currentItem++;
                    activity.sellers_details_vp.setCurrentItem(currentItem);
                    //准备下次播放
                    if (activity.handler.hasMessages(MSG_UPDATE_IMAGE)){
                        activity.handler.removeMessages(MSG_UPDATE_IMAGE);

                        System.out.println("---------------aaaaa----------------"+currentItem);

                    }
                    System.out.println("---------------bbb----------------"+currentItem);
                    activity.handler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                    break;
                case MSG_KEEP_SILENT:
                    //只要不发送消息就暂停了
                    break;
                case MSG_BREAK_SILENT:
                    activity.handler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                    break;
                case MSG_PAGE_CHANGED:
                    //记录当前的页号，避免播放的时候页面显示不正确。
                    currentItem = msg.arg1;
                    break;
                default:
                    break;
            }

        }
    }
}
