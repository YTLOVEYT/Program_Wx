package com.example.program_wx.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.program_wx.MyConst;
import com.example.program_wx.entity.Param;

import java.io.File;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 网络访问工具类-封装
 * Created by YinTao on 2017/12/8.
 */

public class OkHttpUtil
{
    private Context context;
    private OkHttpClient okHttpClient;
    private HttpCallBack httpCallBack;

    private static final int SUCCESSFUL = 10001;//请求成功
    private static final int FAILED = 10002;//请求失败

    /** 初始化OkHttpClient */
    public OkHttpUtil(Context context)
    {
        this.context = context;
        HttpsUtil.SSLParams sslParams = HttpsUtil.getSslSocketFactory(null, null, null);
        if (okHttpClient == null)
        {
            okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .hostnameVerifier(new HostnameVerifier() //host验证
                    {
                        @Override
                        public boolean verify(String hostname, SSLSession session)
                        {
                            return true;
                        }
                    })
                    .sslSocketFactory(sslParams.sslSocketFactory, sslParams.trustManager)
                    .build();
        }
    }

    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case SUCCESSFUL:
                    LogUtil.e("网络请求response=" + (String) msg.obj);
                    httpCallBack.onResponse((String) msg.obj);
                    break;
                case FAILED:
                    httpCallBack.onFailure((String) msg.obj);
                    break;
            }
        }
    };

    ///////////////////////////////////////////////////////////////////////////
    // 封装所有的Http请求
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 普通post请求
     * @param url          地址
     * @param params       参数
     * @param httpCallBack 回调
     */
    public void post(String url, List<Param> params, HttpCallBack httpCallBack)
    {
        this.httpCallBack = httpCallBack;
        FormBody.Builder bodyBuilder = new FormBody.Builder();//参数
        String session = SpUtil.getString(MyConst.SESSION);
        if (!TextUtils.isEmpty(session))
        {
            LogUtil.e("有session=" + session);
            bodyBuilder.add(MyConst.SESSION, session);
        }
        for (Param param : params)
        {
            LogUtil.e("param=" + param);
            bodyBuilder.add(param.getKey(), param.getValue());
        }
        RequestBody requestBody = bodyBuilder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        startRequest(request);//网络请求
    }

    /**
     * 带文件的post的请求
     * @param url          地址
     * @param params       参数
     * @param files        文件地址
     * @param httpCallBack 回调
     */
    public void post(String url, List<Param> params, List<File> files, HttpCallBack httpCallBack)
    {
        this.httpCallBack = httpCallBack;
        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder();
        bodyBuilder.setType(MultipartBody.FORM);
        for (Param param : params)
        {
            LogUtil.e("param=" + param.toString());
            bodyBuilder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + param.getKey() + "\""),
                    RequestBody.create(MediaType.parse(guessMimeType(param.getKey())), param.getValue()));
        }
        for (File file : files)
        {
            if (file != null && file.exists())
            {
                LogUtil.e("file=" + file.getName());
                bodyBuilder.addPart(Headers.of("Content-Disposition",
                        "form-data; name=\"" + "file" + "\"; filename=\"" + file.getName() + "\""),
                        RequestBody.create(MediaType.parse(guessMimeType(file.getName())), file));
            }
        }
        RequestBody requestBody = bodyBuilder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        startRequest(request);//网络请求
    }


    /** 网络请求 */
    private void startRequest(Request request)
    {
        if (CommonUtil.isNetWorkConnected())
        {
            okHttpClient.newCall(request).enqueue(new Callback() //异步
            {
                @Override
                public void onFailure(Call call, IOException e)
                {
                    Message message = handler.obtainMessage();
                    message.obj = e.getMessage();
                    message.what=FAILED;
                    message.sendToTarget();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException
                {
                    LogUtil.e("网络请求response=" + response);
                    // FIXME: 2017/12/8 网络请求中如果包含session 则说明账号在异地登录，强制下线 ywkk123
                    Message message = handler.obtainMessage();
                    message.what=SUCCESSFUL;
                    message.obj = response.body().string();
                    LogUtil.e("网络请求response=" + (String) message.obj);
                    message.sendToTarget();
                }
            });
        }
        else
        {
            Toast.makeText(context, "检查网络", Toast.LENGTH_SHORT).show();
        }

    }

    /** 参数编码 */
    private String guessMimeType(String path)
    {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null)
        {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }

    /** 网络访问回调接口 */
    public interface HttpCallBack
    {
        /** 网络访问正确回调 */
        void onResponse(String response);

        /** 网络访问错误回调 */
        void onFailure(String errorMsg);
    }

    /** 带进度下载的回调接口 */
    public interface ProgressDownLoadCallBack
    {
        /** 下载成功 */
        void onSuccess();

        /** 下载进度 */
        void onProgress(int progress);

        /** 下载失败 */
        void onFailure();
    }
}
