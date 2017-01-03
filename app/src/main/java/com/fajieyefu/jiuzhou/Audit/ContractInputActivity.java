package com.fajieyefu.jiuzhou.Audit;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fajieyefu.jiuzhou.Bean.BaseActivity;
import com.fajieyefu.jiuzhou.Bean.WebService;
import com.fajieyefu.jiuzhou.R;
import com.fajieyefu.jiuzhou.activity.YewuActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Fajieyefu on 2016/7/18.
 */
public class ContractInputActivity extends BaseActivity implements View.OnClickListener {
    //基本信息
    private EditText buyer_edit,sign_date_edit,sign_address_edit,tractor_edit,traction_pin_edit,traction_seat_edit,heightfromground_edit;
    private RadioGroup power_type_radio;
    //车辆类型
    private Spinner vehicle_type_spinner,length_out_spinner,measure_length_out_spinner;
    private EditText type_name_edit;
    private RadioGroup radioGroup;
    //外部尺寸
    private ArrayAdapter<String> vehicle_type_adapter,length_out_adapter,measure_length_out_adapter;
    private ArrayList<String> vehicle_type_list,length_out_list,measure_length_out_list;
    private Spinner width_out_spinner,measure_width_out_spinner,height_out_spinner,measure_height_out_spinner;
    private ArrayAdapter<String> width_out_adapter,measure_width_out_adapter,height_out_adapter,measure_height_out_adapter;
    private ArrayList<String> width_out_list,measure_width_out_list,height_out_list,measure_height_out_list;

    //内部尺寸
    private Spinner length_in_spinner,measure_length_in_spinner;
    private ArrayAdapter<String>length_in_adapter,measure_length_in_adapter;
    private ArrayList<String> length_in_list,measure_length_in_list;
    private Spinner width_in_spinner,measure_width_in_spinner,height_in_spinner,measure_height_in_spinner;
    private ArrayAdapter<String> width_in_adapter,measure_width_in_adapter,height_in_adapter,measure_height_in_adapter;
    private ArrayList<String> width_in_list,measure_width_in_list,height_in_list,measure_height_in_list;
    //纵梁
    private Spinner upper_wing_plate_spinner,lower_wing_plate_spinner,vertical_plate_spinner,stringer_height_spinner;
    private ArrayList<String> upper_wing_plate_list,lower_wing_plate_list,vertical_plate_list,stringer_height_list;
    private ArrayAdapter<String> upper_wing_plate_adapter,lower_wing_plate_adapter,vertical_plate_adapter,stringer_height_adapter;
    private RadioGroup lower_bending_radiogroup,punch_radiogroup;
    //厢底
    private Spinner box_bottom_thickness_spinner,box_bottom_style_spinner;
    private ArrayList<String> box_bottom_thickness_list,box_bottom_style_list;
    private ArrayAdapter<String> box_bottom_thickness_adapter,box_bottom_style_adapter;
    private RadioGroup waterproof_tank_radiogroup;
    //穿梁
    private Spinner middle_beam_material_spinner,middle_beam_Style_spinner,middle_beam_num_spinner;
    private ArrayList<String>  middle_beam_material_list,middle_beam_Style_list,middle_beam_num_list;
    private  ArrayAdapter<String> middle_beam_material_adapter,middle_beam_Style_adapter,middle_beam_num_adapter;
    private RadioGroup middle_beam_punch_radiogroup;
    //边梁
    private Spinner side_beam_material_spinner;
    private ArrayList<String> side_beam_material_list;
    private ArrayAdapter<String> side_beam_material_adapter;
    //厢板
    private Spinner xiangban_style_spinner,xiangban_side_spinner,xiangban_num_spinner;
    private ArrayAdapter<String> xiangban_style_adapter,xiangban_side_adapter,xiangban_num_adapter;
    private ArrayList<String> xiangban_style_list,xiangban_side_list,xiangban_num_list;
    //车厢（左右）
    private Spinner box_size_spinner,box_style_spinner;
    private ArrayAdapter<String> box_size_adapter,box_style_adapter;
    private ArrayList<String> box_size_list,box_style_list;
    //花栏（左、右）
    private Spinner hualan_side_spinner,hualan_side_num_spinner;
    private ArrayAdapter<String> hualan_side_adapter,hualan_side_num_adapter;
    private ArrayList<String> hualan_side_list,hualan_side_num_list;
    private RadioGroup tube_style_radiogroup;
    //站柱形式
    private Spinner zhanzhu_style_spinner;
    private ArrayAdapter<String> zhanzhu_style_adapter;
    private ArrayList<String> zhanzhu_style_list;
    //锁杆
    private RadioGroup suogan_radiogroup;
    //后门样式
    private Spinner backdoor_style_spinner;
    private ArrayAdapter<String> backdoor_style_adapter;
    private ArrayList<String> backdoor_style_list;
    //助推器
    private Spinner booster_spinner;
    private ArrayAdapter<String> booster_adapter;
    private ArrayList<String> booster_list;
    //龙门架
    private Spinner longmenjia_style_spinner;
    private ArrayAdapter<String> longmenjia_style_adapter;
    private ArrayList<String> longmenjia_style_list;
    private Spinner pengbukuang_num_spinner;
    private ArrayAdapter<String> pengbukuang_num_adapter;
    private ArrayList<String> pengbukuang_num_list;
    private EditText pengbu_size_edit;
    private RadioGroup ladder_radiogroup;
    //拔台
    private Spinner ba_tai_spinner;
    private ArrayAdapter<String> ba_tai_adapter;
    private ArrayList<String> ba_tai_list;
    //拉撑
    private Spinner la_cheng_spinner;
    private ArrayAdapter<String> la_cheng_adapter;
    private ArrayList<String> la_cheng_list;
    //蓬杆
    private Spinner  peng_gan_num_spinner;
    private ArrayAdapter<String>  peng_gan_num_adapter;
    private ArrayList<String>  peng_gan_num_list;
    private Spinner  anzhuang_style_spinner;
    private ArrayAdapter<String>  anzhuang_style_adapter;
    private ArrayList<String>  anzhuang_style_list;
    //工具箱
    private Spinner  kit_left_spinner;
    private ArrayAdapter<String>  kit_left_adapter;
    private ArrayList<String>  kit_left_list;
    private Spinner  kit_right_spinner;
    private ArrayAdapter<String>  kit_right_adapter;
    private ArrayList<String>  kit_right_list;
    //紧绳器
    private Spinner  rope_device_num_spinner;
    private ArrayAdapter<String>  rope_device_num_adapter;
    private ArrayList<String>  rope_device_num_list;
    private Spinner  rope_device_position_spinner;
    private ArrayAdapter<String>  rope_device_position_adapter;
    private ArrayList<String>  rope_device_position_list;
    //备胎支架 spare_tire_num
    private Spinner  spare_tire_num_spinner;
    private ArrayAdapter<String>  spare_tire_num_adapter;
    private ArrayList<String>  spare_tire_num_list;
    private Spinner  spare_tire_position_spinner;
    private ArrayAdapter<String>  spare_tire_position_adapter;
    private ArrayList<String>  spare_tire_position_list;
    //升降器 elevator_num
    private Spinner  elevator_num_spinner;
    private ArrayAdapter<String>  elevator_num_adapter;
    private ArrayList<String>  elevator_num_list;
    //水包 water_bag
    private Spinner  water_bag_spinner;
    private ArrayAdapter<String>  water_bag_adapter;
    private ArrayList<String>  water_bag_list;
    //水包空 water_bag_space
    private Spinner  water_bag_space_spinner;
    private ArrayAdapter<String>  water_bag_space_adapter;
    private ArrayList<String>  water_bag_space_list;
    //车桥 axle
    private Spinner  axle_spinner;
    private ArrayAdapter<String>  axle_adapter;
    private ArrayList<String>  axle_list;
    //钢板 steel_plate
    private Spinner  steel_plate_spinner;
    private ArrayAdapter<String> steel_plate_adapter;
    private ArrayList<String>  steel_plate_list;
    //轴距 wheelbase
    private Spinner  wheelbase_spinner;
    private ArrayAdapter<String> wheelbase_adapter;
    private ArrayList<String>  wheelbase_list;
    //刹车气室  brake_air_chamber
    private Spinner  brake_air_chamber_spinner;
    private ArrayAdapter<String> brake_air_chamber_adapter;
    private ArrayList<String>  brake_air_chamber_list;
    //轮胎 tyre
    private Spinner tyre_spinner;
    private ArrayAdapter<String> tyre_adapter;
    private ArrayList<String>  tyre_list;
    // 钢圈 steel_ring
    private Spinner steel_ring_spinner;
    private ArrayAdapter<String> steel_ring_adapter;
    private ArrayList<String>  steel_ring_list;
    //ABS
    private Spinner abs_spinner;
    private ArrayAdapter<String> abs_adapter;
    private ArrayList<String>  abs_list;
    //车身颜色
    private Spinner body_color_up_spinner;
    private ArrayAdapter<String> body_color_up_adapter;
    private ArrayList<String>  body_color_up_list;
    private Spinner body_color_bottom_spinner;
    private ArrayAdapter<String> body_color_bottom_adapter;
    private ArrayList<String>  body_color_bottom_list;
    //悬挂 suspension
    private Spinner suspension_spinner;
    private ArrayAdapter<String> suspension_adapter;
    private ArrayList<String>  suspension_list;
    //其他要求 another_quest
    private EditText another_quest_edit;
    //二、手续要求
    private EditText model_edit,tonnage_edit,plate_num_edit,axis_number_edit,color_edit,steel_edit;
    //三、
    private EditText num_edit,price_edit,total_amount_edit,shoufu_edit;
    //提货时间、提货方式
    private EditText extra_days_edit,delivery_mode_edit;

