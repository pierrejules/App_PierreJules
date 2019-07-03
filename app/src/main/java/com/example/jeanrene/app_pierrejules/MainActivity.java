package com.example.jeanrene.app_pierrejules;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.ClipData;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import org.apache.commons.io.FileUtils;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // items=new ArrayList<>();
        readItems();
        itemsAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
        lvItems=(ListView)findViewById(R.id.lvItems);
        lvItems.setAdapter(itemsAdapter);

        // items.add("First Item");
        //items.add("Second Item");

        setupListViewListener();
    }
    public void onAddItem(View v){
        EditText etNewItem=(EditText) findViewById(R.id.etNewItem);
        String itemText=etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");
        writeItems();
        Toast.makeText(getApplicationContext(),"Item add to list",Toast.LENGTH_SHORT).show();
    }

    private void setupListViewListener(){
        Log.i("MainActivity","Setting up listener on list view");
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("MainActivity","Item remove from list" + position);
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
    }
    private File  getDatafile(){

        return new File(getFilesDir(), "todo.text");
    }
    private void readItems() {
        try {
            items= new ArrayList <>(FileUtils.readLines(getDatafile(), Charset.defaultCharset()));
        } catch
                (IOException e){
            Log.e("MainActivity","Erreur de lire le fichier", e);
            items=new ArrayList<>();
        }
    }
    public  void writeItems(){
        try {
            FileUtils.writeLines(getDatafile(),items);
        }
        catch
                (IOException e){
            Log.e("MainActivity","Erreur de ecrire le fichier", e);
        }
    }
}
