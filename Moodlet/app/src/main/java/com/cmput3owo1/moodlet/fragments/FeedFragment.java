package com.cmput3owo1.moodlet.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cmput3owo1.moodlet.R;
import com.cmput3owo1.moodlet.activities.LoginActivity;
import com.cmput3owo1.moodlet.adapters.FeedListAdapter;
import com.cmput3owo1.moodlet.models.MoodEventAssociation;
import com.cmput3owo1.moodlet.services.IMoodEventServiceProvider;
import com.cmput3owo1.moodlet.services.MoodEventService;
import com.cmput3owo1.moodlet.services.UserService;

import java.util.ArrayList;

/**
 * A fragment that holds the user's Feed. The Feed is a list of the most recent MoodEvents of the
 * users that the current user follows. They are sorted in reverse chronological order by time, and
 * display the username, the emotional state, and the date of the mood event.
 */
public class FeedFragment extends Fragment implements IMoodEventServiceProvider.OnFeedUpdateListener {
    private ListView feedListView;
    private FeedListAdapter feedAdapter;
    private ArrayList<MoodEventAssociation> feedDataList;
    private IMoodEventServiceProvider service;
    private UserService userService;

    /**
     * Called when the fragment is starting.
     * @param savedInstanceState Used to restore a fragment's previous state
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    /**
     * This function is called to have the fragment instantiate its user interface view.
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container  If non-null, this is the parent view that the fragment's UI should be attached to. The fragment should not add the view itself, but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_feed, container, false);

        service = new MoodEventService();
        service.getFeedUpdates(this);

        feedListView = rootView.findViewById(R.id.feed_list);
        feedDataList = new ArrayList<>();

        feedAdapter = new FeedListAdapter(getContext(), feedDataList);
        feedListView.setAdapter(feedAdapter);

        userService = new UserService();

        return rootView;
    }

    /**
     * Initialize the contents of the Activity's standard options menu.
     * @param menu The options menu in which you place your items.
     * @param inflater The MenuInflater object that can be used to inflate any itmes in the menu
     */
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.general_fragment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.logout:
                userService.logoutUser();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                getActivity().finish();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Gets called when there is an update to the user's Feed
     * @param newFeed The updated feed
     */
    @Override
    public void onFeedUpdate(ArrayList<MoodEventAssociation> newFeed) {
        feedDataList.clear();
        feedDataList.addAll(newFeed);
        feedAdapter.notifyDataSetChanged();
    }
}