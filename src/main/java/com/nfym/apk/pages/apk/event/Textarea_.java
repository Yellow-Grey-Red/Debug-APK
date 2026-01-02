package com.nfym.apk.pages.apk.event;

import com.nfym.apk.common.num;
import javafx.application.Platform;
import javafx.scene.control.TextArea;

public class Textarea_ {
    private TextArea textarea;
    private int count=0;

    public Textarea_(TextArea textarea) {
        this.textarea = textarea;
        textarea.setEditable(false);
    }

    public TextArea getTextarea() {
        return textarea;
    }

    public void setTextarea(TextArea textarea) {
        this.textarea = textarea;
    }

    public void clearContent(){
        Platform.runLater(()->{
            textarea.clear();
            count=0;
        });
    }

    public void setContent(String str){
        Platform.runLater(()->{
            if(str==null || str.isEmpty()){
                return;
            }
            if(count> num.MaxItem){
                StringBuilder stringBuilder = new StringBuilder();
                String[] split = textarea.getText().split("\n");
                count=num.MaxItem/2;
                for (int i = count; i < split.length; i++) {
                    stringBuilder.append(split[i]);
                }
                textarea.setText(stringBuilder.toString());
            }
            textarea.appendText(str+"\n");
            count++;
        });
    }
}
