package com.apptravel.Databases;

import com.apptravel.Events.AsyncResponse;
import com.github.rahatarmanahmed.cpv.CircularProgressView;

public class QueryDatabase extends NewDatabase
{

    private static final String TAG = QueryDatabase.class.getSimpleName();
    private static final String GET_ALL_DATA_URL = "http://travelapp.tk/backend_android/index.php?";
    private static final String SEARCH_DATA_URL = "http://travelapp.tk/backend_android/search.php?";
    private static final String LIMIT_TAG = "limit=";
    private static final String SEARCH_TAG = "search=";
    private static final String CONNECT_MUTI_TAG = "&";
    private static final int BASE_LIMIT_LENGTH = 40;

    public QueryDatabase(CircularProgressView circularProgressView) {
        super(circularProgressView);
    }

    @Override
    public void setAsyncResponse(AsyncResponse response) {
        super.setAsyncResponse(response);
    }

    public void loadAllData(int limitLen, AsyncResponse asyncResponse) {
        setAsyncResponse(asyncResponse);
        execute(GET_ALL_DATA_URL + LIMIT_TAG + limitLen);
    }

    public void loadAllData(AsyncResponse asyncResponse) {
        loadAllData(BASE_LIMIT_LENGTH, asyncResponse);
    }

    public void searchAllData(String content, int limitLen, AsyncResponse asyncResponse){
        setAsyncResponse(asyncResponse);
        execute(SEARCH_DATA_URL + SEARCH_TAG + content + CONNECT_MUTI_TAG + LIMIT_TAG + limitLen);
    }

    public void searchAllData(String content, AsyncResponse asyncResponse){
        searchAllData(content, BASE_LIMIT_LENGTH, asyncResponse);
    }
}
