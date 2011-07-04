package nl.saints;

import nl.saints.PullToRefreshListView.OnRefreshListener;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class PullToRefreshListViewActivity extends Activity implements OnRefreshListener{

	String[]	listItems	= { "item 1", "item 2 ", "list", "android", "item 3", "foobar", "bar", "blu", "boooo", "ajax" };
	PullToRefreshListView list;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		list = (PullToRefreshListView) findViewById(R.id.list);
		list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems));
		
		list.setOnRefreshListener(this);
	}

	public void onRefresh(){
		new AsyncTask<Void, Void, Void>(){

			@Override
			protected Void doInBackground(Void... params){
				try{
					Thread.sleep(2000);
				}catch(InterruptedException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
			
			@Override
			protected void onPostExecute(Void result){
				list.onRefreshComplete();
			}
		}.execute();
	}
}
