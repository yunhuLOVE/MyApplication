package com.augur.zongyang.util;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.augur.zongyang.R;

import java.util.List;

/**
 * Created by yunhu on 2018-01-11.
 */

public class SpinnerUtil {
    public static ArrayAdapter<String> getAdapter(Context context,
                                                  List<String> strings) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item, strings);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    public static ArrayAdapter<String> getCenterAdapter(Context context,
                                                        List<String> strings) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                R.layout.spiner_custom_dropdown_item, strings);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    public static ArrayAdapter<String> getLeftAdapter(Context context,
                                                         List<String> strings) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                R.layout.spiner_custom_dropdown_item2, strings);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

}
