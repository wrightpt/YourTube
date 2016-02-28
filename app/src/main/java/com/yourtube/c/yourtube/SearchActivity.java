package com.yourtube.c.yourtube;

import android.content.*;
import android.os.*;
import android.support.v4.app.*;
import android.support.v7.app.AppCompatActivity;
import android.view.*;
import android.view.inputmethod.*;
import android.widget.*;

import com.squareup.picasso.*;

import java.util.*;

public class SearchActivity extends AppCompatActivity {


    private EditText searchInput;
    private ListView videosFound;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchInput = (EditText) findViewById(R.id.search_input);
        videosFound = (ListView) findViewById(R.id.videos_found);

        handler = new Handler();

        searchInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    searchOnYoutube(v.getText().toString());
                    return false;
                }
                return true;
            }
        });

        addClickListener();

    }

        private List<VideoItem> searchResults;

        private void searchOnYoutube(final String keywords){
            new Thread(){
                public void run(){
                    YoutubeConnector yc = new YoutubeConnector(SearchActivity.this);
                    searchResults = yc.search(keywords);
                    handler.post(new Runnable(){
                        public void run(){
                            updateVideosFound();
                        }
                    });
                }
            }.start();
        }


    private void updateVideosFound(){
        ArrayAdapter<VideoItem> adapter = new ArrayAdapter<VideoItem>(getApplicationContext(), R.layout.video_item, searchResults){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if(convertView == null){
                    convertView = getLayoutInflater().inflate(R.layout.video_item, parent, false);
                }
                ImageView thumbnail = (ImageView)convertView.findViewById(R.id.video_thumbnail);
                TextView title = (TextView)convertView.findViewById(R.id.video_title);
                TextView description = (TextView)convertView.findViewById(R.id.video_description);

                VideoItem searchResult = searchResults.get(position);

                Picasso.with(getApplicationContext()).load(searchResult.getThumbnailURL()).into(thumbnail);
                title.setText(searchResult.getTitle());
                description.setText(searchResult.getDescription());
                return convertView;
            }
        };

        videosFound.setAdapter(adapter);
    }

    private void addClickListener(){
        videosFound.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> av, View v, int pos,
                                    long id) {

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");

                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);

                DialogFragment newFragment = DialogFragmentPlayer.newInstance(searchResults.get(pos).getId());

                newFragment.setStyle(DialogFragment.STYLE_NO_TITLE,0);
                newFragment.show(ft, "dialog");


               // Intent intent = new Intent(getApplicationContext(), PlayerActivity.class);
               // intent.putExtra("VIDEO_ID", searchResults.get(pos).getId());
               // startActivity(intent);
            }

        });
    }






    }




