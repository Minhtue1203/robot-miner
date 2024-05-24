package com.example.robominer.view;

import com.example.robominer.model.Grid;
import com.example.robominer.model.SecteurInfo;

import java.util.List;

public class SecteurInfoView {

    public void printSecteurInfo(List<SecteurInfo> addedSecteurs) {
        for (SecteurInfo secteurInfo : addedSecteurs) {
            System.out.println(secteurInfo);
        }
    }
}
