package com.jdenoc.convertit.fragments;
// RGBFragment.java
// GUI: color_tab1.xml
// Author: Denis O'Connor
// Last modified: 08-NOV-2012
// Fragment for Color Conversions. 
// Converts RGB to HEX color codes.

import com.jdenoc.convertit.R;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

@TargetApi(11)
public class RGBFragment extends Fragment{

	private EditText r, g, b;
	private LinearLayout canvas;
	private TextView hexName;
	private ImageButton convert;
	
	public RGBFragment(){
		this.setRetainInstance(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//		return super.onCreateView(inflater, container, savedInstanceState);
		View fragmentView = inflater.inflate(R.layout.color_tab1, container, false);
		
		r = (EditText) fragmentView.findViewById(R.id.colorR);
		g = (EditText) fragmentView.findViewById(R.id.colorG);
		b = (EditText) fragmentView.findViewById(R.id.colorB);
		canvas = (LinearLayout) fragmentView.findViewById(R.id.colorCanvas);
		hexName = (TextView) fragmentView.findViewById(R.id.colorID);
		convert = (ImageButton) fragmentView.findViewById(R.id.doConversion);
		
		hexName.setText("");
		convert.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v) {
				if(r.getText().toString().length() == 0){
					Toast.makeText(getActivity(), "Please enter a value for R input", Toast.LENGTH_SHORT).show();
				}else if(g.getText().toString().length() == 0){
					Toast.makeText(getActivity(), "Please enter a value for G input", Toast.LENGTH_SHORT).show();
				}else if(b.getText().toString().length() == 0){
					Toast.makeText(getActivity(), "Please enter a value for B input", Toast.LENGTH_SHORT).show();
				}else{
					int red = Integer.parseInt(r.getText().toString());
					int green = Integer.parseInt(g.getText().toString());
					int blue = Integer.parseInt(b.getText().toString());
				
					if(red > 255 || red < 0){
						Toast.makeText(getActivity(), "Please enter a value for R between 0 and 255", Toast.LENGTH_SHORT).show();
					}else if(green > 255 || green < 0){
						Toast.makeText(getActivity(), "Please enter a value for G between 0 and 255", Toast.LENGTH_SHORT).show();
					}else if(blue > 255 || blue < 0){
						Toast.makeText(getActivity(), "Please enter a value for B between 0 and 255", Toast.LENGTH_SHORT).show();
					}else{
						String hex = "#"+switcher(red/16)+switcher(red%16);
						hex += switcher(green/16)+switcher(green%16);
						hex += switcher(blue/16)+switcher(blue%16);
						canvas.setBackgroundColor(Color.parseColor(hex));
						hexName.setText(hex);
					}
				}
			}// END onClick()
		});
		
		return fragmentView;
	}// END onCreateView()

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
