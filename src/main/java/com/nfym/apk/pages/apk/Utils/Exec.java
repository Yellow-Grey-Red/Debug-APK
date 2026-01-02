package com.nfym.apk.pages.apk.Utils;

import com.nfym.apk.Impl.Call;
import com.nfym.apk.Impl.Str;
import com.nfym.apk.common.ProcessManager;
import com.nfym.apk.common.Worker;
import com.nfym.apk.common.num;
import com.nfym.apk.pages.apk.Dao;
import com.nfym.apk.pages.apk.event.Log_;
import com.nfym.apk.pages.apk.event.Process_;
import com.nfym.apk.pages.apk.event.Textarea_;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

public class Exec {
    private static String Out=null;

    private static int status=-100;

    private static boolean end=true;

    public static String getOut(){
        return Out;
    }
    public static int getStatus(){
        return status;
    }


    public static void execX(String[] mandate, Log_ log, Str str) {
        try {
            ProcessBuilder processbuilder = new ProcessBuilder(mandate);
            processbuilder.redirectErrorStream(true);
            Process process = processbuilder.start();
            ProcessManager.addProcess(process);

            Worker.run(()->{
                StringBuilder output = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), Charset.defaultCharset()));
                String line=null;
                while ((line=reader.readLine())!=null || end) {
                    if(line==null){
                        continue;
                    }
                    line= str.call(line);
                    log.setContent(line);
                    output.append(line).append("\n");
                }
                reader.close();
                Out = output.toString();
                end=true;
            });


            process.waitFor(5, TimeUnit.SECONDS);
            end=false;
            if (process.exitValue() == 0) {
                status = num.Normal;
            } else {
                status = num.Error;
            }
        } catch (Exception e) {
            e.printStackTrace();
            status = num.CommandError;
        }

    }
    public static void exec(String[] mandate) {
        try {
            ProcessBuilder processbuilder = new ProcessBuilder(mandate);
            processbuilder.redirectErrorStream(true);
            Process process = processbuilder.start();

            process.waitFor(5, TimeUnit.SECONDS);
            ProcessManager.addProcess(process);

            StringBuilder output = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), Charset.defaultCharset()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            Out = output.toString();
            reader.close();

            if (process.exitValue() == 0) {
                status = num.Normal;
            } else {
                status = num.Error;
            }
        } catch (Exception e) {
            e.printStackTrace();
            status = num.CommandError;
        }

    }
    public static void execY(String[] mandate) {
        Textarea_ textarea = (Textarea_) Dao.getInstance().get("textarea");
        try {
            ProcessBuilder processbuilder = new ProcessBuilder(mandate);
            processbuilder.redirectErrorStream(true);
            Process process = processbuilder.start();
            process.waitFor(8, TimeUnit.SECONDS);
            ProcessManager.addProcess(process);

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), Charset.defaultCharset()));
            String line;
            while ((line = reader.readLine()) != null) {
                textarea.setContent(line);
            }
            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
