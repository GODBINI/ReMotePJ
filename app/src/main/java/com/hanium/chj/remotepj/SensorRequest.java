package com.hanium.chj.remotepj;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SensorRequest extends StringRequest {
    final static private String URL = "http://14.63.161.31/sendsensor.php";
    private Map<String,String> parameters;

    public SensorRequest(String parentID,String sensor, Response.Listener<String> listener) {
        super(Method.POST, URL, listener ,null);
        parameters = new HashMap<>();
        parameters.put("parentID",parentID);
        parameters.put("sensor",sensor);
    }

    public Map<String,String> getParams() {
        return parameters;
    }
}
