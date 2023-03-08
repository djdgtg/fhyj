package com.fhyj.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fhyj.cms.aspect.LogType;
import com.fhyj.cms.bean.*;
import com.fhyj.cms.entity.*;
import com.fhyj.cms.mapper.*;
import com.fhyj.cms.service.PersonalService;
import com.fhyj.cms.util.ExcelUtil;
import com.fhyj.common.minio.MinioUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Map.Entry;

/**
 * @author qinc
 * @description
 * @date 2018/12/11
 */
@Service
public class PersonalServiceImpl implements PersonalService {

    @Value("${file.server.url}")
    private String fileServerUrl;

    @Autowired
    private MinioUtils minioUtils;

    @Autowired
    private DbMetadataMapper metadataMapper;

    @Autowired
    private DbTableMapper tableMapper;

    @Autowired
    private BaseDicMapper dicMapper;

    @Autowired
    private DbTableNexusMapper tableNexusMapper;

    @Autowired
    private BaseClassMapper classMapper;

    @Autowired
    private BaseLogMapper logMapper;

    @Autowired
    private BaseMenuMapper menuMapper;

    @Override
    public ActionResult uploadFile(MultipartFile file) {
        try {
            String url = minioUtils.uploadFile(file.getInputStream());
            return ActionResult.ok(url);
        } catch (Exception e) {
            return ActionResult.build(-1, "文件上传失败！");
        }
    }

    @Override
    public HashMap<String, Object> uploadKindEditor(MultipartFile file) {
        HashMap<String, Object> result = new HashMap<>();
        try {
            String url = minioUtils.uploadFile(file.getInputStream());
            result.put("error", 0);
            result.put("url", fileServerUrl + url);
        } catch (Exception e) {
            result.put("error", 1);
            result.put("message", "上传失败！");
        }
        return result;
    }

