package sample;

public class Utils {

    public static int len(int[] tab){
        int count = 0;
        for (int i = 0; i < tab.length; i++){
            if (tab[i] == -1) count++;
        }
        return 4-count;
    }

}
