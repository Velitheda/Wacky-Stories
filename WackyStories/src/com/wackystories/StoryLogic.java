//Written by Jasmine Vickery
//This class handles reading the text file in, extracting the word types needed, 
//taking the list of words from the user and splicing them into the correct position 
//in the story.

// The story is entered into a text file, with the words that need be filled in 
// with commands like /1name_(male) in the appropriate spot, the / to signal that this 
// is a word to be added, the number to signal which number word it is, and the rest to 
// describe the word to the user, with underscores instead of spaces.  Use just a / and the number
// if a word is to be repeated.

package com.wackystories;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class StoryLogic {

	private String tag = MainActivity.class.getSimpleName();

	private Scanner reader;
	private ArrayList<String> story;

	private ArrayList<String> wordTypes;
	private ArrayList<String> userWords;
	private String[] wordFragments;

	private Context c;

	public StoryLogic(Context c) throws IOException {
		this.c = c;
		getWordTypes();
		story = new ArrayList<String>();
	}

	// splices story and words user picked together
	public ArrayList<String> getStory(ArrayList<String> userWords)
			throws IOException {
		BufferedInputStream in = new BufferedInputStream(c.getAssets().open(
				"Milo_Story.txt"));
		reader = new Scanner(in);
		String currentWord;
		int userWordIndex = 1;
		int storyIndex = 0;
		while (reader.hasNext()) {
			currentWord = reader.next();
			if (currentWord.charAt(0) == '/') {
				if (currentWord.contains(".")) {
					userWordIndex = Integer
							.parseInt("" + currentWord.charAt(1));
					currentWord = userWords.get(userWordIndex - 1);
					story.add(currentWord + ". ");
				} else {
					userWordIndex = Integer
							.parseInt("" + currentWord.charAt(1));
					currentWord = userWords.get(userWordIndex - 1);
					story.add(currentWord + " ");
				}
			} else {
				story.add(currentWord + " ");
			}
		}
		return story;
	}

	// gets the types of words the user must pick
	public ArrayList<String> getWordTypes() throws IOException {
		BufferedInputStream in = new BufferedInputStream(c.getAssets().open(
				"Milo_Story.txt"));
		reader = new Scanner(in);
		String currentWord;
		ArrayList<String> wordTypes = new ArrayList<String>(100);
		int wordIndex;
		int maxIndex = 0;
		while (reader.hasNext()) {
			currentWord = reader.next();
			if (currentWord.charAt(0) == '/') {
				wordIndex = Integer.parseInt("" + currentWord.charAt(1));
				if (wordIndex > maxIndex) {
					while (currentWord.contains("_")) {
						currentWord = currentWord.substring(0,
								currentWord.indexOf('_'))
								+ " "
								+ currentWord.substring(currentWord
										.indexOf('_') + 1);
					}
					if (currentWord.contains(".")) {
						wordTypes.add(currentWord.substring(2,
								currentWord.length() - 1));
						maxIndex++;
					} else {
						wordTypes.add(currentWord.substring(2));
						maxIndex++;
					}
				}
			}
		}
		this.wordTypes = wordTypes;
		return wordTypes;
	}

	public int getNumWords() {
		return wordTypes.size();
	}

}
