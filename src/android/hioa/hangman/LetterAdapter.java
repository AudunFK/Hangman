package android.hioa.hangman;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.content.Context;
import android.widget.Button;


public class LetterAdapter extends BaseAdapter {
	
	private String[] letters;
	private LayoutInflater letterInf;
	
	public LetterAdapter(Context c) { // Initsierer bokstavene som vises i input viewet. 
			
		letters = c.getResources().getStringArray(R.array.alphabet);
		letterInf = LayoutInflater.from(c);
	}

	@Override
	public int getCount() {
		  return letters.length;
		}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}
	/***
	 * creates a button for the latter in the alphabet
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	  Button letterBtn;
	  if (convertView == null) {
	    letterBtn = (Button)letterInf.inflate(R.layout.letter, parent, false);
	  } else {
	    letterBtn = (Button) convertView;
	  }
	 
	  letterBtn.setText(letters[position]);
	  return letterBtn;
	}
			
	
	}


