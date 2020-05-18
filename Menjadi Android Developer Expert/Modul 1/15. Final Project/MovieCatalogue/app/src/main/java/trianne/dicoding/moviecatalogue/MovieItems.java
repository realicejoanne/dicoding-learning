package trianne.dicoding.moviecatalogue;

import org.json.JSONObject;

public class MovieItems {

    private String mov_title;
    private String mov_description;
    private String mov_date;
    private String mov_image;

    public MovieItems(JSONObject object){
        try {
            String title        = object.getString("title");
            String description  = object.getString("overview");
            String release_date = object.getString("release_date");
            String image        = object.getString("poster_path");

            this.mov_title          = title;
            this.mov_description    = description;
            this.mov_date           = release_date;
            this.mov_image          = image;

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getMov_title() {
        return mov_title;
    }

    public void setMov_title(String mov_title) {
        this.mov_title = mov_title;
    }

    public String getMov_description() {
        return mov_description;
    }

    public void setMov_description(String mov_description) {
        this.mov_description = mov_description;
    }

    public String getMov_date() {
        return mov_date;
    }

    public void setMov_date(String mov_date) {
        this.mov_date = mov_date;
    }

    public String getMov_image() {
        return mov_image;
    }

    public void setMov_image(String mov_image) {
        this.mov_image = mov_image;
    }
}
