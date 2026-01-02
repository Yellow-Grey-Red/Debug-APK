package com.nfym.apk;

import com.nfym.apk.common.ProcessManager;
import com.nfym.apk.common.TempFile;
import com.nfym.apk.common.num;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(Main.class.getResource("main.css").toExternalForm());
        stage.setTitle("枫叶APK");
        stage.getIcons().add(new Image(String.valueOf(Main.class.getResource("alpha.png"))));
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        new ProcessBuilder(num.adbPath,"kill-server").start();
        ProcessManager.cleanupProcesses();
        long currentPid = ProcessHandle.current().pid();
        ProcessHandle.allProcesses()
                .filter(ph -> {
                    try {
                        return ph.parent().isPresent() && ph.parent().get().pid() == currentPid;
                    } catch (Exception e) {
                        return false; // 忽略权限等问题
                    }
                })
                .forEach(ProcessHandle::destroyForcibly);
    }

    public static void main(String[] args) {
        launch();
    }
}
