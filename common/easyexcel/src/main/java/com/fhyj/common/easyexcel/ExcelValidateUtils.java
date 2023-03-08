package com.fhyj.common.easyexcel;

import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.regex.Pattern;

/**
 * Description
 *
 * @author djdgt
 * @since 2023/2/23 16:17
 */
public class ExcelValidateUtils {

    @SneakyThrows
    public static ExcelErrorMessage validate(Object data, Integer rowIndex) {
        Field[] fields = data.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            ExcelValidate excelValidate = field.getAnnotation(ExcelValidate.class);
            Object value = field.get(data);
            if (excelValidate != null) {
                // 如果不能为空
                if (excelValidate.notNull() && (value == null || value.toString().length() == 0)) {
                    return new ExcelErrorMessage(rowIndex, excelValidate.name(), "不能为空！");
                }
                if (value != null) {
                    if (value.toString().length() < excelValidate.minFieldLength() || value.toString().length() > excelValidate.maxFieldLength()) {
                        return new ExcelErrorMessage(rowIndex, excelValidate.name(), String.format("长度限制%s到%s！", excelValidate.minFieldLength(), excelValidate.maxFieldLength()));
                    }
                    if (excelValidate.pattern() != null && Pattern.matches(excelValidate.pattern(), value.toString())) {
                        return new ExcelErrorMessage(rowIndex, excelValidate.name(), "请输入正确的格式！");
                    }
                }
            }
        }
        return null;
    }
}
