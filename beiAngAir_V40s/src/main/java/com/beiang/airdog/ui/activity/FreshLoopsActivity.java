package com.beiang.airdog.ui.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.beiang.airdog.constant.AirConstant;
import com.beiang.airdog.constant.Constants;
import com.beiang.airdog.net.business.BsOperationHub;
import com.beiang.airdog.net.business.entity.CurrentDevice;
import com.beiang.airdog.net.business.entity.DevEntity;
import com.beiang.airdog.net.business.homer.CommandPair;
import com.beiang.airdog.net.business.homer.QueryWeatherPair;
import com.beiang.airdog.net.httpcloud.aync.abs.BaseMsg.RspMsgBase;
import com.beiang.airdog.net.httpcloud.aync.abs.ReqCbk;
import com.beiang.airdog.ui.base.BaseMultiPartActivity;
import com.beiang.airdog.ui.model.AirInfo;
import com.beiang.airdog.utils.EParse;
import com.beiang.airdog.utils.LogUtil;
import com.beiang.airdog.view.WeatherView;
import com.beiang.airdog.widget.Toast;
import com.broadlink.beiangair.R;

import java.util.Calendar;

/***
 * 新风
 */
public class FreshLoopsActivity extends BaseMultiPartActivity implements OnClickListener {
    private TextView tv_location;
    private TextView tv_time;
    private ImageView iv_level_bg;
    private ImageView iv_tick_bg;
    private TextView tv_pm25_inside_value;
    private TextView tv_pm25_inside_level;
    private ImageView fc_iv_tvoc_level;

    private WeatherView weatherView;

    private TextView tv_pm25_outside_value;
    private TextView tv_temp_outside_value;
    private TextView tv_hum_outside_value;


    private TextView tv_temp_inside_value;
    private TextView tv_hum_inside_value;
    private TextView tv_co2_inside_value;

    private ImageView iv_floop;
    private ImageView iv_fair;

    private ImageView iv_speed;
    private ImageView iv_speed_plus;
    private ImageView iv_speed_minus;

    private ImageView fc_iv_power;
    private ImageView fc_iv_clean;
    private ImageView fc_iv_auto;
    private ImageView fc_iv_sleep;
    private ImageView fc_iv_fast;

    private boolean canRefrash;
    private boolean isActivity;
    private Handler mHandler;

