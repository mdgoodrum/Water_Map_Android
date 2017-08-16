package edu.gatech.thundercats.watermap.services;

import android.os.AsyncTask;

//import edu.gatech.thundercats.watermap.domain.User;
import edu.gatech.thundercats.watermap.domain.User;
import edu.gatech.thundercats.watermap.network.APIServerConnection;
import edu.gatech.thundercats.watermap.util.Callback;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matthieu Gay-Bellile on 10/3/16.
 */
public class RemoteUserService {

    private static RemoteUserService instance = null;
    public static RemoteUserService getInstance() {
        if (instance == null) {
            instance = new RemoteUserService(APIServerConnection.getInstance());
        }
        return instance;
    }

    /**
     * API Server Connection needed for remote persistence.
     */
    private final APIServerConnection api;
    
    /**
     * Sets the api server connection in the class constructor.
     *
     * @param api api server connection object
     */
    public RemoteUserService(final APIServerConnection api) {
        this.api = api;
    }

    public AsyncTask<User, String, Boolean> registerUser(final Callback<String> progessCallback, final Callback<Boolean> completeCallback) {
        AsyncTask<User, String, Boolean> registerUserTask = new AsyncTask<User, String, Boolean>() {
            @Override
            protected Boolean doInBackground(User... params) {
                try {
                    JSONObject response = api.post("/auth/register", params[0], new Callback<String>() {
                        @Override
                        public void call(String s) {
                            publishProgress(s);
                        }
                    });

                    publishProgress(response.getString("message"));
                    return response.getBoolean("success");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                return false;
            }

            @Override
            protected void onProgressUpdate(String... progress) {
                progessCallback.call(progress[0]);
            }

            @Override
            protected void onPostExecute(Boolean result) {
                completeCallback.call(result);
            }
        };

        return registerUserTask;
    }

    public AsyncTask<String, String, Boolean> authenticateUser(final Callback<String> progessCallback, final Callback<Boolean> completeCallback) {
        AsyncTask<String, String, Boolean> authenticateUserTask = new AsyncTask<String, String, Boolean>() {
            @Override
            protected Boolean doInBackground(String... params) {
                try {
                    Map<String, String> credentials = new HashMap<>(2, 1);
                    credentials.put("identifier", params[0]);
                    credentials.put("password", params[1]);
                    JSONObject response;

                    response = api.post("/auth/login", credentials, new Callback<String>() {
                        @Override
                        public void call(String s) {
                            publishProgress(s);
                        }
                    });

                    boolean success = response.getBoolean("success");
                    if (success) {
                        api.setAuthToken(response.getString("token"));
                    } else {
                        publishProgress(response.getString("message"));
                    }
                    return success;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                return false;
            }

            @Override
            protected void onProgressUpdate(String... progress) {
                progessCallback.call(progress[0]);
            }

            @Override
            protected void onPostExecute(Boolean result) {
                completeCallback.call(result);
            }
        };

        return authenticateUserTask;
    }

    public AsyncTask<Void, String, Boolean> logoutUser(final Callback<String> progessCallback, final Callback<Boolean> completeCallback) {
        api.setAuthToken(null);
        AsyncTask<Void, String, Boolean> logoutUserTask = new AsyncTask<Void, String, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                publishProgress("Successfully logged out.");
                return true;
            }

            @Override
            protected void onProgressUpdate(String... progress) {
                progessCallback.call(progress[0]);
            }

            @Override
            protected void onPostExecute(Boolean result) {
                completeCallback.call(result);
            }
        };

        return logoutUserTask;
    }

//    @Override
//    public final Task<User> getLoggedInUser() {
//        return new Task<User>() {
//            @Override
//            protected User call() throws Exception {
//                return api.getAsObject("/users/me", User.class,
//                        error -> updateMessage(error));
//            }
//        };
//    }
//
//    @Override
//    public final <T> Task<Boolean> updateLoggedInUser(final T changed) {
//        return new Task<Boolean>() {
//            @Override
//            protected Boolean call() throws Exception {
//                JSONObject response = api.put("/users/me", changed,
//                        error -> updateMessage(error));
//                updateMessage(response.getString("message"));
//                return response.getBoolean("success");
//            }
//        };
//    }
}
