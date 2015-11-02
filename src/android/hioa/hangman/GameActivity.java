package android.hioa.hangman;

import java.util.Random;

import android.R.string;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.media.AudioManager;
import android.media.SoundPool;

public class GameActivity extends Activity {
	public static final String PREFS_NAME = "MyPrefsFile";
	private Random rand;
	int randomIndex;
	string newWord;
	private AlertDialog helpAlert;
	protected MyApplication app; // tester my application
/***
 * Oncreate called whenever activity starts. 
 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		getActionBar().setDisplayHomeAsUpEnabled(true);//Adding an actionbar element to go back.  
		app = (MyApplication)getApplication();
		Resources res = getResources();
		app.words = res.getStringArray(R.array.words);
		rand = new Random();
		app.currentWord = "";
		app.letters = (GridView)findViewById(R.id.letters);
		app.wordLayout = (LinearLayout)findViewById(R.id.word);
		app.bodyParts = new ImageView[app.numberOfBodyparts];
		app.bodyParts[0] = (ImageView)findViewById(R.id.head);
		app.bodyParts[1] = (ImageView)findViewById(R.id.body);
		app.bodyParts[2] = (ImageView)findViewById(R.id.arm1);
		app.bodyParts[3] = (ImageView)findViewById(R.id.arm2);
		app.bodyParts[4] = (ImageView)findViewById(R.id.leg1);
		app.bodyParts[5] = (ImageView)findViewById(R.id.leg2);
		playGame();
	}
/***
 * Main method for playing game
 */
	private void playGame() {
		app.totalNumberOfSolutions = app.words.length;
		app.numberOfPlayedGames++;
		if (app.numberOfPlayedGames < app.totalNumberOfSolutions) { 
		} else { showNoMoreWords(); }
		
		String newWord = app.words[rand.nextInt(app.words.length)];
		app.currentWord = chooseNewRandomWord();
		while(newWord.equals(app.currentWord)) newWord= app.words[rand.nextInt(app.words.length)];
		app.charViews = new TextView[app.currentWord.length()];
		app.wordLayout.removeAllViews();

			
		for(int i = 0; i < app.currentWord.length(); i++) {
			app.charViews[i] = new TextView(this);
			app.charViews[i].setText(""+app.currentWord.charAt(i));
			app.charViews[i].setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			app.charViews[i].setGravity(Gravity.CENTER);
			app.charViews[i].setTextColor(Color.TRANSPARENT);
			app.charViews[i].setBackgroundResource(R.drawable.letter_bg);
			//add to layout
			app.wordLayout.addView(app.charViews[i]);

		}
		
		
		app.ltrAdapt=new LetterAdapter(this);
		app.letters.setAdapter(app.ltrAdapt);
		app.currentBodypart = 0;
		app.numberOfCharacters=app.currentWord.length();
		app.currentNumber=0; 

		for(int p = 0; p < app.numberOfBodyparts; p++) {
			app.bodyParts[p].setVisibility(View.INVISIBLE);	
		}
	}
/***
 * Method triggerd when user presses botton on keyboard 
 * @param view
 */
	// Method evoked if player has pressed a letter to guess.
	public void letterPressed(View view) {
		String letter=((TextView)view).getText().toString();
		char letterChar = letter.charAt(0);
		view.setEnabled(false);
		view.setBackgroundResource(R.drawable.letter_down);
		//	SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0); //settings eneble getmethods 

		boolean correct= false;
		for(int i = 0; i < app.currentWord.length(); i++) {
			if(app.currentWord.charAt(i)==letterChar){
				correct = true;
				app.currentNumber++;
				app.charViews[i].setTextColor(Color.WHITE);
			}
		}	
		if (correct) {
			//Correct answer given.
			if(app.currentNumber==app.numberOfCharacters){
				disableBtns();
				app.winCount ++;
				final TextView txtValue = (TextView) findViewById(R.id.txtExample);
				txtValue.setText(Integer.toString(app.winCount));
				//int updatecount = settings.getInt(sharedWincount, "")
				//txtValue.setText(settings.getInt(sharedCount, defValue))
				showWin();
			}
		} 
		else if (app.currentBodypart < app.numberOfBodyparts) {
			//User still has guesses left in the game.
			app.bodyParts[app.currentBodypart].setVisibility(View.VISIBLE);
			app.currentBodypart++;
		}
		else{
			//shows the player has lost the game.
			disableBtns();
			app.loseCount ++;
			showLose();	
		}
	}
/***
 * Disablebtns
 */
	public void disableBtns() {
		int numLetters = app.letters.getChildCount();
		for (int l = 0; l < numLetters; l++) {
			app.letters.getChildAt(l).setEnabled(false);
		}
	}	
	/***
	 * Actionbar
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
/***
 * Method contaning switch for actionbar statement.
 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		case R.id.action_help:
			showHelp();
			return true;
		case R.id.action_abort:
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
/***
 * Method that makes sure we dont play a word that has allready been used.
 * @return
 */
	private String chooseNewRandomWord() { 
		int randomIndex = rand.nextInt(app.words.length); 
		String newWord = app.words[randomIndex]; 
		while (newWord.equals("")) { randomIndex = rand.nextInt(app.words.length); 
		newWord = app.words[randomIndex]; }
		app.words[randomIndex] = ""; 
		return newWord; }

