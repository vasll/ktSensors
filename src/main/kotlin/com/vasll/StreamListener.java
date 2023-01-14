package com.vasll;

import com.vasll.model.Component;
import java.util.ArrayList;

public interface StreamListener {
    /**
     * This method is toggled when the data stream is closed and the data can be safely elaborated
     * @param componentList an ArrayList containing model.Component objects
     */
    void onStreamUpdate(ArrayList<Component> componentList);
}
