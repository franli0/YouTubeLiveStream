package com.fengsui.youtubesearch;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoginFragment extends Fragment implements View.OnClickListener, SpinnerAdapter {

    private Button btnEnter;
    private Spinner mSelect;
    private ArrayList<RowItem> mList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mList = new ArrayList<RowItem>();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        btnEnter = view.findViewById(R.id.button3);
        mSelect = view.findViewById(R.id.spinner);
        btnEnter.setOnClickListener(this);
    }

    private class RowItem {
        public int id;
        public String name;
        public RowItem(JSONObject obj) {
            try {
                this.id = obj.getInt("id");
                this.name = obj.getString("name");
            }catch (Exception e){
                Log.e("Login", e.getMessage());
            }
        }
    }

    public void setList(JSONArray arr) {
        for (int i = 0; i < arr.length(); i++) {
            try {
                RowItem item = new RowItem(arr.getJSONObject(i));
                mList.add(item);
            }catch (Exception e){
                Log.e("Login", e.getMessage());
            }
        }
        mSelect.setAdapter(this);
        mSelect.requestFocus();
    }

    private View rowview(View convertView , int position){

        RowItem rowItem = getItem(position);

        View rowview = convertView;
        if (rowview==null) {
            rowview = View.inflate(getContext(), android.R.layout.simple_spinner_item, null);
        }
        ((TextView) rowview).setText(rowItem.name);

        return rowview;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button3:
                ((MainActivity) getActivity()).login(""+mSelect.getSelectedItemId());
                break;
        }
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return rowview(convertView,position);
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public RowItem getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).id;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return rowview(convertView,position);
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return mList.size() == 0;
    }
}
