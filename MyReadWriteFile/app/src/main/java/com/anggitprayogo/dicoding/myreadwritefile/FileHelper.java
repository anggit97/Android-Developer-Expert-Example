package com.anggitprayogo.dicoding.myreadwritefile;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileHelper {

    static void writeToFile(String filename, String data, Context context){
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static String readFromFile(Context context, String filename){
        String ret = "";

        try {
            InputStream inputStream = context.openFileInput(filename);

            if (inputStream != null){

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String stringRecieve = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((stringRecieve = bufferedReader.readLine()) != null){
                    stringBuilder.append(stringRecieve);
                }

                inputStream.close();
                ret = stringBuilder.toString();

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ret;
    }

}
