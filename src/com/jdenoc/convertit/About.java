package com.jdenoc.convertit;
// About.java
// GUI: output.xml
//		fadein.xml	(for animation)
// Author: Denis O'Connor
// Last modified: 05/7/12
// Displays the "about" feature

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class About extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.output);
		
		TextView text = (TextView) findViewById(R.id.outputValue);

		String statement = "This App will allow users to preform conversions in:" +
				"\n\t\u2022 Currency\n\t\u2022 Temperature\n\t\u2022 Length" +
				"\n\t\u2022 Volume\n\t\u2022 Mass\n\t\u2022 Time" +
				"\n\t\u2022 Speed\n\t\u2022 Colour\n\n" +
				"I hope you find it as useful as I had intended.\n";
		statement += "\t\t\t\t\tVersion:"+getResources().getString(R.string.version);
		
		Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);
		text.startAnimation(fadeIn);
		text.setGravity(Gravity.LEFT);
		text.setPadding(10, 5, 10, 10);
		text.setText(statement);
	}//	END onCreate()
}