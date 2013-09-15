package com.romina.employeeinfo;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class Reports extends ListActivity {

	protected Cursor cursor=null;
	protected ListAdapter adapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {

    	super.onCreate(savedInstanceState);
        setContentView(R.layout.report);

        SQLiteDatabase db = (new DatabaseHelper(this)).getWritableDatabase();

        int empId = getIntent().getIntExtra("EMPLOYEE_ID", 0);

        Cursor cursor = db.rawQuery("SELECT _id, fName, lName, title FROM employee WHERE _id = ?", 
				new String[]{""+empId});

        if (cursor.getCount() != 1)
        {
        	return;
        }

        cursor.moveToFirst();

        TextView empNameText = (TextView) findViewById(R.id.empName);
	    empNameText.setText(cursor.getString(cursor.getColumnIndex("fName")) + " " + cursor.getString(cursor.getColumnIndex("lName")));

        TextView tText = (TextView) findViewById(R.id.title);
	    tText.setText(cursor.getString(cursor.getColumnIndex("title")));
        
        cursor = db.rawQuery("SELECT _id, fName, lName, title, mobPhone FROM employee WHERE mgrId = ?", 
				new String[]{""+empId});
		adapter = new SimpleCursorAdapter(
				this, 
				R.layout.list_item, 
				cursor, 
				new String[] {"fName", "lName", "title"}, 
				new int[] {R.id.fName, R.id.lName, R.id.title});
		setListAdapter(adapter);
    }
    
    @Override
	public void onListItemClick(ListView parent, View view, int position, long id) {
    	Intent intent = new Intent(this, Details.class);
    	Cursor cursor = (Cursor) adapter.getItem(position);
    	intent.putExtra("EMPLOYEE_ID", cursor.getInt(cursor.getColumnIndex("_id")));
    	startActivity(intent);
    }
    
}