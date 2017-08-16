package edu.gatech.thundercats.watermap.services;

import android.os.AsyncTask;

import edu.gatech.thundercats.watermap.domain.PurityReport;
import edu.gatech.thundercats.watermap.domain.Report;
import edu.gatech.thundercats.watermap.domain.SourceReport;
import edu.gatech.thundercats.watermap.domain.User;
import edu.gatech.thundercats.watermap.network.APIServerConnection;
import edu.gatech.thundercats.watermap.util.Callback;
import org.json.JSONObject;
import com.google.gson.Gson;

import java.util.Collection;

/**
 * Created by Matthieu Gay-Bellile on 11/1/16.
 */
public class RemoteReportService {

    private static RemoteReportService instance = null;
    public static RemoteReportService getInstance() {
        if (instance == null) {
            instance = new RemoteReportService(APIServerConnection.getInstance());
        }
        return instance;
    }

    /**
     * API Server Connection needed for remote persistence.
     */
    private final APIServerConnection api;

    /**
     * Sets the api server connection in the class constuctor
     *
     * @param api api server connection object
     */
    public RemoteReportService(final APIServerConnection api) {
        this.api = api;
    }

    public AsyncTask<Void, String, SourceReport[]> getSourceReports(final Callback<String> progressCallback, final Callback<SourceReport[]> completeCallback) {
        AsyncTask<Void, String, SourceReport[]> getSourceReportsTask = new AsyncTask<Void, String, SourceReport[]>() {
            @Override
            protected SourceReport[] doInBackground(Void... voids) {
                try {
                    return api.getAsObject("/source_reports", SourceReport[].class, new Callback<String>() {
                        @Override
                        public void call(String error) {
                            publishProgress(error);
                        }
                    });
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                return null;
            }
            @Override
            protected void onProgressUpdate(String... progress) {
                progressCallback.call(progress[0]);
            }
            @Override
            protected void onPostExecute(SourceReport[] result) {
                completeCallback.call(result);
            }
        };
        return getSourceReportsTask;
    }
//    @Override
//    public final Task<SourceReport[]> getSourceReports() {
//        return new Task<SourceReport[]>() {
//            @Override
//            protected SourceReport[] call() throws Exception {
//                return api.getAsObject("/source_reports", SourceReport[].class,
//                        error -> updateMessage(error));
//            }
//        };
//    }

    public AsyncTask<Void, String, PurityReport[]> getPurityReports(final Callback<String> progessCallback, final Callback<PurityReport[]> completeCallback) {
        return new AsyncTask<Void, String, PurityReport[]>() {
            @Override
            protected PurityReport[] doInBackground(Void... voids) {
                try {
                    return api.getAsObject("/purity_reports", PurityReport[].class, new Callback<String>() {
                        @Override
                        public void call(String error) {
                            publishProgress(error);
                        }
                    });
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                return null;
            }
            @Override
            protected void onProgressUpdate(String... error) {
                progessCallback.call(error[0]);
            }
            @Override
            protected void onPostExecute(PurityReport[] result) {
                completeCallback.call(result);
            }
        };
    }
//    @Override
//    public final Task<PurityReport[]> getPurityReports() {
//        return new Task<PurityReport[]>() {
//            @Override
//            protected PurityReport[] call() throws Exception {
//                return api.getAsObject("/purity_reports", PurityReport[].class,
//                        error -> updateMessage(error));
//            }
//        };
//    }

    public AsyncTask<Report, String, Boolean> addReport(final Callback<String> progessCallback, final Callback<Boolean> completeCallback) {
        return new AsyncTask<Report, String, Boolean>() {

            @Override
            protected Boolean doInBackground(Report... reports) {
                Report report = reports[0];
                try {
                    JSONObject response = null;
                    if (report instanceof SourceReport) {
                        response = api.post("/source_reports", report, new Callback<String>() {
                            @Override
                            public void call(String s) {
                                publishProgress(s);
                            }
                        });
                    } else if (report instanceof PurityReport) {
                        response = api.post("/purity_reports", report, new Callback<String>() {
                            @Override
                            public void call(String s) {
                                publishProgress(s);
                            }
                        });
                        throw new IllegalArgumentException("Report must be an " +
                                "instance of either SourceReport or PurityReport");
                    }
                    //updateMessage(response.getString("message"));
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
    }
//    @Override
//    public final <T extends Report> Task<Boolean> addReport(final T report) {
//        return new Task<Boolean>() {
//            @Override
//            protected Boolean call() throws Exception {
//                JSONObject response;
//                if (report instanceof SourceReport) {
//                    response = api.post("/source_reports", report,
//                            error -> updateMessage(error));
//                } else if (report instanceof PurityReport) {
//                    response = api.post("/purity_reports", report,
//                            error -> updateMessage(error));
//                } else {
//                    throw new IllegalArgumentException("Report must be an " +
//                            "instance of either SourceReport or PurityReport");
//                }
//                updateMessage(response.getString("message"));
//                return response.getBoolean("success");
//            }
//        };
//    }
}