    private DevEntity mDevice;
    private AirInfo mAirInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freshloops);

        setMenuEnable(false);

        init();
        initView();
        start();
        loadWeather();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        startMxRefreshTimer();
    }

    /**
     * 初始化数据
     */
    void init() {
        isActivity = true;
        canRefrash = true;
        mDevice = CurrentDevice.instance().curDevice;
        mAirInfo = mDevice.airInfo;
        if (mAirInfo == null) {
            mAirInfo = new AirInfo();
        }

        mHandler = new Handler();
    }

    /**
     * 初始化控件
     */
    void initView() {
        weatherView = new WeatherView(mActivity);
        tv_location = (TextView) findViewById(R.id.tv_location);
        tv_time = (TextView) findViewById(R.id.tv_time);

        iv_level_bg = (ImageView) findViewById(R.id.iv_level_bg);
        iv_tick_bg = (ImageView) findViewById(R.id.iv_tick_bg);

        tv_pm25_inside_value = (TextView) findViewById(R.id.tv_pm25_inside_value);
        tv_pm25_inside_level = (TextView) findViewById(R.id.tv_pm25_inside_level);

        fc_iv_tvoc_level = (ImageView) findViewById(R.id.fc_iv_tvoc_level);
        tv_pm25_outside_value = (TextView) findViewById(R.id.tv_pm25_outside_value);
        tv_temp_outside_value = (TextView) findViewById(R.id.tv_temp_outside_value);
        tv_hum_outside_value = (TextView) findViewById(R.id.tv_hum_outside_value);

        tv_temp_inside_value = (TextView) findViewById(R.id.tv_temp_inside_value);
        tv_hum_inside_value = (TextView) findViewById(R.id.tv_hum_inside_value);
        tv_co2_inside_value = (TextView) findViewById(R.id.tv_co2_inside_value);

        (iv_floop = (ImageView) findViewById(R.id.iv_floop)).setOnClickListener(this);
        (iv_fair = (ImageView) findViewById(R.id.iv_fair)).setOnClickListener(this);

        iv_speed = (ImageView) findViewById(R.id.iv_speed);
        (iv_speed_plus = (ImageView) findViewById(R.id.iv_speed_plus)).setOnClickListener(this);
        (iv_speed_minus = (ImageView) findViewById(R.id.iv_speed_minus)).setOnClickListener(this);

        (fc_iv_power = (ImageView) findViewById(R.id.fc_iv_power)).setOnClickListener(this);
        (fc_iv_clean = (ImageView) findViewById(R.id.fc_iv_clean)).setOnClickListener(this);
        (fc_iv_auto = (ImageView) findViewById(R.id.fc_iv_auto)).setOnClickListener(this);
        (fc_iv_sleep = (ImageView) findViewById(R.id.fc_iv_sleep)).setOnClickListener(this);
        (fc_iv_fast = (ImageView) findViewById(R.id.fc_iv_fast)).setOnClickListener(this);
    }

    /**
     * 开始执行
     */
    void start() {
        setData();
    }

    void loadWeather() {
        weatherView.load(new WeatherView.WeatherLoadListener() {
            @Override
            public void onSucess(QueryWeatherPair.RspQueryWeather.Weatherinfo weatherinfo) {
                if (!TextUtils.isEmpty(weatherinfo.city)) {
                    tv_location.setText(weatherinfo.city.replace("市", ""));
                }
                Calendar curCalendar = Calendar.getInstance();
                int curH = curCalendar.get(Calendar.HOUR_OF_DAY);
                int curMin = curCalendar.get(Calendar.MINUTE);
                String min = String.format("%02d", curMin);
                if (min.length() == 1) {
                    min = "0" + min;
                }
                tv_time.setText("今天" + String.format("%02d", curH) + ":" + min);
                int pm25 = 0;
                try {
                    pm25 = Integer.parseInt(weatherinfo.pm25);
                } catch (NumberFormatException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                String level = "";
                if (pm25 < 50) {
                    level = "优";
                } else if (pm25 >= 50 && pm25 < 150) {
                    level = "良";
                } else if (pm25 >= 150 && pm25 < 210) {
                    level = "中";
                } else {
                    level = "差";
                }
                tv_pm25_outside_value.setText(pm25 + level);
                tv_temp_outside_value.setText(weatherinfo.temp + "℃");
                tv_hum_outside_value.setText(weatherinfo.humidity + "%");
            }
        });
    }

    private void setData() {
        if (mAirInfo != null) {
            int airValue = mAirInfo.getAirValue();
            // pm2.5值 --
            int airPm25 = airValue;

            // 显示PM2.5数值
            if (mAirInfo.getOnoff() == AirConstant.UNABLE) {
                // 关机状态下
                tv_pm25_inside_value.setText("--");
            } else {
                // 开机状态下
                tv_pm25_inside_value.setText(airPm25+"");
            }

            // 空气质量状态
            int airlevel =  mAirInfo.getAirLevel();//空气质量等级
            if (mAirInfo.getOnoff() == AirConstant.UNABLE) {
                // 关机状态下
                tv_pm25_inside_level.setText("");
            } else {
                // 开机状态下
                if (airlevel == AirConstant.AIR_QUALITY.LEVEL1) {
                    tv_pm25_inside_level.setText("优");
                    iv_level_bg.setImageResource(R.drawable.ic_level_green);
                } else if (airlevel == AirConstant.AIR_QUALITY.LEVEL2) {
                    tv_pm25_inside_level.setText("良");
                    iv_level_bg.setImageResource(R.drawable.ic_level_orenge);
                } else if (airlevel == AirConstant.AIR_QUALITY.LEVEL3) {
                    tv_pm25_inside_level.setText("中");
                    iv_level_bg.setImageResource(R.drawable.ic_level_blue);
                } else if (airlevel == AirConstant.AIR_QUALITY.LEVEL4) {
                    tv_pm25_inside_level.setText("差");
                    iv_level_bg.setImageResource(R.drawable.ic_level_red);
                }else {
                    tv_pm25_inside_level.setText("优");
                    iv_level_bg.setImageResource(R.drawable.ic_level_green);
                }
            }

            //指针刻度
            int index = (int) (airPm25 / 2.55);
            int resource_ID = getResources().getIdentifier("ic_tick" + index, "drawable", "com.broadlink.beiangair");
            if (resource_ID != 0) {
                Drawable drawable = getResources().getDrawable(resource_ID);
                iv_tick_bg.setImageDrawable(drawable);
            }

            // TVOC
            if (mAirInfo.getOnoff() == AirConstant.UNABLE) {
                //关机状态下
                fc_iv_tvoc_level.setImageResource(R.drawable.ic_tvoc_level_off);
            } else {
                //开机状态下
                int tvoc = mAirInfo.getAirTvoc();
                switch (tvoc) {
                    case 1:
                        fc_iv_tvoc_level.setImageResource(R.drawable.ic_tvoc_level_1);
                        break;
                    case 2:
                        fc_iv_tvoc_level.setImageResource(R.drawable.ic_tvoc_level_2);
                        break;
                    case 3:
                        fc_iv_tvoc_level.setImageResource(R.drawable.ic_tvoc_level_3);
                        break;
                    case 4:
                        fc_iv_tvoc_level.setImageResource(R.drawable.ic_tvoc_level_4);
                        break;
                    default:
                        break;
                }
            }

            // 温度
            if (mAirInfo.getOnoff() == AirConstant.UNABLE) {
                // 关机状态下
                tv_temp_inside_value.setText("0");
            } else {
                // 开机状态下
                int temp = mAirInfo.getTem();
                tv_temp_inside_value.setText(temp + "℃");
            }

            // 湿度
            if (mAirInfo.getOnoff() == AirConstant.UNABLE) {
                // 关机状态下
                tv_hum_inside_value.setText("0");
            } else {
                // 开机状态下
                int hum = mAirInfo.getHum();
                tv_hum_inside_value.setText(hum + "%");
            }

            // CO2
            if (mAirInfo.getOnoff() == AirConstant.UNABLE) {
                // 关机状态下
                tv_co2_inside_value.setText("0");
            } else {
                // 开机状态下
                int co2 = mAirInfo.getCo2Levle();
                tv_co2_inside_value.setText(co2 + "ppm");
            }

            // 设置按钮状态
            if (mAirInfo.getOnoff() == AirConstant.UNABLE) {
                // 关机状态下
                iv_speed.setImageResource(R.drawable.ic_speed_f0);
                iv_floop.setImageResource(R.drawable.ic_floop0);
                iv_fair.setImageResource(R.drawable.ic_fair0);
                iv_speed.setImageResource(R.drawable.ic_speed_f0);
                fc_iv_clean.setImageResource(R.drawable.ic_fclean0);
                fc_iv_power.setImageResource(R.drawable.ic_fon0);
                fc_iv_auto.setImageResource(R.drawable.ic_fauto0);
                fc_iv_sleep.setImageResource(R.drawable.ic_fsleep0);
                fc_iv_fast.setImageResource(R.drawable.ic_fast0);
            } else {
                // 开机状态下
                fc_iv_power.setImageResource(R.drawable.ic_fon1);

                // 手自动状态
                if (mAirInfo.getIsAuto() == AirConstant.MODE.AUTO) {
                    fc_iv_auto.setImageResource(R.drawable.ic_fauto1);
                } else {
                    fc_iv_auto.setImageResource(R.drawable.ic_fauto0);
                }

                // 睡眠状态
                if (mAirInfo.getSleep() == AirConstant.SLEEP.ON) {
                    fc_iv_sleep.setImageResource(R.drawable.ic_fsleep1);
                } else {
                    fc_iv_sleep.setImageResource(R.drawable.ic_fsleep0);
                }

                //清洁
                if (mAirInfo.getErr() == 1) {
                    fc_iv_clean.setImageResource(R.drawable.ic_fclean1);
                } else {
                    fc_iv_clean.setImageResource(R.drawable.ic_fclean0);
                }

                //新风 / 内循环
                int floopair = mAirInfo.getFreshloop();
                if (0 == floopair) {
                    //内循环
                    iv_floop.setImageResource(R.drawable.ic_floop1);
                    iv_fair.setImageResource(R.drawable.ic_fair0);
                }
                if (1 == floopair) {
                    //新风
                    iv_floop.setImageResource(R.drawable.ic_floop0);
                    iv_fair.setImageResource(R.drawable.ic_fair1);
                }

                //风速
                int speed = mAirInfo.getPosition();
                switch (speed) {
                    case AirConstant.WIND.LOW:
                        iv_speed.setImageResource(R.drawable.ic_speed_f1);
                        break;
                    case AirConstant.WIND.MID:
                        iv_speed.setImageResource(R.drawable.ic_speed_f2);
                        break;
                    case AirConstant.WIND.HIGH:
                        iv_speed.setImageResource(R.drawable.ic_speed_f3);
                        break;
                    case AirConstant.WIND.FIVE:
                        //急速
                        fc_iv_fast.setImageResource(R.drawable.ic_fast1);
                        break;
                }

            }
        }
    }

    /**************
     * 定时器
     */
    private void startMxRefreshTimer() {
        mHandler.postDelayed(task, 4500);
    }

    private void stopMxRefreshTimer() {
        canRefrash = false;
        mHandler.removeCallbacks(task);
    }

    private Runnable task = new Runnable() {
        public void run() {
            if (canRefrash && isActivity) {
                queryMXData();
            }
        }
    };

    private void queryMXData() {
        LogUtil.i("queryMXData()");
        BsOperationHub.instance().queryDevStatus(mDevice.devId, "beiang_status", new ReqCbk<RspMsgBase>() {
            @Override
            public void onSuccess(RspMsgBase rspData) {
                // TODO Auto-generated method stub
                if (canRefrash) {
                    if (rspData.isSuccess()) {
                        AirInfo airInfo = CurrentDevice.instance().queryDevice.airInfo;
                        if (airInfo != null) {
                            mAirInfo = airInfo;
                            LogUtil.i("setData()");
                            setData();
                        }
                    }
                    canRefrash = true;
                    startMxRefreshTimer();
                }
            }

            @Override
            public void onFailure(ErrorObject err) {
                // TODO Auto-generated method stub
                if (canRefrash) {
                    canRefrash = true;
                    startMxRefreshTimer();
                }
            }

        });
    }

    private void controlBeiAngAir(byte[] data) {
        LogUtil.i(data);
        setData();
        if (!canRefrash) {
            return;
        }
        stopMxRefreshTimer();

        BsOperationHub.instance().sendCtrlCmd(mDevice.devId, data, new ReqCbk<RspMsgBase>() {
            @Override
            public void onSuccess(RspMsgBase rspData) {
                // TODO Auto-generated method stub
                if (rspData.isSuccess()) {
                    CommandPair.RspCommand rsp = (CommandPair.RspCommand) rspData;
                    if (rsp.reply != null) {
                        AirInfo airInfo = EParse.parseEairByte(rsp.reply);
                        mAirInfo = airInfo;
                        setData();
                    }
                }
                canRefrash = true;
                startMxRefreshTimer();
            }

            @Override
            public void onFailure(ErrorObject err) {
                // TODO Auto-generated method stub
                canRefrash = true;
                startMxRefreshTimer();
            }
        });
    }

    /***
     * OnClickListener
     */
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.fc_iv_power:
                if (mAirInfo.getOnoff() == AirConstant.ENABLE) {
                    mAirInfo.setOnoff(AirConstant.UNABLE);
                } else {
                    mAirInfo.setOnoff(AirConstant.ENABLE);
                }
                break;
            case R.id.fc_iv_auto:
                if (mAirInfo.getOnoff() == AirConstant.UNABLE) {
                    Toast.show(mActivity, getString(R.string.poweroff_hint));
                    return;
                }
                if (mAirInfo.getIsAuto() == AirConstant.MODE.HAND) {
                    mAirInfo.setIsAuto(AirConstant.MODE.AUTO);
                } else {
                    mAirInfo.setIsAuto(AirConstant.MODE.HAND);
                }
                break;
            case R.id.fc_iv_sleep:
                if (mAirInfo.getOnoff() == AirConstant.UNABLE) {
                    Toast.show(mActivity, getString(R.string.poweroff_hint));
                    return;
                }
                if (mAirInfo.getSleep() == AirConstant.SLEEP.ON) {
                    mAirInfo.setSleep(AirConstant.SLEEP.OFF);
                } else {
                    mAirInfo.setSleep(AirConstant.SLEEP.ON);
                }
                break;
            case R.id.iv_speed_plus:
                int speedUp = mAirInfo.getPosition();
                if (speedUp < AirConstant.WIND.HIGH) {
                    speedUp = speedUp + 1;
                }
                mAirInfo.setPosition(speedUp);
                break;
            case R.id.iv_speed_minus:
                int speedDown = mAirInfo.getPosition();
                if (speedDown > AirConstant.WIND.LOW) {
                    speedDown = speedDown - 1;
                }
                mAirInfo.setPosition(speedDown);
                break;
            case R.id.fc_iv_fast:
                //急速
                mAirInfo.setPosition(AirConstant.WIND.FIVE);
                break;
            case R.id.iv_floop:
                //内循环
                mAirInfo.setFreshloop(0);
                break;
            case R.id.iv_fair:
                //新风
                mAirInfo.setFreshloop(1);
                break;
            case R.id.fc_iv_clean:
                if (1 == mAirInfo.getErr()) {
                    // 需要清洁复位
                    mAirInfo.setClean(245);
                }
                break;
            default:
                break;
        }
        controlBeiAngAir(EParse.parseEairInfo(mAirInfo));
    }

    @Override
    protected void onPause() {
        super.onPause();
        canRefrash = false;
        stopMxRefreshTimer();
    }

    protected void onDestroy() {
        // TODO Auto-generated method stub
        isActivity = false;
        super.onDestroy();
    }

}
