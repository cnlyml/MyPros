package com.yishu.idcarder;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/8.
 */
public class ClientActivity extends AppCompatActivity implements View.OnClickListener
{
    private Context mContext;
    private Button btn_send;
    private ServerSocket serverSocket;
    private Socket socket;
    private InetAddress address;
    private String ip;
    private BufferedWriter writer;
    private BufferedReader reader;
    private static final int PORT = 3535;
    private static final String TAG = "==ClientActivity===";
    private String msgFrom = "12345678900";
    private String msgTo = "12345678901";
    private String name = "黄三";
    private String gender = "男";
    private String nation = "汉";
    private String birth = "19800808";
    private String IDCardAddress = "南京市雨花区软件大道180号大数据产业基地8栋409";
    private String IDCardNumber = "156321477556214592";
    private String department = "南京市公安局";
    private String lifecycle = "20100305-20200305";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        bindViews();
        sendMessage();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_send :
            {
                try
                {
                    writer.write(msgFrom + "," + msgTo + "," + name + "," + gender + "," +   //
                            nation + "," + birth + "," + IDCardAddress + "," +  //
                            IDCardNumber + "," + department + "," + lifecycle + "\n");
                    writer.flush();
                }catch (Exception e){e.printStackTrace();}
            }
        }
    }

    private void bindViews()
    {
        btn_send = (Button) findViewById(R.id.btn_send);

        btn_send.setOnClickListener(this);
    }

    private void sendMessage()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {
                    socket = new Socket("192.168.1.32", PORT);
                    address = InetAddress.getLocalHost();
                    ip = address.getHostAddress();
                    writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    writer.write(msgFrom + "," + msgTo + "," + name + "," + gender + "," +   //
                            nation + "," + birth + "," + IDCardAddress + "," +  //
                            IDCardNumber + "," + department + "," + lifecycle + "\n");
                    writer.flush();
//                    if (reader.ready())
//                    {
                        String data = null;
                        while (true)
                        {
                            data = reader.readLine();
                            Log.e(TAG, "info from server ==>" + data);
                        }
//                    }

                }catch (Exception e){e.printStackTrace();}
            }
        }).start();


    }
    private Map<String, Map<String, Object>> testHashMap()
    {
        Map<String, Object> data = new HashMap<String, Object>();
        Map<String, Map<String, Object>> datas = new HashMap<String, Map<String, Object>>();
        String key = "key";
        for (int i = 0; i < 1000; i ++)
        {
            data.put(key + i, i);
        }
        datas.put("key1", data);
        return datas;
    }
}