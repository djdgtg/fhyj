package com.fhyj.common.easyexcel;

import com.alibaba.excel.EasyExcel;

/**
 * Description
 *
 * @author djdgt
 * @since 2023/2/23 16:07
 */
public class ExcelTest {

    public void testImport() {
        EasyExcel.read().registerReadListener(new GeneralAnalysisEventListener<Device>(
                data -> {

                },
                error -> {

                }
        )).sheet().doRead();
    }
}
