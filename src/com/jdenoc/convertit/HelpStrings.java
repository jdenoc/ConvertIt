package com.jdenoc.convertit;
// HelpStrings.java
// GUI: n/a
// Author: Denis O'Connor
// Last Modified: 28/6/12
// Contains functions that provide all the text for the Help Screen

public class HelpStrings {

	public String currencyStrings(int position){
		String[] info = {
		// position 0
			"Need help figuring out Currency conversions? Then you've come to the right place. ^_^",
		// position 1
			"To access the Currency conversion function, tap on the Currency button of the main screen. You will need to have an active internet connection.\n" +
			"You can connect to the internet via a number of ways:\n\t\u2022 Tapping the Currency button of the main screen while not connected"+
			"\n\t\u2022 Pressing the \"MENU\" button of your device, then tap Settings. When the Settings Menu opens, tap \"Activate Internet\"" +
			"\n\t\u2022 Tapping on \"TAP HERE\" just below.",
		// position 2
			"\t\t\t\t\tTAP HERE",
		// position 3
			"Once you've gained access, fill the input with a decimal value.\nNow select the region of your input and the corresponding currency.\n" +
			"Now select the region of the currency you with to convert to and the currency.\nNow ConvertIt.\n\nYou will be presented with an acurate value up to 2 decimal places.\n\n\n" +
			"Should you wish to change how currencies are displayed to you, you can do this by going to the settings and tapping \"Change Currency Designation\". Or you could this by tapping on \"CLICK HERE\" just below.",
		// position 4
			"This will change the display for mat of the currency to either a 3 letter format (USD) or a full name format (United States Dollar)"
		};
		return info[position];
	}// END currencyStrings()
	
	public String tempStrings(int position){
		String[] info = {
//		position 0
			"Need help figuring out Currency conversions? Then you've come to the right place. ^_^",
//		position 1
			"To gain access to the Temperature function, tap on the \"Temperature\" icon on the main screen.\n" +
			"From here enter a decimal value into the input box and select the unit you wish to convert from and the unit you wish to convert to.\n\n" +
			"You can also change the amount of decimal places that your output will display. You can do this by" +
			"Pressing the \"MENU\" button of your device, then tap Settings. When the Settings Menu opens, tap \"No. of Decimal Places\", or by tapping \"TAP HERE\" below.",
//		position 2
			"\t\t\t\tTAP HERE",
//		position 3
			"By default this setting will be set to 3 decimal places."
		};
		return info[position];
	}// END tempStrings()
	
	public String lenStrings(int position){
		String[] info = {
//		position 0
			"Need help figuring out Length conversions? Then you've come to the right place. ^_^",
//		position 1
			"To gain access to the Length function, tap on the \"Length\" icon on the main screen.\n" +
			"From here enter a decimal value into the input box and select the unit you wish to convert from and the unit you wish to convert to.\n\n" +
			"You can also change the amount of decimal places that your output will display. You can do this by" +
			"Pressing the \"MENU\" button of your device, then tap Settings. When the Settings Menu opens, tap \"No. of Decimal Places\", or by tapping \"TAP HERE\" below.",
//		position 2
			"\t\t\t\tTAP HERE",
//		position 3
			"By default this setting will be set to 3 decimal places.",
//		position 4
			"In case you are unaware of some of the units identifiers, here they are in their un abreviated forms:\n" +
			"\t\u2022 \"MM\" = Millimetre\n\t\u2022 \"CM\" = Centimetre\n\t\u2022 \"M\" = Metre\n\t\u2022 \"KM\" = Kilometre"
		};
		return info[position];
	}// END lenStrings()
	
	public String volStrings(int position){
		String[] info = {
//		position 0
			"Need help figuring out Volume conversions? Then you've come to the right place. ^_^",
//		position 1
			"To gain access to the Volume function, tap on the \"Volume\" icon on the main screen.\n" +
			"From here enter a decimal value into the input box and select the unit you wish to convert from and the unit you wish to convert to.\n\n" +
			"You can also change the amount of decimal places that your output will display. You can do this by" +
			"Pressing the \"MENU\" button of your device, then tap Settings. When the Settings Menu opens, tap \"No. of Decimal Places\", or by tapping \"TAP HERE\" below.",
//		position 2
			"\t\t\t\tTAP HERE",
//		position 3
			"By default this setting will be set to 3 decimal places.",
//		position 4
			"There are 3 type of measurements for volume:\n\t\u2022 US\n\t\u2022 UK (Imperial)\n\t\u2022 Metric.\n" +
			"\t\tNote: Units of the same name will have different values depending on where they are associated with \"(US)\" or \"(UK)\"\n" +
			"In case you are unaware of some of the units identifiers, here they are in their un abreviated forms:\n" +
			"\t\u2022 \"Fl.Oz(US)\" = US Fluid Ounce\n\t\u2022 \"Fl.Oz(UK)\" = Imperial Fluid Ounce\n"
		};
		return info[position];
	}// END volStrings()

