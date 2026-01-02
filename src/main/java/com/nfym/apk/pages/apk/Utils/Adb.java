package com.nfym.apk.pages.apk.Utils;

import com.nfym.apk.Impl.Str;
import com.nfym.apk.common.End;
import com.nfym.apk.common.Worker;
import com.nfym.apk.common.num;
import com.nfym.apk.pages.apk.Dao;
import com.nfym.apk.pages.apk.event.Choices_;
import com.nfym.apk.pages.apk.event.Log_;
import com.nfym.apk.pages.apk.event.Process_;
import com.nfym.apk.pages.apk.event.Textarea_;
import javafx.scene.control.ComboBox;
import net.dongliu.apk.parser.ApkFile;
import net.dongliu.apk.parser.bean.ApkMeta;
import net.dongliu.apk.parser.bean.ApkSigner;
import net.dongliu.apk.parser.bean.ApkV2Signer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

public class Adb {

    public static void detectDevices(){

        Log_ log = (Log_)Dao.getInstance().get("log");
        log.setContent("正在重启adb");

        Process_ process = (Process_)Dao.getInstance().get("process");
        process.start();

        try{
            Worker.run(()->{
                reboot();
                log.setContent("正在寻找设备");
                getDevices();
                log.clearContent();
                process.stop();
            });
        }catch (Exception e){
            e.printStackTrace();
            log.clearContent();
            process.stop();
            Textarea_ textarea = (Textarea_)Dao.getInstance().get("textarea");
            textarea.setContent("文件可能损坏");
        }
    }

    public static void reboot(){
        Exec.exec(new String[]{num.adbPath, "kill-server"});
        Exec.exec(new String[]{num.adbPath, "start-server"});
    }

    public static String[] filter(String[] command){
        String[] a={num.adbPath,"-s",Dao.getEmulatorNum()};
        String[] strings = Arrays.copyOf(a, command.length + 3);
        System.arraycopy(command, 0, strings, 3, command.length);
        return strings;
    }

    public static void runAPK(String packages) {
        Exec.exec(filter(new String[]{"shell", "am", "start", "-n", packages+"/.AppActivity"}));
        End.endl(
                ()->{

                },
                ()->{
                    Textarea_ textarea = (Textarea_)Dao.getInstance().get("textarea");
                    textarea.setContent(packages+" 运行失败");
                    textarea.setContent(Exec.getOut());
                },
                ()->{
                    Textarea_ textarea = (Textarea_)Dao.getInstance().get("textarea");
                    textarea.setContent(packages+" 运行成功");
                }
        );
    }

    public static void install(String apkPath) {

        Log_ log = (Log_)Dao.getInstance().get("log");
        log.setContent("正在安装应用...");
        Process_ process = (Process_)Dao.getInstance().get("process");
        process.start();

        Exec.exec(filter(new String[]{
                "install",
                apkPath
        }));

        End.endl(
                ()->{

                },
                ()->{
                    Textarea_ textarea = (Textarea_)Dao.getInstance().get("textarea");
                    textarea.setContent("安装失败");
                    textarea.setContent(Exec.getOut());
                },
                ()->{
                    Textarea_ textarea = (Textarea_)Dao.getInstance().get("textarea");
                    textarea.setContent("安装成功");
                    log.setContent("正在启动应用");
                    runAPK(getLocalPackName(apkPath));
                }
        );

        log.clearContent();
        process.stop();


    }



    public static void uninstall(String packages) {
        End.end("正在卸载应用",()->{
            Exec.exec(filter(new String[]{"shell", "pm", "uninstall", packages}));
            End.endl(
                    ()->{

                    },
                    ()->{
                        Textarea_ textarea = (Textarea_)Dao.getInstance().get("textarea");
                        textarea.setContent("当前设备并未安装 "+packages);
                    },
                    ()->{
                        Textarea_ textarea = (Textarea_)Dao.getInstance().get("textarea");
                        textarea.setContent(packages+" 卸载成功");
                    }
            );
        },true,(e)->{},()->{});
    }


    public static String getLocalPackName(String apkPath) throws IOException {
        // 获取包名和签名
        String result =null;
        ApkFile apkFile = new ApkFile(apkPath);
        ApkMeta apkMeta = apkFile.getApkMeta();
        result =apkMeta.getPackageName();
        apkFile.close();
        return result;
    }

    public static String getLocalPackNameFromXML(String apkPath) {
        try{
            // 1. 创建XML文档构建工厂
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // 2. 创建文档构建器
            DocumentBuilder builder = factory.newDocumentBuilder();
            // 3. 加载并解析XML文件，得到文档对象
            Document document = builder.parse(new File(apkPath));
            // 4. 获取根节点（<manifest>标签）
            Element rootElement = document.getDocumentElement();
            // 5. 获取根节点的package属性值（核心：包名存储在该属性中）

            return rootElement.getAttribute("package");
        }catch (Exception e){
            return null;
        }

    }

    public static boolean isSigner(String apkPath) throws Exception {
        ApkFile apkFile = new ApkFile(apkPath);
        List<ApkV2Signer> v2 = apkFile.getApkV2Singers();
        List<ApkSigner> v1 = apkFile.getApkSingers();
        boolean hasV1 = v1.isEmpty();
        boolean hasV2Plus = v2.isEmpty();
        apkFile.close();
        return hasV1 && hasV2Plus;
    }



    public static void getDevices() {
        Exec.exec(new String[]{num.adbPath, "devices"});
        End.endl(
                ()->{

                },
                ()->{
                    System.out.println("执行 adb devices 命令失败：" + Exec.getOut());
                },
                ()->{
                    List<String> strings = parseDevices(Exec.getOut());
                    Choices_ choices1 = (Choices_) Dao.getInstance().get("choices");
                    choices1.update(strings);
                }
        );
    }


    public static void detectADB() {
        Worker.run(Adb::detectDevices);
    }

    private static List<String> parseDevices(String output) {
        List<String> devices = new ArrayList<>();
        String[] lines = output.split("\n");

        for (String line : lines) {
            if (line.contains("List of devices attached") || line.trim().isEmpty()) {
                continue;
            }
            if (Pattern.matches("^[a-zA-Z0-9_.-]*\\s+device.*$", line.trim())) {
                devices.add(line.trim().split("\\s+")[0]);
            }
        }
        return devices;
    }
}
