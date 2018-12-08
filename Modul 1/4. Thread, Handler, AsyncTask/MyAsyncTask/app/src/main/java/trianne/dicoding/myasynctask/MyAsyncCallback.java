package trianne.dicoding.myasynctask;

public interface MyAsyncCallback {
    void onPreExecute();
    void onPostExecute(String text);
}