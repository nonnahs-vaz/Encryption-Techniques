//implementation of hill cipher for a 2x2 key
import java.util.Scanner;

class HillCipher {
    public static void main(String[] args) {
        String plainText,cipherText;
        int key[][] = new int [2][2];
        System.out.println("\n1> Encrypt message\n2> Decrypt message");
        switch((new Scanner(System.in)).nextInt()) {
            case 1:
                System.out.println("Enter the key: ");
                readMatrix(key);
                System.out.print("\nEnter the plain text: ");
                plainText = (new Scanner(System.in)).nextLine();
                plainText = processPlaintext(plainText);
                cipherText = encrypt(key,plainText);
                System.out.println("The cipher text is: "+cipherText);
            break;
            case 2:
                System.out.println("Enter the key: ");
                readMatrix(key);
                System.out.print("\nEnter the cipher text: ");
                cipherText = (new Scanner(System.in)).nextLine();
                cipherText = cipherText.toUpperCase();
                plainText = decrypt(key,cipherText);
                System.out.println("The plain text is: "+plainText);
            break;
            default: return;
        }
    }

    static String encrypt(int key[][], String plainText) {
        int buffer[] = new int [2]; //stores the two characters currently being encrypted
        int cipher[];   //store result of matrix multiplication
        int encryptIterations = plainText.length()/2;
        String cipherText = "";
        for(int i = 0; i < encryptIterations; i++) {
            buffer[0] = plainText.charAt(2 * i) - 'A';
            buffer[1] = plainText.charAt(2 * i + 1) - 'A';
            cipher = matMultiplication(key,buffer); //perform matrix multiplication of key and plain text
            cipherText += (char) ((cipher[0] % 26) + 'A');
            cipherText += (char) ((cipher[1] % 26) + 'A');
        }
        return cipherText;
    }

    static String decrypt(int key[][], String cipherText) {
        int inverseKey[][] = calcInverseKey(key);   //compute inverse of key matrix (mod 26)
        int buffer[] = new int [2]; //stores the two characters currently being decrypted
        int decipher[]; //stores result of matrix multiplication
        int decryptIterations = cipherText.length()/2;
        String plainText = "";
        for(int i = 0; i < decryptIterations; i++) {
            buffer[0] = cipherText.charAt(2 * i) - 'A';
            buffer[1] = cipherText.charAt(2 * i + 1) - 'A';
            decipher = matMultiplication(inverseKey,buffer);
            plainText += (char) ((decipher[0] % 26) + 'A');
            plainText += (char) ((decipher[1] % 26) + 'A');
        }
        return plainText;
    }

    static int [][] calcInverseKey(int key[][]) {
        int det = key[0][0]*key[1][1] - key[0][1]*key[1][0];    //find determinant of key
        det = det % 26;
        if(det < 0) det += 26;
        int inverseDet = getMultiplicativeInverse(det);   //get multiplicative inverse of determinant
        int adj[][] = findAdj(key); //find the adj of the key matrix
        //multiply adj by the inverse determinant to get inverse key
        for(int i = 0; i < 2; i++) {
            for(int j = 0; j < 2; j++) {
                adj[i][j] = (adj[i][j] * inverseDet) % 26;
                if(adj[i][j] < 0) adj[i][j] += 26;
            }
        }
        return adj;
    }

    static int [][] findAdj(int mat[][]) {
        int temp;
        temp = mat[0][0];
        mat[0][0] = mat[1][1];
        mat[1][1] = temp;
        mat[0][1] = -1 * mat[0][1];
        mat[1][0] = -1 * mat[1][0];
        return mat;
    }

    static int getMultiplicativeInverse(int n) {
        int i;
        for(i = 1; i < 26; i++) {
            if((n*i) % 26 == 1)
                break;
        }
        return i;
    }

    static int [] matMultiplication(int key[][], int message[]) {
        int cipher[] = new int [2];
        cipher[0] = (key[0][0]*message[0] + key[0][1]*message[1]) % 26;
        cipher[1] = (key[1][0]*message[0] + key[1][1]*message[1]) % 26;
        if(cipher[0] < 0) cipher[0] += 26;
        if(cipher[1] < 0) cipher[1] += 26;
        return cipher;
    }

    static String processPlaintext(String plainText) {
        plainText = plainText.replace(" ","");  //remove spaces from plain text
        if(plainText.length()%2 == 1)   //if length of plaintext is not even
            plainText += "x";   //add filler letter
        plainText = plainText.toUpperCase();
        return plainText;
    }

    static void readMatrix(int mat[][]) {
        for(int i = 0; i < 2; i++) {
            for(int j = 0; j < 2; j++) {
                System.out.print("mat["+i+"]["+j+"]> ");
                mat[i][j] = (new Scanner(System.in)).nextInt() % 26;
            }
        }
    }
}
