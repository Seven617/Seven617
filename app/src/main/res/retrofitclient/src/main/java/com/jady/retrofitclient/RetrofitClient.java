package com.jady.retrofitclient;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.jady.retrofitclient.callback.FileResponseResult;
import com.jady.retrofitclient.callback.HttpCallback;
import com.jady.retrofitclient.download.DownloadInterceptor;
import com.jady.retrofitclient.interceptor.HeaderAddInterceptor;
import com.jady.retrofitclient.interceptor.OffLineIntercept;
import com.jady.retrofitclient.interceptor.UploadFileInterceptor;
import com.jady.retrofitclient.request.CommonRequest;
import com.jady.retrofitclient.subscriber.CommonResultSubscriber;
import com.jady.retrofitclient.upload.UploadFileRequestBody;
import com.jady.retrofitclient.upload.UploadSubscriber;

import java.io.File;
import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.JavaNetCookieJar;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jady on 2016/12/7.
 */
public class RetrofitClient {

    public static final String TAG = "RetrofitClient";

    private static Context mContext;
    private boolean isJson = false;
    private CommonRequest commonRequest;
    private Retrofit retrofit;
    private Retrofit.Builder retrofitBuilder;
    private OkHttpClient okHttpClient;
    private OkHttpClient.Builder okHttpClientBuilder;
    /**
     * 默认超时时间：30秒
     */
    private static final int DEFAULT_TIMEOUT = 30;
    /**
     * retrofit缓存文件夹大小：10M
     */
    private static final int RETROFIT_CACHEDIR_SIZE = 10 * 1024 * 1024;

    public RetrofitClient(CommonRequest commonRequest, Retrofit retrofit, Retrofit.Builder retrofitBuilder, OkHttpClient okHttpClient, OkHttpClient.Builder okHttpClientBuilder) {
        this.commonRequest = commonRequest;
        this.retrofit = retrofit;
        this.retrofitBuilder = retrofitBuilder;
        this.okHttpClient = okHttpClient;
        this.okHttpClientBuilder = okHttpClientBuilder;
    }

    public static class Builder {

        private String baseUrl;
        private int TIME_OUT = 60;
        private boolean isLog = true;

        private CommonRequest request;
        private Retrofit retrofit;
        private Retrofit.Builder retrofitBuilder;
        private static OkHttpClient okHttpClient;
        private OkHttpClient.Builder okHttpClientBuilder;

        //Interceptor
        private GsonConverterFactory gsonConverterInterceptor;
        private RxJavaCallAdapterFactory javaCallAdapterInterceptor;
        private UploadFileInterceptor uploadFileInterceptor;
        private DownloadInterceptor downLoadFileInterceptor;
        private OffLineIntercept offLineIntercept;

        public Builder() {
            retrofitBuilder = new Retrofit.Builder();
            okHttpClientBuilder = new OkHttpClient.Builder();
        }

        public Builder baseUrl(String url) {
            baseUrl = url;
            return this;
        }

        public Builder timeOut(int TIME_OUT) {
            this.TIME_OUT = TIME_OUT;
            return this;
        }

        public Builder isLog(boolean isLog) {
            this.isLog = isLog;
            return this;
        }

        public Builder addOffLineIntercept(OffLineIntercept offLineIntercept) {
            this.offLineIntercept = offLineIntercept;
            return this;
        }

        public Builder addGsonConverterInterceptor(GsonConverterFactory factory) {
            this.gsonConverterInterceptor = factory;
            return this;
        }

        public Builder addRxJavaCallAdapterInterceptor(RxJavaCallAdapterFactory factory) {
            this.javaCallAdapterInterceptor = factory;
            return this;
        }

        public Builder addUploadFileInterceptor(UploadFileInterceptor interceptor) {
            this.uploadFileInterceptor = interceptor;
            return this;
        }

        public Builder addDownloadFileInterceptor(DownloadInterceptor interceptor) {
            this.downLoadFileInterceptor = interceptor;
            return this;
        }

        public Builder client(OkHttpClient client) {
            this.okHttpClient = client;
            return this;
        }

        public Builder addHeader(Map<String, String> headers) {
            okHttpClientBuilder.addInterceptor(new HeaderAddInterceptor(headers));
            return this;
        }

        public void createHttpClient() {

            if (this.uploadFileInterceptor != null) {
                okHttpClientBuilder.addInterceptor(this.uploadFileInterceptor);
            }

            if (this.downLoadFileInterceptor != null) {
                okHttpClientBuilder.addInterceptor(this.downLoadFileInterceptor);
            }

            if (this.offLineIntercept != null) {
                okHttpClientBuilder.addInterceptor(this.offLineIntercept);
            }

            okHttpClientBuilder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
            okHttpClientBuilder.readTimeout(TIME_OUT, TimeUnit.SECONDS);
            okHttpClientBuilder.writeTimeout(TIME_OUT, TimeUnit.SECONDS);
            if (isLog)
                okHttpClientBuilder.addNetworkInterceptor(
                        new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));


