import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

public class CodeGenerator {
    public static void main(String[] args) throws InterruptedException {
        //1.创建代码生成器
        AutoGenerator mpg = new AutoGenerator();

        //2. 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath=System.getProperty("user.dir");
        gc.setOutputDir("F:\\parent\\service\\service_statistics"+"/src/main/java");
        gc.setAuthor("zy");
        gc.setOpen(false);//生成后是否打开资源管理器
        gc.setFileOverride(false); //重新生成时文件是否覆盖
        gc.setServiceName("%sService"); //去掉service接口的首字母I
        gc.setIdType(IdType.ID_WORKER_STR);  //主键生成策略
        gc.setDateType(DateType.ONLY_DATE);//定义生成的实体类中日期类型
        gc.setSwagger2(true); //开启Swagger2模式

        mpg.setGlobalConfig(gc);

        // 3.数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUrl("jdbc:mysql://localhost:3306/guli?serverTimezone=GMT%2B8");
        dsc.setUsername("root");
        dsc.setPassword("zy");
        mpg.setDataSource(dsc);

        //4. 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName("sta");
        pc.setParent("com.jk");
        pc.setController("controller");
        pc.setEntity("entity");
        pc.setService("service");
        pc.setMapper("mapper");
        mpg.setPackageInfo(pc);


        //5. 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setInclude("statistics_daily");//包括的表名
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        strategy.setTablePrefix(pc.getModuleName()+"_");  //生成实体时去掉表前缀

        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true); //lombok 模型@Accessors

        strategy.setRestControllerStyle(true); //restful api风格控制器
        strategy.setControllerMappingHyphenStyle(true); //url中驼峰转连字符

        mpg.setStrategy(strategy);

        //6.执行
        mpg.execute();


    }
}
