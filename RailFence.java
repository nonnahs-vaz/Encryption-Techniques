//implementation of rail fence cipher
import java.util.Scanner;
class RailFence {

    public static void main(String[] args) {
        String plainText, cipherText;
        int depth;
        System.out.print("Enter the plain text: ");
        plainText = (new Scanner(System.in)).nextLine();
        System.out.print("\nEnter the depth: ");
        depth = (new Scanner(System.in)).nextInt();
        plainText = plainText.replace(" ","");
        cipherText = encrypt(plainText,depth);
        System.out.println("Cipher text obtained after applying rail fence method is:\n"+cipherText);
        System.out.println("\nAttempting to decrypt cipher text..");
        plainText = decrypt(cipherText,depth);
        System.out.println("Decrypted plain text is: "+plainText);
    }

    public static String encrypt(String plainText, int depth) {
        String rows[] = new String [depth];
        String cipherText = "";
        int n = plainText.length();
        int i,j;
        boolean increment;
        for(i = 0; i < depth; i++) {
            rows[i] = "";   //initializing rows to empty string
        }
        increment = true;
        j = 0;
        for(i = 0; i < n; i++) {
            rows[j] += plainText.charAt(i);    //appending plaintext character at corresponding position to row
            if(increment) j++;
            else j--;
            if(j == depth-1) increment = false;
            if(j == 0) increment = true;
        }

        for(String str : rows) {
            cipherText += str;
        }

        return cipherText;
    }

    public static String decrypt(String cipherText, int depth) {
        int rowSizes [] = new int [depth];  //array to store sizes of each row
        String rows[] = new String [depth];
        String plainText = "";
        int n = cipherText.length();
        int i,j;
        boolean increment = true;
        j = 0;
        for(i = 0; i < n; i++) {    //loop to find size of each partition
            rowSizes[j]++;
            if(increment) j++;
            else j--;
            if(j == depth-1) increment = false;
            if(j == 0) increment = true;
        }

        j = 0;
        for(i = 0; i < depth; i++) {    //loop to extract partitions from the ciphertext
            rows[i] = cipherText.substring(j,j+rowSizes[i]);
            j = j+rowSizes[i];
        }

        int rowIndex[] = new int [depth];   //array to keep track of positions in rows
        j = 0;
        increment = true;
        for(i = 0; i < n; i++) {
            plainText += rows[j].charAt(rowIndex[j]);
            rowIndex[j]++;
            if(increment) j++;
            else j--;
            if(j == depth-1) increment = false;
            if(j == 0) increment = true;
        }
        return plainText;
    }
}