            CookieManager cookieManager = new CookieManager();
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
            okHttpClientBuilder.cookieJar(new JavaNetCookieJar(cookieManager));

            okHttpClient = okHttpClientBuilder.build();
        }

        public RetrofitClient build() {
            if(okHttpClient ==null){
                createHttpClient();
            }


            // retrofit
            if (this.javaCallAdapterInterceptor != null) {
                retrofitBuilder.addCallAdapterFactory(javaCallAdapterInterceptor);
            }

            if (this.gsonConverterInterceptor != null) {
                retrofitBuilder.addConverterFactory(gsonConverterInterceptor);
            }
            if (!TextUtils.isEmpty(baseUrl)) {
                if (baseUrl.startsWith("http")) {
                    retrofitBuilder.baseUrl(baseUrl);
                } else {
                    throw new RuntimeException("base url不合法，请以http开头");
                }
            }
            retrofitBuilder.client(okHttpClient);
            retrofit = retrofitBuilder.build();

            request = retrofit.create(CommonRequest.class);

            return new RetrofitClient(request, retrofit, retrofitBuilder, okHttpClient, okHttpClientBuilder);
        }
    }

    public OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            new Builder().createHttpClient();
        }
        return okHttpClient;
    }

    public void post(Context context, String url, Map<String, Object> parameters, HttpCallback callback) {
        this.mContext = context;
        commonRequest
                .doPost(url, parameters)
                .compose(schedulerTransformer)
                .subscribe(new CommonResultSubscriber(mContext, callback));
    }

    public <T> void putByBody(Context context, String url, T body, HttpCallback callback) {
        this.mContext = context;
        String parameters = new Gson().toJson(body);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), parameters);
        commonRequest
                .doPut(url, requestBody)
                .compose(schedulerTransformer)
                .subscribe(new CommonResultSubscriber(mContext, callback));
    }

    public void put(Context context, String url, Map<String, Object> parameters, HttpCallback callback) {
        this.mContext = context;
        commonRequest
                .doPut(url, parameters)
                .compose(schedulerTransformer)
                .subscribe(new CommonResultSubscriber(mContext, callback));
    }

    public void delete(Context context, String url, HttpCallback callback) {
        this.mContext = context;
        commonRequest
                .doDelete(url)
                .compose(schedulerTransformer)
                .subscribe(new CommonResultSubscriber(mContext, callback));
    }

    public void delete(Context context, String url, Map<String, Object> parameters, HttpCallback callback) {
        this.mContext = context;
        commonRequest
                .doDelete(url, parameters)
                .compose(schedulerTransformer)
                .subscribe(new CommonResultSubscriber(mContext, callback));
    }

    public <T> void deleteByBody(Context context, String url, T body, HttpCallback callback) {
        this.mContext = context;
        String parameters = new Gson().toJson(body);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), parameters);
        commonRequest
                .doDelete(url, requestBody)
                .compose(schedulerTransformer)
                .subscribe(new CommonResultSubscriber(mContext, callback));
    }

    public <T> void postByBody(Context context, String url, T t, HttpCallback callback) {
        this.mContext = context;
        String parameters = new Gson().toJson(t);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), parameters);
        commonRequest
                .doPost(url, body)
                .compose(schedulerTransformer)
                .subscribe(new CommonResultSubscriber(mContext, callback));
    }

    public void syncPost(Context context, String url, Map<String, Object> parameters, HttpCallback callback) {
        this.mContext = context;
        FormBody.Builder builder = new FormBody.Builder();
        if (parameters != null && parameters.size() > 0) {
            for (Map.Entry<String, Object> map : parameters.entrySet()) {
                builder.add(map.getKey(), String.valueOf(map.getValue()));
            }
        }
        Request request = new Request.Builder().url(url).post(builder.build()).build();
        Call call = getOkHttpClient().newCall(request);
        try {
            Response response = call.execute();
            if (response.isSuccessful()) {
                callback.onResolve(response.body().string());
            } else {
                callback.onFailed("failure", response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void postFullPath(Context context, String fullUrl, Map<String, Object> parameters, HttpCallback callback) {
        this.mContext = context;
        commonRequest
                .doPostFullPath(fullUrl, parameters)
                .compose(schedulerTransformer)
                .subscribe(new CommonResultSubscriber(mContext, callback));
    }

    /**
     * 回调形式
     *
     * @param context
     * @param url
     * @param parameters
     * @param callback
     */
    public void get(Context context, String url, Map<String, Object> parameters, HttpCallback callback) {
        mContext = context;

        if (parameters == null || parameters.size() == 0) {
            commonRequest
                    .doGet(url)
                    .compose(schedulerTransformer)
                    .subscribe(new CommonResultSubscriber(mContext, callback));
        } else {
            commonRequest
                    .doGet(url, parameters)
                    .compose(schedulerTransformer)
                    .subscribe(new CommonResultSubscriber(mContext, callback));
        }
    }

    /**
     * RxJava形式
     *
     * @param context
     * @param url
     * @param parameters
     * @return
     */
    public Observable get(Context context, String url, Map<String, Object> parameters) {
        mContext = context;

        if (parameters == null || parameters.size() == 0) {
            return commonRequest
                    .doGet(url)
                    .compose(schedulerTransformer);
        } else {
            return commonRequest
                    .doGet(url, parameters)
                    .compose(schedulerTransformer);
        }
    }

    public void syncGet(Context context, String url, Map<String, Object> parameters, HttpCallback callback) {
        this.mContext = context;
        StringBuilder sb = new StringBuilder();
        sb.append(url);
        if (parameters != null && parameters.size() > 0) {
            sb.append("?");
            for (Map.Entry<String, Object> map : parameters.entrySet()) {
                sb.append(map.getKey()).append("=").append(map.getValue()).append("&");
            }
            sb.deleteCharAt(sb.lastIndexOf("&"));
        }
        Request request = new Request.Builder().url(sb.toString()).build();
        Call call = getOkHttpClient().newCall(request);
        try {
            Response response = call.execute();
            if (response.isSuccessful()) {
                callback.onResolve(response.body().string());
            } else {
                callback.onFailed("failure", response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getFullPath(Context context, String fullUrl, Map<String, Object> parameters, HttpCallback callback) {
        mContext = context;

        if (parameters == null || parameters.size() == 0) {
            commonRequest
                    .doGetFullPath(fullUrl)
                    .compose(schedulerTransformer)
                    .subscribe(new CommonResultSubscriber(mContext, callback));
        } else {
            commonRequest
                    .doGetFullPath(fullUrl, parameters)
                    .compose(schedulerTransformer)
                    .subscribe(new CommonResultSubscriber(mContext, callback));
        }
    }

    public void uploadFile(String url, String filePath, String fileDes, boolean isFullUrl, final FileResponseResult callback) {
        final File file = new File(filePath);
        if (!file.exists()) {
            return;
        }

        RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data; charset=utf-8"), fileDes);

//        UploadFileRequestBody requestBody = new UploadFileRequestBody(file, new TransformProgressListener() {
//            private long curUploadProgress = 0;
//
//            @Override
//            public void onProgress(long progress, long total, boolean completed) {
//                if (completed) {
//                    curUploadProgress += total;
//                }
//                callback.onExecuting(curUploadProgress + (progress), file.length(), curUploadProgress + (progress) == file.length());
//            }
//
//            @Override
//            public void onFailed(String msg) {
//                callback.onFailure(null, msg);
//            }
//        });
        RequestBody requestBody = UploadFileRequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        if (isFullUrl) {
            commonRequest.uploadFileFullPath(url, description, part)
                    .compose(schedulerTransformer)
                    .subscribe(new UploadSubscriber(mContext, callback));
        } else {
            commonRequest.uploadFile(url, description, part)
                    .compose(schedulerTransformer)
                    .subscribe(new UploadSubscriber(mContext, callback));
        }
    }

    private long curUploadProgress = 0;

    public void uploadFiles(String url, List<String> filePathList, boolean isFullUrl, final FileResponseResult callback) {
        curUploadProgress = 0;
        if (filePathList == null || filePathList.size() == 0) {
            return;
        }
        List<File> fileList = new ArrayList<>();
        long totalSize = 0;
        for (String filePath : filePathList) {
            File file = new File(filePath);
            if (!file.exists()) {
                continue;
            }
            totalSize += file.length();
            fileList.add(file);
        }

        HashMap<String, RequestBody> params = new HashMap<>();
        for (int i = 0; i < fileList.size(); i++) {
            File file = fileList.get(i);
            RequestBody body =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file);

//            final long finalTotalSize = totalSize;
//            UploadFileRequestBody body_up = new UploadFileRequestBody(file, new TransformProgressListener() {
//
//                @Override
//                public void onProgress(long progress, long total, boolean completed) {
//                    if (completed) {
//                        curUploadProgress += total;
//                    }
//                    callback.onExecuting(curUploadProgress + (progress), finalTotalSize, curUploadProgress + (progress) == finalTotalSize);
//                }
//
//                @Override
//                public void onFailed(String msg) {
//                    callback.onFailure(null, msg);
//                }
//            });
            params.put("file[]\"; filename=\"" + file.getName(), body);
//            params.put("file[]\"; filename=\"" + file.getName(), body);
        }
        if (isFullUrl) {
            commonRequest.uploadFilesFullPath(url, params)
                    .compose(schedulerTransformer)
                    .subscribe(new UploadSubscriber(mContext, callback));
        } else {
            commonRequest.uploadFiles(url, params)
                    .compose(schedulerTransformer)
                    .subscribe(new UploadSubscriber(mContext, callback));
        }
    }

    Observable.Transformer schedulerTransformer = new Observable.Transformer() {
        @Override
        public Object call(Object o) {
            return ((Observable) o)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    };

}
