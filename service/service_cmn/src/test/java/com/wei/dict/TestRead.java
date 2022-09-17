package com.wei.dict;

import com.alibaba.excel.EasyExcel;

public class TestRead {

    public static void main(String[] args) {
//        文件的路径
        String filePath = "//Users//nikoworld//Documents//hospitalProject//EasyExcelQuick//niko.xlsx";
//        执行读的核心操作
        EasyExcel.read(filePath,UserData.class,new ReadListener()).sheet("我的模板1").doRead();
    }

}
