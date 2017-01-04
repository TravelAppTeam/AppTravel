package com.apptravel.Fragment;

import android.app.Dialog;
import android.app.FragmentManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

import com.apptravel.R;

import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

/**
 * Created by lldti on 03-Jan-17.
 */

public class MapsDialogFragment extends DialogFragment {
    public static String KEY_MAP_DIALOG_FRAGMENT = "MapsDialogFragmentKey";
    public static String TAG = MapsDialogFragment.class.getSimpleName();
    private MapView map;
    private Marker marker;

    public static MapsDialogFragment newInstance(String location) {
        MapsDialogFragment yourDialogFragment = new MapsDialogFragment();
        Log.d(TAG, "in newinstace");
        //example of passing args
        Bundle args = new Bundle();
        args.putString(KEY_MAP_DIALOG_FRAGMENT, location);
        yourDialogFragment.setArguments(args);
        return yourDialogFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "in onCreateDialog");
        View v = getActivity().getLayoutInflater().inflate(R.layout.fragment_maps, new LinearLayout(getContext()), false);
        map = (MapView) v.findViewById(R.id.map);
        marker = new Marker(map);
        configMarker();

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) map.getLayoutParams();
        params.rightMargin = 50; params.leftMargin = 50; params.topMargin = 50; params.bottomMargin = 50;

        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);

        GeoPoint geo = getGeoPoint(getArguments().getString(KEY_MAP_DIALOG_FRAGMENT));
        marker.setPosition(geo);
        map.getOverlays().add(marker);
        map.getController().setCenter(geo);
        map.getController().setZoom(MapsFragment.ZOOM_LEVEL);

        Dialog builder = new Dialog(getActivity());
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setContentView(v);
        return builder;
    }

    //10.8525386,106.665021,16
    private GeoPoint getGeoPoint(String location) {
        Log.d(TAG, "in get GeoPoint");
        if (location != null) {
            String[] arrPoint = location.split(",");
            // s[0] lattitule
            // s[1] longtitule
            // s[2] zoom level
            double lat, log;
            try {
                lat = Double.valueOf(arrPoint[0].trim());
                log = Double.valueOf(arrPoint[1].trim());
            } catch (Exception e) {
                Log.d(TAG, "cant cant location string to double");
                return null;
            }
            return new GeoPoint(lat, log);
        }
        Log.d(TAG, "in location is null");
        return null;
    }
    private void configMarker() {
        marker.setAnchor(Marker.ANCHOR_CENTER,Marker.ANCHOR_BOTTOM);
        marker.setIcon(getResources().getDrawable(R.drawable.ic_marker_1));
    }

}
