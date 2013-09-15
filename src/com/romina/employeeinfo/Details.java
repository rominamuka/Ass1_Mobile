package com.romina.employeeinfo;

import java.util.ArrayList;
import java.util.List;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Details extends ListActivity {

	protected TextView empNameText;
	protected TextView tText;
	protected List<Action> actions;
    protected EmployeeActionAdapter adapter;
    protected int empId;
    protected int mgId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);
        
        empId = getIntent().getIntExtra("EMPLOYEE_ID", 0);
        SQLiteDatabase db = (new DatabaseHelper(this)).getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT emp._id, emp.fName, emp.lName, emp.title, emp.mobPhone, emp.mgrId, mgr.fName managerFirstName, mgr.lName managerLastName FROM employee emp LEFT OUTER JOIN employee mgr ON emp.mgrId = mgr._id WHERE emp._id = ?", 
				new String[]{""+empId});

        if (cursor.getCount() == 1)
        {
        	cursor.moveToFirst();
        
	        empNameText = (TextView) findViewById(R.id.empName);
	        empNameText.setText(cursor.getString(cursor.getColumnIndex("fName")) + " " + cursor.getString(cursor.getColumnIndex("lName")));
	
	        tText = (TextView) findViewById(R.id.title);
	        tText.setText(cursor.getString(cursor.getColumnIndex("title")));
	        
	        actions = new ArrayList<Action>();
	        
	        String mobPhone = cursor.getString(cursor.getColumnIndex("mobPhone"));
	        if (mobPhone != null) {
	        	actions.add(new Action("Call mobile phone", mobPhone, Action.CALL));
	        }
	        
	        String cellPhone = cursor.getString(cursor.getColumnIndex("mobPhone"));
	        if (cellPhone != null) {
	        	actions.add(new Action("SMS", cellPhone, Action.SMS));
	        }
	              
	        mgId = cursor.getInt(cursor.getColumnIndex("mgrId"));
	        if (mgId>0) {
	        	actions.add(new Action("View manager", cursor.getString(cursor.getColumnIndex("managerFirstName")) + " " + cursor.getString(cursor.getColumnIndex("managerLastName")), Action.VIEW));
	        }
	
	        cursor = db.rawQuery("SELECT count(*) FROM employee WHERE mgrId = ?", 
					new String[]{""+empId});
	        cursor.moveToFirst();
	        int count = cursor.getInt(0);
	        if (count>0) {
	        	actions.add(new Action("View direct reports", "(" + count + ")", Action.REPORTS));
	        }

	        adapter = new EmployeeActionAdapter();
	        setListAdapter(adapter);
        }
 
    }
    
    @Override
	public void onListItemClick(ListView parent, View view, int position, long id) {
    	
	    Action action = actions.get(position);

	    Intent intent;
	    switch (action.getType()) {

	    	case Action.CALL:  
	        	Uri callUri = Uri.parse("tel:" + action.getData());  
	        	intent = new Intent(Intent.ACTION_CALL, callUri); 
	    	    startActivity(intent);
	        	break;
                
	    	case Action.SMS:  
	        	Uri smsUri = Uri.parse("sms:" + action.getData());  
	        	intent = new Intent(Intent.ACTION_VIEW, smsUri); 
	    	    startActivity(intent);
	        	break;
	        	
	    	case Action.VIEW:  
	        	intent = new Intent(this, Details.class);
	        	intent.putExtra("EMPLOYEE_ID", mgId);
	        	startActivity(intent);
	        	break;

	    	case Action.REPORTS:  
	        	intent = new Intent(this, Reports.class);
	        	intent.putExtra("EMPLOYEE_ID", empId);
	        	startActivity(intent);
	    		break;
	    }
    }    
    
    class EmployeeActionAdapter extends ArrayAdapter<Action> {

    	EmployeeActionAdapter() {
			super(Details.this, R.layout.action, actions);
		}
    	
    	@Override
    	public View getView(int position, View convertView, ViewGroup parent) {
    		Action action = actions.get(position);
    		LayoutInflater inflater = getLayoutInflater();
    		View view = inflater.inflate(R.layout.action, parent, false);
    		TextView label = (TextView) view.findViewById(R.id.label);
    		label.setText(action.getLabel());
    		TextView data = (TextView) view.findViewById(R.id.data);
    		data.setText(action.getData());
    		return view;
    	}
    	
    }
    
}