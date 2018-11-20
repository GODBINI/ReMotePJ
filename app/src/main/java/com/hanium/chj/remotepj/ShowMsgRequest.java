package com.hanium.chj.remotepj;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ShowMsgRequest extends StringRequest{
    final static private String URL = "http://14.63.161.31/seemsg.php";
    private Map<String,String> parameters;

    public ShowMsgRequest(String parentID, Response.Listener<String> listener) {
        super(Method.POST, URL, listener,null);
        parameters = new HashMap<>();
        parameters.put("parentID",parentID);
    }

    public Map<String,String> getParams () {
        return parameters;
    }
}
