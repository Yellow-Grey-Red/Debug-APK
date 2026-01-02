(1)
非模块化包 - > 模块化包
    1.生成module-info.java:
        jdeps --generate-module-info . --ignore-missing-deps apktool-lib.jar
            jdeps:java自带工具，查看依赖关系
            --generate-module-info:module-info.java
            .:放置当前目录
            --ignore-missing-deps:忽略错误信息
            apktool-lib.jar:要分析的目标

    2.module-info.java - > module-info.class
        javac --patch-module apktool.lib=apktool-lib.jar --release 17 apktool.lib/module-info.java
        解释：javac : java编译命令，将Java编译为class文件
             --patch-module apktool.lib=apktool-lib.jar: 指定补丁位置。
             --release 17:java版本。保持和本地版本一致，不然会出现特性不支持。(可选)
             apktool.lib/module-info.java:编译的文件
        将apktool.lib/module-info.java编译，如果编译的时候遇到模块名apktool.lib，他依赖的包就去apktool-lib.jar中找。
                module apktool.lib {          // ← 这里定义了模块名
                    exports brut.apktool;
                    requires java.base;
                }
    
    3.将module-info.class注入jar
        jar uf apktool-lib.jar -C apktool.lib module-info.class

        jar :JDK 自带的 JAR 工具，用于创建/更新 JAR 文件
        u	:update：更新已存在的 JAR（而不是新建）
        f	:指定 JAR 文件名（后面紧跟文件名）
        apktool-lib.jar	:要被更新的目标 JAR
        -C apktool.lib	:切换到 apktool.lib 目录（作为工作目录）
        module-info.class :要添加到 JAR 中的文件（相对于 apktool.lib/ 目录）

        注意：
            module-info.class位置：

            jar uf apktool-lib.jar apktool.lib/module-info.class   ?
            apktool-lib.jar
                └── apktool.lib/
                    └── module-info.class
            
            jar uf apktool-lib.jar -C apktool.lib module-info.class  ?
            
                apktool-lib.jar
                    ├── module-info.class

(2)使用插件将非模块化 -> 模块化：
                                     <plugin>
                                        <groupId>org.moditect</groupId>
                                        <artifactId>moditect-maven-plugin</artifactId>
                                        <version>1.0.0.RC2</version>
                                        <executions>
                                        <execution>
                                        <phase>generate-resources</phase>
                                        <goals>
                                        <goal>add-module-info</goal>
                                        </goals>
                                        <configuration>
                                        <outputDirectory>${project.build.directory}/modules</outputDirectory>
                                        <modules>
                                        <module>
                                        <artifact>
                           需要改        <groupId>net.dongliu</groupId>                       |
                           需要改             <artifactId>apk-parser</artifactId>             |->apk-parser-2.6.10.jar\META-INF\maven\net.dongliu\apk-parser\pom.properties
                           需要改         <version>2.6.10</version>                           |
                                        </artifact>
    moduleInfoSource内也要改           <moduleInfoSource>                   来自(1)中的module-info.java
                                        module net.dongliu.apkparser {                    
                                        requires transitive java.xml;

                                        exports net.dongliu.apk.parser;
                                        exports net.dongliu.apk.parser.bean;
                                        exports net.dongliu.apk.parser.cert.asn1;
                                        exports net.dongliu.apk.parser.cert.asn1.ber;
                                        exports net.dongliu.apk.parser.cert.pkcs7;
                                        exports net.dongliu.apk.parser.exception;
                                        exports net.dongliu.apk.parser.parser;
                                        exports net.dongliu.apk.parser.struct;
                                        exports net.dongliu.apk.parser.struct.dex;
                                        exports net.dongliu.apk.parser.struct.resource;
                                        exports net.dongliu.apk.parser.struct.signingv2;
                                        exports net.dongliu.apk.parser.struct.xml;
                                        exports net.dongliu.apk.parser.struct.zip;
                                        exports net.dongliu.apk.parser.utils;
                                        exports net.dongliu.apk.parser.utils.xml;

                                        }
                                    </moduleInfoSource>
                                </module>
                            </modules>
                        </configuration>
                    </execution>
                </executions>
            </plugin>''


(3)非模块化打包：
    模块化打包，可以使用Jlink官方工具，方便，可以调试，一键完成，尤其是体积小
    非模块打包：不用折腾，方法很多，工具也多，但是体积大，随便就是>100MB
<!--            <plugin>-->
<!--                <groupId>io.gitee.podigua</groupId>-->
<!--                <artifactId>maven-jfx-plugin</artifactId>-->
<!--                <version>0.0.2</version>-->
<!--                <configuration>-->
<!--                    <mainClass>com.nfym.apk.Main</mainClass>-->    将<version>1.0-SNAPSHOT</version> -SNAPSHOT去掉。
<!--                </configuration>-->
<!--            </plugin>-->
        需要额外安装wix工具。https://github.com/wixtoolset/wix3/releases 下载exe就行。安装后C:\Program Files (x86)\WiX Toolset v3.14加入环境变量

    还可以将项目打包为fat jar再用jpackage打包。



(4)将本地文件夹中jar安装到本地仓库：
    1.命令行
        mvn install:install-file \
        -Dfile=lib/some-lib-1.0.jar \
        -DgroupId=com.example \
        -DartifactId=some-lib \
        -Dversion=1.0 \
        -Dpackaging=jar
    2.maven插件
        maven-install-plugin


