package trianne.dicoding.myunittest;

public class MainPresenter {
    private MainView view;

    public MainPresenter(MainView view) {
        this.view = view;
    }

    /* Hasil UnitTest
    public double volume(double panjang, double lebar, double tinggi) {
        return panjang / lebar * tinggi;
    }
    */

    public double volume(double panjang, double lebar, double tinggi) {
        return panjang * lebar * tinggi;
    }

    public void hitungVolume(double panjang, double lebar, double tinggi) {
        double volume = volume(panjang, lebar, tinggi);
        MainModel model = new MainModel(volume);
        view.tampilVolume(model);
    }
}