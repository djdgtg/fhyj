package com.fhyj.cms.bean;

import java.util.ArrayList;
import java.util.List;


/**
 * @author qinc
 * @description
 * @date 2018/12/11
 */
public class Constants {


    /**
     * 系统异常
     */
    public static final String MESSAGE_E01001 = "E01001";

    /**
     * 操作失败，请重试
     */
    public static final String MESSAGE_E01002 = "E01002";

    /**
     * 接口正常
     */
    public static final String MESSAGE_I01001 = "I01001";

    /**
     * 接口异常
     */
    public static final String MESSAGE_I01002 = "I01002";

    /**
     * 配置属性：管理系统普通用户角色ID
     */
    public static final String MANAGE_USER_ROLEID = "manage.user.roleId";

    /**
     * 系统字段
     */
    public static final String CODE_C010_0001 = "C010_0001";

    /**
     * 自定义字段
     */
    public static final String CODE_C010_0002 = "C010_0002";

    /**
     * 日志类型-页面
     */
    public static final String CODE_C030_0001 = "C030_0001";

    /**
     * 操作类型-查询
     */
    public static final String CODE_C031_0001 = "C031_0001";

    /**
     * 操作类型-新增
     */
    public static final String CODE_C031_0002 = "C031_0002";

    /**
     * 操作类型-编辑
     */
    public static final String CODE_C031_0003 = "C031_0003";

    /**
     * 删除
     */
    public static final String CODE_C031_0004 = "C031_0004";

    /**
     * 禁止实例
     */
    private Constants() {

    }

    /**
     * 默认密码，加盐
     */
    public static final String DefaultUserPasswd = "689EE787E0EA220E6D5A72163EB8C437"; //"123456"
    //public static final String DefaultUserPasswd = "e10adc3949ba59abbe56e057f20f883e"; //"123456"小写加密
    /**
     * 资源库管理菜单编号
     */
    public static final int KnowledgeManagerMenuID = 100;

    public static final String IndexFolderName = "RIPS";

    /**
     * 自建库表前缀
     */
    public static final String SelfDBPrefix = "front_";
    /**
     * mysql数据库保留字
     */
    public static final String[] SysReserved = new String[]{"ADD", "ALL", "ALTER", "ANALYZE", "AND", "AS", "ASC", "ASENSITIVE", "BEFORE", "BETWEEN", "BIGINT", "BINARY", "BLOB", "BOTH", "BY", "CALL", "CASCADE", "CASE", "CHANGE", "CHAR", "CHARACTER", "CHECK", "COLLATE", "COLUMN", "CONDITION", "CONNECTION", "CONSTRAINT", "CONTINUE", "CONVERT", "CREATE", "CROSS", "CURRENT_DATE", "CURRENT_TIME", "CURRENT_TIMESTAMP", "CURRENT_USER", "CURSOR", "DATABASE", "DATABASES", "DAY_HOUR", "DAY_MICROSECOND", "DAY_MINUTE", "DAY_SECOND", "DEC", "DECIMAL", "DECLARE", "DEFAULT", "DELAYED", "DELETE", "DESC", "DESCRIBE", "DETERMINISTIC", "DISTINCT", "DISTINCTROW", "DIV", "DOUBLE", "DROP", "DUAL", "EACH", "ELSE", "ELSEIF", "ENCLOSED", "ESCAPED", "EXISTS", "EXIT", "EXPLAIN", "FALSE", "FETCH", "FLOAT", "FLOAT4", "FLOAT8", "FOR", "FORCE", "FOREIGN", "FROM", "FULLTEXT", "GOTO", "GRANT", "GROUP", "HAVING", "HIGH_PRIORITY", "HOUR_MICROSECOND", "HOUR_MINUTE", "HOUR_SECOND", "IF", "IGNORE", "IN", "INDEX", "INFILE", "INNER", "INOUT", "INSENSITIVE", "INSERT", "INT", "INT1", "INT2", "INT3", "INT4", "INT8", "INTEGER", "INTERVAL", "INTO", "IS", "ITERATE", "JOIN", "KEY", "KEYS", "KILL", "LABEL", "LEADING", "LEAVE", "LEFT", "LIKE", "LIMIT", "LINEAR", "LINES", "LOAD", "LOCALTIME", "LOCALTIMESTAMP", "LOCK", "LONG", "LONGBLOB", "LONGTEXT", "LOOP", "LOW_PRIORITY", "MATCH", "MEDIUMBLOB", "MEDIUMINT", "MEDIUMTEXT", "MIDDLEINT", "MINUTE_MICROSECOND", "MINUTE_SECOND", "MOD", "MODIFIES", "NATURAL", "NOT", "NO_WRITE_TO_BINLOG", "NULL", "NUMERIC", "ON", "OPTIMIZE", "OPTION", "OPTIONALLY", "OR", "ORDER", "OUT", "OUTER", "OUTFILE", "PRECISION", "PRIMARY", "PROCEDURE", "PURGE", "RAID0", "RANGE", "READ", "READS", "REAL", "REFERENCES", "REGEXP", "RELEASE", "RENAME", "REPEAT", "REPLACE", "REQUIRE", "RESTRICT", "RETURN", "REVOKE", "RIGHT", "RLIKE", "SCHEMA", "SCHEMAS", "SECOND_MICROSECOND", "SELECT", "SENSITIVE", "SEPARATOR", "SET", "SHOW", "SMALLINT", "SPATIAL", "SPECIFIC", "SQL", "SQLEXCEPTION", "SQLSTATE", "SQLWARNING", "SQL_BIG_RESULT", "SQL_CALC_FOUND_ROWS", "SQL_SMALL_RESULT", "SSL", "STARTING", "STRAIGHT_JOIN", "TABLE", "TERMINATED", "THEN", "TINYBLOB", "TINYINT", "TINYTEXT", "TO", "TRAILING", "TRIGGER", "TRUE", "UNDO", "UNION", "UNIQUE", "UNLOCK", "UNSIGNED", "UPDATE", "USAGE", "USE", "USING", "UTC_DATE", "UTC_TIME", "UTC_TIMESTAMP", "VALUES", "VARBINARY", "VARCHAR", "VARCHARACTER", "VARYING", "WHEN", "WHERE", "WHILE", "WITH", "WRITE", "X509", "XOR", "YEAR_MONTH", "ZEROFILL"};
    /**
     * 站点名称，用于静态页面生成时的页面标题
     */
    public static final String WebSiteName = "知识资源服务中心";
    /**
     * 模板路径,用于静态生成
     */
    public static final String TemplateFilePath = "D://file/templateFile/";
    /**
     * 页面路径，静态生成的文件存储路径
     */
    public static final String HtmlFilePath = "D://file/view/";
    /**
     * 上传文件路径
     */
    public static final String ContentFilePath = "D://file/uploadFile";
    /**
     * 文件虚拟目录名称，默认Upload
     */
//	public static final String VirtualDirectory = StringHelper.isNullOrEmpty(ConfigurationManager.AppSettings.Get("VirtualDirectory")) ? "Upload" : ConfigurationManager.AppSettings.Get("VirtualDirectory");
//
//	public static final java.util.HashMap<String, UploadParamModel> UploadParams = new java.util.HashMap<String, UploadParamModel>();

