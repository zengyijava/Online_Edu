package com.jk.test.Excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class TestEasyExcel {
    public static void main(String[] args) {
//        String path="F:\\write.xls";
//        EasyExcel.write(path,DemoData.class).sheet("学生列表").doWrite(getData());

        //Excel 读取文件
        String path="F:\\write.xls";
        EasyExcel.read(path,DemoData.class,new ExcelLister()).sheet().doRead();
    }

    private static List<DemoData> getData(){
        List<DemoData> list=new ArrayList<>();
        for(int i=1;i<=10;i++){
            DemoData demoData=new DemoData();
            demoData.setSname("Luck"+i);
            demoData.setSno(i);
            list.add(demoData);
        }
        return list;
    }

}
