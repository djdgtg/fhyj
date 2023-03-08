package com.fhyj.common.easyexcel;

import lombok.Getter;
import lombok.Setter;

/**
 * Description
 *
 * @author djdgt
 * @since 2023/2/23 15:49
 */
@Getter
@Setter
public class ExcelErrorMessage {

    private int rowIndex;

    private String columnName;

    private String errorMessage;

    public ExcelErrorMessage(int rowIndex, String columnName, String errorMessage) {
        this.rowIndex = rowIndex;
        this.columnName = columnName;
        this.errorMessage = errorMessage;
    }
}