    private JSONObject jsonObject,jsonObject_temp;
    private Handler handler = new Handler();

    private Button submit;
    private int mYear,mMonth,mDay;
    private String sign_date;
    private String account,user_man_name;
    private SharedPreferences pref;
    private Calendar c;
    private TextView titleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.contractinput);
        pref=ContractInputActivity.this.getSharedPreferences("userInfo",MODE_PRIVATE);
        account=pref.getString("account","");
        user_man_name=pref.getString("username","");
        //初始化控件
        initWeight();
        //创建适配器
        initAdapter();
        //从服务器获取数据
        getInfoFromServlet();
        submit.setOnClickListener(this);
    }
    private void getInfoFromServlet() {
        new Thread(new Runnable() {
            @Override
            public void run() {
               String info= WebService.getSpinnerInfo();
                System.out.println(info);
                try {
                    jsonObject= new JSONObject(info);
                    manageJsonInfo("vehicle_type",vehicle_type_list,vehicle_type_adapter,vehicle_type_spinner);
                    manageJsonInfo("D2_1",length_out_list,length_out_adapter,length_out_spinner);//外部尺寸长度
                    manageJsonInfo("D2_2",measure_length_out_list,measure_length_out_adapter,measure_length_out_spinner);//测量外部尺寸长度测量范围
                    manageJsonInfo("D2_3",width_out_list,width_out_adapter,width_out_spinner);//外部尺寸宽度
                    manageJsonInfo("D2_4",measure_width_out_list,measure_width_out_adapter,measure_width_out_spinner);//测量外部尺寸宽度测量范围
                    manageJsonInfo("D2_5",height_out_list,height_out_adapter,height_out_spinner);//外部尺寸高度
                    manageJsonInfo("D2_6",measure_height_out_list,measure_height_out_adapter,measure_height_out_spinner);//测量外部尺寸高度测量范围
                    manageJsonInfo("D2_7",length_in_list,length_in_adapter,length_in_spinner);//内部尺寸长度
                    manageJsonInfo("D2_8",measure_length_in_list,measure_length_in_adapter,measure_length_in_spinner);//测量内部尺寸长度测量范围
                    manageJsonInfo("D2_9",width_in_list,width_in_adapter,width_in_spinner);//内部尺寸宽度
                    manageJsonInfo("D2_10",measure_width_in_list,measure_width_in_adapter,measure_width_in_spinner);//测量内部尺寸宽度测量范围
                    manageJsonInfo("D2_11",height_in_list,height_in_adapter,height_in_spinner);//内部尺寸高度
                    manageJsonInfo("D2_12",measure_height_in_list,measure_height_in_adapter,measure_height_in_spinner);//测量内部尺寸高度测量范围
                    manageJsonInfo("D3_3",upper_wing_plate_list,upper_wing_plate_adapter,upper_wing_plate_spinner);//上翼板
                    manageJsonInfo("D3_4",lower_wing_plate_list,lower_wing_plate_adapter,lower_wing_plate_spinner);//下翼板
                    manageJsonInfo("D3_5",vertical_plate_list,vertical_plate_adapter,vertical_plate_spinner);//立板
                    manageJsonInfo("D3_6",stringer_height_list,stringer_height_adapter,stringer_height_spinner);
                    manageJsonInfo("D4_1",box_bottom_thickness_list,box_bottom_thickness_adapter,box_bottom_thickness_spinner);//厢底厚度
                    manageJsonInfo("D4_2",box_bottom_style_list,box_bottom_style_adapter,box_bottom_style_spinner);//厢底样式
                    manageJsonInfo("D5_1",middle_beam_material_list,middle_beam_material_adapter,middle_beam_material_spinner);//穿梁材质
                    manageJsonInfo("D5_2",middle_beam_Style_list,middle_beam_Style_adapter,middle_beam_Style_spinner);//穿梁形式
                    manageJsonInfo("D5_3",middle_beam_num_list,middle_beam_num_adapter,middle_beam_num_spinner);///穿梁数量
                    manageJsonInfo("D6_1",side_beam_material_list,side_beam_material_adapter,side_beam_material_spinner);//边梁材质
                    manageJsonInfo("D7_1",xiangban_style_list,xiangban_style_adapter,xiangban_style_spinner);//厢板板芯样式
                    manageJsonInfo("D7_2",xiangban_side_list,xiangban_side_adapter,xiangban_side_spinner);//厢板
                    manageJsonInfo("D7_3",xiangban_num_list,xiangban_num_adapter,xiangban_num_spinner);//厢板页数
                    manageJsonInfo("D8_1",box_size_list,box_size_adapter,box_size_spinner);//车厢尺寸
                    manageJsonInfo("D8_2",box_style_list,box_style_adapter,box_style_spinner);//车厢样式
                    manageJsonInfo("D8_3",hualan_side_list,hualan_side_adapter,hualan_side_spinner);//花栏边框
                    manageJsonInfo("D8_4",hualan_side_num_list,hualan_side_num_adapter,hualan_side_num_spinner);//花栏坚撑数量
                    manageJsonInfo("D9_1",zhanzhu_style_list,zhanzhu_style_adapter,zhanzhu_style_spinner);//站柱形式
                    manageJsonInfo("D10_1",backdoor_style_list,backdoor_style_adapter,backdoor_style_spinner);//后门样式
                    manageJsonInfo("D10_2",booster_list,booster_adapter,booster_spinner);//助推器
                    manageJsonInfo("D11_1",longmenjia_style_list,longmenjia_style_adapter,longmenjia_style_spinner);//龙门架
                    manageJsonInfo("D11_2",pengbukuang_num_list,pengbukuang_num_adapter,pengbukuang_num_spinner);//篷布框
                    manageJsonInfo("D12_1",ba_tai_list,ba_tai_adapter,ba_tai_spinner);//拔台
                    manageJsonInfo("D12_2",la_cheng_list,la_cheng_adapter,la_cheng_spinner);//拉撑
                    manageJsonInfo("D12_3",peng_gan_num_list,peng_gan_num_adapter,peng_gan_num_spinner);//篷杆数量
                    manageJsonInfo("D12_4",anzhuang_style_list,anzhuang_style_adapter,anzhuang_style_spinner);//安装方式
                    manageJsonInfo("D13_1",kit_left_list,kit_left_adapter,kit_left_spinner);//工具箱左边
                    manageJsonInfo("D13_3",kit_right_list,kit_right_adapter,kit_right_spinner);//工具箱右边
                    manageJsonInfo("D13_2",rope_device_num_list,rope_device_num_adapter,rope_device_num_spinner);//紧绳器数量
                    manageJsonInfo("D13_4",rope_device_position_list,rope_device_position_adapter,rope_device_position_spinner);//紧绳器安装位置
                    manageJsonInfo("D14_1",spare_tire_num_list,spare_tire_num_adapter,spare_tire_num_spinner);//备胎支架数量
                    manageJsonInfo("D14_2",spare_tire_position_list,spare_tire_position_adapter,spare_tire_position_spinner);//备胎支架安装位置
                    manageJsonInfo("D14_3",elevator_num_list,elevator_num_adapter,elevator_num_spinner);//升降器数量
                    manageJsonInfo("D15_1",water_bag_list,water_bag_adapter,water_bag_spinner);//水包
                    manageJsonInfo("D15_2",water_bag_space_list,water_bag_space_adapter,water_bag_space_spinner);//水包空
                    manageJsonInfo("D16_1",axle_list,axle_adapter,axle_spinner);//车桥
                    manageJsonInfo("D16_2",steel_plate_list,steel_plate_adapter,steel_plate_spinner);//钢板
                    manageJsonInfo("D17_1",wheelbase_list,wheelbase_adapter,wheelbase_spinner);//轴距
                    manageJsonInfo("D17_2",brake_air_chamber_list,brake_air_chamber_adapter,brake_air_chamber_spinner);//刹车气室
                    manageJsonInfo("D18_1",tyre_list,tyre_adapter,tyre_spinner);//轮胎
                    manageJsonInfo("D18_2",steel_ring_list,steel_ring_adapter,steel_ring_spinner);//钢圈
                    manageJsonInfo("D19_1",abs_list,abs_adapter,abs_spinner);//ABS
                    manageJsonInfo("D19_2",body_color_up_list,body_color_up_adapter,body_color_up_spinner);//车身颜色上
                    manageJsonInfo("D19_3",body_color_bottom_list,body_color_bottom_adapter,body_color_bottom_spinner);//车身颜色下
                    manageJsonInfo("D20_1",suspension_list,suspension_adapter,suspension_spinner);//悬挂

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void manageJsonInfo(final String name, final ArrayList<String> list, final ArrayAdapter<String> adapter, final Spinner spinner) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    jsonObject_temp= jsonObject.getJSONObject(name);
                    int len=jsonObject_temp.length();
                    int i=1;
                    while (i<=len){
                        list.add(jsonObject_temp.getString(i+""));
                        i++;
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            spinner.setPrompt("请选择类型");
                            spinner.setAdapter(adapter);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }).start();


    }
    /**
     * 初始化控件
     */
    private void initWeight() {
        titleText= (TextView) findViewById(R.id.title_text);
        titleText.setText("销售合同录入");
        //基本信息
        buyer_edit= (EditText) findViewById(R.id.buyer);
        sign_address_edit=(EditText)findViewById(R.id.sign_address);
        sign_date_edit= (EditText) findViewById(R.id.sign_date);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long times=System.currentTimeMillis();
        String date = simpleDateFormat.format(times);
        sign_date_edit.setText(date);
        c=Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        sign_date_edit.setInputType(InputType.TYPE_NULL);
        sign_date_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new DatePickerDialog(ContractInputActivity.this,mDateSetListener,mYear,mMonth,mDay);
                dialog.show();
            }
        });

        tractor_edit= (EditText) findViewById(R.id.tractor);
        traction_pin_edit= (EditText) findViewById(R.id.traction_pin);
        traction_seat_edit= (EditText) findViewById(R.id.traction_seat);
        heightfromground_edit= (EditText) findViewById(R.id.heightfromground);
        power_type_radio= (RadioGroup) findViewById(R.id.power_type);
        //车辆类型
        type_name_edit= (EditText) findViewById(R.id.type_name);
        vehicle_type_spinner= (Spinner) findViewById(R.id.vehicle_type);
        radioGroup= (RadioGroup) findViewById(R.id.plan);
        //外部尺寸
        length_out_spinner= (Spinner) findViewById(R.id.length_out);
        measure_length_out_spinner= (Spinner) findViewById(R.id.measure_length_out);
        width_out_spinner= (Spinner) findViewById(R.id.width_out);
        measure_width_out_spinner= (Spinner) findViewById(R.id.measure_width_out);
        height_out_spinner= (Spinner) findViewById(R.id.height_out);
        measure_height_out_spinner= (Spinner) findViewById(R.id.measure_height_out);
        //内部尺寸
        length_in_spinner= (Spinner) findViewById(R.id.length_in);
        measure_length_in_spinner= (Spinner) findViewById(R.id.measure_length_in);
        width_in_spinner= (Spinner) findViewById(R.id.width_in);
        measure_width_in_spinner= (Spinner) findViewById(R.id.measure_width_in);
        height_in_spinner= (Spinner) findViewById(R.id.height_in);
        measure_height_in_spinner= (Spinner) findViewById(R.id.measure_height_in);
        //纵梁
        upper_wing_plate_spinner= (Spinner) findViewById(R.id.upper_wing_plate);
        lower_wing_plate_spinner= (Spinner) findViewById(R.id.lower_wing_plate);
        vertical_plate_spinner= (Spinner) findViewById(R.id.vertical_plate);
        stringer_height_spinner= (Spinner) findViewById(R.id.stringer_height);
        lower_bending_radiogroup= (RadioGroup) findViewById(R.id.lower_bending);
        punch_radiogroup= (RadioGroup) findViewById(R.id.punch);

        //厢底
        box_bottom_thickness_spinner= (Spinner) findViewById(R.id.box_bottom_thickness);
        box_bottom_style_spinner= (Spinner) findViewById(R.id.box_bottom_style);
        waterproof_tank_radiogroup= (RadioGroup) findViewById(R.id.waterproof_tank);
        //穿梁
        middle_beam_material_spinner= (Spinner) findViewById(R.id.middle_beam_material);
        middle_beam_Style_spinner= (Spinner) findViewById(R.id.middle_beam_Style);
        middle_beam_num_spinner= (Spinner) findViewById(R.id.middle_beam_num);
        middle_beam_punch_radiogroup= (RadioGroup) findViewById(R.id.middle_beam_punch);
        //边梁
        side_beam_material_spinner= (Spinner) findViewById(R.id.side_beam_material);
        //厢板
        xiangban_style_spinner= (Spinner) findViewById(R.id.xiangban_style);
        xiangban_num_spinner= (Spinner) findViewById(R.id.xiangban_num);
        xiangban_side_spinner= (Spinner) findViewById(R.id.xiangban_side);
        //车厢（左右）
        box_size_spinner= (Spinner) findViewById(R.id.box_size);
        box_style_spinner= (Spinner) findViewById(R.id.box_style);
        //花栏（左、右）
        hualan_side_spinner= (Spinner) findViewById(R.id.hualan_side);
        hualan_side_num_spinner= (Spinner) findViewById(R.id.hualan_side_num);
        tube_style_radiogroup= (RadioGroup) findViewById(R.id.tube_style);
        //站柱形式
        zhanzhu_style_spinner= (Spinner) findViewById(R.id.zhanzhu_style);
        //锁杆
        suogan_radiogroup= (RadioGroup) findViewById(R.id.suogan);
        //后门样式
        backdoor_style_spinner= (Spinner) findViewById(R.id.backdoor_style);
        //助推器
        booster_spinner= (Spinner) findViewById(R.id.booster);
        //龙门架
        longmenjia_style_spinner= (Spinner) findViewById(R.id.longmenjia_style);
        pengbukuang_num_spinner= (Spinner) findViewById(R.id.pengbukuang_num);
        pengbu_size_edit= (EditText) findViewById(R.id.pengbu_size);
        ladder_radiogroup= (RadioGroup) findViewById(R.id.ladder);
        //拔台
        ba_tai_spinner= (Spinner) findViewById(R.id.ba_tai);
        //拉撑
        la_cheng_spinner= (Spinner) findViewById(R.id.la_cheng);
        //蓬杆
        peng_gan_num_spinner= (Spinner) findViewById(R.id.peng_gan_num);
        anzhuang_style_spinner= (Spinner) findViewById(R.id.anzhuang_style);
        //工具箱
        kit_left_spinner= (Spinner) findViewById(R.id.kit_left);
        kit_right_spinner= (Spinner) findViewById(R.id.kit_right);
        //紧绳器
        rope_device_num_spinner= (Spinner) findViewById(R.id.rope_device_num);
        rope_device_position_spinner= (Spinner) findViewById(R.id.rope_device_position);
        //备胎支架
        spare_tire_num_spinner= (Spinner) findViewById(R.id.spare_tire_num);
        spare_tire_position_spinner= (Spinner) findViewById(R.id.spare_tire_position);
        //升降器
        elevator_num_spinner= (Spinner) findViewById(R.id.elevator_num);
        //水包
        water_bag_spinner= (Spinner) findViewById(R.id.water_bag);
        //水包空
        water_bag_space_spinner= (Spinner) findViewById(R.id.water_bag_space);
        //车桥 axle
        axle_spinner= (Spinner) findViewById(R.id.axle);
        //钢板 steel_plate
        steel_plate_spinner= (Spinner) findViewById(R.id.steel_plate);
        //轴距 wheelbase
        wheelbase_spinner= (Spinner) findViewById(R.id.wheelbase);
        //刹车气室  brake_air_chamber
        brake_air_chamber_spinner= (Spinner) findViewById(R.id.brake_air_chamber);
        //轮胎 tyre
        tyre_spinner= (Spinner) findViewById(R.id.tyre);
        // 钢圈 steel_ring
        steel_ring_spinner= (Spinner) findViewById(R.id.steel_ring);
        //ABS
        abs_spinner= (Spinner) findViewById(R.id.abs);
        //车身颜色
        body_color_up_spinner= (Spinner) findViewById(R.id.body_color_up);
        body_color_bottom_spinner= (Spinner) findViewById(R.id.body_color_bottom);
        //悬挂 suspension
        suspension_spinner= (Spinner) findViewById(R.id.suspension);
        //其他要求
        another_quest_edit= (EditText) findViewById(R.id.another_quest);
        //手续要求
        model_edit= (EditText) findViewById(R.id.model);
        tonnage_edit= (EditText) findViewById(R.id.tonnage);
        plate_num_edit= (EditText) findViewById(R.id.plate_num);
        axis_number_edit= (EditText) findViewById(R.id.axis_number);
        color_edit= (EditText) findViewById(R.id.color);
        steel_edit= (EditText) findViewById(R.id.steel);
        //三、
        num_edit= (EditText) findViewById(R.id.num);
        price_edit= (EditText) findViewById(R.id.price);
        total_amount_edit= (EditText) findViewById(R.id.total_amount);
        shoufu_edit= (EditText) findViewById(R.id.shoufu);
        //提货时间、提货方式
        extra_days_edit= (EditText) findViewById(R.id.extra_days);
        delivery_mode_edit= (EditText) findViewById(R.id.delivery_mode);
        //提交按钮
        submit= (Button) findViewById(R.id.submit);
    }

    /**
     * 创建适配器
     */
    private void initAdapter() {
        vehicle_type_list= new ArrayList<String>();
        vehicle_type_adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, vehicle_type_list);
        //外部尺寸
        length_out_list=new ArrayList<String>();
        length_out_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,length_out_list);
        measure_length_out_list=new ArrayList<String>();
        measure_length_out_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,measure_length_out_list);
        width_out_list=new ArrayList<String>();
        width_out_adapter= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,width_out_list);
        measure_width_out_list=new ArrayList<String>();
        measure_width_out_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,measure_width_out_list);
        height_out_list=new ArrayList<String>();
        height_out_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,height_out_list);
        measure_height_out_list=new ArrayList<String>();
        measure_height_out_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,measure_height_out_list);
        //内部尺寸
        length_in_list=new ArrayList<String>();
        length_in_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,length_in_list);
        measure_length_in_list=new ArrayList<String>();
        measure_length_in_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,measure_length_in_list);
        width_in_list=new ArrayList<String>();
        width_in_adapter= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,width_in_list);
        measure_width_in_list=new ArrayList<String>();
        measure_width_in_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,measure_width_in_list);
        height_in_list=new ArrayList<String>();
        height_in_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,height_in_list);
        measure_height_in_list=new ArrayList<String>();
        measure_height_in_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,measure_height_in_list);
        //纵梁
        upper_wing_plate_list=new ArrayList<String>();
        upper_wing_plate_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,upper_wing_plate_list);
        lower_wing_plate_list=new ArrayList<String>();
        lower_wing_plate_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,lower_wing_plate_list);
        vertical_plate_list=new ArrayList<String>();
        vertical_plate_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,vertical_plate_list);
        stringer_height_list=new ArrayList<String>();
        stringer_height_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,stringer_height_list);
        //厢底
        box_bottom_thickness_list=new ArrayList<String>();
        box_bottom_thickness_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,box_bottom_thickness_list);
        box_bottom_style_list= new ArrayList<String>();
        box_bottom_style_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,box_bottom_style_list);
        //穿梁
        middle_beam_material_list=new ArrayList<String>();
        middle_beam_material_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,middle_beam_material_list);
        middle_beam_Style_list=new ArrayList<String>();
        middle_beam_Style_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,middle_beam_Style_list);
        middle_beam_num_list=new ArrayList<String>();
        middle_beam_num_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,middle_beam_num_list);
        //边梁
        side_beam_material_list= new ArrayList<String>();
        side_beam_material_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,side_beam_material_list);
        //厢板
        xiangban_style_list= new ArrayList<String>();
        xiangban_style_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,xiangban_style_list);
        xiangban_side_list= new ArrayList<String>();
        xiangban_side_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,xiangban_side_list);
        xiangban_num_list= new ArrayList<String>();
        xiangban_num_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,xiangban_num_list);
        //车厢（左右）
        box_size_list= new ArrayList<String>();
        box_size_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,box_size_list);
        box_style_list= new ArrayList<String>();
        box_style_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,box_style_list);
        //花栏（左、右）
        hualan_side_list=new ArrayList<String>();
        hualan_side_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,hualan_side_list);
        hualan_side_num_list=new ArrayList<String>();
        hualan_side_num_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,hualan_side_num_list);
        //站柱形式
        zhanzhu_style_list=new ArrayList<String>();
        zhanzhu_style_adapter= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,zhanzhu_style_list);
        //锁杆
        //后门样式
        backdoor_style_list=new ArrayList<String>();
        backdoor_style_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,backdoor_style_list);
        //助推器
        booster_list=new ArrayList<String>();
        booster_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,booster_list);
        //龙门架
        longmenjia_style_list=new ArrayList<String>();
        longmenjia_style_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,longmenjia_style_list);
        pengbukuang_num_list=new ArrayList<String>();
        pengbukuang_num_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,pengbukuang_num_list);
        //拔台
        ba_tai_list=new ArrayList<String>();
        ba_tai_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,ba_tai_list);
        //拉撑
        la_cheng_list=new ArrayList<String>();
        la_cheng_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,la_cheng_list);
        //蓬杆
        peng_gan_num_list=new ArrayList<String>();
        peng_gan_num_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,peng_gan_num_list);
        anzhuang_style_list=new ArrayList<String>();
        anzhuang_style_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,anzhuang_style_list);
        //工具箱
        kit_left_list=new ArrayList<String>();
        kit_left_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,kit_left_list);
        kit_right_list=new ArrayList<String>();
        kit_right_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,kit_right_list);
        //紧绳器
        rope_device_num_list=new ArrayList<String>();
        rope_device_num_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,rope_device_num_list);
        rope_device_position_list=new ArrayList<String>();
        rope_device_position_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,rope_device_position_list);
        //备胎支架
        spare_tire_num_list=new ArrayList<String>();
        spare_tire_num_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,spare_tire_num_list);
        spare_tire_position_list=new ArrayList<String>();
        spare_tire_position_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,spare_tire_position_list);
        //升降器
        elevator_num_list=new ArrayList<String>();
        elevator_num_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,elevator_num_list);
        //水包
        water_bag_list=new ArrayList<String>();
        water_bag_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,water_bag_list);
        //水包空
        water_bag_space_list=new ArrayList<String>();
        water_bag_space_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,water_bag_space_list);
        //车桥 axle
        axle_list=new ArrayList<String>();
        axle_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,axle_list);
        //钢板 steel_plate
        steel_plate_list=new ArrayList<String>();
        steel_plate_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,steel_plate_list);
        //轴距 wheelbase
        wheelbase_list=new ArrayList<String>();
        wheelbase_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,wheelbase_list);
        //刹车气室  brake_air_chamber
        brake_air_chamber_list=new ArrayList<String>();
        brake_air_chamber_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,brake_air_chamber_list);
        //轮胎 tyre
        tyre_list=new ArrayList<String>();
        tyre_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,tyre_list);
        // 钢圈 steel_ring
        steel_ring_list=new ArrayList<String>();
        steel_ring_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,steel_ring_list);
        //ABS
        abs_list=new ArrayList<String>();
        abs_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,abs_list);
        //车身颜色
        body_color_up_list=new ArrayList<String>();
        body_color_up_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,body_color_up_list);
        body_color_bottom_list=new ArrayList<String>();
        body_color_bottom_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,body_color_bottom_list);
        //悬挂 suspension
        suspension_list=new ArrayList<String>();
        suspension_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,suspension_list);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.submit:
                chooseDialog();
                break;
        }
    }
    private void chooseDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(ContractInputActivity.this);
        dialog.setTitle("提示：");
        dialog.setMessage("确认录入该合同信息吗？");
        dialog.setCancelable(false);
        dialog.setNegativeButton("取消",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.setPositiveButton("确认",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                makeContract();
            }
        });
        dialog.show();
    }
    private void makeContract() {

        if(TextUtils.isEmpty(buyer_edit.getText().toString())){
            Toast.makeText(ContractInputActivity.this, "请填写需方名称", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(sign_address_edit.getText().toString())){
            Toast.makeText(ContractInputActivity.this, "请填写签订地点", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(tractor_edit.getText().toString())){
            Toast.makeText(ContractInputActivity.this, "请填写牵引车头", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(tractor_edit.getText().toString())){
            Toast.makeText(ContractInputActivity.this, "请填写牵引车头", Toast.LENGTH_SHORT).show();
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                //用户信息
                String buyer_name = getItemString(buyer_edit);
                String sign_date = getItemString(sign_date_edit);
                String sign_address = getItemString(sign_address_edit);
                String tractor = getItemString(tractor_edit);
                String power_type = getItemString(power_type_radio);
                String traction_pin = getItemString(traction_pin_edit);
                String traction_seat = getItemString(traction_seat_edit);
                String heightfromground = getItemString(heightfromground_edit);
                //车辆类型
                String vehicle_type = getItemString(vehicle_type_spinner);
                String type_name = getItemString(type_name_edit);
                String plan=getItemString(radioGroup);
                //外部尺寸
                String length_out = getItemString(length_out_spinner);
                String measure_length_out = getItemString(measure_length_out_spinner);
                String width_out = getItemString(width_out_spinner);
                String measure_width_out = getItemString(measure_width_out_spinner);
                String height_out = getItemString(height_out_spinner);
                String measure_height_out = getItemString(measure_height_out_spinner);
                //内部尺寸
                String length_in = getItemString(length_in_spinner);
                String measure_length_in = getItemString(measure_length_in_spinner);
                String width_in = getItemString(width_in_spinner);
                String measure_width_in = getItemString(measure_width_in_spinner);
                String height_in = getItemString(height_in_spinner);
                String measure_height_in = getItemString(measure_height_in_spinner);
                //纵梁
                String upper_wing_plate = getItemString(upper_wing_plate_spinner);
                String lower_wing_plate = getItemString(lower_wing_plate_spinner);
                String vertical_plate = getItemString(vertical_plate_spinner);
                String stringer_height = getItemString(stringer_height_spinner);
                String lower_bending = getItemString(lower_bending_radiogroup);
                String punch = getItemString(punch_radiogroup);
                //厢底
                String box_bottom_thickness = getItemString(box_bottom_thickness_spinner);
                String box_bottom_style = getItemString(box_bottom_style_spinner);
                String waterproof_tank = getItemString(waterproof_tank_radiogroup);
                //穿梁
                String middle_beam_material = getItemString(middle_beam_material_spinner);
                String middle_beam_Style = getItemString(middle_beam_Style_spinner);
                String middle_beam_num = getItemString(middle_beam_num_spinner);
                String middle_beam_punch = getItemString(middle_beam_punch_radiogroup);
                //边梁
                String side_beam_material = getItemString(side_beam_material_spinner);
                //厢板
                String xiangban_style = getItemString(xiangban_style_spinner);
                String xiangban_side = getItemString(xiangban_side_spinner);
                String xiangban_num = getItemString(xiangban_num_spinner);
                //车厢（左右）
                String box_size = getItemString(box_size_spinner);
                String box_style = getItemString(box_style_spinner);
                //花栏（左、右）
                String hualan_side = getItemString(hualan_side_spinner);
                String hualan_side_num = getItemString(hualan_side_num_spinner);
                String tube_style = getItemString(tube_style_radiogroup);
                //站柱形式
                String zhanzhu_style = getItemString(zhanzhu_style_spinner);
                //锁杆
                String suogan = getItemString(suogan_radiogroup);
                //后门样式
                String backdoor_style = getItemString(backdoor_style_spinner);
                //助推器
                String booster = getItemString(booster_spinner);
                //龙门架
                String longmenjia_style = getItemString(longmenjia_style_spinner);
                String pengbukuang_num = getItemString(pengbukuang_num_spinner);
                String pengbu_size = getItemString(pengbu_size_edit);
                String ladder = getItemString(ladder_radiogroup);
                //拔台
                String ba_tai = getItemString(ba_tai_spinner);
                //拉撑
                String la_cheng = getItemString(la_cheng_spinner);
                //蓬杆
                String peng_gan_num = getItemString(peng_gan_num_spinner);
                String anzhuang_style = getItemString(anzhuang_style_spinner);
                //工具箱
                String kit_left = getItemString(kit_left_spinner);
                String kit_right = getItemString(kit_right_spinner);
                //紧绳器
                String rope_device_num = getItemString(rope_device_num_spinner);
                String rope_device_position = getItemString(rope_device_position_spinner);
                //备胎支架 spare_tire_num
                String spare_tire_num = getItemString(spare_tire_num_spinner);
                String spare_tire_position = getItemString(spare_tire_position_spinner);
                //升降器 elevator_num
                String elevator_num = getItemString(elevator_num_spinner);
                //水包 water_bag
                String water_bag = getItemString(water_bag_spinner);
                //水包空 water_bag_space
                String water_bag_space = getItemString(water_bag_space_spinner);
                //车桥 axle
                String axle = getItemString(axle_spinner);
                //钢板 steel_plate
                String steel_plate = getItemString(steel_plate_spinner);
                //轴距 wheelbase
                String wheelbase = getItemString(wheelbase_spinner);
                //刹车气室  brake_air_chamber
                String brake_air_chamber = getItemString(brake_air_chamber_spinner);
                //轮胎 tyre
                String tyre = getItemString(tyre_spinner);
                // 钢圈 steel_ring
                String steel_ring = getItemString(steel_ring_spinner);
                //ABS
                String abs = getItemString(abs_spinner);
                //车身颜色
                String body_color_up = getItemString(body_color_up_spinner);
                String body_color_bottom = getItemString(body_color_bottom_spinner);
                //悬挂 suspension
                String suspension = getItemString(suspension_spinner);
                //其他要求 another_quest
                String another_quest = getItemString(another_quest_edit);
                //二、手续要求
                String model = getItemString(model_edit);
                String tonnage = getItemString(tonnage_edit);
                String plate_num = getItemString(plate_num_edit);
                String axis_number = getItemString(axis_number_edit);
                String color = getItemString(color_edit);
                String steel = getItemString(steel_edit);
                //三、
                final String num = getItemString(num_edit);
                String price = getItemString(price_edit);
                String total_amount = getItemString(total_amount_edit);
                String shoufu = getItemString(shoufu_edit);
                //提货时间、提货方式
                String extra_days = getItemString(extra_days_edit);
                String delivery_mode = getItemString(delivery_mode_edit);

                Map<String, String> params = new HashMap<String, String>();
                params.put("account", account);
                params.put("user_man_name", user_man_name);
                params.put("buyer_name", buyer_name);
                params.put("sign_date", sign_date);
                params.put("sign_address", sign_address);
                params.put("tractor", tractor);
                params.put("power_type", power_type);
                params.put("traction_pin", traction_pin);
                params.put("traction_seat", traction_seat);
                params.put("heightfromground", heightfromground);
                params.put("vehicle_type", vehicle_type);
                params.put("type_name", type_name);
                params.put("plan",plan);
                params.put("length_out", length_out);
                params.put("measure_length_out", measure_length_out);
                params.put("width_out", width_out);
                params.put("measure_width_out", measure_width_out);
                params.put("height_out", height_out);
                params.put("measure_height_out", measure_height_out);
                params.put("length_in", length_in);
                params.put("measure_length_in", measure_length_in);
                params.put("width_in", width_in);
                params.put("measure_width_in", measure_width_in);
                params.put("height_in", height_in);
                params.put("measure_height_in", measure_height_in);
                params.put("upper_wing_plate", upper_wing_plate);
                params.put("lower_wing_plate", lower_wing_plate);
                params.put("vertical_plate", vertical_plate);
                params.put("stringer_height", stringer_height);
                params.put("lower_bending", lower_bending);
                params.put("punch", punch);
                params.put("box_bottom_thickness", box_bottom_thickness);
                params.put("box_bottom_style", box_bottom_style);
                params.put("waterproof_tank", waterproof_tank);
                params.put("middle_beam_material", middle_beam_material);
                params.put("middle_beam_Style", middle_beam_Style);
                params.put("middle_beam_num", middle_beam_num);
                params.put("middle_beam_punch", middle_beam_punch);
                params.put("side_beam_material", side_beam_material);
                params.put("xiangban_style", xiangban_style);
                params.put("xiangban_side", xiangban_side);
                params.put("xiangban_num", xiangban_num);
                params.put("box_size", box_size);
                params.put("box_style", box_style);
                params.put("hualan_side", hualan_side);
                params.put("hualan_side_num", hualan_side_num);
                params.put("tube_style", tube_style);
                params.put("zhanzhu_style", zhanzhu_style);
                params.put("suogan", suogan);
                params.put("backdoor_style", backdoor_style);
                params.put("booster", booster);
                params.put("longmenjia_style", longmenjia_style);
                params.put("pengbukuang_num", pengbukuang_num);
                params.put("pengbu_size", pengbu_size);
                params.put("ladder", ladder);
                params.put("ba_tai", ba_tai);
                params.put("la_cheng", la_cheng);
                params.put("peng_gan_num", peng_gan_num);
                params.put("anzhuang_style", anzhuang_style);
                params.put("kit_left", kit_left);
                params.put("kit_right", kit_right);
                params.put("rope_device_num", rope_device_num);
                params.put("rope_device_position", rope_device_position);
                params.put("spare_tire_num", spare_tire_num);
                params.put("spare_tire_position", spare_tire_position);
                params.put("elevator_num", elevator_num);
                params.put("water_bag", water_bag);
                params.put("water_bag_space", water_bag_space);
                params.put("axle", axle);
                params.put("steel_plate", steel_plate);
                params.put("wheelbase", wheelbase);
                params.put("brake_air_chamber", brake_air_chamber);
                params.put("tyre", tyre);
                params.put("steel_ring", steel_ring);
                params.put("abs", abs);
                params.put("body_color_up", body_color_up);
                params.put("body_color_bottom", body_color_bottom);
                params.put("suspension", suspension);
                params.put("another_quest", another_quest);
                params.put("model", model);
                params.put("tonnage", tonnage);
                params.put("plate_num", plate_num);
                params.put("axis_number", axis_number);
                params.put("color", color);
                params.put("steel", steel);
                params.put("num", num);
                params.put("price", price);
                params.put("total_amount", total_amount);
                params.put("shoufu", shoufu);
                params.put("extra_days", extra_days);
                params.put("delivery_mode", delivery_mode);
                final String info = WebService.makeContractInput(params);
                if (info != null&&!info.equals("fail")) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            final AlertDialog.Builder dialog = new AlertDialog.Builder(ContractInputActivity.this);
                            dialog.setTitle("合同信息录入成功！");
                            dialog.setMessage("合同单号:" + info + "申请人：" + account);
                            dialog.setCancelable(false);
                            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(ContractInputActivity.this, YewuActivity.class);
                                    startActivity(intent);
                                    ContractInputActivity.this.finish();
                                }
                            });
                            dialog.show();
                        }
                    });
                }else{
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ContractInputActivity.this, "奥奥！出现错误，请稍后尝试", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }} ).start();

    }

    private String getItemString(Spinner spinner) {
        try{
            String spinnerString=spinner.getSelectedItem().toString();
            return  spinnerString;
        }catch (Exception e){
            return "";
        }


    }

    private String getItemString(EditText editText) {
        String editString=editText.getText().toString();
        return editString;
    }

    private String getItemString(RadioGroup radioGroup) {
        String radioString=((RadioButton)ContractInputActivity.this.findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
        return  radioString;
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;

            updateDateDisplay();
        }
    };
    private void updateDateDisplay(){
        sign_date=new StringBuilder().append(mYear).append("-")
                .append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)).append("-")
                .append((mDay < 10) ? "0" + mDay : mDay).toString();
        sign_date_edit.setText(sign_date);
    }
    /**
     * 适配
     */
