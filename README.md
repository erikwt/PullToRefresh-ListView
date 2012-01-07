#  Android 'Pull to Refresh' ListView library

![Screenshot](https://github.com/erikwt/PullToRefresh-ListView/raw/master/android-pull-to-refresh.png)

Demo video: http://www.youtube.com/watch?v=VjmdELnm3GI

## Project
A generic, customizable, open source Android ListView implementation that has 'Pull to Refresh' functionality. This ListView can be used as a replacement of the normal Android android.widget.ListView class.

``` java
/**
 * Users of this library should implement OnRefreshListener and call setOnRefreshListener(..)
 * to get notified on refresh events. The using class should call onRefreshComplete() when
 * refreshing is finished.
 * 
 * The using class can call setRefreshing() to set the state explicitly to refreshing. This 
 * is useful when you want to show the spinner and 'Refreshing' text when the
 * refresh was not triggered by 'Pull to Refresh', for example on start.
 * 
 * @author Erik Wallentinsen <dev+ptr at erikw.eu>
 */
```


## Features
* Drop-in replacement for the android.widget.ListView widget
* Nice graphics (thanks to johannilsson - see credits)
* Animations; bouncing animation and rotation animation (see [video](http://www.youtube.com/watch?v=VjmdELnm3GI))
* Easy to integrate in your Android project (see usage)
* Works for any Android project targeting Android 1.6 (API level 4) and up
* Highly customizable (using Android styles)


## Info
Feel free to ask questions, report bugs and request features at dev at erikw dot eu 
or on the PullToRefresh-ListView github [issue](https://github.com/erikwt/PullToRefresh-ListView/issues) page. 
You can also hit me up on [twitter](http://www.twitter.com/ewallentinsen) if that's your thing.


## Usage

### Example project
Check out the [example project](https://github.com/erikwt/PullToRefresh-ListView/tree/master/sampleproject) 
in this repository for an implementation example. There is also moretechnical documentation available as 
javadoc in the [library project code](https://github.com/erikwt/PullToRefresh-ListView/blob/master/libraryproject/src/eu/erikw/PullToRefreshListView.java).

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

### Style
To change the looks of the 'PullToRefresh' ListView, you can override the styles that are defined in the library project.
Default, the looks are very basic (see screenshot above), with black text on a white background. You can change every
aspect to your needs though, like the arrow image, text size, text color and background.

To do so, override the style attributes to your liking, like the following example:

``` xml
<style name="ptr_text">
        
    <!-- Change the text style and color -->
    <item name="android:textStyle">bold|italic</item>
    <item name="android:textColor">#cccccc</item>
</style>
```

The various styles you can override are;

* ptr_headerContainer
* ptr_header
* ptr_arrow
* ptr_spinner
* ptr_text

The default attributes can be found in the [library project](https://github.com/erikwt/PullToRefresh-ListView/blob/master/libraryproject/res/values/default_style.xml)


## Author
I’m a young, enthusiastic hacker from Amsterdam. I study computer science at the VU (Free University) and my work mostly involves Android programming. I do a lot of hacking in my spare time, resulting in many projects I want to share with the world.

* Twitter: [@ewallentinsen](http://www.twitter.com/ewallentinsen)
* Blog: http://www.erikw.eu


## Other projects
This project might not be the best library for you to use in your project, depending on your likes and needs. Consider
using these great 'Pull to Refresh' libraries;

* chrisbanes: https://github.com/chrisbanes/Android-PullToRefresh
* johannilsson: https://github.com/johannilsson/android-pulltorefresh


## Credits
* johannilsson for the original PullToRefresh project and some graphics: https://github.com/johannilsson/android-pulltorefresh
* chrisbanes for the layout of this README and for inspiring me to opensource this project as well (http://github.com/chrisbanes/Android-PullToRefresh)


## License
Copyright (c) 2012 - Erik Wallentinsen

Licensed under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)

