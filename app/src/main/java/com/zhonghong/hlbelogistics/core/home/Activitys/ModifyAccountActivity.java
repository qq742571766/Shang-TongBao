package com.zhonghong.hlbelogistics.core.home.Activitys;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.yanzhenjie.permission.AndPermission;
import com.zhonghong.hlbelogistics.R;
import com.zhonghong.hlbelogistics.base.Application.MyApplication;
import com.zhonghong.hlbelogistics.bean.Area;
import com.zhonghong.hlbelogistics.bean.City;
import com.zhonghong.hlbelogistics.bean.Province;
import com.zhonghong.hlbelogistics.bean.User;
import com.zhonghong.hlbelogistics.callback.AreaCallBack;
import com.zhonghong.hlbelogistics.callback.CityCallBack;
import com.zhonghong.hlbelogistics.callback.ModifyPlanStatusCallBack;
import com.zhonghong.hlbelogistics.callback.ProvinceCallBack;
import com.zhonghong.hlbelogistics.respone.AllRespone;
import com.zhonghong.hlbelogistics.utils.CameraAlbumUtil;
import com.zhonghong.hlbelogistics.utils.NetWorkStatte;
import com.zhonghong.hlbelogistics.utils.OkHttpUtil;
import com.zhonghong.hlbelogistics.utils.SelfDialog;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;

/**
 * 修改个人信息
 */
