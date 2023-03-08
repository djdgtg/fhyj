package com.fhyj.cms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fhyj.cms.bean.MenuCustomBean;
import com.fhyj.cms.entity.BaseMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author djdgt
 * @description 针对表【base_menu(菜单表)】的数据库操作Mapper
 * @createDate 2023-03-07 15:09:45
 * @Entity generator.entity.BaseMenu
 */
public interface BaseMenuMapper extends BaseMapper<BaseMenu> {

    List<MenuCustomBean> getRolesMenus(Integer userId);

    List<String> getMenusStringList(String contextPath);

    List<String> getMenusStringListByUserId(@Param("id") Integer id, @Param("contextPath") String contextPath);
}




