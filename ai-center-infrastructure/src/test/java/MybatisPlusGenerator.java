import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

import java.util.Collections;

/**
 * mybatis mapper自动生成工具类
 *
 * @author author
 */
public class MybatisPlusGenerator {

    /**
     * 创建人
     */
    public static final String AUTHOR = "xiaobaicai";

    /**
     * 要生成文件的表名
     */
    public static final String[] TABLE_NAMES =
        {"t_user"};

    public static final String TABLE_PREFIX = "t_";

    /**
     * 数据库连接地址
     */
    public static final String JDBC_URL =
        "jdbc:mysql://127.0.0.1:3306/project_archetype?useUnicode=true&noDatetimeStringSync=true&characterEncoding=utf-8"
        + "&rewriteBatchedStatements=true&allowMultiQueries=true&useServerPrepStmts=false&cachePrepStmts=false"
        + "&useCompression=true&serverTimezone=Asia/Shanghai&connectTimeout=2000&autoReconnect=true&useSSL=false"
        + "&tinyInt1isBit=false";

    /**
     * 数据库连接用户名
     */
    public static final String USER_NAME = "root";

    /**
     * 数据库连接密码
     */
    public static final String PWD = "cjy123456";

    /**
     * 包路径
     */
    public static final String PKG_PATH = "com.xiaobaicai.dao";

    /**
     * 项目路径--直接生成在对应项目中
     */
    public static final String PROJECT_PATH =
        System.getProperty("user.dir") + "/AutoGenerateTmp/ai-center-infrastructure";

    public static void main(String[] args) {
        // 代码生成器
        FastAutoGenerator.create(JDBC_URL, USER_NAME, PWD).globalConfig(builder -> {
            builder.author(AUTHOR).outputDir(PROJECT_PATH + "/src/main/java") // 输出路径(写到java目录)
                // .enableSwagger()
                .disableOpenDir().commentDate("yyyy-MM-dd").fileOverride();

        }).packageConfig(builder -> {
            builder.parent(PKG_PATH)
                // .moduleName("模块名")
                .entity("dataobject").service("service").serviceImpl("service.impl")
                // .controller("controller")
                .mapper("mapper").xml("mapper")
                .pathInfo(Collections.singletonMap(OutputFile.mapperXml, PROJECT_PATH + "/src/main/resources/mapper"));
        }).strategyConfig(builder -> {
            builder.addInclude(TABLE_NAMES).addTablePrefix(TABLE_PREFIX).serviceBuilder()
                .formatServiceFileName("%sDaoService").formatServiceImplFileName("%sDaoServiceImpl")
                .superServiceClass(IService.class).superServiceImplClass(ServiceImpl.class).entityBuilder()
                .formatFileName("%sDO").naming(NamingStrategy.underline_to_camel)
                .columnNaming(NamingStrategy.underline_to_camel).enableLombok().logicDeleteColumnName("deleted")
                .enableTableFieldAnnotation()
                // .formatFileName("%sController")
                // .enableRestStyle()
                .mapperBuilder().enableBaseResultMap() // 生成通用的resultMap
                .superClass(BaseMapper.class).formatMapperFileName("%sMapper").enableMapperAnnotation()
                .formatXmlFileName("%sMapper");
        }).templateEngine(new FreemarkerTemplateEngine()).execute();
    }
}