	protected void onStop(){
		super.onStop();
		SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
		editor.putInt("sharedWinCount", app.winCount);
		editor.putInt("sharedLoseCount", app.loseCount);
		editor.commit();

	}
	protected void onPause() {
		super.onPause();
		SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
		editor.putInt("sharedWinCount", app.winCount);
		editor.putInt("sharedLoseCount", app.loseCount);
		editor.commit();
	}

	protected void onResume() {
		super.onResume();

	}
/***
 * The dialog displays if a user requests help about the rules of the game. 
 */
	public void showHelp() {
		AlertDialog.Builder helpBuild = new AlertDialog.Builder(this);
		helpBuild.setTitle(getString(R.string.Help));
		helpBuild.setMessage(getString(R.string.rules));
		helpBuild.setPositiveButton(getString(R.string.ok),
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				helpAlert.dismiss();
			}});
		helpAlert = helpBuild.create();
		helpBuild.show();
	}
	
	public void showNoMoreWords(){
		AlertDialog.Builder noMoreWords = new AlertDialog.Builder(this);
		noMoreWords.setTitle(getString(R.string.noMoreWordsTitle));
		noMoreWords.setMessage(getString(R.string.noMoreWordsMessage));
		noMoreWords.setNegativeButton(getString(R.string.noMoreWordsButton),
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				GameActivity.this.finish();
			}});
		noMoreWords.show();
	}

	/***
	 * The information displayed if a player looses the game,
	 */
	public void showLose() {
		final MediaPlayer mp = MediaPlayer.create(this, R.raw.fail);
		mp.start();
		AlertDialog.Builder loseBuild = new AlertDialog.Builder(this);
		loseBuild.setTitle("OOPS");
		loseBuild.setMessage(getString(R.string.looseMessage1)+app.currentWord + (getString(R.string.looseMessage2)+
				app.loseCount + (getString(R.string.looseMessage3)
						)));
		loseBuild.setPositiveButton(getString(R.string.looseAgain),
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				GameActivity.this.playGame();
			}});

		loseBuild.setNegativeButton(getString(R.string.setTittle),
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				GameActivity.this.finish();
			}});
		loseBuild.show();	
	}

	
	
	/***
	 * The information displayed if a user wins the game. 
	 */
	public void showWin() {
		final MediaPlayer mp = MediaPlayer.create(this, R.raw.tada);
		mp.start();
		//let user know they have won, ask if they want to play again
		AlertDialog.Builder winBuild = new AlertDialog.Builder(this);
		winBuild.setTitle(getString(R.string.winTittle));
		//winBuild.setTitle(app.test);
		winBuild.setMessage(getString(R.string.winMessage1)+app.currentWord);
		winBuild.setPositiveButton(getString(R.string.winAgain), 
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				GameActivity.this.playGame();
			}});
		winBuild.setNegativeButton(getString(R.string.winExit), 
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				GameActivity.this.finish();
			}});
		winBuild.show();	
	}

	public void showMute(){
		Toast.makeText(getApplicationContext(),
				"you have muted the game", Toast.LENGTH_SHORT)
				.show();	
	}
	/***
	 * 
	 * Prepering the imageview and filling with bodyparts from id. 
	 */
	public void setUpImageView(){
	app.wordLayout = (LinearLayout)findViewById(R.id.word);
	app.bodyParts = new ImageView[app.numberOfBodyparts];
	app.bodyParts[0] = (ImageView)findViewById(R.id.head);
	app.bodyParts[1] = (ImageView)findViewById(R.id.body);
	app.bodyParts[2] = (ImageView)findViewById(R.id.arm1);
	app.bodyParts[3] = (ImageView)findViewById(R.id.arm2);
	app.bodyParts[4] = (ImageView)findViewById(R.id.leg1);
	app.bodyParts[5] = (ImageView)findViewById(R.id.leg2);
	}
	
	
	/***
	 * setting upp the view for the textfield with correct words
	 */
	public void displaySoltuinHints(){
		
		}
}	