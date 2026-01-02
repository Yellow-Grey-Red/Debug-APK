package com.nfym.apk.pages.apk.Utils;
import brut.androlib.ApkDecoder;
import brut.androlib.Config;
import com.nfym.apk.common.*;
import com.nfym.apk.pages.apk.Dao;
import brut.directory.ExtFile;
import com.nfym.apk.pages.apk.event.Log_;
import com.nfym.apk.pages.apk.event.Textarea_;
import java.io.File;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.*;
public class APKDecompile {

    private Logger_ logger_;
    private Logger targetLogger;
    private AtomicBoolean is_end= new AtomicBoolean(true);
    private void doDecompile(String path){
        if(!Check.verify(0,path,null,null,null)){
            return;
        }

        is_end.set(true);

        File file = new File(path);
        String parent = file.getParent();
        parent= Paths.get(parent,file.getName().substring(0,file.getName().lastIndexOf("."))).toString();
        File file1 = new File(parent);

        Config config = new Config("");
        config.setJobs(Math.min(Runtime.getRuntime().availableProcessors(), 8));
        config.setForced(true);
        config.setVerbose(true);

        ApkDecoder decoder = new ApkDecoder(new ExtFile(file), config);

        addStream();

        Log_ log = (Log_) Dao.getInstance().get("log");
        Textarea_ textarea = (Textarea_) Dao.getInstance().get("textarea");
        String finalParent = parent;
        End.end("反编译启动",()->{
            decoder.decode(file1);
            textarea.setContent("Built into:");
            textarea.setContent(finalParent);
            log.setContent("反编译完成");
            textarea.setContent("反编译完成");
            removeStream();
        },false,(e)->{
            log.setContent("反编译APk损坏");
            removeStream();
            textarea.setContent("文件可能损坏");
        },()->{});

    }
    private void removeStream(){
        is_end.set(false);
        CapNorRest();
    }

    private void addStream(){
        CapErr();
        CapNor();
    }

    private void CapErr(){
        Worker.run(()->{
            RedirectIO redirectIO = new RedirectIO();
            redirectIO.startRIO();
            Textarea_ textarea = (Textarea_) Dao.getInstance().get("textarea");
            while (is_end.get()){
                String rio = redirectIO.RIO();
                if(rio==null){
                    continue;
                }
                textarea.setContent(rio);
            }
            redirectIO.endRIO();
        });
    }
    private void CapNor(){
        logger_= new Logger_();
        targetLogger = Logger.getLogger("brut.androlib");
        targetLogger.setLevel(Level.ALL);

        targetLogger.addHandler(logger_);
        targetLogger.setUseParentHandlers(false);
    }

    private void CapNorRest(){
        targetLogger.setUseParentHandlers(true);
        targetLogger.removeHandler(logger_);
    }
    public void main(){
        Dao instance = Dao.getInstance();
        doDecompile(instance.getText1());
    }




     class Logger_ extends Handler {

         Log_ log;
        public Logger_() {
            log = (Log_) Dao.getInstance().get("log");
        }

        @Override
        public void publish(LogRecord record) {
            if (isLoggable(record)) {
                String message = record.getMessage();

                if(message.startsWith("Skip") || message.startsWith("Chunk")){
                    return;
                }
                log.setContent(message);
            }
        }

        @Override
        public void flush() {

        }

        @Override
        public void close() throws SecurityException {

        }
    }
}
