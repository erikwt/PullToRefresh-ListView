#  Android Pull to Refresh ListView

![Screenshot](https://github.com/erikwt/PullToRefresh-ListView/raw/master/android-pull-to-refresh.png)

## Project

``` java
/**
 * A generic Android ListView implementation that has 'Pull to Refresh' functionality.
 * 
 * This ListView can be used in place of the normal Android android.widget.ListView class.
 * 
 * Users of this class should implement OnRefreshListener and call setOnRefreshListener(..)
 * to get notified on refresh events. The using class should call onRefreshComplete() when
 * refreshing is finished.
 * 
 * The using class can call setRefreshing() to set the state explicitly to refreshing. This 
 * is useful when you want to show the spinner and 'Refreshing' text when the
 * refresh was not triggered by 'Pull to Refresh', for example on start.
 * 
 * @author Erik Wallentinsen
 */
```

## Features
* Drop-in replacement for the android.widget.ListView widget
* Nice graphics (thanks to johannilsson - see credits)
* Animations; bouncing animation and rotation animation
* Easy to integrate in your Android project (see usage)

## Info

Feel free to ask questions, report bugs and request features at dev at erikw dot eu 
or on the PullToRefresh-ListView github issue page:
https://github.com/erikwt/PullToRefresh-ListView/issues


## Usage

### Example project

Check out the example project in this repository for an implementation example.

### Layout

``` xml
<!--
  The PullToRefresh-ListView replaces a standard ListView widget,
  and has all the features android.widget.ListView has.
-->
<eu.erikw.PullToRefreshListView
    android:id="@+id/pull_to_refresh_listview"
    android:layout_height="fill_parent"
    android:layout_width="fill_parent" />
```

### Activity

``` java
// Set a listener to be invoked when the list should be refreshed.
PullToRefreshListView listView = (PullToRefreshListView) findViewById(R.id.pull_to_refresh_listview);
listView.setOnRefreshListener(new OnRefreshListener() {
    
    @Override
    public void onRefresh() {
        // Your code to refresh the list contents
        
        // ...
        
        // Make sure you call listView.onRefreshComplete()
        // when the loading is done. This can be done from here or any
        // other place, like on a broadcast receive from your loading
        // service or the onPostExecute of your AsyncTask.
        
        listView.onRefreshComplete();
    }
});

```

## Other projects
This project might not be the best library for you to use in your project, depending on your likes and needs. Consider
using these great 'Pull to Refresh' libraries;

* chrisbanes: https://github.com/chrisbanes/Android-PullToRefresh
* johannilsson: https://github.com/johannilsson/android-pulltorefresh

## Credits
* johannilsson for the original PullToRefresh project that I used as a base, and the awesome
graphic at the top of this readme: https://github.com/johannilsson/android-pulltorefresh
* chrisbanes for the layout of this README and for inspiring me to opensource this project as well (http://github.com/chrisbanes/Android-PullToRefresh)

## License

Copyright (c) 2012 - Erik Wallentinsen

Licensed under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)

