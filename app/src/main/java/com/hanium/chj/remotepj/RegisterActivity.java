package com.hanium.chj.remotepj;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegisterActivity extends AppCompatActivity {
    private static String IP_ADDRESS = "14.63.161.31";
    private static String TAG = "registerPHP";

    private EditText R_idText;
    private EditText R_pwText;
    private TextView TestText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Intent beforeintent = getIntent();
        final String data = beforeintent.getStringExtra("role"); // data 변수에 role 값 저장
        Button RegisterButton = (Button) findViewById(R.id.RegisterButton);
        R_idText = (EditText) findViewById(R.id.R_idText);
        R_pwText = (EditText) findViewById(R.id.R_pwText);
        TestText = (TextView) findViewById(R.id.TestText);

        TestText.setMovementMethod(new ScrollingMovementMethod());

        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = R_idText.getText().toString();
                String pw = R_pwText.getText().toString();

                InsertData task = new InsertData();
                task.execute("http://" + IP_ADDRESS + "/register_child.php",id,pw);

                R_idText.setText("");
                R_pwText.setText("");
            }
        });


    }
    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(RegisterActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            TestText.setText(result);
            Log.d(TAG, "POST response  - " + result);
            Intent RegisterIntent = new Intent(RegisterActivity.this , RegisterPopUpActivity.class);
            if((TestText.getText().toString()).equals("Success!!")) {
                RegisterIntent.putExtra("response", "S");
                startActivityForResult(RegisterIntent,1);
            }
        }


        @Override
        protected String doInBackground(String... params) {

            String id = (String)params[1];
            String pw = (String)params[2];

            String serverURL = (String)params[0];
            String postParameters = "id=" + id + "&pw=" + pw;


            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }
                bufferedReader.close();
                return sb.toString();
            } catch (Exception e) {
                Log.d(TAG, "InsertData: Error ", e);
                return new String("Error: " + e.getMessage());
            }
        }
    }
}