    //字段来源
    public static final int COlUMNSOURCE_DIC = 1;
    public static final int COlUMNSOURCE_CLASS = 2;
    public static final int COlUMNSOURCE_DB = 3;

    //发布类型
    public static final int DATASTEPTYPE_JPUB = 2;
    public static final int DATASTEPTYPE_ONLYPUB = 4;
    public static final int DATASTEPTYPE_NOPUB = 5;

    //资源状态
    public static final int RE_STATUS_FORINDEX = 0;
    public static final int RE_STATUS_FORPROCESS = 4;
    public static final int RE_STATUS_FORPUB = 8;
    public static final int RE_STATUS_PUBLISHED = 9;

    //字段类型
    public static final int COLUMN_TYPE_TEXT = 1;
    public static final int COLUMN_TYPE_TEXTAREA = 2;
    public static final int COLUMN_TYPE_RICHTEXT = 7;
    public static final int COLUMN_TYPE_RADIO = 3;
    public static final int COLUMN_TYPE_CHECKBOX = 4;
    public static final int COLUMN_TYPE_FILE = 8;
    public static final int COLUMN_TYPE_DATATIME = 10;
    public static final int COLUMN_TYPE_DATE = 11;
    public static final int COLUMN_TYPE_INT = 17;
    public static final int COLUMN_TYPE_DECIMAL = 18;

    //索引类型
    public static final String INDEXTYPE_DISTRIBUTED = "distributed";
    public static final String INDEXTYPE_PLAIN = "plain";
    //索引数据源类型
    public static final String DB_SOURCE_TYPE_MYSQL = "mysql";
    public static final String DB_SOURCE_TYPE_ODBC = "odbc";

    //状态
    public static final int STATUS_normal = 1;
    public static final int STATUS_del = -1;
    public static final int STATUS_lock = -2;


    public static List<MetadataCustomBean> initSysMetaList() {
        List<MetadataCustomBean> sysBaseMetaList = new ArrayList<MetadataCustomBean>();
        MetadataCustomBean seqId = new MetadataCustomBean(null, "id", "资源编号", 1, null, null, "int", null, 0, 1, null, null, null, 0, null, "base");
        MetadataCustomBean createdTime = new MetadataCustomBean(null, "create_time", "创建时间", 10, null, null, "TIMESTAMP", null, 0, 1, null, null, "CURRENT_TIMESTAMP", 1, null, "sys");
        MetadataCustomBean creatorId = new MetadataCustomBean(null, "creator", "创建人", 1, null, null, "int", null, 0, 1, null, null, null, 0, null, "sys");
        MetadataCustomBean status = new MetadataCustomBean(null, "status", "状态", 3, 1, "status", "int", null, 0, 1, null, null, null, 1, null, "sys");
        sysBaseMetaList.add(seqId);
        sysBaseMetaList.add(createdTime);
        sysBaseMetaList.add(creatorId);
        sysBaseMetaList.add(status);
        return sysBaseMetaList;
    }
}
