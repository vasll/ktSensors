package com.vasll;

import com.vasll.model.Component;
import java.util.ArrayList;

public interface StreamListener {
    void onStreamUpdate(ArrayList<Component> componentList);
}
