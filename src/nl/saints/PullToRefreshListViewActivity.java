package nl.saints;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class PullToRefreshListViewActivity extends Activity{

	String[]	listItems	= { "item 1", "item 2 ", "list", "android", "item 3", "foobar", "bar", "blu", "boooo", "ajax" };

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		PullToRefreshListView list = (PullToRefreshListView) findViewById(R.id.list);
		list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems));
	}
}
