package trianne.dicoding.myintentapp;

import android.os.Parcel;
import android.os.Parcelable;

//POJO adalah sebuah kelas Java biasa yang tidak bergantung dengan kelas lain.
// Umumnya kelas POJO ini disebut sebagai kelas Java yang memiliki properti/variabel dan metode setter-getter.

//POJO akan membantu kita saat aplikasi semakin kompleks.
// Contohnya, POJO bisa kita gunakan untuk melakukan koneksi ke server untuk request API
// atau akses ke database lokal dengan SQLite.

public class Person implements Parcelable {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    private String name;
    private int age;
    private String email;
    private String city;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.age);
        dest.writeString(this.email);
        dest.writeString(this.city);
    }

    public Person() {
    }

    protected Person(Parcel in) {
        this.name = in.readString();
        this.age = in.readInt();
        this.email = in.readString();
        this.city = in.readString();
    }

    public static final Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel source) {
            return new Person(source);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };
}
