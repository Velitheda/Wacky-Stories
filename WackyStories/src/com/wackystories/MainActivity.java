// Written by Jasmine Vickery

// This is an android app that displays blanks for the user to fill in,
// with prompts as to the type of words,
// and displays a story to the user with their words inserted into it.

// This is the main activity of the app, 
// it collects all the words the user enters and sends them to the other activity
// to display them

package com.wackystories;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private String tag = MainActivity.class.getSimpleName();

	private TextView storyText;
	private StoryLogic storyLogic;
	private ScrollView sv;
	private Button anotherStoryButton;
	private ArrayList<String> userWords;
	private ArrayList<String> promptWords;
	private EditText[] textBoxes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(tag, "Creating1");
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		// setContentView(R.layout.activity_main);

		try {
			storyLogic = new StoryLogic(this);
			Log.d(tag, "Creating1.5");
			promptWords = storyLogic.getWordTypes();
			Log.d(tag, "Creating2");
		} catch (IOException e) {
			Log.d(tag, "Could't load story");
			promptWords = new ArrayList<String>(100);
		}

		sv = new ScrollView(this);
		LinearLayout ll = new LinearLayout(this);
		ll.setOrientation(LinearLayout.VERTICAL);
		sv.addView(ll);
		textBoxes = new EditText[100];
		for (int i = 0; i < promptWords.size(); i++) {
			textBoxes[i] = new EditText(this);
			textBoxes[i].setHint(promptWords.get(i));
			ll.addView(textBoxes[i]);
		}

		Button createStoryButton = new Button(this);
		createStoryButton.setText(R.string.createStoryButtonText);
		createStoryButton.setOnClickListener(createStoryButtonListener);
		ll.addView(createStoryButton);

		this.setContentView(sv);
		Log.d(tag, "Set scroll view");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public OnClickListener createStoryButtonListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Log.d(tag, "Clicked create story");

			userWords = new ArrayList<String>(promptWords.size());
			String enteredWord;
			// getting the words the user entered. Needs to check all is filled
			for (int i = 0; i < promptWords.size(); i++) {
				enteredWord = textBoxes[i].getText().toString();
				enteredWord.trim();
				userWords.add(i, enteredWord);
				Log.d(tag, "Each text box" + textBoxes[i].getText().toString());
			}
			Log.d(tag, "Userwords: " + userWords.toString());
			Intent intent = new Intent(getApplicationContext(),
					StoryActivity.class);

			// passing the array of words with a loop through the intent
			for (int i = 0; i < userWords.size(); i++)
				intent.putExtra("" + i, userWords.get(i));

			boolean allFilledOut = true;
			for (int i = 0; i < promptWords.size(); i++) {
				if (userWords.get(i).equals("")) {
					allFilledOut = false;
				}

			}
			if (allFilledOut)
				startActivity(intent);
			else
				createAlert();
		}
	};

	protected void createAlert() {
		Builder alertDialogBuilder = new AlertDialog.Builder(this);
		// set title
		// alertDialogBuilder.setTitle("Paused");

		// set dialog message
		alertDialogBuilder.setMessage("Please fill in all the blanks");
		alertDialogBuilder.setCancelable(false);

		alertDialogBuilder.setNeutralButton("Ok",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
	}

}
