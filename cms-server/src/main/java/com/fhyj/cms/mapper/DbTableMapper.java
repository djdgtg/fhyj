package com.fhyj.cms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fhyj.cms.bean.TableCustomBean;
import com.fhyj.cms.bean.TableManageSearchBean;
import com.fhyj.cms.entity.DbTable;

import java.util.LinkedHashMap;
import java.util.List;

/**
* @author djdgt
* @description 针对表【db_table(模型数据库表)】的数据库操作Mapper
* @createDate 2023-03-07 15:09:45
* @Entity generator.entity.DbTable
*/
public interface DbTableMapper extends BaseMapper<DbTable> {

    int alterTable(String sql);

    int checkUnique(String toString);

    List<TableCustomBean> getTableCustomBean(TableManageSearchBean searchBean);

    int getResCount(String sqlCount);

    List<LinkedHashMap<String, Object>> getResList(String sqlList);
}




