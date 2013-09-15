package com.romina.employeeinfo;

public class Action {

	private String label;
	
	private String data;
	
	private int type;

	public static final int CALL = 1; 
	public static final int SMS = 2; 
	public static final int VIEW = 3;
	public static final int REPORTS = 4;
	 
	
	public Action(String label, String data, int type) {
		super();
		this.label = label;
		this.data = data;
		this.type = type;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}	
}

