package trianne.dicoding.myreadwritefile;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileHelper {

    //proses menulis data ke file
    static void writeToFile(String filename, String data, Context context) {
        try {
            //buka file
            //Untuk menggunakan method openFileOutput() kita harus mengetahui context aplikasi yang menggunakannya.
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE));
            //menulis data
            outputStreamWriter.write(data);
            //tutup file
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    //proses membaca file
    static String readFromFile(Context context,String filename) {
        String ret = "";
        try {
            //data dibaca menggunakan stream
            InputStream inputStream = context.openFileInput(filename);

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                //Data pada tiap baris dalam berkas akan mampu diperoleh dengan menggunakan bufferedReader.
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                //Obyek bufferedReader akan memeriksa tiap baris pada berkas.
                //Bila pada baris tersebut terdapat data, maka data tersebut akan ditambahkan ke dalam obyek stringBuilder.
                //Perulangan akan dilakukan sampai datanya telah terbaca semua.
                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }

                //tutup file
                inputStream.close();

                //tampilkan data dlm string
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        }
        catch (IOException e) {
            Log.e("login activity", "Cannot read file: " + e.toString());
        }
        return ret;
    }
}
