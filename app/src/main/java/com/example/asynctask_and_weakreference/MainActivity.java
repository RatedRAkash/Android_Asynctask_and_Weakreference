package com.example.asynctask_and_weakreference;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress_bar_Id);
    }

    public void startAsyncTask(View view){
        ExampleAsyncTask task = new ExampleAsyncTask(this);
        task.execute(10);
    }





    //Activity Destroy hoye jawa por jodi ExampleAsyncTask "MainActivity" er Reference dhore rakhe taile sheita hobe "Strong Reference"
    //er fole "Memory Leak" hobe... caz... "MainActivity" GARBAGE COLLECTED hobe nah... caz... MainActivity er Reference Use ee ache "ExampleAsyncTask"
    //Ei jonno amra "WeakReference" use korbo and "STATIC CLASS" banabo
    private static class ExampleAsyncTask extends AsyncTask<Integer, Integer, String> {

        private WeakReference<MainActivity> activityWeakReference;

        ExampleAsyncTask(MainActivity activity){
            activityWeakReference = new WeakReference<MainActivity>(activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            MainActivity activity = activityWeakReference.get(); //eita abr STRONG REFERENCE
            if(activity == null || activity.isFinishing()){
                return;
            }

            activity.progressBar.setVisibility(View.VISIBLE);
        }


        //"Integer..." ---> eita mane "VAR ARGS"... eitar mane amra "joto ichha" pari INTEGER Pass korte pari
        @Override
        protected String doInBackground(Integer... integers) { //jehetu amra RETURN Type STRING disi... tai RETURN korse "STRING"
            for (int i = 0; i<integers[0]; i++){  //jehetu "VAR ARGS" er "0"th Element ee amra ACCESS kortasi... tai "integers[0]"
                publishProgress((i*100)/integers[0]); //PUBLISHPROGRESS automatic "onPostExecute" reh CALL dibe

                try {
                    Thread.sleep(1000);
                }

                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return "Finished!!!";
        }



        //MAIN UI thread ke UPDATE korbe "onProgressUpdate"
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            MainActivity activity = activityWeakReference.get();
            if(activity == null || activity.isFinishing()){
                return;
            }
            activity.progressBar.setProgress(values[0]);
        }


        //"doInBackground" er return Type hocche "onPostExecute" er INPUT
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            MainActivity activity = activityWeakReference.get();
            if(activity == null || activity.isFinishing()){
                return;
            }
            Toast.makeText(activity, s, Toast.LENGTH_SHORT).show();
            activity.progressBar.setProgress(0);
            activity.progressBar.setVisibility(View.INVISIBLE);
        }



    }

}