package com.asking91.app.util.networkfetch;

import android.content.Context;

import com.asking91.app.api.Networks;
import com.facebook.imagepipeline.producers.BaseNetworkFetcher;
import com.facebook.imagepipeline.producers.BaseProducerContextCallbacks;
import com.facebook.imagepipeline.producers.Consumer;
import com.facebook.imagepipeline.producers.FetchState;
import com.facebook.imagepipeline.producers.ProducerContext;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by jswang on 2017/3/16.
 */

public class OkHttpNetworkFetcher extends BaseNetworkFetcher {

    private static OkHttpClient mOkClient;

    private Map<String, Call> mCallMap;


    private Context     mAppContext;


    static {
        mOkClient = new OkHttpClient.Builder().build();
    }

    public OkHttpNetworkFetcher(Context mAppContext) {
        this.mAppContext = mAppContext;
    }

    @Override
    public FetchState createFetchState(Consumer consumer, ProducerContext producerContext) {
        return new FetchState(consumer, producerContext);
    }

    @Override
    public void fetch(FetchState fetchState,final Callback callback) {
        Request request = buildRequest(fetchState);
        Call fetchCall = mOkClient.newCall(request);

        if(mCallMap == null)
            mCallMap = new HashMap<>();

        mCallMap.put(fetchState.getId(), fetchCall);

        final String fetchId = fetchState.getId();

        fetchState.getContext().addCallbacks(new BaseProducerContextCallbacks(){
            @Override
            public void onCancellationRequested() {
                cancelAndRemove(fetchId);
                callback.onCancellation();
            }
        });

        try {
            Response response =  fetchCall.execute();
            ResponseBody responseBody = response.body();
            callback.onResponse(responseBody.byteStream(), (int) responseBody.contentLength());
        } catch (Exception e) {
            callback.onFailure(e);
        }

        removeCall(fetchId);
    }


    private void removeCall(String id){
        if(mCallMap != null){
            mCallMap.remove(id);
        }
    }

    private void cancelAndRemove(String id){
        if(mCallMap != null){
            Call call = mCallMap.remove(id);
            if(call != null)
                call.cancel();
        }
    }


    private Request buildRequest(FetchState fetchState){
        return new Request.Builder()
                .addHeader("Authorization", Networks.getToken())
                .get()
                .url(fetchState.getUri().toString())
                .build();

    }


}
