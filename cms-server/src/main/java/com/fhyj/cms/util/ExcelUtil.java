package com.fhyj.cms.util;

import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.*;

import java.util.List;


/**
 * @author qinc
 * @description
 * @date 2018/12/11
 */
public class ExcelUtil {
    /**
     * @param sheetname @Title: createExcelTemplate @Description:
     *                  生成Excel导入模板 @param @param filePath Excel文件路径 @param @param handers
     *                  Excel列标题(数组) @param @param downData 下拉框数据(数组) @param @param downRows
     *                  下拉列的序号(数组,序号从0开始) @return void @throws
     */
    public static void getExcelTemplate(XSSFWorkbook wb, XSSFSheet sheet1, List<String[]> downData, List<Integer> downRows) {
        XSSFSheet sheet2 = wb.createSheet("Sheet2");
        // 设置sheet2隐藏
        wb.setSheetHidden(1, true);
        // 设置下拉框数据
        String[] arr = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S",
                "T", "U", "V", "W", "X", "Y", "Z"};
        int index = 0;
        XSSFRow row = null;
        for (int r = 0; r < downRows.size(); r++) {
            String[] dlData = downData.get(r);// 获取下拉对象
            int rownum = downRows.get(r);
            // 255以上的下拉，即下拉列表元素很多的情况

            // 1、设置有效性
            String strFormula = "Sheet2!$" + arr[index] + "$1:$" + arr[index] + "$" + dlData.length; // Sheet2第A1到A5000作为下拉列表来源数据
            sheet2.setColumnWidth(r, 4000); // 设置每列的列宽
            // 设置数据有效性加载在哪个单元格上,参数分别是：从sheet2获取A1到A5000作为一个下拉的数据、起始行、终止行、起始列、终止列
            XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper(sheet1);
            DataValidationConstraint constraint = dvHelper.createFormulaListConstraint(strFormula);
            CellRangeAddressList addressList = new CellRangeAddressList(1, 100, rownum, rownum);
            XSSFDataValidation validation = (XSSFDataValidation) dvHelper.createValidation(constraint, addressList);
            validation.setSuppressDropDownArrow(true);
            validation.setShowErrorBox(true);
            sheet1.addValidationData(validation);// 下拉列表元素很多的情况

            // 2、生成sheet2内容
            for (int j = 0; j < dlData.length; j++) {
                if (index == 0) { // 第1个下拉选项，直接创建行、列
                    row = sheet2.createRow(j); // 创建数据行
                    sheet2.setColumnWidth(j, 4000); // 设置每列的列宽
                    row.createCell(0).setCellValue(dlData[j]); // 设置对应单元格的值
                } else { // 非第1个下拉选项
                    int rowCount = sheet2.getLastRowNum();
                    if (j <= rowCount) { // 前面创建过的行，直接获取行，创建列
                        // 获取行，创建列
                        sheet2.getRow(j).createCell(index).setCellValue(dlData[j]); // 设置对应单元格的值
                    } else { // 未创建过的行，直接创建行、创建列
                        sheet2.setColumnWidth(j, 4000); // 设置每列的列宽
                        // 创建行、创建列
                        sheet2.createRow(j).createCell(index).setCellValue(dlData[j]); // 设置对应单元格的值
                    }
                }
            }
            index++;
        }
    }
}
