package com.shubham.todoassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import com.shubham.todoassignment.adapter.OnToDoClickListener;
import com.shubham.todoassignment.adapter.RecyclerViewAdapter;
import com.shubham.todoassignment.model.Item;
import com.shubham.todoassignment.util.ConvertDate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnToDoClickListener {

    private TabLayout tabLayout;
    private RecyclerView incomplete_recyclerView;
    private RecyclerView complete_recyclerView;
    private List<Item> completedList = new ArrayList<>();
    private List<Item> incompleteList = new ArrayList<>();
    private RecyclerViewAdapter incomplete_adapter;
    private RecyclerViewAdapter complete_adapter;
    List<Item> today_list = new ArrayList<>();
    List<Item> later_list = new ArrayList<>();
    List<Item> apiData = new ArrayList<>();

    private final String url = "http://www.mocky.io/v2/582695f5100000560464ca40";
    private final String TAG = "MainActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tab);
        incomplete_recyclerView = findViewById(R.id.incomplete_recyclerView);
        complete_recyclerView = findViewById(R.id.completed_recyclerView);
        getData();

        complete_recyclerView.hasFixedSize();
        complete_recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        incomplete_recyclerView.hasFixedSize();
        incomplete_recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));


//        dummyLists();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition()==0){
                    complete_recyclerView.setVisibility(View.VISIBLE);
                    incomplete_recyclerView.setVisibility(View.VISIBLE);

                } else if (tab.getPosition()==1) {
                    complete_recyclerView.setVisibility(View.GONE);
                    incomplete_recyclerView.setVisibility(View.GONE);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    public void dummyLists(){
        List<Item> total = new ArrayList<>();
        total.add(new Item("Description", "December, 3", true ));
        total.add(new Item("Description 1", "December, 3", true ));
        total.add(new Item("Description 2 ", "December, 3", true ));
        total.add(new Item("Description 3 ", "December, 01", true ));
        total.add(new Item("Description 4 ", "December, 20", false ));
        total.add(new Item("Description 5 ", "December, 18", false ));
        total.add(new Item("Description 6 ", "December, 18", false ));
        total.add(new Item("Description 7 ", "December, 3", false ));

        for (Item item: total) {
            Date today = new Date();
            String current = ConvertDate.toDate(String.valueOf(ConvertDate.toTimeStamp(today)));
            if(current.equals(item.getDate())){
                today_list.add(item);
            }else {
                later_list.add(item);
            }
        }
        for (Item item:today_list) {
            splitList(item);
        }
    }

    public void splitList(Item item){
        if (item.isCompleted()) {
            completedList.add(item);
            Log.d(TAG, "splitList: Completed" + item.toString());
        } else {
            incompleteList.add(item);
            Log.d(TAG, "splitList: Pending" + item.toString());
        }
    }

    private void getData() {
        RequestQueue referenceQueue = Volley.newRequestQueue(MainActivity.this);

        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i < response.length() ; i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                Item item = new Item();

                                item.setDescription(object.getString("description"));
                                item.setDate(ConvertDate.toDate( String.valueOf(object.get("scheduledDate"))));
                                item.setCompleted((object.getString("status").equals("COMPLETED")));
                                splitList(item);
                                apiData.add(item);
//                                Log.d(TAG, "onResponse: " + itemList.get(i).toString());

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        incomplete_adapter = new RecyclerViewAdapter(incompleteList, MainActivity.this);
                        complete_adapter = new RecyclerViewAdapter(completedList, MainActivity.this);
                        complete_recyclerView.setAdapter(complete_adapter);
                        incomplete_recyclerView.setAdapter(incomplete_adapter);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "onErrorResponse: " + error.getMessage());
                    }
                });
        referenceQueue.add(arrayRequest);
    }

    @Override
    public void onClickRadioButton(Item item) {
//        ischeck == true -> completed -> incopmlete

        if (!item.isCompleted()){
            item.setCompleted(true);
            incompleteList.remove(item);
            completedList.add(item);
            complete_adapter.notifyDataSetChanged();
            incomplete_adapter.notifyDataSetChanged();

        }else if(item.isCompleted()){
            item.setCompleted(false);
            completedList.remove(item);
            complete_adapter.notifyDataSetChanged();
            incompleteList.add(item);
            incomplete_adapter.notifyDataSetChanged();
        }
    }
}