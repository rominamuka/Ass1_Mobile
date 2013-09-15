package com.romina.employeeinfo;

import android.app.ListActivity;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;
import android.view.View;

public class List extends ListActivity {

	protected EditText sText;
	protected SQLiteDatabase db;
	protected Cursor cursor;
	protected ListAdapter adapter;

    @Override
	public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.employee);
        sText = (EditText) findViewById(R.id.searchT);
    	db = (new DatabaseHelper(this)).getWritableDatabase();
    }
    
    @Override
	public void onListItemClick(ListView parent, View view, int position, long id) {
    	Intent intent = new Intent(this, Details.class);
    	Cursor cursor = (Cursor) adapter.getItem(position);
    	intent.putExtra("EMPLOYEE_ID", cursor.getInt(cursor.getColumnIndex("_id")));
    	startActivity(intent);
    }
    
    public void search(View view) {
    	
		cursor = db.rawQuery("SELECT _id, fName, lName, title FROM employee WHERE fName || ' ' || lName LIKE ?", 
						new String[]{"%" + sText.getText().toString() + "%"});
		adapter = new SimpleCursorAdapter(
				this, 
				R.layout.list_item, 
				cursor, 
				new String[] {"fName", "lName", "title"}, 
				new int[] {R.id.fName, R.id.lName, R.id.title});
		setListAdapter(adapter);
    }
    
    public void showImage(View view){
    	Intent intent = new Intent(getApplicationContext(), Image.class);
    	startActivity(intent);
    }
    
    public void showLocation(View view){
    	Intent intent = new Intent(getApplicationContext(), Map.class);
    	startActivity(intent);
    }
    
}