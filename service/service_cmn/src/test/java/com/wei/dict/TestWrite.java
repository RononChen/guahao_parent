package com.wei.dict;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;

public class TestWrite {

    public static void main(String[] args) {
//        需要向excel中插入的数据
        ArrayList<UserData> dataArrayList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            UserData userData = new UserData();
            userData.setUid(i);
            userData.setUsername("神经病"+i);
            dataArrayList.add(userData);
        }
//        文件的路径
        String filePath = "//Users//nikoworld//Documents//hospitalProject//EasyExcelQuick//niko.xlsx";
//        执行核心API  写入指定数据
        EasyExcel.write(filePath,UserData.class).sheet("我的模板1").doWrite(dataArrayList);
    }

}
