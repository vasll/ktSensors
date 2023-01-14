package com.vasll.listeners;

import com.vasll.model.Component;
import java.util.ArrayList;

public interface StreamUpdateListener {
    void onStreamUpdate(ArrayList<Component> componentList);
}
