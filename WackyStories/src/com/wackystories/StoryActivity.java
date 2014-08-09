//Written by Jasmine Vickery
//This activity displays the story with the words the user has entered in their correct places.

package com.wackystories;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class StoryActivity extends Activity {

	private String tag = MainActivity.class.getSimpleName();
	private StoryLogic storyLogic;
	private ArrayList<String> userWords;
	private TextView storyTextBox;

	private ScrollView sv;
	private ArrayList<TextView> words;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_story);

		sv = new ScrollView(this);
		LinearLayout ll = new LinearLayout(this);
		ll.setOrientation(LinearLayout.VERTICAL);
		sv.addView(ll);

		storyTextBox = new TextView(this);
		words = new ArrayList<TextView>();

		userWords = new ArrayList<String>();

		try {
			storyLogic = new StoryLogic(this);

			// sort out stuff from previous activity
			Bundle extras = getIntent().getExtras();
			if (extras != null) {
				for (int i = 0; i < storyLogic.getNumWords(); i++) {
					String something = "" + i;
					userWords.add(extras.getString(something));
				}
			}
			ArrayList<String> story = storyLogic.getStory(userWords);
			String storyString = "";
			for (int i = 0; i < story.size(); i++) {
				storyString += story.get(i);
			}

			storyTextBox.setTextSize(22);
			storyTextBox.setText(storyString);
			Log.d(tag, "Hi");
			for (String s : story) {
				TextView tv = new TextView(this);
				tv.setText(s);
				words.add(tv);
				// ll.addView(tv);
			}

			ll.addView(storyTextBox);

		} catch (IOException e) {
			Log.d(tag, "Couldn't find story :'(");
		}

		Button anotherStoryButton = new Button(this);
		anotherStoryButton.setText(R.string.anotherStoryButtonText);
		ll.addView(anotherStoryButton);
		anotherStoryButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d(tag, "Clicked another story");
				Intent i = new Intent(getApplicationContext(),
						MainActivity.class);
				startActivity(i);
			}
		});
		setContentView(sv);
		Log.d(tag, "finished story logic");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.story, menu);
		return true;
	}

}
