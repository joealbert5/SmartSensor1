package com.smartsensor.www;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;

/**
 * Created by joeal_000 on 4/26/2017.
 */

public class SpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {
    public String shortloc = "";
    public String type = "";
    public int num = 0;
    public String code = shortloc + type + 0 + num;

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        String s = parent.getItemAtPosition(pos).toString();
        //new UseMachineActivity().updateTV();

    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    public String getCodeFromSpinners(){
        return code;
    }

    public String getType(){
        return type;
    }
    public String getShortloc(){
        return shortloc;
    }
    public int getNum(){
        return num;
    }
}
