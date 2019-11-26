package com.cmput3owo1.moodlet.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cmput3owo1.moodlet.R;
import com.cmput3owo1.moodlet.adapters.UserListAdapter;
import com.cmput3owo1.moodlet.models.User;
import com.cmput3owo1.moodlet.services.IUserServiceProvider;
import com.cmput3owo1.moodlet.services.UserService;

import java.util.ArrayList;


public class SearchFragment extends Fragment implements
        SearchView.OnQueryTextListener, UserListAdapter.OnFollowClickListener,
        IUserServiceProvider.OnUserSearchListener, IUserServiceProvider.OnFollowRequestListener {

    private SearchView userSearchView;
    private ListView userListView;
    private ArrayList<User> userDataList;
    private UserListAdapter userAdapter;
    private IUserServiceProvider service;

    /**
     * Called when the fragment is starting.
     * @param savedInstanceState Used to restore a fragment's previous state
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        service = new UserService();

        userSearchView = rootView.findViewById(R.id.user_search_view);
        userListView = rootView.findViewById(R.id.user_list_view);
        userDataList = new ArrayList<>();

        userAdapter = new UserListAdapter(getContext(), userDataList, this);
        userListView.setAdapter(userAdapter);

        userSearchView.setOnQueryTextListener(this);

        return rootView;
    }

    @Override
    public boolean onQueryTextSubmit(String newString) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newString) {
        if (newString.isEmpty()) {
            userDataList.clear();
            userAdapter.notifyDataSetChanged();
        } else {
            service.searchForUsers(newString, this);
        }
        return true;
    }

    @Override
    public void onSearchResult(ArrayList<User> searchResult, String searchText) {
        if (searchText.equals(userSearchView.getQuery().toString())) {
            userDataList.clear();
            userDataList.addAll(searchResult);
            userAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onFollowClick(User user) {
        service.sendFollowRequest(user, this);
    }

    @Override
    public void onRequestSuccess(User user) {
        user.setRequested(true);
        userAdapter.notifyDataSetChanged();
    }


}