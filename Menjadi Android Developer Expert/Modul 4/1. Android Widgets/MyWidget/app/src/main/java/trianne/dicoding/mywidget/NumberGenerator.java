package trianne.dicoding.mywidget;

import java.util.Random;

//NumberGenerator memiliki metode static Generate.
//Karena sifatnya yang static, kita tidak perlu menginisiasi NumberGenerator terlebih dahulu untuk menggunakannya.
public class NumberGenerator {
    public static int Generate(int max){

        Random random = new Random();
        int randomInt = random.nextInt(max);
        return randomInt;
    }
}