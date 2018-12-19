# Teori Content Description #

## TalkBack app (sudah ada default di Android tapi jika tidak bisa unduh ini)
https://play.google.com/store/apps/details?id=com.google.android.marvin.talkback&hl=en

## Implementasi contentDescription
Hanya perlu menambah satu baris seperti pada contoh berikut ini:

    <Button
        android:id="@+id/pause_button"
        android:src="@drawable/pause"
        android:contentDescription="@string/pause"/>

Hasil yang sama dapat Anda peroleh dengan menggunakan metode berikut:

    String contentDescription = "Select" + strValues[position];
    label.setContentDescription(contentDescription);

## Cara menjalankan
Untuk menjalankan proyek ini Anda harus mengaktifkan komponen TalkBack yang terdapat di Settings > Accessibility > TalkBack. Pastikan bahwa TalkBack sudah terpasang bila Anda tidak menemukan menu ini. Aktifkan fitur TalkBack dengan menggeser tombol toggle On-Off.

## Referensi
* [Accessible App](https://developer.android.com/training/accessibility/accessible-app.html)
* [ImageView Accessible Content Descriptions](http://www.deque.com/blog/android-imageviews-accessible-content-descriptions/)