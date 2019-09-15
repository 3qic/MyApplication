package com.example.myapplication;

import android.app.AlertDialog;
import android.app.LauncherActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ShoppingListFragment extends Fragment {
    private Button addButton;
    private Button savebutton;
    private EditText amount;
    private EditText itemname;
    private ListView listView;
    private ListItemAdapter adapter;
    private ArrayList <ListItem>itemList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_shopping_list,container,false);

        addButton = v.findViewById(R.id.Add_Shopping_Item);
        itemname = v.findViewById(R.id.Enter_Item_Name);
        amount = v.findViewById(R.id.Enter_Item_Amount);
        listView = v.findViewById(R.id.Shopping_List_List_View);
        savebutton = v.findViewById(R.id.Savebutton_Shopping_List);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        Gson gson = new Gson();
        String json = preferences.getString("item" , null);
        Type type = new TypeToken<ArrayList<ListItem>>() {}.getType();
        itemList = gson.fromJson(json, type);

        if(itemList == null) {
            itemList = new ArrayList<>();
        }

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name_item = itemname.getText().toString().trim();
                String amount_item = amount.getText().toString().trim();
                if(!name_item.isEmpty() && !amount_item.isEmpty() ){
                addItem();
            } else{ Toast toast = Toast.makeText(getContext(),"Bitte Name und Anzahl angeben",Toast.LENGTH_SHORT);
                    toast.show();
            }}
        });

        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

        itemList = new ArrayList<>();
        adapter = new ListItemAdapter(getContext(), R.layout.shopping_listview_item_layout, itemList);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                itemList.remove(position);
                adapter.notifyDataSetChanged();
                return true;
            }
        });

        return v;
    }

    private void addItem(){

        String name = itemname.getText().toString();
        String number = amount.getText().toString() + " Stück";
        ListItem item = new ListItem(name, number);
        itemList.add(item);
        adapter.notifyDataSetChanged();
        itemname.setText("");
        amount.setText("");
    }

    private void saveData(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(itemList);
        editor.putString("item", json);
    }

}