	public String massStrings(int position){
		String[] info = {
//		position 0
			"Need help figuring out Mass conversions? Then you've come to the right place. ^_^",
//		position 1
			"To gain access to the Mass function, tap on the \"Mass\" icon on the main screen.\n" +
			"From here enter a decimal value into the input box and select the unit you wish to convert from and the unit you wish to convert to.\n\n" +
			"You can also change the amount of decimal places that your output will display. You can do this by" +
			"Pressing the \"MENU\" button of your device, then tap Settings. When the Settings Menu opens, tap \"No. of Decimal Places\", or by tapping \"TAP HERE\" below.",
//		position 2
			"\t\t\t\tTAP HERE",
//		position 3
			"By default this setting will be set to 3 decimal places."
		};
		return info[position];
	}// END massStrings()
	
	public String speedStrings(int position){
		String[] info = {
//		position 0
			"Need help figuring out Speed conversions? Then you've come to the right place. ^_^",
//		position 1
			"To gain access to the Speed function, tap on the \"Speed\" icon on the main screen.\n" +
			"From here enter a decimal value into the input box and select the unit you wish to convert from and the unit you wish to convert to.\n\n" +
			"You can also change the amount of decimal places that your output will display. You can do this by" +
			"Pressing the \"MENU\" button of your device, then tap Settings. When the Settings Menu opens, tap \"No. of Decimal Places\", or by tapping \"TAP HERE\" below.",
//		position 2
			"\t\t\t\tTAP HERE",
//		position 3
			"By default this setting will be set to 3 decimal places.",
//		position 4
			"In case you are unaware of some of the units identifiers, here they are in their un abreviated forms:\n" +
			"\t\u2022 \"m/s\" = Metres per Second\n\t\u2022 \"km/h\" = Kilometres per Hour\n\"mph\" = Miles per Hour\n" +
			"\t\u2022 \"Knots\" are based on Nautical Miles\n\t\u2022 \"ft/s\" = Feet per Second\n" +
			"\t\u2022 \"Mach\" is a measurement for the Speed of sound"
		};
		return info[position];
	}// END speedStrings()
	
	public String colourStrings(int position){
		String[] info = {
//		position 0
			"Need help figuring out Colour conversions? Then you've come to the right place. ^_^",
//		position 1
			"To gain access to the Colour function, tap on the \"Colour\" icon on the main screen.\n" +
			"From here enter you will given the choice of two methods (Tabs) of conversion:\n\t\t\u2022 RGB\n\t\t\u2022 HEX\n\n" +
			"The 1st tab (RGB) allows you to convert Red, Green and Blue colour values to a Hexidecimal code. It will also display how the colour will look.\n" +
			"The 2nd tab (HEX) allows you to convert Hexidecimal code to Red, Green and Blue colour values, as well as display how the colour will look." +
			"a decimal value into the input box and select the unit you wish to convert from and the unit you wish to convert to.\n\n"
		};
			return info[position];
		}// END colourStrings()
	
	public String timeStrings(int position){
		String[] info = {
//		position 0
			"Need help figuring out Time conversions? Then you've come to the right place. ^_^",
//		position 1
			"To gain access to the Time function, tap on the \"Time\" icon on the main screen.\n" +
			"Time and Time Zone " +
			"From here enter you will given the choice of two methods (Tabs) of conversion:\n\t\t\u2022 Location\n\t\t\u2022 Time Zone\n\n" +
			"The 1st tab (Location) allows you to perform a time conversion by allowing you to choose your desired Time Zone by region and city.\n" +
			"The 2nd tab (Time Zone) allows you to perform a time conversion by allowing you to choose your desired time zone\n\n" +
			"Both tabs allow you to set the Time you wish to convert and the Time Zone you wish to convert from. By default, current Time and Time Zone are used. \n\n" +
			"You can change the format of the time display by pressing the \"MENU\" button on the device. Then tap on the Settings button to open the Settings menu. There tap \"Change Time Display Format\".\n" +
			"You can also access this by tapping the \"TAP HERE\" button below.",
//		position 2
			"\t\t\t\tTAP HERE"
		};
		return info[position];
	}// END timeStrings()
}
