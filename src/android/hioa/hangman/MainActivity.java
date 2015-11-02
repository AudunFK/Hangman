package android.hioa.hangman;

import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;


public class MainActivity extends Activity implements OnClickListener {

	Spinner spinnerctrl;
    Button btn;
    Locale myLocale;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button playBtn = (Button)findViewById(R.id.playBtn);
        playBtn.setOnClickListener(this);
        
        Button helpBtn = (Button)findViewById(R.id.helpBtn);
        helpBtn.setOnClickListener(this);
        
        Button exitBtn = (Button)findViewById(R.id.exitBtn);
        exitBtn.setOnClickListener(this);
        
        Button langBtn = (Button)findViewById(R.id.langBtn);
        langBtn.setOnClickListener(this);
        
        //Button optionsBtn = (Button)findViewById(R.id.optionsBtn);
       // optionsBtn.setOnClickListener(this);
        
        /***
         * Sets the font of the game. 
         */
       
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/spaceXrebron.ttf");
        playBtn.setTypeface(tf);
        helpBtn.setTypeface(tf);
        exitBtn.setTypeface(tf);
        langBtn.setTypeface(tf);
        //optionsBtn.setTypeface(tf);
        
        
      /***
       * enables and creates a spinner to handel language changes 
       */
        spinnerctrl = (Spinner) findViewById(R.id.spinner1);
        spinnerctrl.setOnItemSelectedListener(new OnItemSelectedListener() {
        	
        	
        	public void onItemSelected(AdapterView<?> parent, View view,
                    int pos, long id) {
 
                if (pos == 1) {
 
                    Toast.makeText(parent.getContext(),
                            "You have selected English", Toast.LENGTH_SHORT)
                            .show();
                    setLocale("en");
                } else if (pos == 2) {
 
                    Toast.makeText(parent.getContext(),
                            "You have selected Norwegian", Toast.LENGTH_SHORT)
                            .show();
                    setLocale("no");
                } 
        	}
 
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
            
 
        });
        }
    /***
     * preforms the language change from the spinner 
     * @param lang
     */
    public void setLocale(String lang) { 
        myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);
    }
      /***
       * onclick method, describes information about actions during buttonclicks
       */
    
    @Override
    public void onClick(View view) {
    	if (view.getId() == R.id.playBtn) {
    		  Intent playIntent = new Intent(this, GameActivity.class);
    		  this.startActivity(playIntent);
    		}
    	
    	if (view.getId() == R.id.helpBtn) {
    		AlertDialog.Builder infoBuild = new AlertDialog.Builder(this);
    		infoBuild.setTitle(getString(R.string.setMessage));
    		infoBuild.setMessage(getString(R.string.rules));
    		//pr¿vd Œ endre strings
    		infoBuild.setPositiveButton(getString(R.string.positiveRules),
				    new DialogInterface.OnClickListener() {
			      public void onClick(DialogInterface dialog, int id) {
			        return;
			    }});
    		infoBuild.show();
						}
    	{ 
    		if (view.getId() == R.id.exitBtn) {
    			AlertDialog.Builder exitBuild = new AlertDialog.Builder(this);
    			exitBuild.setTitle(getString(R.string.setTittle));
        		exitBuild.setMessage(getString(R.string.exitinfo));
        		exitBuild.setNegativeButton(getString(R.string.negativeBtn), new DialogInterface.OnClickListener(){
        			public void onClick(DialogInterface dialog, int id) {
    			        finish();
    			    }	
        		});
        		
        		exitBuild.show();
        			  			
    		}	
    	}
    	
    	if (view.getId() == R.id.langBtn) {
    		spinnerctrl.performClick();
    		spinnerctrl.setVisibility(View.VISIBLE);
    	}
    	
    
					};
  /***
   * shows the actionbar 
   */
   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
  
   /***
    * method handels actions preformed on the actionbar with a switch
    */
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
	 * shows the helpscreen
	 */
	public void showHelp() {
		AlertDialog.Builder helpBuild = new AlertDialog.Builder(this);
		helpBuild.setTitle(getString(R.string.Help));
		helpBuild.setMessage(getString(R.string.rules));
		helpBuild.setPositiveButton(getString(R.string.ok),
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
			}});
		 helpBuild.create();
		helpBuild.show();
	}
}
