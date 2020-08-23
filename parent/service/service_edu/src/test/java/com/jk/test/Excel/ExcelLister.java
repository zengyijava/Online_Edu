package com.jk.test.Excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

public class ExcelLister extends AnalysisEventListener<DemoData> {
    @Override
    public void invoke(DemoData demoData, AnalysisContext analysisContext) {
        System.out.println("Excel表中每一行的内容："+demoData);
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("Excel表头内容："+headMap);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