//    private void setAdapter() {
//        vehicle_type_spinner.setPrompt("请选择基本机型!");
//        vehicle_type_spinner.setAdapter(vehicle_type_adapter);
//        //外部尺寸
//        length_out_spinner.setAdapter(length_out_adapter);
//        measure_length_out_spinner.setAdapter(measure_length_out_adapter);
//        width_out_spinner.setAdapter(width_out_adapter);
//        measure_width_out_spinner.setAdapter(measure_width_out_adapter);
//        height_out_spinner.setAdapter(height_out_adapter);
//        measure_height_out_spinner.setAdapter(measure_height_out_adapter);
//        //内部尺寸
//        length_in_spinner.setAdapter(length_in_adapter);
//        measure_length_in_spinner.setAdapter(measure_length_in_adapter);
//        width_in_spinner.setAdapter(width_in_adapter);
//        measure_width_in_spinner.setAdapter(measure_width_in_adapter);
//        height_in_spinner.setAdapter(height_in_adapter);
//        measure_height_in_spinner.setAdapter(measure_height_in_adapter);
//        //厢底
//        box_bottom_thickness_spinner.setAdapter(box_bottom_thickness_adapter);
//        box_bottom_style_spinner.setAdapter(box_bottom_style_adapter);
//        //穿梁
//        middle_beam_material_spinner.setAdapter(middle_beam_material_adapter);
//        middle_beam_Style_spinner.setAdapter(middle_beam_Style_adapter);
//        middle_beam_num_spinner.setAdapter(middle_beam_num_adapter);
//        //边梁
//        side_beam_material_spinner.setAdapter(side_beam_material_adapter);
//        //厢板
//        xiangban_style_spinner.setAdapter(xiangban_style_adapter);
//        xiangban_side_spinner.setAdapter(xiangban_side_adapter);
//        xiangban_num_spinner.setAdapter(xiangban_num_adapter);
//        //车厢（左右）
//        box_size_spinner.setAdapter(box_size_adapter);
//        box_style_spinner.setAdapter(box_style_adapter);
//        //花栏（左右）
//        hualan_side_spinner.setAdapter(hualan_side_adapter);
//        hualan_side_num_spinner.setAdapter(hualan_side_num_adapter);
//        //站柱形式
//        zhanzhu_style_spinner.setAdapter(zhanzhu_style_adapter);
//        //锁杆
//        //后门样式
//        backdoor_style_spinner.setAdapter(backdoor_style_adapter);
//        //助推器
//        booster_spinner.setAdapter(booster_adapter);
//        //龙门架
//        pengbukuang_num_spinner.setAdapter(pengbukuang_num_adapter);
//        longmenjia_style_spinner.setAdapter(longmenjia_style_adapter);
//        //拔台
//        ba_tai_spinner.setAdapter(ba_tai_adapter);
//        //拉撑
//        la_cheng_spinner.setAdapter(la_cheng_adapter);
//        //蓬杆
//        peng_gan_num_spinner.setAdapter(peng_gan_num_adapter);
//        anzhuang_style_spinner.setAdapter(anzhuang_style_adapter);
//        //工具箱
//        kit_left_spinner.setAdapter(kit_left_adapter);
//        kit_right_spinner.setAdapter(kit_right_adapter);
//        //紧绳器
//        rope_device_num_spinner.setAdapter(rope_device_num_adapter);
//        rope_device_position_spinner.setAdapter(rope_device_position_adapter);
//        //备胎支架
//        spare_tire_num_spinner.setAdapter(spare_tire_num_adapter);
//        spare_tire_position_spinner.setAdapter(spare_tire_position_adapter);
//        //升降器
//        elevator_num_spinner.setAdapter(elevator_num_adapter);
//        //水包
//        water_bag_spinner.setAdapter(water_bag_adapter);
//        //水包空
//        water_bag_space_spinner.setAdapter(water_bag_space_adapter);
//        //车桥 axle
//        axle_spinner.setAdapter(axle_adapter);
//        //钢板 steel_plate
//        steel_plate_spinner.setAdapter(steel_plate_adapter);
//        //轴距 wheelbase
//        wheelbase_spinner.setAdapter(wheelbase_adapter);
//        //刹车气室  brake_air_chamber
//        brake_air_chamber_spinner.setAdapter(brake_air_chamber_adapter);
//        //轮胎 tyre
//        tyre_spinner.setAdapter(tyre_adapter);
//        // 钢圈 steel_ring
//        steel_ring_spinner.setAdapter(steel_ring_adapter);
//        //ABS
//        abs_spinner.setAdapter(abs_adapter);
//        //车身颜色
//        body_color_up_spinner.setAdapter(body_color_up_adapter);
//        body_color_bottom_spinner.setAdapter(body_color_bottom_adapter);
//        //悬挂 suspension
//        suspension_spinner.setAdapter(suspension_adapter);
//
//    }

}
