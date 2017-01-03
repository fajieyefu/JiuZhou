package com.fajieyefu.jiuzhou.Query;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Window;
import android.widget.TextView;
import android.widget.TextView;
import android.widget.TextView;
import android.widget.TextView;

import com.fajieyefu.jiuzhou.Bean.BaseActivity;
import com.fajieyefu.jiuzhou.Bean.WebService;
import com.fajieyefu.jiuzhou.R;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Fajieyefu on 2016/7/26.
 */
public class ContractQueryActivity extends BaseActivity {
    private TextView titleText;
    //基本信息
    private TextView contract_no_TextView;
    private TextView buyer_edit,sign_date_edit,sign_address_edit,tractor_edit,traction_pin_edit,traction_seat_edit,heightfromground_edit;
    private TextView power_type_radio;
    //车辆类型
    private TextView vehicle_type_TextView,length_out_TextView,measure_length_out_TextView;
    private TextView type_name_edit;
    //外部尺寸
    private TextView width_out_TextView,measure_width_out_TextView,height_out_TextView,measure_height_out_TextView;

    //内部尺寸
    private TextView length_in_TextView,measure_length_in_TextView;
    private TextView width_in_TextView,measure_width_in_TextView,height_in_TextView,measure_height_in_TextView;
    //纵梁
    private TextView upper_wing_plate_TextView,lower_wing_plate_TextView,vertical_plate_TextView,stringer_height_TextView;
    private TextView lower_bending_TextView,punch_TextView;
    //厢底
    private TextView box_bottom_thickness_TextView,box_bottom_style_TextView;
    private TextView waterproof_tank_TextView;
    //穿梁
    private TextView middle_beam_material_TextView,middle_beam_Style_TextView,middle_beam_num_TextView;
    private TextView middle_beam_punch_TextView;
    //边梁
    private TextView side_beam_material_TextView;
    //厢板
    private TextView xiangban_style_TextView,xiangban_side_TextView,xiangban_num_TextView;
    //车厢（左右）
    private TextView box_size_TextView,box_style_TextView;
    //花栏（左、右）
    private TextView hualan_side_TextView,hualan_side_num_TextView;
    private TextView tube_style_TextView;
    //站柱形式
    private TextView zhanzhu_style_TextView;
    //锁杆
    private TextView suogan_TextView;
    //后门样式
    private TextView backdoor_style_TextView;
    //助推器
    private TextView booster_TextView;
    //龙门架
    private TextView longmenjia_style_TextView;
    private TextView pengbukuang_num_TextView;
    private TextView pengbu_size_edit;
    private TextView ladder_TextView;
    //拔台
    private TextView ba_tai_TextView;
    //拉撑
    private TextView la_cheng_TextView;
    //蓬杆
    private TextView  peng_gan_num_TextView;
    private TextView  anzhuang_style_TextView;
    //工具箱
    private TextView  kit_left_TextView;
    private TextView  kit_right_TextView;
    //紧绳器
    private TextView  rope_device_num_TextView;
    private TextView  rope_device_position_TextView;
    //备胎支架 spare_tire_num
    private TextView  spare_tire_num_TextView;
    private TextView  spare_tire_position_TextView;
    //升降器 elevator_num
    private TextView  elevator_num_TextView;
    //水包 water_bag
    private TextView  water_bag_TextView;
    //水包空 water_bag_space
    private TextView  water_bag_space_TextView;
    //车桥 axle
    private TextView  axle_TextView;
    //钢板 steel_plate
    private TextView  steel_plate_TextView;
    //轴距 wheelbase
    private TextView  wheelbase_TextView;
    //刹车气室  brake_air_chamber
    private TextView  brake_air_chamber_TextView;
    //轮胎 tyre
    private TextView tyre_TextView;
    // 钢圈 steel_ring
    private TextView steel_ring_TextView;
    //ABS
    private TextView abs_TextView;
    //车身颜色
    private TextView body_color_up_TextView;
    private TextView body_color_bottom_TextView;
    //悬挂 suspension
    private TextView suspension_TextView;
    //其他要求 another_quest
    private TextView another_quest_edit;
    //二、手续要求
    private TextView model_edit,tonnage_edit,plate_num_edit,axis_number_edit,color_edit,steel_edit;
    //三、
    private TextView num_edit,price_edit,total_amount_edit,shoufu_edit;
    //提货时间、提货方式
    private TextView extra_days_edit,delivery_mode_edit;


