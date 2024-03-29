package killerapp.istanbul24;

import java.util.ArrayList;

import killerapp.istanbul24.db.Venue;

import org.mapsforge.core.model.GeoPoint;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * The main activity. Categories are selected.
 * 
 */
public class MainActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case R.id.settings_range_button:
			final EditText input = new EditText(this);
	        input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
	        
	        SharedPreferences init = getSharedPreferences("init", 0);
	        input.setText(init.getFloat("range", 1)+"");
			
			new AlertDialog.Builder(this).setView(input).setTitle(R.string.settings_range)
					.setNeutralButton("Ok", new DialogInterface.OnClickListener()
					{
						public void onClick(DialogInterface dialog, int whichButton)
						{
							try
							{
								float range = Float.parseFloat(input.getText().toString());
								Editor editor = getSharedPreferences("init", 0).edit();
								editor.putFloat("range", range);
								editor.commit();
							}
							catch (NumberFormatException nfe)
							{

							}

						}
					}).show();
			break;
		}
		return true;
	}

	public void startQuestionActivity(View view)
	{
		ArrayList<Integer> questions = null;

		// TODO: get questions for selected tag
		switch (view.getId())
		{
		case R.id.eatButton:
			break;
		case R.id.attractionsButton:
			break;
		case R.id.goButton:
			break;
		}

		ArrayList<Integer> selected = new ArrayList<Integer>(); // It is empty.
																// Selected tags
																// will add into
																// this.
		ArrayList<Venue> venues = new ArrayList<Venue>(); // It is empty. Places
															// will add into
															// this.

		Intent intent = new Intent(this, QuestionActivity.class); // Intent is
																	// created.
		intent.putExtra("selected", selected);
		intent.putExtra("question", 0);
		intent.putExtra("questions", questions);
		intent.putParcelableArrayListExtra("venues", venues);
		startActivity(intent); // Activity is created.
	}

	public void routeDemo(View view)
	{
		// If can't unzip and can't find the map directory, stop
		if (!Download.unzip() && !Download.getDir().exists())
		{
			Toast.makeText(this, "Please download the map.", Toast.LENGTH_LONG)
					.show();
			return;
		}
		else
			Download.deleteZip();
		/*
		 * GeoPoint start = new GeoPoint(41.02546548103654, 28.968420743942254);
		 * GeoPoint end = new GeoPoint(41.011898495994, 28.97524428367614);
		 */

		GeoPoint start = new GeoPoint(41.0119, 28.925972);
		GeoPoint end = new GeoPoint(41.01177, 29.013519);

		Intent intent = new Intent(this, RouteActivity.class);
		intent.putExtra("start", start);
		intent.putExtra("end", end);
		startActivity(intent);

	}

	public void shareDemo(View view)
	{
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_TEXT, "I found this awesome place with 24Istanbul.");

		// startActivity(Intent.createChooser(fbIntent(), "Share with"));
		startActivity(intent);
	}

}
