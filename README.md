# 2025-03-14 11:33 引入mybatis-plus,简化数据库操作 支持打包本地可执行文件 
# hexa21Demo
# springboot3.3.3和JDK21使用示例 使用虚拟线程
# 编译本地应用 -DskipTests(跳过单元测试)
mvn clean  -Pnative native:compile -DquickBuild -DskipTests

./mvnw native:compile -Pnative 