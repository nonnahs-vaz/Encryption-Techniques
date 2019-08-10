//implementation of columnar transposition technique
import java.util.Scanner;
class ColumnarTransposition {
    public static void main(String[] args) {
        String plainText, cipherText;
        System.out.println("Enter the key length: ");
        int keyLength = (new Scanner(System.in)).nextInt();
        int key[] = new int [keyLength];
        System.out.println("Enter the key (sequence of numbers between 0 - "+(keyLength - 1)+"):");
        inputKey(key);
        if(!validKey(key)) return;
        System.out.print("Enter the plain text: ");
        plainText = (new Scanner(System.in)).nextLine();
        plainText = plainText.replace(" ","");
        cipherText = encrypt(plainText,key);
        System.out.println("Cipher text obtained after applying columnar transposition is:\n"+cipherText);
        System.out.println("\nAttempting to decrypt cipher text..");
        plainText = decrypt(cipherText,key);
        System.out.println("Decrypted plain text is: "+plainText);
    }

    static void inputKey(int key[]) {
        boolean increment = true;
        int i,j;
        for(i = 0; i < key.length;) {
            key[i] = (new Scanner(System.in)).nextInt();
            for(j = 0; j < i; j++) {
                if(key[j] == key[i]) {
                    System.out.println("please re-enter the number");
                    increment = false;
                    break;
                }
            }
            if(j == i) increment = true;
            if(increment) i++;
        }
    }

    static boolean validKey(int key[]) {
        for(int i = 0; i < key.length; i++) {
            if(key[i] >= key.length || key[i] < 0)
                return false;
        }
        return true;
    }

    static String encrypt(String plainText,int []key) {
        int keyLength = key.length;
        String cipherText = "";
        String columns[] = new String [keyLength];
        int n = plainText.length();
        int i,j;
        for(i = 0; i < columns.length; i++) {   //loop to initialize the columns
            columns[i] = "";
        }

        for(i = 0; i < n; i++) {    //loop to construct columns from plaintext
            columns[i%keyLength] += plainText.charAt(i);
        }

        if((n%keyLength) > 0) { //if columns dont have the same number of characters
            for(i = (n%keyLength); i < keyLength; i++) {    //loop to fill columns with filler letter
                columns[i] += 'x';
            }
        }

        for(i = 0; i < keyLength; i++) {
            for(j = 0; j < keyLength; j++) {
                if(key[j] == i) break;
            }
            cipherText += columns[j];   //append columns to plaintext in order present in key
        }

        return cipherText;
    }

    static String decrypt(String cipherText, int key[]) {
        int keyLength = key.length;
        int n = cipherText.length();
        int columnSize = n/keyLength;
        String plainText = "";
        String columns[] = new String [keyLength];
        for(int i = 0; i < keyLength; i++) {
            columns[i] = cipherText.substring(i*columnSize,i*columnSize+columnSize); //construct columns
        }
        int row = -1;
        for(int i = 0; i < n; i++) {
            if(i%keyLength == 0) row++;
            plainText += columns[key[i%keyLength]].charAt(row);
        }
        return plainText;
    }
}
