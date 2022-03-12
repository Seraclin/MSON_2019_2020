import java.util.*;
public class array1 {
    public static void main(String[] args) {
        int[][] arr1 = new int[2][5]; // [rows][columns]
        arr1[0][1] = 2;
        arr1[1][0] = 4;

        for (int i = 0; i < arr1.length; i++) { //arr1.length gets the number of rows
            for (int j = 0; j <arr1[0].length;j++){ //arr1[0].length gets the number of columns
                System.out.print(arr1[i][j]);
            }
            System.out.println();
        }

    }
}
