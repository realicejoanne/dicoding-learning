package trianne.dicoding.moviecataloguev31.api;

public class Server {
    public static final String BASE_URL_API = "https://api.themoviedb.org/";

    public static APIService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(APIService.class);
    }
}
