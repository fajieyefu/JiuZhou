package com.fajieyefu.jiuzhou.Bean;

/**
 * Created by Fajieyefu on 2016/8/9.
 */
public class My_MD5 {
    private static final String regcode="54RT3276Y3486583SW48566NG6788IT567";
    private static String tempString;
    public static String get_MD5Code(String password){
        tempString=password+regcode;
        tempString=tempString.substring(0,32);
        System.out.println(tempString+":"+tempString.length());
        //将32位字符均分成8组
        String StrCode1 = tempString.substring(0,4);
        System.out.println(StrCode1+":"+StrCode1.length());
        String StrCode2 = tempString.substring(4,8);
        String StrCode3 = tempString.substring(8,12);
        String StrCode4 = tempString.substring(12,16);
        String StrCode5 = tempString.substring(16,20);
        String StrCode6 = tempString.substring(20,24);
        String StrCode7 = tempString.substring(24,28);
        String StrCode8 = tempString.substring(27,31);
        //将1，3，5，7的字符串倒序
        StrCode1=invert(StrCode1);
        System.out.println(StrCode1+":"+StrCode1.length());
        System.out.println("StrCode1:"+StrCode1+":"+StrCode1.length());
        StrCode3=invert(StrCode3);
        StrCode5=invert(StrCode5);
        StrCode7=invert(StrCode7);
        //字母互换,将所有的字母和数字按照规定的换置
        StrCode1=covt_Str(StrCode1);
        System.out.println(StrCode1+":"+StrCode1.length());
        System.out.println("StrCode1:"+StrCode1+":"+StrCode1.length());
        StrCode2=covt_Str(StrCode2);
        StrCode3=covt_Str(StrCode3);
        StrCode4=covt_Str(StrCode4);
        StrCode5=covt_Str(StrCode5);
        System.out.println("StrCode5:"+StrCode5+":"+StrCode5.length());
        StrCode6=covt_Str(StrCode6);
        StrCode7=covt_Str(StrCode7);
        StrCode8=covt_Str(StrCode8);
        //位置交换,将1-5,2-6,3-7,4-8进行位置互换

        String temp;
        temp=StrCode1;
        StrCode1=StrCode5;
        StrCode5=temp;
        System.out.println("StrCode1:"+StrCode1+":"+StrCode1.length());
        System.out.println("StrCode5:"+StrCode5+":"+StrCode5.length());

        temp=StrCode2;
        StrCode2=StrCode6;
        StrCode6=temp;

        temp=StrCode3;
        StrCode3=StrCode7;
        StrCode7=temp;

        temp=StrCode4;
        StrCode4=StrCode8;
        StrCode8=temp;
        System.out.println(StrCode1+StrCode2+StrCode3+StrCode4+StrCode5+StrCode6+StrCode7+StrCode8);
        return StrCode1+StrCode2+StrCode3+StrCode4+StrCode5+StrCode6+StrCode7+StrCode8;
    }
    private static String covt_Str(String str) {
        String tmp_txt = str.replace("1","A");
        tmp_txt = tmp_txt.replace("2", "B");
        tmp_txt = tmp_txt.replace( "3", "C");
        tmp_txt = tmp_txt.replace( "4", "D");
        tmp_txt = tmp_txt.replace( "5", "E");
        tmp_txt = tmp_txt.replace( "M", "6");
        tmp_txt = tmp_txt.replace( "N", "7");
        tmp_txt = tmp_txt.replace( "O", "8");
        tmp_txt = tmp_txt.replace( "P", "9");
        tmp_txt = tmp_txt.replace( "Q", "0");
        tmp_txt = tmp_txt.replace( "F", "G");
        tmp_txt = tmp_txt.replace( "H", "I");
        tmp_txt = tmp_txt.replace( "J", "K");
        tmp_txt = tmp_txt.replace( "L", "R");


        return tmp_txt;
    }

    private static String invert(String str) {
        return  new StringBuffer(str).reverse().toString();
    }
}
