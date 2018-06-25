package com.tripadvisor.tripadvisortest;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class FileReaderFragment extends Fragment {

    public interface FileReaderListener {
        void onRequestStarted();
        void onRequestFinished(List<City> result);
    }

    private FileReaderTask fileReaderTask;
    private FileReaderListener listener;
    private List<City> result;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FileReaderListener) {
            listener = (FileReaderListener) context;
        } else {
            throw new IllegalStateException("Activity should implement NetworkRequestListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        startTask(R.raw.cities);
    }

    public void startTask(int resId) {
        fileReaderTask = new FileReaderTask(new CSVParser());
        fileReaderTask.execute(resId);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if ((result != null) && (listener != null)) {
            listener.onRequestFinished(result);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if ((fileReaderTask != null) && (fileReaderTask.getStatus() == AsyncTask.Status.RUNNING)) {
            fileReaderTask.cancel(true);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public InputStream openResourceFile(int id) {
        return getResources().openRawResource(id);

    }

    private class FileReaderTask extends AsyncTask<Integer, Void, List<City>> {
        ICSVParser parser;

        FileReaderTask(ICSVParser parser) {
            this.parser = parser;
        }

        @Override
        protected void onPreExecute() {
            if (listener != null) {
                listener.onRequestStarted();
            }
        }

        @Override
        protected List<City> doInBackground(Integer... resId) {
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            InputStream inputStream = openResourceFile(R.raw.cities);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            try {
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return parser.parse(stringBuilder.toString());
        }


        @Override
        protected void onPostExecute(List<City> result) {
            if (listener != null && !isCancelled()) {
                listener.onRequestFinished(result);
            }
            FileReaderFragment.this.result = result;
        }

    }
}
