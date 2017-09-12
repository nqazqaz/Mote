package y.shane.mote.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import y.shane.mote.R;
import y.shane.mote.bnbar.BottomNavigationBar;
import y.shane.mote.model.BottomNavigationItem;
import y.shane.mote.bnbar.BottomNavigationStyle;

public class MainActivity extends AppCompatActivity {


    private BottomNavigationBar mBottomNavigationBar;
    private ArrayList<BottomNavigationItem> mItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        createList();

        mBottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation);
        mBottomNavigationBar.setStyle(BottomNavigationStyle.MODE_ANIMATION_SHIFTING, BottomNavigationStyle.MODE_BACKGROUND_HIGH_LIGHT);
        mBottomNavigationBar.setScrollingHide(true);
        mBottomNavigationBar.addMenuItems(mItems);

    }

    private void createList() {
        mItems.add(new BottomNavigationItem("One",
                getResources().getDrawable(R.drawable.ic_local_florist_white_24dp)));
        mItems.add(new BottomNavigationItem("Two",
                getResources().getDrawable(R.drawable.ic_map_white_24dp)));
        mItems.add(new BottomNavigationItem("Three",
                getResources().getDrawable(R.drawable.ic_music_note_white_24dp)));
        mItems.add(new BottomNavigationItem("Four",
                getResources().getDrawable(R.drawable.ic_sentiment_neutral_white_24dp)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