public class ModifyAccountActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int MODE_PRIVATES = 1;
    private CircleImageView iv_personal_icon;
    private EditText nick_name;
    private EditText email;
    private EditText adress;
    private EditText phone;
    private TextView province;
    private TextView city;
    private TextView area;
    private SelfDialog dialog;
    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    private List<Province> provinceList;
    private List<City> cityList;
    private List<Area> areaList;
    private String provinceCode;
    private String cityCode;
    private String areaCode;
    private OptionsPickerView pvOptions;
    private File mFile;
    /**
     * 裁剪图片方法
     */
    CameraAlbumUtil cameraAlbumUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_account);
        initView();
        Intent intent = getIntent();
        User user = (User) intent.getSerializableExtra("user");
        nick_name.setText(user.getUser_name());
        email.setText(user.getE_mail());
        adress.setText(user.getAddress());
        phone.setText(user.getPhone());
        province.setText(user.getP_address());
        city.setText(user.getC_address());
        area.setText(user.getD_address());
        AndPermission.with(this)
                .requestCode(100)
                .permission(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .send();
    }

    //初始化控件
    private void initView() {
        iv_personal_icon = (CircleImageView) findViewById(R.id.iv_personal_icon);

        //----------------------------------------------------------------------------
        // TODO: 2017/4/27  读取
//
//        mFile = new File(Environment.getExternalStorageDirectory().getPath() + "/revoeye/"+"bitmap.png");
//        if (mFile.exists()) {
//            Bitmap bitmap=BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getPath() + "/revoeye/"+"bitmap.png");
//            iv_personal_icon.setImageBitmap(bitmap);
//        } else {
//            iv_personal_icon.setImageResource(R.drawable.head);
//        }
        //----------------------------------------------------------------------------

        iv_personal_icon.setOnClickListener(this);
        ImageView modify_account_Activity_iv = (ImageView) findViewById(R.id.modify_account_Activity_iv);
        province = (TextView) findViewById(R.id.province);
        city = (TextView) findViewById(R.id.city);
        area = (TextView) findViewById(R.id.area);
        province.setOnClickListener(this);
        city.setOnClickListener(this);
        area.setOnClickListener(this);
        modify_account_Activity_iv.setOnClickListener(this);
        phone = (EditText) findViewById(R.id.phone);
        nick_name = (EditText) findViewById(R.id.nick_name);
        email = (EditText) findViewById(R.id.email);
        adress = (EditText) findViewById(R.id.adress);
        Button modify_btn = (Button) findViewById(R.id.modify_btn);
        modify_btn.setOnClickListener(this);
    }

    //获取省
    private void getProvince() {
        if (NetWorkStatte.networkConnected(ModifyAccountActivity.this)) {
            OkHttpUtils.get()
                    .url(OkHttpUtil.URL)
                    .addParams("config", "findCity")
                    .addParams("level", "1")
                    .build()
                    .execute(new ProvinceCallBack() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
//                                Toast.makeText(ModifyAccountActivity.this,"异常"+e.getMessage(),Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(List<Province> response, int id) {
                            if (response != null) {
                                provinceList = response;
                                if (provinceList.get(0).getProId() == "0") {
                                    List<String> list = new ArrayList<String>();
                                    for (int i = 0; i < provinceList.size(); i++) {
                                        list.add(provinceList.get(i).getProvinceName());
                                    }
                                    initOptionPicker(list);
                                } else if (provinceList.get(0).getProId() == "-52") {
//                                        Toast.makeText(ModifyAccountActivity.this,provinceList.get(0).getInfo(),Toast.LENGTH_SHORT).show();
                                }

                            }
                        }
                    });
        } else {
            Toast.makeText(ModifyAccountActivity.this, "请连接网络", Toast.LENGTH_SHORT).show();
        }
    }

    //获取城市
    private void getCity() {
        if (provinceCode == null) {
            Toast.makeText(ModifyAccountActivity.this, "请选择省份", Toast.LENGTH_SHORT).show();
        } else {
            if (NetWorkStatte.networkConnected(ModifyAccountActivity.this)) {
                OkHttpUtils.get()
                        .url(OkHttpUtil.URL)
                        .addParams("config", "findCity")
                        .addParams("level", "2")
                        .addParams("provinceCode", provinceCode)
                        .build()
                        .execute(new CityCallBack() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
//                            Toast.makeText(ModifyAccountActivity.this,"异常"+e.getMessage(),Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(List<City> response, int id) {
                                if (response != null) {
                                    cityList = response;
                                    if (cityList.get(0).getProId() == "0") {
                                        List<String> list = new ArrayList<String>();
                                        for (int i = 0; i < cityList.size(); i++) {
                                            list.add(cityList.get(i).getCityName());
                                        }
                                        initCityOptionPicker(list);
                                    } else if (cityList.get(0).getProId() == "-52") {
                                    }
                                }
                            }
                        });
            } else {
                Toast.makeText(ModifyAccountActivity.this, "请连接网络", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //获取地区
    private void getArea() {
        if (cityCode == null) {
            Toast.makeText(ModifyAccountActivity.this, "请选择城市", Toast.LENGTH_SHORT).show();
        } else {
            if (NetWorkStatte.networkConnected(ModifyAccountActivity.this)) {
                OkHttpUtils.get()
                        .url(OkHttpUtil.URL)
                        .addParams("config", "findCity")
                        .addParams("level", "3")
                        .addParams("cityCode", cityCode)
                        .build()
                        .execute(new AreaCallBack() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                            }

                            @Override
                            public void onResponse(List<Area> response, int id) {
                                if (response != null) {
                                    areaList = response;
                                    if (areaList.get(0).getProId() == "0") {
                                        List<String> list = new ArrayList<String>();
                                        for (int i = 0; i < areaList.size(); i++) {
                                            list.add(areaList.get(i).getAreaName());
                                        }
                                        initAreaOptionPicker(list);
                                    } else if (areaList.get(0).getProId() == "-52") {
                                    }
                                }
                            }
                        });
            } else {
                Toast.makeText(ModifyAccountActivity.this, "请连接网络", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //加载省份控件
    private void initOptionPicker(final List<String> list) {//条件选择器初始化
        //如果是三级联动的数据 请参照 JsonDataActivity 类里面的写法。
        pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = list.get(options1);
                for (int i = 0; i < provinceList.size(); i++) {
                    if (tx.equals(provinceList.get(i).getProvinceName())) {
                        provinceCode = provinceList.get(i).getProvinceCode();
                        break;
                    }
                }
                province.setText(tx);
            }
        })
                .setTitleText("城市选择")
                .setContentTextSize(20)//设置滚轮文字大小
                .setDividerColor(Color.GREEN)//设置分割线的颜色
                .setSelectOptions(0, 1)//默认选中项
                .setBgColor(Color.BLACK)
                .setTitleBgColor(Color.DKGRAY)
                .setTitleColor(Color.LTGRAY)
                .setCancelColor(Color.YELLOW)
                .setSubmitColor(Color.YELLOW)
                .setTextColorCenter(Color.LTGRAY)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setLabels("", null, null)
                .build();
        pvOptions.setPicker(list);//一级选择器
        pvOptions.show();
    }

    //加载城市控件
    private void initCityOptionPicker(final List<String> list) {//条件选择器初始化
        //如果是三级联动的数据 请参照 JsonDataActivity 类里面的写法。
        pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = list.get(options1);
                for (int i = 0; i < cityList.size(); i++) {
                    if (tx.equals(cityList.get(i).getCityName())) {
                        cityCode = cityList.get(i).getCityCode();
                        break;
                    }
                }
                city.setText(tx);
            }
        })
                .setTitleText("城市选择")
                .setContentTextSize(20)//设置滚轮文字大小
                .setDividerColor(Color.GREEN)//设置分割线的颜色
                .setSelectOptions(0, 1)//默认选中项
                .setBgColor(Color.BLACK)
                .setTitleBgColor(Color.DKGRAY)
                .setTitleColor(Color.LTGRAY)
                .setCancelColor(Color.YELLOW)
                .setSubmitColor(Color.YELLOW)
                .setTextColorCenter(Color.LTGRAY)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setLabels("", null, null)
                .build();
        pvOptions.setPicker(list);//一级选择器
        pvOptions.show();
    }

    //加载地区控件
    private void initAreaOptionPicker(final List<String> list) {//条件选择器初始化
        //如果是三级联动的数据 请参照 JsonDataActivity 类里面的写法。
        pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = list.get(options1)
                       /* + options3Items.get(options1).get(options2).get(options3).getPickerViewText()*/;
                for (int i = 0; i < areaList.size(); i++) {
                    if (tx.equals(areaList.get(i).getAreaName())) {
                        areaCode = areaList.get(i).getAreaCode();
                        break;
                    }
                }
                area.setText(tx);
            }
        })
                .setTitleText("城市选择")
                .setContentTextSize(20)//设置滚轮文字大小
                .setDividerColor(Color.GREEN)//设置分割线的颜色
                .setSelectOptions(0, 1)//默认选中项
                .setBgColor(Color.BLACK)
                .setTitleBgColor(Color.DKGRAY)
                .setTitleColor(Color.LTGRAY)
                .setCancelColor(Color.YELLOW)
                .setSubmitColor(Color.YELLOW)
                .setTextColorCenter(Color.LTGRAY)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setLabels("", null, null)
                .build();
        pvOptions.setPicker(list);//一级选择器
        pvOptions.show();
    }

    //修改信息
    private void modify_user_info() {
        String did = MyApplication.getInstance().getDeliveryId();
        String accountId = MyApplication.getInstance().getUid();
        String userName = nick_name.getText().toString();
        String eml = email.getText().toString();
        String place = adress.getText().toString();
        String pAddressName = province.getText().toString();
        String cAddressName = city.getText().toString();
        String dAddressName = area.getText().toString();
        String phoneNum = phone.getText().toString();
        if (NetWorkStatte.networkConnected(ModifyAccountActivity.this)) {
            OkHttpUtils.get()
                    .url(OkHttpUtil.URL)
                    .addParams("config", "userCenter")
                    .addParams("deliveryId", did)
                    .addParams("accountId", accountId)
                    .addParams("infoType", "2")
                    .addParams("name", userName)
                    .addParams("email", eml)
                    .addParams("address", place)
                    .addParams("pAddress", provinceCode)
                    .addParams("pAddressName", pAddressName)
                    .addParams("cAddress", cityCode)
                    .addParams("cAddressName", cAddressName)
                    .addParams("dAddress", areaCode)
                    .addParams("dAddressName", dAddressName)
                    .addParams("phone", phoneNum)
                    .build()
                    .execute(new ModifyPlanStatusCallBack() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
//                                Toast.makeText(ModifyAccountActivity.this,"异常"+e.getMessage(),Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(AllRespone response, int id) {
                            if (response != null) {
                                AllRespone ar = response;
                                if (ar.getId() == "0") {
                                    Intent intent = new Intent(ModifyAccountActivity.this, AccountManagementActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                } else if (ar.getId() == "-52") {
                                    Toast.makeText(ModifyAccountActivity.this, ar.getInfo(), Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(ModifyAccountActivity.this, "空的响应", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(ModifyAccountActivity.this, "请连接网络", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.modify_btn:
                dialog = new SelfDialog(ModifyAccountActivity.this);
                dialog.setTitle("提示");
                dialog.setMessage("是否确认修改？");
                dialog.setYesOnclickListener("确认", new SelfDialog.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        if (city.getText().equals("市名称")) {
                            Toast.makeText(ModifyAccountActivity.this, "请选择所在城市", Toast.LENGTH_SHORT).show();
                        } else if (area.getText().equals("区名称")) {
                            Toast.makeText(ModifyAccountActivity.this, "请选择所在区", Toast.LENGTH_SHORT).show();
                        } else {
                            mFile = new File(Environment.getExternalStorageDirectory().getPath() + "/revoeye/" + "bitmap.png");
                            if (mFile.exists()) {
                                //向服务器上传图片
                                postIO(mFile);
                                //修改用户信息
//                                modify_user_info();
                            }
                        }
                        dialog.dismiss();
                    }
                });
                dialog.setNoOnclickListener("取消", new SelfDialog.onNoOnclickListener() {
                    @Override
                    public void onNoClick() {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
            case R.id.modify_account_Activity_iv:
                finish();
                break;
            case R.id.iv_personal_icon:
                showChoosePicDialog();
                break;
            case R.id.province:
                getProvince();
                cityCode = null;
                city.setText("市名称");
                area.setText("区名称");
                break;
            case R.id.city:
                getCity();
                area.setText("区名称");
                break;
            case R.id.area:
                getArea();
                break;
        }
    }

    /**
     * 显示修改头像的对话框
     */
    private void showChoosePicDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("设置头像");
        String[] items = {"选择本地照片", "拍照"};
        builder.setNegativeButton("取消", null);
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                cameraAlbumUtil = new CameraAlbumUtil(ModifyAccountActivity.this);
                switch (which) {
                    case CHOOSE_PICTURE: // 选择本地照片
                        cameraAlbumUtil.openAlbum();
                        break;
                    case TAKE_PICTURE: // 拍照
                        cameraAlbumUtil.takePhoto();
                        break;
                }
            }
        });
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bitmap = CameraAlbumUtil.onActivityResult(requestCode, resultCode, data);
        iv_personal_icon.setImageBitmap(bitmap);
        if (bitmap != null) {
//            io = BitmapBytes(bitmap);
            try {
                saveFile(bitmap, "bitmap.png");
            } catch (IOException e) {
                e.printStackTrace();
            }
            // TODO: 2017/4/26 返回字符流
            //----------------------------------------------------------------------------
            //将字符流发送给服务器

            // TODO: 2017/4/27 存
//            SharedPreferences sp = getSharedPreferences("bitmaps", Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = sp.edit();
//            editor.putString("bitmap", io.toString());
//            editor.apply();
            //----------------------------------------------------------------------------
        }
    }


//    private static byte[] BitmapBytes(Bitmap bm) {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
//        return baos.toByteArray();
//    }

    /**
     * 将bitmap格式图片保存至sd卡
     * @param bm
     * @param fileName
     * @throws IOException
     */
    public void saveFile(Bitmap bm, String fileName) throws IOException {
        String path = Environment.getExternalStorageDirectory().getPath() + "/revoeye/";
        File dirFile = new File(path);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }

        File myCaptureFile = new File(path + fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.PNG, 80, bos);
        bos.flush();
        bos.close();
    }


    private void postIO(File file) {
        if (NetWorkStatte.networkConnected(ModifyAccountActivity.this)) {
            OkHttpUtils.post()
                    .addFile("file", "bitmap.png", file)
                    .url(OkHttpUtil.URL)
                    .addParams("config", "uploadImage")
                    .addParams("path", "C:/Users/rainze11/Desktop/0426.png")
                    .addParams("filename", "bitmap.png")
                    .addParams("contentType", "image/png")
                    .addParams("type", "1")
                    .build()
                    .execute(new ModifyPlanStatusCallBack() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
//                                Toast.makeText(ModifyAccountActivity.this,"异常"+e.getMessage(),Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(AllRespone response, int id) {
                            if (response != null) {
                                AllRespone ar = response;
                                if (ar.getId() == "0") {
//                                   Intent intent = new Intent(ModifyAccountActivity.this, AccountManagementActivity.class);
//                                   intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                   startActivity(intent);
                                    Toast.makeText(ModifyAccountActivity.this, ar.getInfo(), Toast.LENGTH_SHORT).show();
                                } else if (ar.getId() == "-52") {
                                    Toast.makeText(ModifyAccountActivity.this, ar.getInfo(), Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(ModifyAccountActivity.this, "空的响应", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(ModifyAccountActivity.this, "请连接网络", Toast.LENGTH_SHORT).show();
        }

    }
}