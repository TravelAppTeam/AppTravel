package com.apptravel.Events;

import com.apptravel.Entity.Travel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lldti on 31-Dec-16.
 */

public interface AsyncResponse {
    void onAsyncLoadDone(ArrayList<Travel> listTravel);
}
