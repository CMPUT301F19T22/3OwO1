package com.cmput3owo1.moodlet.services;

import com.cmput3owo1.moodlet.models.FollowRequest;
import com.cmput3owo1.moodlet.models.User;
import java.util.ArrayList;

/**
 * Interface that abstracts user service functions. It contains functions for
 * user registration and login. User registration functions includes validating
 * usernames, creating the account and putting their information into the database.
 * Login functions include login and making checking if there was a previous instance
 * of a logged in account
 */
public interface IUserServiceProvider {
    /**
     * Interface that creates a listener for the register fragment. It contains
     * functions that are defined in the register fragment. These functions help
     * with the flow of the registration process.
     */
    interface RegistrationListener {
        void onRegistrationSuccess();
        void onRegistrationFailure();
        void onDatabaseAccessFailure();
        void onUsernameIsTaken();
    }

    /**
     * Interface that creates a listener for the login fragment. It contains
     * functions that are defined in the login fragment. These functions help
     * with the flow of the login process
     */
    interface LoginListener {
        void onLoginSuccess();
        void onLoginFailure();
    }

    /**
     * Interface for a listener that receives the results of a search for users. The search result
     * contains the list of users that match the searchText.
     */
    interface OnUserSearchListener {
        void onSearchResult(ArrayList<User> searchResult, String searchText);
    }

    /**
     * Interface for a listener that listens to the success of a follow request. The listener is
     * invoked when the follow request is sent to the specified user.
     */
    interface OnFollowRequestListener {
        void onRequestSuccess(User user);
    }

    /**
     * Interface for a listener that receives the username of the follower after accepting the
     * request. The listener is invoked when the follow request is successfully accepted.
     */
    interface OnAcceptRequestListener {
        void onAcceptRequestSuccess(String newFollowerUsername);
    }

    /**
     * This function is called to check if there is an existing instance of the logged in user.
     * @return Returns true if there is a logged in user; false otherwise.
     */
    boolean hasPreviousLogin();

    /**
     * Listener interface to get the new follow request list upon an update
     */
    interface OnRequestsUpdateListener {
        void onRequestsUpdate(ArrayList<FollowRequest> newRequests);
    }

    /**
     * This function first checks if the username is taken. If the username is not taken,
     * the account will be created, otherwise it will notify the user that their username is already taken
     *
     * @param user Details of the user to register
     * @param password Password of Account to register with.
     * @param listener Registration listener passed from fragment
     */
    void validateUsernameAndCreateUser(User user, String password, RegistrationListener listener);

    /**
     * This is a wrapper function that is called to login a user with their email and password.
     *
     * @param txt_email    Email to login with.
     * @param txt_password Password to login with.
     * @param listener     Login listener passed from fragment
     */
    void loginUser(String txt_email, String txt_password, LoginListener listener);

    /**
     * This is a wrapper function that is called to logout the current signed in user
     */
    void logoutUser();

    /**
     * Search for users of Moodlet that begin with the searchText. Pass the results to the listener.
     * @param searchText The username prefix to search with
     * @param listener The listener to pass the user search results to
     */
    void searchForUsers(String searchText, OnUserSearchListener listener);

    /**
     * Send a follow request to the user specified.
     * @param user The user to send the follow request to
     * @param listener The listener to inform of a success
     */
    void sendFollowRequest(User user, OnFollowRequestListener listener);

    /**
     * Listen to follow request updates of the current user. Calls the listener's onRequestUpdate
     * method with the new follow request list when a change occurs.
     * @param listener The listener to pass the new follow request list to
     */
    void getRequestUpdates(OnRequestsUpdateListener listener);

    /**
     * Delete the specified follow request.
     * @param request The follow request to delete
     */
    void deleteFollowRequest(FollowRequest request);

    /**
     * Accept the specified follow request. Call the listener's callback function upon success
     * @param request The follow request to accept
     * @param listener The listener to notify upon success
     */
    void acceptFollowRequest(FollowRequest request, OnAcceptRequestListener listener);

}