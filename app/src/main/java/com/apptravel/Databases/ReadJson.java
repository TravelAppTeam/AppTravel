package com.apptravel.Databases;

import android.util.Log;

import com.apptravel.Entity.Travel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


class ReadJson {
    private final static String TAG = ReadJson.class.getSimpleName();
    private final static String ID = "id";
    private final static String TEN = "ten";
    private final static String MOTA = "mota";
    private final static String DIACHI = "diachi";
    private final static String CONTACT = "tel";
    private final static String IMG = "img";
    private final static String LOWIMG = "lowimg";
    private final static String MAP = "map";
    private final static String VIEW = "view";

     static ArrayList<Travel> readStringJson(String content) {
        ArrayList<Travel> listTravel = new ArrayList<>();
        try {
            JSONArray jsonArr = new JSONArray(content);
            Log.i(TAG, "read json");
            for (int i = 0; i < jsonArr.length(); ++i) {
                JSONObject travelJson = jsonArr.getJSONObject(i);
                Travel travel = new Travel();
                travel.setId(travelJson.getString(ID));
                travel.setTen(travelJson.getString(TEN));
                travel.setContact(travelJson.getString(CONTACT));
                travel.setMota(travelJson.getString(MOTA));
                travel.setDiaChi(travelJson.getString(DIACHI));
                travel.setImg(travelJson.getString(IMG));
                travel.setLowimg(travelJson.getString(LOWIMG));
                travel.setMap(travelJson.getString(MAP));
                travel.setView(travelJson.getInt(VIEW));
                listTravel.add(travel);
            }
        } catch (JSONException e) {
            Log.e(TAG, "cant convert text to json");
            return null;
        }
        return listTravel;
    }
}
