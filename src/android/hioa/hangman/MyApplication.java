package android.hioa.hangman;

import android.app.Application;
import android.content.res.Configuration;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.media.AudioManager;
import android.media.SoundPool;

public class MyApplication extends Application {
	
	public String test;
	public String[] words;
	public String currentWord;
	public LinearLayout wordLayout;
	public TextView[] charViews;
	public GridView letters;
	public LetterAdapter ltrAdapt;
	public ImageView[] bodyParts;
	public int numberOfBodyparts=6;
	public int currentBodypart;
	public int numberOfCharacters;
	public int currentNumber;
	public int winCount =0;
	public int loseCount =0;
	public int numberOfPlayedGames = 0;
	public int totalNumberOfSolutions = 0;
	
	
	//public int winSound;
	//public SoundPool sounds;	
	//public int msprite = 1;
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}
 
	@Override
	public void onCreate() {
		super.onCreate();
		test= "testtexts";
	}
	public void initSingletons() {
		//setter verdier
	}
	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}
	@Override
	public void onTerminate() {
		super.onTerminate();
	}
 
}
