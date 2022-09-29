package com.zcitc.advlib.bean;


import java.util.ArrayList;
import java.util.List;

/**
 * @author liuj
 */
public class ButtonClickRecordBean {
    List<ButtonClickRecordData> buttonClickRecordData = new ArrayList<ButtonClickRecordData>();

    public List<ButtonClickRecordData> getButtonClickRecordData() {
        return buttonClickRecordData;
    }

    public void setButtonClickRecordData(List<ButtonClickRecordData> buttonClickRecordData) {
        this.buttonClickRecordData = buttonClickRecordData;
    }
}