    private String account,loan_code;
    private SharedPreferences pref;
    private Handler handler= new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.contract_query);
        titleText= (TextView) findViewById(R.id.title_text);
        titleText.setText("合同录入查询");
        pref=ContractQueryActivity.this.getSharedPreferences("userInfo",MODE_PRIVATE);
        account=pref.getString("account","");
        Intent intent=getIntent();
        loan_code=intent.getStringExtra("loan_code");

        initWeight();
        getContractInfo();
        

    }

    private void getContractInfo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
               String info= WebService.getContractDetailInfo(loan_code);
                if(!TextUtils.isEmpty(info)&&!info.equals("fail")){
                    try {
                        JSONObject jsonObject=new JSONObject(info);
                        final String contract_no=jsonObject.getString("contract_no");
                        final String buyer_name=jsonObject.getString("buyer_name");
                        final String sign_date=jsonObject.getString("sign_date");
                        final String sign_address=jsonObject.getString("sign_address");
                        final String tractor=jsonObject.getString("tractor");
                        final String power_type=jsonObject.getString("power_type");
                        final String traction_pin=jsonObject.getString("traction_pin");
                        final String traction_seat=jsonObject.getString("traction_seat");
                        final String heightfromground=jsonObject.getString("heightfromground");
                        final String product_code=jsonObject.getString("product_code");
                        final String product_name=jsonObject.getString("product_name");
                        final String type_name=jsonObject.getString("type_name");
                        final String length_out=jsonObject.getString("length_out");
                        final String measure_length_out=jsonObject.getString("measure_length_out");
                        final String width_out=jsonObject.getString("width_out");
                        final String measure_width_out=jsonObject.getString("measure_width_out");
                        final String height_out=jsonObject.getString("height_out");
                        final String measure_height_out=jsonObject.getString("measure_height_out");
                        final String length_in=jsonObject.getString("length_in");
                        final String measure_length_in=jsonObject.getString("measure_length_in");
                        final String width_in=jsonObject.getString("width_in");
                        final String measure_width_in=jsonObject.getString("measure_width_in");
                        final String height_in=jsonObject.getString("height_in");
                        final String measure_height_in=jsonObject.getString("measure_height_in");
                        final String upper_wing_plate=jsonObject.getString("upper_wing_plate");
                        final String lower_wing_plate=jsonObject.getString("lower_wing_plate");
                        final String vertical_plate=jsonObject.getString("vertical_plate");
                        final String stringer_height=jsonObject.getString("stringer_height");
                        final String lower_bending=jsonObject.getString("lower_bending");
                        final String punch=jsonObject.getString("punch");
                        final String box_bottom_thickness=jsonObject.getString("box_bottom_thickness");
                        final String box_bottom_style=jsonObject.getString("box_bottom_style");
                        final String waterproof_tank=jsonObject.getString("waterproof_tank");
                        final String middle_beam_material=jsonObject.getString("middle_beam_material");
                        final String middle_beam_Style=jsonObject.getString("middle_beam_Style");
                        final String middle_beam_num=jsonObject.getString("middle_beam_num");
                        final String middle_beam_punch=jsonObject.getString("middle_beam_punch");
                        final String side_beam_material=jsonObject.getString("side_beam_material");
                        final String xiangban_style=jsonObject.getString("xiangban_style");
                        final String xiangban_side=jsonObject.getString("xiangban_side");
                        final String xiangban_num=jsonObject.getString("xiangban_num");
                        final String box_size=jsonObject.getString("box_size");
                        final String box_style=jsonObject.getString("box_style");
                        final String hualan_side=jsonObject.getString("hualan_side");
                        final String hualan_side_num=jsonObject.getString("hualan_side_num");
                        final String tube_style=jsonObject.getString("tube_style");
                        final String zhanzhu_style=jsonObject.getString("zhanzhu_style");
                        final String suogan=jsonObject.getString("suogan");
                        final String backdoor_style=jsonObject.getString("backdoor_style");
                        final String booster=jsonObject.getString("booster");
                        final String longmenjia_style=jsonObject.getString("longmenjia_style");
                        final String pengbukuang_num=jsonObject.getString("pengbukuang_num");
                        final String pengbu_size=jsonObject.getString("pengbu_size");
                        final String ba_tai=jsonObject.getString("ba_tai");
                        final String la_cheng=jsonObject.getString("la_cheng");
                        final String peng_gan_num=jsonObject.getString("peng_gan_num");
                        final String anzhuang_style=jsonObject.getString("anzhuang_style");
                        final String kit_left=jsonObject.getString("kit_left");
                        final String kit_right=jsonObject.getString("kit_right");
                        final String rope_device_num=jsonObject.getString("rope_device_num");
                        final String rope_device_position=jsonObject.getString("rope_device_position");
                        final String spare_tire_num=jsonObject.getString("spare_tire_num");
                        final String spare_tire_position=jsonObject.getString("spare_tire_position");
                        final String elevator_num=jsonObject.getString("elevator_num");
                        final String water_bag=jsonObject.getString("water_bag");
                        final String water_bag_space=jsonObject.getString("water_bag_space");
                        final String axle=jsonObject.getString("axle");
                        final String steel_plate=jsonObject.getString("steel_plate");
                        final String wheelbase=jsonObject.getString("wheelbase");
                        final String brake_air_chamber=jsonObject.getString("brake_air_chamber");
                        final String tyre=jsonObject.getString("tyre");
                        final String steel_ring=jsonObject.getString("steel_ring");
                        final String abs=jsonObject.getString("abs");
                        final String body_color_up=jsonObject.getString("body_color_up");
                        final String body_color_bottom=jsonObject.getString("body_color_bottom");
                        final String suspension=jsonObject.getString("suspension");
                        final String another_quest=jsonObject.getString("another_quest");
                        final String model=jsonObject.getString("model");
                        final String tonnage=jsonObject.getString("tonnage");
                        final String plate_num=jsonObject.getString("plate_num");
                        final String axis_number=jsonObject.getString("axis_number");
                        final String color=jsonObject.getString("color");
                        final String steel=jsonObject.getString("steel");
                        final String num=jsonObject.getString("num");
                        final String price=jsonObject.getString("price");
                        final String total_amount=jsonObject.getString("total_amount");
                        final String shoufu=jsonObject.getString("shoufu");
                        final String extra_days=jsonObject.getString("extra_days");
                        final String delivery_mode=jsonObject.getString("delivery_mode");
                        System.out.println(contract_no);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                contract_no_TextView.setText("合同单号："+contract_no);
                                buyer_edit.setText(buyer_name); 
                                sign_address_edit.setText(sign_address);
                                sign_date_edit.setText(sign_date);
                                tractor_edit.setText(tractor);
                                traction_pin_edit.setText(traction_pin);
                                traction_seat_edit.setText(traction_seat);
                                heightfromground_edit.setText(heightfromground);
                                power_type_radio.setText(power_type);
                                //车辆类型
                                type_name_edit.setText(type_name);
                                vehicle_type_TextView.setText(product_name+product_code);
                                //外部尺寸
                                length_out_TextView.setText(length_out);
                                measure_length_out_TextView.setText(measure_length_out);
                                width_out_TextView.setText(width_out);
                                measure_width_out_TextView.setText(measure_width_out);
                                height_out_TextView.setText(height_out);
                                measure_height_out_TextView.setText(measure_height_out);
                                //内部尺寸
                                length_in_TextView.setText(length_in);
                                measure_length_in_TextView.setText(measure_length_in);
                                width_in_TextView.setText(width_in);
                                measure_width_in_TextView.setText(measure_width_in);
                                height_in_TextView.setText(height_in);
                                measure_height_in_TextView.setText(measure_height_in);
                                //纵梁
                                upper_wing_plate_TextView.setText(upper_wing_plate);
                                lower_wing_plate_TextView.setText(lower_wing_plate);
                                vertical_plate_TextView.setText(vertical_plate);
                                stringer_height_TextView.setText(stringer_height);
                                lower_bending_TextView.setText(lower_bending);
                                punch_TextView.setText(punch);

                                //厢底
                                box_bottom_thickness_TextView.setText(box_bottom_thickness);
                                box_bottom_style_TextView.setText(box_bottom_style);
                                waterproof_tank_TextView.setText(waterproof_tank);
                                //穿梁
                                middle_beam_material_TextView.setText(middle_beam_material);
                                middle_beam_Style_TextView.setText(middle_beam_Style);
                                middle_beam_num_TextView.setText(middle_beam_num);
                                middle_beam_punch_TextView.setText(middle_beam_punch);
                                //边梁
                                side_beam_material_TextView.setText(side_beam_material);
                                //厢板
                                xiangban_style_TextView.setText(xiangban_style);
                                xiangban_num_TextView.setText(xiangban_num);
                                xiangban_side_TextView.setText(xiangban_side);
                                //车厢（左右）
                                box_size_TextView.setText(box_size);
                                box_style_TextView.setText(box_style);
                                //花栏（左、右）
                                hualan_side_TextView.setText(hualan_side);
                                hualan_side_num_TextView.setText(hualan_side_num);
                                tube_style_TextView.setText(tube_style);
                                //站柱形式
                                zhanzhu_style_TextView.setText(zhanzhu_style);
                                //锁杆
                                suogan_TextView.setText(suogan);
                                //后门样式
                                backdoor_style_TextView.setText(backdoor_style);
                                //助推器
                                booster_TextView.setText(booster);
                                //龙门架
                                longmenjia_style_TextView.setText(longmenjia_style);
                                pengbukuang_num_TextView.setText(pengbukuang_num);
                                pengbu_size_edit.setText(pengbu_size);
                                ladder_TextView.setText("");
                                //拔台
                                ba_tai_TextView.setText(ba_tai);
                                //拉撑
                                la_cheng_TextView.setText(la_cheng);
                                //蓬杆
                                peng_gan_num_TextView.setText(peng_gan_num);
                                anzhuang_style_TextView.setText(anzhuang_style);
                                //工具箱
                                kit_left_TextView.setText(kit_left);
                                kit_right_TextView.setText(kit_right);
                                //紧绳器
                                rope_device_num_TextView.setText(rope_device_num);
                                rope_device_position_TextView.setText(rope_device_position);
                                //备胎支架
                                spare_tire_num_TextView.setText(spare_tire_num);
                                spare_tire_position_TextView.setText(spare_tire_position);
                                //升降器
                                elevator_num_TextView.setText(elevator_num);
                                //水包
                                water_bag_TextView.setText(water_bag);
                                //水包空
                                water_bag_space_TextView.setText(water_bag_space);
                                //车桥 axle
                                axle_TextView.setText(axle);
                                //钢板 steel_plate
                                steel_plate_TextView.setText(steel_plate);
                                //轴距 wheelbase
                                wheelbase_TextView.setText(wheelbase);
                                //刹车气室  brake_air_chamber
                                brake_air_chamber_TextView.setText(brake_air_chamber);
                                //轮胎 tyre
                                tyre_TextView.setText(tyre);
                                // 钢圈 steel_ring
                                steel_ring_TextView.setText(steel_ring);
                                //ABS
                                abs_TextView.setText(abs);
                                //车身颜色
                                body_color_up_TextView.setText(body_color_up);
                                body_color_bottom_TextView.setText(body_color_bottom);
                                //悬挂 suspension
                                suspension_TextView.setText(suspension);
                                //其他要求
                                another_quest_edit.setText(another_quest);
                                //手续要求
                                model_edit.setText(model);
                                tonnage_edit.setText(tonnage);
                                plate_num_edit.setText(plate_num);
                                axis_number_edit.setText(axis_number);
                                color_edit.setText(color);
                                steel_edit.setText(steel);
                                //三、
                                num_edit.setText(num);
                                price_edit.setText(price);
                                total_amount_edit.setText(total_amount);
                                shoufu_edit.setText(shoufu);
                                //提货时间、提货方式
                                extra_days_edit.setText(extra_days);
                                delivery_mode_edit.setText(delivery_mode);
                                
                            }
                        });
                        
                            
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void initWeight() {
        contract_no_TextView= (TextView) findViewById(R.id.contract_no);
        buyer_edit= (TextView) findViewById(R.id.buyer);
        sign_address_edit=(TextView)findViewById(R.id.sign_address);
        sign_date_edit= (TextView) findViewById(R.id.sign_date);
        tractor_edit= (TextView) findViewById(R.id.tractor);
        traction_pin_edit= (TextView) findViewById(R.id.traction_pin);
        traction_seat_edit= (TextView) findViewById(R.id.traction_seat);
        heightfromground_edit= (TextView) findViewById(R.id.heightfromground);
        power_type_radio= (TextView) findViewById(R.id.power_type);
        //车辆类型
        type_name_edit= (TextView) findViewById(R.id.type_name);
        vehicle_type_TextView= (TextView) findViewById(R.id.vehicle_type);
        //外部尺寸
        length_out_TextView= (TextView) findViewById(R.id.length_out);
        measure_length_out_TextView= (TextView) findViewById(R.id.measure_length_out);
        width_out_TextView= (TextView) findViewById(R.id.width_out);
        measure_width_out_TextView= (TextView) findViewById(R.id.measure_width_out);
        height_out_TextView= (TextView) findViewById(R.id.height_out);
        measure_height_out_TextView= (TextView) findViewById(R.id.measure_height_out);
        //内部尺寸
        length_in_TextView= (TextView) findViewById(R.id.length_in);
        measure_length_in_TextView= (TextView) findViewById(R.id.measure_length_in);
        width_in_TextView= (TextView) findViewById(R.id.width_in);
        measure_width_in_TextView= (TextView) findViewById(R.id.measure_width_in);
        height_in_TextView= (TextView) findViewById(R.id.height_in);
        measure_height_in_TextView= (TextView) findViewById(R.id.measure_height_in);
        //纵梁
        upper_wing_plate_TextView= (TextView) findViewById(R.id.upper_wing_plate);
        lower_wing_plate_TextView= (TextView) findViewById(R.id.lower_wing_plate);
        vertical_plate_TextView= (TextView) findViewById(R.id.vertical_plate);
        stringer_height_TextView= (TextView) findViewById(R.id.stringer_height);
        lower_bending_TextView= (TextView) findViewById(R.id.lower_bending);
        punch_TextView= (TextView) findViewById(R.id.punch);

        //厢底
        box_bottom_thickness_TextView= (TextView) findViewById(R.id.box_bottom_thickness);
        box_bottom_style_TextView= (TextView) findViewById(R.id.box_bottom_style);
        waterproof_tank_TextView= (TextView) findViewById(R.id.waterproof_tank);
        //穿梁
        middle_beam_material_TextView= (TextView) findViewById(R.id.middle_beam_material);
        middle_beam_Style_TextView= (TextView) findViewById(R.id.middle_beam_Style);
        middle_beam_num_TextView= (TextView) findViewById(R.id.middle_beam_num);
        middle_beam_punch_TextView= (TextView) findViewById(R.id.middle_beam_punch);
        //边梁
        side_beam_material_TextView= (TextView) findViewById(R.id.side_beam_material);
        //厢板
        xiangban_style_TextView= (TextView) findViewById(R.id.xiangban_style);
        xiangban_num_TextView= (TextView) findViewById(R.id.xiangban_num);
        xiangban_side_TextView= (TextView) findViewById(R.id.xiangban_side);
        //车厢（左右）
        box_size_TextView= (TextView) findViewById(R.id.box_size);
        box_style_TextView= (TextView) findViewById(R.id.box_style);
        //花栏（左、右）
        hualan_side_TextView= (TextView) findViewById(R.id.hualan_side);
        hualan_side_num_TextView= (TextView) findViewById(R.id.hualan_side_num);
        tube_style_TextView= (TextView) findViewById(R.id.tube_style);
        //站柱形式
        zhanzhu_style_TextView= (TextView) findViewById(R.id.zhanzhu_style);
        //锁杆
        suogan_TextView= (TextView) findViewById(R.id.suogan);
        //后门样式
        backdoor_style_TextView= (TextView) findViewById(R.id.backdoor_style);
        //助推器
        booster_TextView= (TextView) findViewById(R.id.booster);
        //龙门架
        longmenjia_style_TextView= (TextView) findViewById(R.id.longmenjia_style);
        pengbukuang_num_TextView= (TextView) findViewById(R.id.pengbukuang_num);
        pengbu_size_edit= (TextView) findViewById(R.id.pengbu_size);
        ladder_TextView= (TextView) findViewById(R.id.ladder);
        //拔台
        ba_tai_TextView= (TextView) findViewById(R.id.ba_tai);
        //拉撑
        la_cheng_TextView= (TextView) findViewById(R.id.la_cheng);
        //蓬杆
        peng_gan_num_TextView= (TextView) findViewById(R.id.peng_gan_num);
        anzhuang_style_TextView= (TextView) findViewById(R.id.anzhuang_style);
        //工具箱
        kit_left_TextView= (TextView) findViewById(R.id.kit_left);
        kit_right_TextView= (TextView) findViewById(R.id.kit_right);
        //紧绳器
        rope_device_num_TextView= (TextView) findViewById(R.id.rope_device_num);
        rope_device_position_TextView= (TextView) findViewById(R.id.rope_device_position);
        //备胎支架
        spare_tire_num_TextView= (TextView) findViewById(R.id.spare_tire_num);
        spare_tire_position_TextView= (TextView) findViewById(R.id.spare_tire_position);
        //升降器
        elevator_num_TextView= (TextView) findViewById(R.id.elevator_num);
        //水包
        water_bag_TextView= (TextView) findViewById(R.id.water_bag);
        //水包空
        water_bag_space_TextView= (TextView) findViewById(R.id.water_bag_space);
        //车桥 axle
        axle_TextView= (TextView) findViewById(R.id.axle);
        //钢板 steel_plate
        steel_plate_TextView= (TextView) findViewById(R.id.steel_plate);
        //轴距 wheelbase
        wheelbase_TextView= (TextView) findViewById(R.id.wheelbase);
        //刹车气室  brake_air_chamber
        brake_air_chamber_TextView= (TextView) findViewById(R.id.brake_air_chamber);
        //轮胎 tyre
        tyre_TextView= (TextView) findViewById(R.id.tyre);
        // 钢圈 steel_ring
        steel_ring_TextView= (TextView) findViewById(R.id.steel_ring);
        //ABS
        abs_TextView= (TextView) findViewById(R.id.abs);
        //车身颜色
        body_color_up_TextView= (TextView) findViewById(R.id.body_color_up);
        body_color_bottom_TextView= (TextView) findViewById(R.id.body_color_bottom);
        //悬挂 suspension
        suspension_TextView= (TextView) findViewById(R.id.suspension);
        //其他要求
        another_quest_edit= (TextView) findViewById(R.id.another_quest);
        //手续要求
        model_edit= (TextView) findViewById(R.id.model);
        tonnage_edit= (TextView) findViewById(R.id.tonnage);
        plate_num_edit= (TextView) findViewById(R.id.plate_num);
        axis_number_edit= (TextView) findViewById(R.id.axis_number);
        color_edit= (TextView) findViewById(R.id.color);
        steel_edit= (TextView) findViewById(R.id.steel);
        //三、
        num_edit= (TextView) findViewById(R.id.num);
        price_edit= (TextView) findViewById(R.id.price);
        total_amount_edit= (TextView) findViewById(R.id.total_amount);
        shoufu_edit= (TextView) findViewById(R.id.shoufu);
        //提货时间、提货方式
        extra_days_edit= (TextView) findViewById(R.id.extra_days);
        delivery_mode_edit= (TextView) findViewById(R.id.delivery_mode);
    }

}
