package com.fhyj.common.easyexcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Description
 *
 * @author djdgt
 * @since 2023/2/23 15:40
 */
public class GeneralAnalysisEventListener<T> extends AnalysisEventListener<T> {

    private final List<T> data = new ArrayList<>();
    private final List<ExcelErrorMessage> error = new ArrayList<>();
    private final Consumer<List<T>> dataConsumer;
    private final Consumer<List<ExcelErrorMessage>> errorConsumer;

    public GeneralAnalysisEventListener(Consumer<List<T>> dataConsumer, Consumer<List<ExcelErrorMessage>> errorConsumer) {
        this.dataConsumer = dataConsumer;
        this.errorConsumer = errorConsumer;
    }

    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
        // 如果数据校验成功，data添加改数据；否则error添加失败信息
        ExcelErrorMessage errorMessage = ExcelValidateUtils.validate(t, analysisContext.readRowHolder().getRowIndex());
        if (errorMessage == null) {
            data.add(t);
        } else {
            error.add(errorMessage);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // 如果全部数据校验都通过，对data进行处理如入库等；如果有一条失败则不入库，对error处理，返回给前端或者生产异常文件提供下载链接
        if (error.size() == 0) {
            dataConsumer.accept(data);
        } else {
            errorConsumer.accept(error);
        }
    }
}
