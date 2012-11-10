package com.jdenoc.convertit.fragments;
// HEXFragment.java
// GUI: color_tab2.xml
// Author: Denis O'Connor
// Last modified: 08-NOV-2012
// Fragment for Color Conversions. 
// Converts HEX to RGB color codes.

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
public class HEXFragment extends Fragment{

	private EditText hex;
	private LinearLayout canvas;
	private TextView rgb;
	private ImageButton convert;
	
	public HEXFragment(){
		this.setRetainInstance(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View fragmentView = inflater.inflate(R.layout.color_tab2, container, false);
		
		hex = (EditText) fragmentView.findViewById(R.id.hexCode);
		canvas = (LinearLayout) fragmentView.findViewById(R.id.colorCanvas);
		rgb = (TextView) fragmentView.findViewById(R.id.colorID);
		convert = (ImageButton) fragmentView.findViewById(R.id.doConversion);
		
		convert.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if(isValid()){
					String hexCode = hex.getText().toString();
					rgb.setText(getRGB(hexCode));
				}
			}// END onClick()
		});
		rgb.setText("");
		
		return fragmentView;
	}// END onCreateView
	
	public boolean isValid(){
		String hexCode = hex.getText().toString();
		
		if(hexCode.length() == 0){
			Toast.makeText(getActivity(), "Please enter a value for HEX input", Toast.LENGTH_SHORT).show();
			return false;
		}

		String warning = "Invalid input.\nInput must be 7 or 4 characters long with a '#' at the start.\n" +
				"6 or 3 characters without a '#' at the start.";
		if(hexCode.charAt(0) == '#'){
			if(hexCode.length() != 7){
				if(hexCode.length() != 4){
					Toast.makeText(getActivity(), warning, Toast.LENGTH_LONG).show();
					return false;
				}
			}
			hexCode = hexCode.substring(1);
		}else {
			if(hexCode.length() != 6){
				if(hexCode.length() != 3){
					Toast.makeText(getActivity(), warning, Toast.LENGTH_LONG).show();
					return false;
				}
			}
		}
		
		if(hexCode.matches("[A-F0-9]{6}")){
			return true;
		}else if(hexCode.matches("[A-F0-9]{3}")){
			return true;
		}else{
			Toast.makeText(getActivity(), "Invalid input", Toast.LENGTH_SHORT).show();
			return false;
		}
				
	}// END isValid()
	
	
	public String getRGB(String hex){
		String rgb = "";
		String[] titles = {"R:", "G:", "B:"};
		
		if(hex.charAt(0)=='#'){
			hex = hex.substring(1);
		}
		
		if(hex.length() == 3){
			hex = ""+hex.charAt(0)+hex.charAt(0)+
					hex.charAt(1)+hex.charAt(1)+
					hex.charAt(2)+hex.charAt(2);
		}
		
		canvas.setBackgroundColor(Color.parseColor("#"+hex));
		
		for(int i=0, value=0; i<hex.length(); i++){
			if(i%2 == 0){
				rgb += titles[i/2]+" ";
				value = 0;
				switch(hex.charAt(i)){
				case 'A':
					value = 10*16;
					break;
				case 'B':
					value = 11*16;
					break;
				case 'C':
					value = 12*16;
					break;
				case 'D':
					value = 13*16;
					break;
				case 'E':
					value = 14*16;
					break;
				case 'F':
					value = 15*16;
					break;
				default:
					value = Character.getNumericValue(hex.charAt(i))*16;
					break;
				}
			}else{
				switch(hex.charAt(i)){
				case 'A':
					value += 10;
					break;
				case 'B':
					value += 11;
					break;
				case 'C':
					value += 12;
					break;
				case 'D':
					value += 13;
					break;
				case 'E':
					value += 14;
					break;
				case 'F':
					value += 15;
					break;
				default:
					value += Character.getNumericValue(hex.charAt(i));
					break;
				}
				rgb += value+", ";
			}
		}
		return rgb;
	}// END getRGB()

}
