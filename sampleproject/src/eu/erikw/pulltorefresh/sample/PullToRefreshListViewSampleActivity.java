package eu.erikw.pulltorefresh.sample;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;

public class PullToRefreshListViewSampleActivity extends Activity {
    
	private static ArrayList<String> items;
	static{
		items = new ArrayList<String>();
		
		items.add("Ajax Amsterdam");
		items.add("Barcelona");
		items.add("Manchester United");
		items.add("Chelsea");
		items.add("Real Madrid");
		items.add("Bayern Munchen");
		items.add("Internationale");
		items.add("Valencia");
		items.add("Arsenal");
		items.add("AS Roma");
		items.add("Tottenham Hotspur");
		items.add("PSV");
		items.add("Olympique Lyon");
		items.add("AC Milan");
		items.add("Dortmund");
		items.add("Schalke 04");
		items.add("Twente");
		items.add("Porto");
		items.add("Juventus");
	}
	
	private PullToRefreshListView listView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        listView = (PullToRefreshListView) findViewById(R.id.pull_to_refresh_listview);
        
        // Set the onRefreshListener on the list. You could also use
        // listView.setOnRefreshListener(this); and let this Activity
        // implement OnRefreshListener.
        listView.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				// Your code to refresh the list contents goes here

				// Make sure you call listView.onRefreshComplete()
				// when the loading is done. This can be done from here or any
				// other place, like on a broadcast receive from your loading
				// service or the onPostExecute of your AsyncTask.

				// For the sake of this sample, the code will pause here to
				// force a delay when invoking the refresh
				listView.postDelayed(new Runnable() {
					
					@Override
					public void run() {
						listView.onRefreshComplete();
					}
				}, 2000);
			}
		});
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, items);
        listView.setAdapter(adapter);
    }
}