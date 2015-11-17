package com.jackie.contactstest;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    List<Map<String, Object>> contactsList;
    SimpleAdapter simpleAdapter;
    ListView listView;
    Map<String, Object> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contactsList = new ArrayList<Map<String, Object>>();
        listView = (ListView) findViewById(R.id.lvContacts);

        readContacts();
        simpleAdapter = new SimpleAdapter(
                this,
                contactsList,
                R.layout.item_contact,
                new String[]{"name", "number"},
                new int[]{R.id.tvName, R.id.tvNumber});
        listView.setAdapter(simpleAdapter);
    }

    private void readContacts() {
        Cursor cursor = getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                null,
                null,
                null);
        while (cursor.moveToNext()) {
            //map的初始化要放在这里，否则会出现bug
            map = new HashMap<String, Object>();
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            map.put("name", name);
            map.put("number", number);
            contactsList.add(map);
        }
    }
}

