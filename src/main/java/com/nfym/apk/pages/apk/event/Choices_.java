package com.nfym.apk.pages.apk.event;

import com.nfym.apk.pages.apk.Dao;
import javafx.application.Platform;
import javafx.scene.control.ComboBox;

import java.util.List;

public class Choices_ {
    private ComboBox<String> choices;

    public Choices_(ComboBox<String> choices) {
        this.choices = choices;
    }

    public ComboBox<String> getChoices() {
        return choices;
    }

    public void setChoices(ComboBox<String> choices) {
        this.choices = choices;
    }
    public void update(List<String> devices){
        Choices_ choices1 = (Choices_) Dao.getInstance().get("choices");
        ComboBox<String> choices2 = choices1.choices;
        Platform.runLater(()->{
            choices2.getItems().clear();
            if(devices.size()>0){
                for (String s:devices) {
                    choices2.getItems().add(s);
                }
                choices2.setValue(devices.get(0));
                Dao.setEmulatorNum(choices2.getValue());
            }else {
                choices2.setPromptText("当前设备没有可用的调试设备");
            }
        });


    }

    public void OnClick() {
        Choices_ choices1 = (Choices_) Dao.getInstance().get("choices");
        ComboBox<String> choices2 = choices1.choices;
        Dao.setEmulatorNum(choices2.getValue());
    }
}
