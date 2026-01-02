package com.nfym.apk.pages.apk.Utils;

import com.nfym.apk.common.*;
import com.nfym.apk.pages.apk.Dao;

import com.nfym.apk.pages.apk.event.Log_;

import com.nfym.apk.pages.apk.event.Textarea_;

import java.io.File;
import java.io.InputStream;

public class APKSigner {
    private void doSigner(String apkPath, String jksPath, String jksName, String jksPassword){

        if(!Check.verify(2,apkPath,jksPath,jksName,jksPassword)){
            Dao.finish= num.Error;
            return;
        }

        if(jksPath==null){

            String keystoreStream = TempFile.extractResourceToTempFile("/com/nfym/apk/nfym.jks");

            if (keystoreStream == null) {
                System.out.println("JKS 资源未找到！请确保它在 src/main/resources 下");
                return;
            }
            jksPath=keystoreStream;
            jksName="nfym";
            jksPassword="123456";
        }
        String outPath=apkPath.substring(0,apkPath.lastIndexOf("."))+"_.apk";
        while (new File(outPath).exists()){
            outPath=outPath.substring(0,outPath.lastIndexOf("."))+"_.apk";
        }

        String[] commandParts = {
                "jarsigner",
                "-verbose",
                "-keystore", jksPath,
                "-storepass", jksPassword,
                "-signedjar", outPath,
                apkPath,
                jksName
        };

        Log_ log = (Log_) Dao.getInstance().get("log");
        Textarea_ textarea = (Textarea_) Dao.getInstance().get("textarea");
        String finalOutPath = outPath;
        End.end("签名启动",()->{
            Exec.execX(commandParts,log,(e)->{return e;});
            End.endl(
                    ()->{
                        textarea.setContent("签名错误"+Exec.getOut());
                        System.out.println("签名错误"+Exec.getOut());
                        Dao.finish= num.Error;
                    },
                    ()->{
                        textarea.setContent("签名失败"+Exec.getOut());
                        System.out.println("签名失败"+Exec.getOut());
                        Dao.finish= num.Error;
                    },
                    ()->{
                        textarea.setContent("Built apk into(With Signer):");
                        textarea.setContent(finalOutPath);
                        textarea.setContent("签名完成");
                        log.setContent("签名完成");
                        Dao.finish= num.Normal;
                        Dao.path=finalOutPath;
                    }
                    );
        },false,(e)->{
            log.setContent("签名文件有误");
            textarea.setContent("文件可能损坏");
            Dao.finish= num.Error;
        },()->{});
    }
    public void main(){
        Dao instance = Dao.getInstance();
        doSigner(instance.getText1(),instance.getText2(),instance.getText3(),instance.getText4());
    }

    public void main(String p){
        Dao instance = Dao.getInstance();
        doSigner(p,instance.getText2(),instance.getText3(),instance.getText4());
    }
}