    @Override
    public ActionResult importFile(MultipartFile file, TableManageSearchBean searchBean, HttpServletRequest request) {
        String filePath = request.getSession().getServletContext().getRealPath("/upload");
        String fileName = file.getOriginalFilename();
        String databaseName = searchBean.getDatabaseName();
        String mouldId = searchBean.getMouldId();
        BaseManager loginUser = (BaseManager) request.getSession().getAttribute("USER_VALUE_OBJECT");
        // 生成上传的文件对象
        File target = new File(filePath, fileName);
        if (target.exists()) {
            target.delete();
        }
        int savetotal = 0;
        int rows;
        int nums = 0;
        FileInputStream fis;
        try {
            FileCopyUtils.copy(file.getInputStream(), Files.newOutputStream(target.toPath()));
            CustomQueryBean creatorUser = new CustomQueryBean();
            creatorUser.setName("creatorId");
            creatorUser.setNameValue(loginUser.getId().toString());

            CustomQueryBean status = new CustomQueryBean();
            status.setName("status");
            status.setNameValue("1");

            fis = new FileInputStream(filePath + "\\" + fileName);
            XSSFWorkbook workbook = new XSSFWorkbook(fis); // 创建对Excel工作簿文件的引用
            XSSFSheet sheet = workbook.getSheetAt(0); // 在Excel文档中，第一张工作表的缺省索引是0
            rows = sheet.getPhysicalNumberOfRows(); // 获取到Excel文件中的所有行数­
            Map<Integer, String> keys = new HashMap<>();
            int cells = 0;
            // 遍历行（第1行 表头） 准备Map里的key
            XSSFRow firstRow = sheet.getRow(0);
            if (firstRow != null) {
                // 获取到Excel文件中的所有的列
                cells = firstRow.getPhysicalNumberOfCells();
                // 遍历列
                for (int j = 0; j < cells; j++) {
                    // 获取到列的值
                    try {
                        XSSFCell cell = firstRow.getCell(j);
                        String cellValue = cell.getStringCellValue();
                        if (cellValue != null && cellValue.split("###").length == 2) {
                            cellValue = cellValue.split("###")[1];
                        }
                        keys.put(j, cellValue);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            // 遍历行（从第二行开始）
            for (int i = 1; i < rows; i++) {
                List<CustomQueryBean> returnlist = new ArrayList<>();
                // 保存用户信息
                returnlist.add(creatorUser);
                returnlist.add(status);
                // 读取左上端单元格(从第二行开始)
                XSSFRow row = sheet.getRow(i);
                // 行不为空
                if (row != null) {
                    // 遍历列
                    for (int j = 0; j < cells; j++) {
                        CustomQueryBean bean = new CustomQueryBean();
                        // 获取到列的值
                        try {
                            XSSFCell cell = row.getCell(j);
                            String cellValue = cell.getStringCellValue();
                            if (cellValue != null && cellValue.trim().length() > 0) {
                                bean.setName(keys.get(j));
                                bean.setNameValue(cellValue);
                                returnlist.add(bean);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    // 一行所有的列数据读取完毕
                    // 入库
                    boolean b = addExcelData(databaseName, mouldId, returnlist);
                    if (b) {
                        savetotal++;
                    }
                }
                if (returnlist.size() > 2) {
                    nums++;
                }
            }
            // 所有的行读完

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ActionResult.ok("成功保存" + savetotal + "/" + nums + "条数据");
    }


    private boolean addExcelData(String databaseName, String mouldId, List<CustomQueryBean> queryList) {
        StringBuilder nameSql = new StringBuilder();
        StringBuilder valueSql = new StringBuilder();
        List<DbMetadata> list = metadataMapper.selectList(new LambdaQueryWrapper<DbMetadata>().eq(DbMetadata::getMouldId, mouldId));
        if (queryList.size() > 2) {
            for (CustomQueryBean customQueryBean : queryList) {
                String namevalue = customQueryBean.getNameValue();
                if (customQueryBean.getName() != null && !customQueryBean.getName().equals("undefined") && customQueryBean.getNameValue() != null && !customQueryBean.getNameValue().equals("undefined")) {
                    if (list != null) {
                        for (DbMetadata dbMetadata : list) {
                            if (dbMetadata.getColumnName().equals(customQueryBean.getName())) {
                                if (dbMetadata.getColumnType().equals(3) || dbMetadata.getColumnType().equals(4)) {
                                    if (dbMetadata.getColumnSource().equals(1)) {
                                        String[] split = customQueryBean.getNameValue().split("-");
                                        namevalue = split[0];
                                    }
                                }
                            }
                        }
                    }
                    nameSql.append(customQueryBean.getName()).append(",");
                    valueSql.append("'").append(namevalue).append("',");
                }
            }
        }
        String nameSqlStr = nameSql.substring(0, nameSql.length() - 1);
        String valueSqlStr = valueSql.substring(0, valueSql.length() - 1);
        String sql = "insert into " + databaseName + " (" + nameSqlStr + ") values(" + valueSqlStr + ")";
        int inRes = tableMapper.alterTable(sql);
        return inRes > 0;
    }

    @Override
    public void exportModelExcel(List<DbMetadata> metadataList, HttpServletResponse response,
                                 HttpServletRequest request, String tableChName, String type, String tableName, String mouldId) {
        try {
            OutputStream outputStream = response.getOutputStream();

            // 动态列获取
            StringBuilder colNames = new StringBuilder();
            //下拉框数据
            List<String[]> downData = new ArrayList<>();
            List<Integer> downRows = new ArrayList<>();
            List<Integer> datetimelist = new ArrayList<>();
            boolean b = false;
            for (int i = 0; i < metadataList.size(); i++) {
                if (b) {
                    colNames.append(",");
                }
                colNames.append(metadataList.get(i).getColumnChName()).append("###").append(metadataList.get(i).getColumnName());
                b = true;
                if (metadataList.get(i).getColumnType() == 3) {//下拉单选
                    if (metadataList.get(i).getColumnSource() == 1) {//字典表
                        List<BaseDic> dicList = dicMapper.selectList(new LambdaQueryWrapper<BaseDic>().eq(BaseDic::getType, metadataList.get(i).getColumnTypeRule()).eq(BaseDic::getIsDicType, 0));
                        List<String> diclist = new ArrayList<>();
                        for (BaseDic baseDic : dicList) {
                            diclist.add(baseDic.getValue() + "-" + baseDic.getName());
                        }
                        String[] arr = new String[diclist.size()];
                        downData.add(diclist.toArray(arr));
                        downRows.add(i);
                    } else if (metadataList.get(i).getColumnSource() == 3) {//资源表
                        List<DbTableNexus> list = tableNexusMapper.selectList(new LambdaQueryWrapper<DbTableNexus>()
                                .eq(DbTableNexus::getNexusField, metadataList.get(i).getColumnName()
                                ).eq(DbTableNexus::getNexusTable, tableName));
                        String dbSql = list.get(0).getDbSql();
                        List<LinkedHashMap<String, Object>> maplist = tableNexusMapper.getResList(dbSql);
                        List<String> reslist = new ArrayList<>();
                        List<Object> str = new ArrayList<>();
                        for (LinkedHashMap<String, Object> linkedHashMap : maplist) {
                            Set<Entry<String, Object>> set = linkedHashMap.entrySet();
                            for (Entry<String, Object> entry : set) {
                                str.add(entry.getValue());
                            }
                        }
                        for (int j = 0; j < str.size() - 1; j += 2) {
                            reslist.add(str.get(j) + "-" + str.get(j + 1));
                        }
                        String[] arr = new String[list.size()];
                        downData.add(reslist.toArray(arr));
                        downRows.add(i);
                    }
                } else if (metadataList.get(i).getColumnType() == 10 || metadataList.get(i).getColumnType() == 11) {//日期时间
                    datetimelist.add(i);
                }
            }
            // 处理列名字符串
            List<String> headColumnName = Arrays.asList(colNames.toString().split(","));
            // 写数据到Excel文件
            XSSFWorkbook workbook = exportExcel(headColumnName, type, tableName, mouldId, datetimelist, downData, downRows);
            String filename = tableChName + "导入模板.xlsx";
            if (("1").equals(type)) {
                filename = tableChName + "数据导出" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + ".xlsx";
            }
            String downloadFileName;
            String agent = request.getHeader("USER-AGENT");
            if (agent != null && agent.toLowerCase().indexOf("firefox") > 0) {
                downloadFileName = "=?UTF-8?B?" + (new String(Base64.encodeBase64(filename.getBytes(StandardCharsets.UTF_8)))) + "?=";
            } else {
                downloadFileName = java.net.URLEncoder.encode(filename, "UTF-8");
            }

            response.setHeader("Content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + downloadFileName);
            response.setContentType("application/octet-stream");
            response.setCharacterEncoding("UTF-8");

            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private XSSFWorkbook exportExcel(List<String> headColumnName, String type, String databaseName, String mouldId, List<Integer> datetimelist, List<String[]> downData, List<Integer> downRows) {

        // 写数据到Excel文件
        XSSFWorkbook workBook = new XSSFWorkbook();
        // 设置表头字体
        Font headfont = workBook.createFont();
        headfont.setFontName("黑体");
        headfont.setFontHeightInPoints((short) 9);// 字体大小
        headfont.setBold(true);// 加粗

        CellStyle headStyle = workBook.createCellStyle(); // 表头第一列的样式
        headStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_CORNFLOWER_BLUE.getIndex()); // 前景色设置
        headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND); // 填充模式 设置
        headStyle.setFont(headfont);
        headStyle.setAlignment(HorizontalAlignment.CENTER);// 左右居中
        headStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居中
        headStyle.setWrapText(true);
        headStyle.setBorderRight(BorderStyle.THIN);
        headStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        headStyle.setBorderTop(BorderStyle.THIN);
        headStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        // 表格正文样式，背景色,边框
        CellStyle style = workBook.createCellStyle();
        style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LEMON_CHIFFON.getIndex()); // 前景色设置
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND); // 填充模式 设置
        style.setBorderBottom(BorderStyle.MEDIUM); // 设置单元格的边框为粗体
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex()); // 设置单元格的边框颜色
        style.setBorderRight(BorderStyle.MEDIUM);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(BorderStyle.MEDIUM);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());

        Row row;
        Cell cell;
        XSSFSheet sheet;
        sheet = workBook.createSheet("资源数据");
        row = sheet.createRow(0);// 第一行，标题
        // 如果没有设置要下载的字段
        if (headColumnName.size() == 1 && !StringUtils.hasLength(headColumnName.get(0))) {
            cell = row.createCell(0);
            cell.setCellValue("没有要下载的数据！");
            return workBook;
        }
        // 设置第一行标题
        for (int i = 0; i < headColumnName.size(); i++) {
            cell = row.createCell(i);
            cell.setCellValue(headColumnName.get(i));
            cell.setCellStyle(headStyle);
            // 设置列宽
            sheet.setColumnWidth(i, 18 * 256);
        }
        if (StringUtils.hasLength(type) && type.equals("0")) {//导出模板
            ExcelUtil.getExcelTemplate(workBook, sheet, downData, downRows);
            XSSFCellStyle cellStyle = workBook.createCellStyle();
            CreationHelper creationHelper = workBook.getCreationHelper();
            cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-MM-dd hh:mm:ss"));
            // 创建行
            for (int i = 1; i < 100; i++) {
                row = sheet.createRow(i);
                for (Integer integer : datetimelist) {//日期时间
                    cell = row.createCell(integer);
                    cell.setCellStyle(cellStyle);
                }
            }
        } else if (StringUtils.hasLength(type) && type.equals("1")) {// 如果是导出全部数据 设置正文内容
            // 查出数据
            TableManageSearchBean searchBean = new TableManageSearchBean();
            searchBean.setDatabaseName(databaseName);
            searchBean.setMouldId(mouldId);
            ListInfo<LinkedHashMap<String, Object>> result = getSelfDataList(searchBean);

            List<LinkedHashMap<String, Object>> listNew = result.getList();

            for (int i = 0, len = listNew.size(); i < len; i++) {// 循环创建数据行
                row = sheet.createRow(i + 1);
                for (int j = 0; j < headColumnName.size(); j++) {
                    cell = row.createCell(j);
                    String fieldName = "";
                    if (null != headColumnName.get(j) && headColumnName.get(j).split("###").length > 1) {
                        fieldName = headColumnName.get(j).split("###")[1];
                    }
                    String cellValue = String.valueOf(listNew.get(i).get(fieldName));
                    if ("&nbsp;".equals(cellValue) || !StringUtils.hasLength(cellValue) || "null".equals(cellValue)) {
                        cellValue = "";
                    }
                    if (cellValue.length() >= 32767) {
                        cellValue = cellValue.substring(0, 32000) + "...";
                    }
                    cell.setCellValue(cellValue);
                    cell.setCellStyle(style);
                }
            }
        }

        return workBook;
    }

    private ListInfo<LinkedHashMap<String, Object>> getSelfDataList(TableManageSearchBean searchBean) {

        ListInfo<LinkedHashMap<String, Object>> listInfo = new ListInfo<>();
        // 获取db基本信息
        List<TableCustomBean> dbList = tableMapper.getTableCustomBean(searchBean);
        if (dbList != null && dbList.size() == 1) {
            TableCustomBean db = dbList.get(0);
            // 获取资源库元数据
            List<MetadataCustomBean> metaList = metadataMapper.getMetadataCustomBean(db.getMouldId());

            if (metaList == null) {
                metaList = new ArrayList<>();
            }

            StringBuilder whereSql = new StringBuilder(" where status != -1 ");
            if (searchBean.getUserId() > 0) {
                whereSql.append(" and creator=").append(searchBean.getUserId());
            }

            List<MetadataCustomBean> rMetaList = new ArrayList<>();
            StringBuilder showCol = new StringBuilder();
            for (MetadataCustomBean metadataCustomBean : metaList) {
                if (null != metadataCustomBean.getImportExportShow()
                        && metadataCustomBean.getImportExportShow() == 1) {
                    if (metadataCustomBean.getColumnType() == 10) {
                        showCol.append("DATE_FORMAT(").append(metadataCustomBean.getColumnName()).append(",'%y-%m-%d %H:%i:%s')").append(metadataCustomBean.getColumnName()).append(",");
                    } else {
                        showCol.append(metadataCustomBean.getColumnName()).append(",");
                    }
                    if (metadataCustomBean.getColumnType() == Constants.COLUMN_TYPE_RADIO
                            || metadataCustomBean.getColumnType() == Constants.COLUMN_TYPE_CHECKBOX) {
                        rMetaList.add(metadataCustomBean);
                    }
                }
            }

            showCol = new StringBuilder(showCol.substring(0, showCol.length() - 1));

            String sqlCount = "select count(1) from " + db.getTableName() + " " + whereSql;
            int count = tableMapper.getResCount(sqlCount);
            String sqlList;
            if (count > 0) {
                if (showCol.toString().contains("creator")) {
                    showCol = new StringBuilder(showCol.toString().replaceAll("creator,", ""));
                    showCol.append(",u.real_Name as creator ");
                    sqlList = "select " + showCol + " from " + db.getTableName()
                            + " db LEFT JOIN (select user_id,real_name from base_manager) u on db.creator = u.user_id "
                            + whereSql + " order by id desc ";
                } else {
                    sqlList = "select " + showCol + " from " + db.getTableName() + whereSql
                            + " order by id desc ";
                }
                List<LinkedHashMap<String, Object>> resList = tableMapper.getResList(sqlList);

                List<LinkedHashMap<String, Object>> resultList = new ArrayList<>();

                if (rMetaList.size() > 0) {
                    Map<String, List<?>> list = new HashMap<>();
                    for (MetadataCustomBean metaCustom : rMetaList) {
                        switch (metaCustom.getColumnSource()) {
                            case Constants.COlUMNSOURCE_DIC:
                                List<BaseDic> dic = dicMapper.selectList(new LambdaQueryWrapper<BaseDic>().eq(BaseDic::getIsDicType, 0)
                                        .eq(BaseDic::getType, metaCustom.getColumnTypeRule()));
                                list.put(metaCustom.getColumnName(), dic);
                                break;
                            case Constants.COlUMNSOURCE_CLASS:
                                String typeRule = metaCustom.getColumnTypeRule();
                                List<BaseClass> classes = classMapper.selectList(new LambdaQueryWrapper<BaseClass>().eq(BaseClass::getId, typeRule));
                                list.put(metaCustom.getColumnName(), classes);
                                break;
                            case Constants.COlUMNSOURCE_DB:
                                List<DbTableNexus> nexusList = tableNexusMapper.selectList(new LambdaQueryWrapper<DbTableNexus>().eq(DbTableNexus::getNexusTable, searchBean.getDatabaseName())
                                        .eq(DbTableNexus::getNexusField, metaCustom.getColumnName()));
                                List<List<LinkedHashMap<String, Object>>> nexusResList = new ArrayList<>();
                                if (nexusList != null) {
                                    for (DbTableNexus tableNexus : nexusList) {
                                        if (tableNexus != null) {
                                            List<LinkedHashMap<String, Object>> nexusSqlList = tableMapper.getResList(tableNexus.getDbSql());
                                            if (nexusSqlList != null && nexusSqlList.size() > 0) {
                                                nexusResList.add(nexusSqlList);
                                            }
                                        }
                                    }
                                }
                                list.put(metaCustom.getColumnName(), nexusResList);
                                break;
                        }
                        for (LinkedHashMap<String, Object> resMap : resList) {
                            resMap.put(metaCustom.getColumnName() + "Name", null);
                        }
                    }
                    for (LinkedHashMap<String, Object> object : resList) {
                        for (MetadataCustomBean metaCustomBean : rMetaList) {
                            if (list.size() == 0) {
                                continue;
                            }
                            if (!list.containsKey(metaCustomBean.getColumnName())) {
                                continue;
                            }
                            Object value = object.get(metaCustomBean.getColumnName());
                            if (value == null || value.toString().isEmpty()) {
                                continue;
                            }
                            List<?> valueList = list.get(metaCustomBean.getColumnName());
                            StringBuilder str = new StringBuilder();
                            String valueStr = value.toString();
                            for (Object v : valueList) {
                                if (v.getClass().equals(BaseDic.class)) {
                                    BaseDic dic = (BaseDic) v;
                                    if (valueStr.contains(dic.getValue())) {
                                        str.append(dic.getName()).append(",");
                                    }
                                } else if (v.getClass().equals(ClassCustomBean.class)) {
                                    ClassCustomBean cls = (ClassCustomBean) v;
                                    if (valueStr.contains(cls.getId().toString())) {
                                        str.append(cls.getName()).append(",");
                                    }
                                } else {
                                    List<LinkedHashMap<String, Object>> nexusL = (List<LinkedHashMap<String, Object>>) v;
                                    if (nexusL.size() > 0) {
                                        for (LinkedHashMap<String, Object> map : nexusL) {
                                            if (map != null && map.size() > 1) {
                                                Collection<Object> mapValue = map.values();
                                                Object[] mapValueArr = mapValue.toArray();
                                                if (valueStr.contains(mapValueArr[0].toString())) {
                                                    str.append(mapValueArr[1]).append(",");
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (str.length() > 1) {
                                str = new StringBuilder(str.substring(0, str.length() - 1));
                            }
                            object.put(metaCustomBean.getColumnName(), str.toString());
                        }
                        resultList.add(object);
                    }
                } else {
                    resultList = resList;
                }
                listInfo.setLength(count);
                listInfo.setList(resultList);
                return listInfo;
            }
        }

        return listInfo;
    }


    @Override
    public ActionResult updateResByFiledBatch(TableManageSearchBean searchBean, String fieldValue,
                                              String fieldName, HttpServletRequest request) {
        StringBuilder delSql = new StringBuilder("update ");
        String resIds = searchBean.getResIds();
        if (StringUtils.hasLength(resIds)) {
            delSql.append(searchBean.getDatabaseName()).append(" set ").append(fieldName).append(" = '").append(fieldValue).append("' where seqid in(");
            delSql.append(resIds).append(")");
            int count = tableMapper.alterTable(delSql.toString());
            if (count > 0) {
                BaseManager loginUser = (BaseManager) request.getSession().getAttribute("USER_VALUE_OBJECT");
                String mouldId = searchBean.getMouldId();
                List<BaseMenu> menus = menuMapper.selectList(new LambdaQueryWrapper<BaseMenu>().likeRight(BaseMenu::getPath, "mouldId=" + mouldId));
                BaseLog logs = new BaseLog();
                logs.setCreateTime(new Date());
                String logDetail = "修改了" + menus.get(0).getName() + count + "条数据状态";
                logs.setDetail(logDetail);
                logs.setCreator(Integer.valueOf(loginUser.getId()));
                logs.setType(Integer.valueOf(LogType.OPERATION_3));
                logMapper.insert(logs);
            }
        }
        return ActionResult.ok();
    }

    @Override
    public boolean checkUnique(String columnName, String value, Integer id, String tableName) {
        StringBuilder sb = new StringBuilder();
        sb.append("select count(1) from ").append(tableName).append(" Where ").append(columnName)
                .append("='").append(value).append("'");
        if (id != null) {
            sb.append(" and ").append("id!= ").append(id);
        }
        return tableMapper.checkUnique(sb.toString()) > 0;
    }

    @Override
    public String getFileServerUrl() {
        return fileServerUrl;
    }


}
