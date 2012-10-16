package com.jdenoc.convertit;
// RGBtoHEX.java
// GUI:	color_tab1.xml
// Author: Denis O'Connor
// Last modified: 05/7/12
// First Tab of Colour Conversion
// Converts RGB colours into HEX colour code and displays how it might look

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RGBtoHEX extends Activity implements OnClickListener{

	private EditText r, g, b;
	private LinearLayout canvas;
	private TextView hexName;
	private ImageButton convert;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.color_tab1);
		
		r = (EditText) findViewById(R.id.colorR);
		g = (EditText) findViewById(R.id.colorG);
		b = (EditText) findViewById(R.id.colorB);
		canvas = (LinearLayout) findViewById(R.id.colorCanvas);
		hexName = (TextView) findViewById(R.id.colorID);
		convert = (ImageButton) findViewById(R.id.doConversion);
		
		convert.setOnClickListener(this);
		hexName.setText("");
	}// END onCreate()

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.doConversion:
			if(isValid()){
				int red = Integer.parseInt(r.getText().toString());
				int green = Integer.parseInt(g.getText().toString());
				int blue = Integer.parseInt(b.getText().toString());
			
				if(checkValues(red, green, blue)){
					String hex = "#"+switcher(red/16)+switcher(red%16);
					hex += switcher(green/16)+switcher(green%16);
					hex += switcher(blue/16)+switcher(blue%16);
					canvas.setBackgroundColor(Color.parseColor(hex));
					hexName.setText(hex);
					break;
				}
			}
		}
	}// END onClick()
	
	public boolean isValid(){
		if(r.getText().toString().length() == 0){
			Toast.makeText(this, "Please enter a value for R input", Toast.LENGTH_SHORT).show();
			return false;
		}else if(g.getText().toString().length() == 0){
			Toast.makeText(this, "Please enter a value for G input", Toast.LENGTH_SHORT).show();
			return false;
		}else if(b.getText().toString().length() == 0){
			Toast.makeText(this, "Please enter a value for B input", Toast.LENGTH_SHORT).show();
			return false;
		}else{
			return true;
		}
	}// END isValid()
	
	public boolean checkValues(int r, int g, int b){
		if(r > 255 || r < 0){
			Toast.makeText(this, "Please enter a value for R between 0 and 255", Toast.LENGTH_SHORT).show();
			return false;
		}else if(g > 255 || g < 0){
			Toast.makeText(this, "Please enter a value for G between 0 and 255", Toast.LENGTH_SHORT).show();
			return false;
		}else if(b > 255 || b < 0){
			Toast.makeText(this, "Please enter a value for B between 0 and 255", Toast.LENGTH_SHORT).show();
			return false;
		}else{
			return true;
		}
	}// END checkValues()
	
	public String switcher(int value){
		String character= "";
		switch(value){
			case 10:
				character += "A";
				break;
				
			case 11:
				character += "B";
				break;
				
			case 12:
				character += "C";
				break;
				
			case 13:
				character += "D";
				break;
				
			case 14:
				character += "E";
				break;
				
			case 15:
				character += "F";
				break;
				
			default:
				character += value;
				break;
		}
		return character;
	}// END switcher()

}
