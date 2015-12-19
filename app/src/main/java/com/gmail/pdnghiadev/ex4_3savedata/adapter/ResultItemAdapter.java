package com.gmail.pdnghiadev.ex4_3savedata.adapter;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.gmail.pdnghiadev.ex4_3savedata.R;
import com.gmail.pdnghiadev.ex4_3savedata.model.ResultItem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by PDNghiaDev on 12/1/2015.
 */
public class ResultItemAdapter extends ArrayAdapter<ResultItem> {
    private Context mContext;
    private int layoutResourceId;
    private List<ResultItem> items;

    public ResultItemAdapter(Context context, int layoutResourceId, List<ResultItem> items) {
        super(context,layoutResourceId, items);
        this.mContext = context;
        this.layoutResourceId = layoutResourceId;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
        }

        ResultItem item = items.get(position);

        TextView mDate = (TextView) convertView.findViewById(R.id.tv_date);
        TextView mCountTap = (TextView) convertView.findViewById(R.id.tv_tap_count);

        mDate.setText(convertDateToString(item.getDate()));
        mCountTap.setText(String.valueOf(item.getCountTap()));

        return convertView;
    }

    public String convertDateToString(Date date){
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        return format.format(date);
    }

}
