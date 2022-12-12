package com.cainiao;


import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class GeneratorTest {

    /**
     * 自定义模板,模板引擎是 freemarker
     */
    private static final String ENTITY_TEMPLATE_PATH = "/templates/entity.java.ftl";
    private static final String XML_TEMPLATE_PATH = "/templates/mapper.xml.ftl";
    private static final String MAPPER_TEMPLATE_PATH = "/templates/mapper.java.ftl";
    private static final String CONTROLLER_TEMPLATE_PATH = "/templates/controller.java.ftl";
    private static final String SERVICE_IMPL_TEMPLATE_PATH = "/templates/serviceImpl.java.ftl";
    private static final String SERVICE_TEMPLATE_PATH = "/templates/service.java.ftl";

    // 自定义基类
    private final static String SuperEntity = "com.mybatisplus.generator.common.BaseEntity";// 所有实体的基类(全类名)
    private final static String SuperController = "com.mybatisplus.generator.common.BaseController";// 所有控制器的基类(全类名)


    @Autowired
    private DataSourceProperties dataSource;

    /**
     * 项目路径
     */
    private static final String projectPath = System.getProperty("user.dir");

    /**
     * 要生成的表名称
     */
    private static final String tableNames = "student";

    @Test
    public void generate() {
        String projectPath = System.getProperty("user.dir");
        //最终的代码生成
        config().execute();
    }

    private AutoGenerator config() {
        AutoGenerator generator = new AutoGenerator()
                .setDataSource(getDataSourceConfig())
                .setGlobalConfig(getGlobalConfig())
                .setStrategy(getStrategyConfig())
                .setPackageInfo(getPackageConfig())
//                .setTemplate(getTemplateConfig())
                //设置模板引擎
                .setTemplateEngine(new FreemarkerTemplateEngine())
                .setCfg(getInjectionConfig());
        return generator;
    }


    /**
     * 数据源配置：连接数据库
     */
    private DataSourceConfig getDataSourceConfig() {
        return new DataSourceConfig().
                setUrl(dataSource.getUrl())
                .setDriverName(dataSource.getDriverClassName())
                .setUsername(dataSource.getUsername())
                .setPassword(dataSource.getPassword());
    }

    /**
     * 全局信息设置：设置文件输出路径、作者信息、是否覆盖文件等
     * @return
     */
    private GlobalConfig getGlobalConfig() {
//        final String outPutDir = projectPath + "/src/main/java";
//        final String author = "kpq";
        GlobalConfig globalConfig = new GlobalConfig();

        globalConfig.setOutputDir(projectPath + "/src/main/java");
        //Author设置作者
        globalConfig.setAuthor("peishunwu");
        //开启 Swagger2 注解
        globalConfig.setSwagger2(false);
        //是否覆盖文件
        globalConfig.setFileOverride(true);
        // 开启 ActiveRecord 模式
        globalConfig.setActiveRecord(true);
        // 开启 BaseResultMap（XML ResultMap,生成基本的resultMap）
        globalConfig.setBaseResultMap(true);
        // 开启 baseColumnList（XML columList,生成基本的SQL片段）
        globalConfig.setBaseColumnList(true);
        //生成后打开文件
        globalConfig.setOpen(false);
        // 自定义文件命名，注意 %s 会自动填充表实体属性！（各层文件名称方式，例如： %Mapper 生成 UserMapper）
        globalConfig.setMapperName("%sDao");
        globalConfig.setXmlName("%sMapper");
        globalConfig.setServiceName("%sService");
        globalConfig.setServiceImplName("%sServiceImpl");
        globalConfig.setControllerName("%sController");
        return globalConfig;
    }

    /**
     * 生成包名设置
     */
    private PackageConfig getPackageConfig() {
        //父级目录
        final String parentPath = "com.cainiao";
        //设置service实现类所在的包名
        final String servicePackage = "service";
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent(parentPath);
        //Controller包名
        packageConfig.setController("controller");
        //Entity包名
        packageConfig.setEntity("entity");
        //Mapper包名
        packageConfig.setMapper("dao");
        //Mapper XML包名
        packageConfig.setXml("mapper.xml");
        //Service包名
        packageConfig.setService("service");
        //Service Impl包名
        packageConfig.setServiceImpl("service.impl");
        return packageConfig;
    }

    /**
     * 策略配置
     */
    private StrategyConfig getStrategyConfig() {
        //实体类要继承的超类
        final String superEntityClass = "com.example.distributedLock.entity.base.BaseEntity";
        //超类当中的字段
        final String superEntityColumns = "last_update";
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setInclude(tableNames.split(","));
        //实体是否为lombok模型（默认 false）
        strategy.setEntityLombokModel(false);
        //生成 @RestController 控制器
        strategy.setRestControllerStyle(true);
        //设置自定义继承的Service类全称，带包名
        //strategy.setSuperServiceClass();
        //strategy.setSuperServiceImplClass();
        //设置自定义继承的Entity类全称，带包名
        strategy.setSuperEntityClass(SuperEntity);
        //设置自定义继承的Controller类全称，带包名
        strategy.setSuperControllerClass("BaseController");
        //设置自定义继承的Mapper类全称，带包名
        //strategy.setSuperMapperClass();
        //设置自定义基础的Entity类，公共字段
        //strategy.setSuperEntityColumns("id");
        //驼峰转连字符
        strategy.setControllerMappingHyphenStyle(false);
        //表名前缀
//        strategy.setTablePrefix(packageConfig.getModuleName() + "_");
        return strategy;
    }

    /**
     * 模板配置：配置自定义模板
     */
    private TemplateConfig getTemplateConfig() {
        TemplateConfig templateConfig = new TemplateConfig()
                .setXml(null).setService("").setController("")
                .setServiceImpl("templates/customizeServiceImpl.java");
        return templateConfig;
    }

    private InjectionConfig getInjectionConfig() {
       /**
         * 自定义配置
                */
        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("Author", "Author : " + this.getConfig().getGlobalConfig().getAuthor());
                this.setMap(map);
            }
        };
        /**
         * 自定义输出配置
         */
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig(XML_TEMPLATE_PATH) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                //	mapper.xml生成路径+名称		如果Entity设置了前后缀、此处注意xml的名称会跟着发生变化
//                return projectPath  + "/src/main/resources/mapper/"+modileName1+"/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
                return projectPath  + "/src/main/resources/mapper/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        //调整 Entity 生成目录
        focList.add(new FileOutConfig(ENTITY_TEMPLATE_PATH) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                //	Entity生成路径+名称		如果Entity设置了前后缀、此处名称会跟着发生变化
//                return projectPath  + "/src/main/java/cn/fyyice/"+modileName1+"/entity/" + tableInfo.getEntityName() + StringPool.DOT_JAVA;
                return projectPath  + "/src/main/java/com.cainiao/entity/" + tableInfo.getEntityName() + StringPool.DOT_JAVA;

            }
        });
        //调整 Mapper 生成目录
        focList.add(new FileOutConfig(MAPPER_TEMPLATE_PATH) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                //	Mapper生成路径+名称		如果Entity设置了前后缀、此处注意Mapper的名称会跟着发生变化
//                return projectPath  + "/src/main/java/cn/fyyice/"+modileName1+"/dao/" + tableInfo.getEntityName() + "Dao" + StringPool.DOT_JAVA;
                return projectPath  + "/src/main/java/com.cainiao/dao/" + tableInfo.getEntityName() + "Dao" + StringPool.DOT_JAVA;
            }
        });
        //调整Controller生成
        focList.add(new FileOutConfig(CONTROLLER_TEMPLATE_PATH) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                //	Controller生成路径+名称		如果Entity设置了前后缀、此处注意Controller的名称会跟着发生变化
//                return projectPath + "/src/main/java/cn/fyyice/"+modileName1+"/controller/" + tableInfo.getEntityName() + "Controller" + StringPool.DOT_JAVA;
                return projectPath + "/src/main/java/com.cainiao/controller/" + tableInfo.getEntityName() + "Controller" + StringPool.DOT_JAVA;
            }
        });
        //调整Service生成
        focList.add(new FileOutConfig(SERVICE_TEMPLATE_PATH) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                //	Service生成路径+名称		如果Entity设置了前后缀、此处注意Service的名称会跟着发生变化
//                return projectPath  + "/src/main/java/cn/fyyice/"+modileName1+"/service/" + tableInfo.getEntityName() + "Service" + StringPool.DOT_JAVA;
                return projectPath  + "/src/main/java/com.cainiao/service/" + tableInfo.getEntityName() + "Service" + StringPool.DOT_JAVA;
            }
        });

        return injectionConfig;
    }
}
