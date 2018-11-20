package com.hanium.chj.remotepj;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AlarmRequest extends StringRequest{
    final static private String URL = "http://14.63.161.31/sendalram.php";
    private Map<String,String> parameters;

    public AlarmRequest(String userID,String Year, String Month,String Day,String Hour, String Minute, String AlarmText, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID",userID);
        parameters.put("Year", Year);
        parameters.put("Month", Month);
        parameters.put("Day", Day);
        parameters.put("Hour", Hour);
        parameters.put("Minute", Minute);
        parameters.put("AlarmText", AlarmText);
    }

    public Map<String,String> getParams() {
        return parameters;
    }
}
