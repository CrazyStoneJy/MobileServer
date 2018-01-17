package me.crazystone.study.mobileserver;

import android.Manifest;
import android.content.res.AssetManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.koushikdutta.async.http.server.AsyncHttpServer;
import com.koushikdutta.async.http.server.AsyncHttpServerRequest;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.koushikdutta.async.http.server.HttpServerRequestCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends AppCompatActivity {

    AsyncHttpServer server;
    TextView txt_server_info;
    String responseString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createServer();
        showServerInfo();
    }

    private void showServerInfo() {
        txt_server_info = findViewById(R.id.txt_server_info);
        txt_server_info.setText(Wifis.getIp(getApplicationContext()) + ":" + Config.PORT);

    }

    private void createServer() {
        server = new AsyncHttpServer();
        server.get("/", new HttpServerRequestCallback() {
            @Override
            public void onRequest(AsyncHttpServerRequest request, AsyncHttpServerResponse response) {
                Log.d("TAG", "index.html");
                response.send(getIndexContent());
            }
        });
        server.get("/test", new HttpServerRequestCallback() {
            @Override
            public void onRequest(AsyncHttpServerRequest request, AsyncHttpServerResponse response) {
                response.send("test");
            }
        });
        server.get("/files", new HttpServerRequestCallback() {
            @Override
            public void onRequest(AsyncHttpServerRequest request, AsyncHttpServerResponse response) {
//                MainActivityPermissionsDispatcher.getFilesWithPermissionCheck(MainActivity.this);
                response.send(getFileNames());
            }
        });
        server.listen(Config.PORT);
    }

    @NeedsPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE})
    public void getFiles() {
        JSONArray array = new JSONArray();
        String path = Environment.getExternalStorageDirectory().getPath();
        Log.d("TAG", "path:" + path);
        File storage_file = new File(path);
        if (storage_file.exists()) {
            String[] file_names = storage_file.list();
            if (file_names != null) {
                for (String name : file_names) {
                    File file = new File(storage_file, name);
                    if (file.getName().endsWith(".mp4") || file.getName().endsWith(".txt") || file.getName().endsWith(".jpg")) {
                        try {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("name", name);
                            jsonObject.put("path", file.getAbsolutePath());
                            array.put(jsonObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        Log.d("TAG", "TEST");
        responseString = array.toString();
        Log.d("TAG", responseString);
//        return array.toString();
    }

    public String getFileNames() {
        JSONArray array = new JSONArray();
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath();
        Log.d("TAG", "path:" + path);
        File storage_file = new File(path);
        if (storage_file.exists()) {
            String[] file_names = storage_file.list();
            File[] files = storage_file.listFiles();
            if (files != null) {
                for (File file : files) {
                    Log.d("TAG", file.getName());
                }
            }
            if (file_names != null) {
                for (String name : file_names) {
                    File file = new File(storage_file, name);
                    if (file.getName().endsWith(".mp4") || file.getName().endsWith(".txt") || file.getName().endsWith(".jpg")) {
                        try {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("name", name);
                            jsonObject.put("path", file.getAbsolutePath());
                            array.put(jsonObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
//        Log.d("TAG", "TEST");
//        responseString = array.toString();
        Log.d("TAG", array.toString());
        return array.toString();
    }


    private String getIndexContent() {
        BufferedInputStream bInputStream = null;
        try {
            bInputStream = new BufferedInputStream(getAssets().open("index.html"));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len = 0;
            byte[] tmp = new byte[10240];
            while ((len = bInputStream.read(tmp)) > 0) {
                baos.write(tmp, 0, len);
            }
            return new String(baos.toByteArray(), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
//            throw e;
        } finally {
            if (bInputStream != null) {
                try {
                    bInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (server != null) {
            server.stop();
        }
    }

    @OnPermissionDenied({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE})
    public void deny() {
        Log.d("TAG", "deny");
    }

    @OnNeverAskAgain({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE})
    public void ask() {
        Log.d("TAG", "ask");
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }
}
