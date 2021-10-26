package com.example.asynctask_and_weakreference;

import android.content.Context;
import android.os.AsyncTask;

//AsyncTask<PARAMS, PROGRESS, RESULT>
/*
        PARAMS : the type of the parameters sent to the task upon execution. (Normally : URL pass kori)
        PROGRESS: the type of the progress units published during the background computation.
        RESULT: the type of the result of the background computation. (Normally : BitMap result hisave Back Pabo)
*/

//eikane amra "progress Bar" update korte chai... sheitar jonno "Integer" pass korechi

//onPreExecute, onProgressUpdate, onPostExecute--->Main Thread ee RUN hoy
//doInBackground only NEW ALADA Thread ee RUN hoy

public class MyAsyncTask extends AsyncTask<Integer, Integer, String> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }


    //"Integer..." ---> eita mane "VAR ARGS"... eitar mane amra joto ichha pari INTEGER Pass korte pari
    @Override
    protected String doInBackground(Integer... integers) { //jehetu amra RETURN Type STRING disi... tai RETURN korse "STRING"
        return "Finished!!!";
    }



    //MAIN UI thread ke UPDATE korbe "onProgressUpdate"
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }


    //"doInBackground" er return Type hocche "onPostExecute" er INPUT
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
