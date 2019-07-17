//package com.jingjing.sqlSession;
//
///**
// * @Author: 020188
// * @Date: 2019/7/8
// */
//
//import com.jingjing.config.MyConfiguration;
//import com.jingjing.config.MappedStatement;
//import org.dom4j.Document;
//import org.dom4j.DocumentException;
//import org.dom4j.Element;
//import org.dom4j.io.SAXReader;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.URL;
//import java.util.List;
//import java.util.Properties;
//
//
///**
// * 初始化阶段将配置文件加载到内存，保存到configuration对象中，
// * 主要是将数据库的连接信息，xxxmapper.xml加载到configuration中
// */
//public class SqlSessionFactory {
//
//    private MyConfiguration configuration = new MyConfiguration();
//
//    public SqlSessionFactory() {
//        //加载数据库信息
//        loadDbInfo();
//        //解析mapper.xml内容，保存到configuration中
//        loadMappersInfo();
//
//    }
//
//    /**
//     * 加载数据库的连接信息，设置到configuration中
//     */
//    private void loadDbInfo() {
//        InputStream dbInfo = SqlSessionFactory.class.getClassLoader().getResourceAsStream(configuration.DB_FILE);
//        Properties properties = new Properties();
//        try {
//            properties.load(dbInfo);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        configuration.setDbDriver(properties.getProperty("jdbc.driver"));
//        configuration.setDbUrl(properties.getProperty("jdbc.url"));
//        configuration.setDbUserName(properties.getProperty("jdbc.username"));
//        configuration.setDbPassWord(properties.getProperty("jdbc.password"));
//    }
//
//    private void loadMappersInfo() {
//        URL resources = null;
//        //获取存放mapper文件的路径
//        resources = SqlSessionFactory.class.getClassLoader().getResource(configuration.MAPPER_LOCATION);
//        File mappers = new File(resources.getFile());
//        //检查一个对象是否是文件夹
//        if (mappers.isDirectory()) {
//            //获取目录下的所有文件
//            File[] listFiles = mappers.listFiles();
//            if (listFiles == null || listFiles.length == 0)
//                return;
//            for (File file:listFiles) {
//                loadMappersInfo(file);
//            }
//        }
//    }
//
//    /**
//     * 加载mapper文件
//     * @param file
//     */
//    private void loadMappersInfo(File file) {
//        SAXReader reader = new SAXReader();
//        Document document = null;
//        try {
//            document = reader.read(file);
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        }
//        Element node = document.getRootElement();
//        String namespace = node.attribute("namespace").getData().toString();
//        List<Element> selects = node.elements("select");
//        if (selects==null || selects.isEmpty()) return;
//        for (Element element:selects) {
//            MappedStatement mappedStatement = new MappedStatement();
//            String id = element.attribute("id").getData().toString();
//            String resultType = element.attribute("resultType").getData().toString();
//            String sql = element.getData().toString();
//            String sourceId = namespace+"."+id;
//
//            mappedStatement.setSourceId(sourceId);
//            mappedStatement.setNamespace(namespace);
//            mappedStatement.setResultType(resultType);
//            mappedStatement.setSql(sql);
//            configuration.().put(sourceId,mappedStatement);
//        }
//    }
//
//
//}
