package com.gmail.pdnghiadev.ex4_3savedata.controller;



import com.gmail.pdnghiadev.ex4_3savedata.model.ResultItem;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by PDNghiaDev on 12/1/2015.
 */
public class MainController {

    private static MainController instance;
    private ArrayList<ResultItem> resultItemsList;

    private MainController(){
        this.resultItemsList = new ArrayList<>();
    }

    public static MainController getInstance(){
        if (instance == null){
            instance = new MainController();
        }

        return instance;
    }

    public void addResultItem(Date date, int countTap){
        ResultItem resultItem = new ResultItem(date, countTap);
        resultItemsList.add(resultItem);
    }

    public ArrayList<ResultItem> getResultItemsList(){
        return this.resultItemsList;
    }
}
