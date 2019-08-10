/*implementation of playfair cipher*/
import java.util.Scanner;

class PlayfairCipher {

	public static void main(String args[]) {
		String key,plainText,cipherText;
		char playfairMatrix[][] = new char[5][5];
		System.out.println("Enter the key: ");
		key = (new Scanner(System.in)).nextLine();	//read key from user
		key = key.toUpperCase();		//convert all values to upper case
		key = removeDuplicates(key);	//remove duplicate characters from the key
		key.replace("J","I");	//replace j by i in the key
		constructPlayfairMatrix(key,playfairMatrix);	//construct the playfair matrix using key
		System.out.println("\nPlayfair matrix constructed using key is:");
		displayMatrix(playfairMatrix);
		System.out.println("\n\n1> Encrypt message\n2> Decrypt message\n3> exit");
		switch((new Scanner(System.in)).nextInt()) {
			case 1:  //encrypt the plaintext
				System.out.println("Enter the plaintext:");
				plainText = (new Scanner(System.in)).nextLine();
                plainText = plainText.toUpperCase();
				cipherText = encrypt(playfairMatrix,plainText);
				System.out.println("The Encrypted text is : "+cipherText);
			break;

			case 2:  //decrypt the plaintext
				System.out.println("Enter the ciphertext:");
				cipherText = (new Scanner(System.in)).nextLine();
                cipherText = cipherText.toUpperCase();
				plainText = decrypt(playfairMatrix,cipherText);
				System.out.println("The decrypted text is : "+plainText);
			break;

			default: return;
		}
	}

	static String removeDuplicates(String text) {	//remove duplicate characters from a string
		String output = "";
		for(int i = 0; i < text.length(); i++) {
			if(!output.contains(String.valueOf(text.charAt(i)))) {    //if output string does not contain the character
				output += text.charAt(i);    //append the character to output
			}
		}
		return output;
	}

	static void constructPlayfairMatrix(String key, char playfairMatrix[][]) {
		String matrixContents = removeDuplicates(key + "ABCDEFGHIKLMNOPQRSTUVWXYZ");	//preparing contents to fill in 5x5 matrix
		int k = 0;
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 5; j++) {
				playfairMatrix[i][j] = matrixContents.charAt(k);
				k++;
			}
		}
	}

	static String encrypt(char playfairMatrix[][], String plainText) {
		int x1,y1,x2,y2,location1,location2;
		plainText = processPlaintext(plainText);  //generate plaintext with unique digrams and even length
        String cipherText = "";
        for(int i = 0; i < plainText.length(); i++) {
            if(i%2 == 1) {  //for every even letter
                location1 = locateCharacter(playfairMatrix,plainText.charAt(i - 1));
                x1 = location1/5;   //get row of first character
                y1 = location1%5;   //get column of first character
                location2 = locateCharacter(playfairMatrix,plainText.charAt(i));
                x2 = location2/5;   //get row of second character
                y2 = location2%5;   //get column of second character

                if(x1 == x2) {  //if letters are in the same row
                    cipherText += playfairMatrix[x1][(y1 + 1)%5];   //append letter in column next to current letter to ciphertext
                    cipherText += playfairMatrix[x2][(y2 + 1)%5];
                }
                else if(y1 == y2) { //if letter are in the same column
                    cipherText += playfairMatrix[(x1 + 1)%5][y1];   //append letter in row below current letter to ciphertext
                    cipherText += playfairMatrix[(x2 + 1)%5][y2];
                }
                else {  //if letters are in different row and different column
                    cipherText += playfairMatrix[x1][y2];
                    cipherText += playfairMatrix[x2][y1];
                }
            }
        }
        return cipherText;
	}

	static String decrypt(char playfairMatrix[][], String cipherText) {
        int x1,y1,x2,y2,location1,location2;
		String plainText = "";
        for(int i = 0; i < cipherText.length(); i++) {
            if(i%2 == 1) {  //for every even letter
                location1 = locateCharacter(playfairMatrix,cipherText.charAt(i - 1));
                x1 = location1/5;   //get row of first character
                y1 = location1%5;   //get column of first character
                location2 = locateCharacter(playfairMatrix,cipherText.charAt(i));
                x2 = location2/5;   //get row of second character
                y2 = location2%5;   //get column of second character

                if(x1 == x2) {  //if letters are in the same row
                    plainText += playfairMatrix[x1][(y1 - 1 + 5)%5];   //append letter in column next to current letter to ciphertext
                    plainText += playfairMatrix[x2][(y2 - 1 + 5)%5];
                }
                else if(y1 == y2) { //if letter are in the same column
                    plainText += playfairMatrix[(x1 - 1 + 5)%5][y1];   //append letter in row below current letter to ciphertext
                    plainText += playfairMatrix[(x2 - 1 + 5)%5][y2];
                }
                else {  //if letters are in different row and different column
                    plainText += playfairMatrix[x1][y2];
                    plainText += playfairMatrix[x2][y1];
                }
            }
        }
        return plainText;
	}

    static String processPlaintext(String plainText) {  //process plaintext to produce digrams with distinct characters
        int length;
        String fillerLetter = "X";
        boolean isDigram = false;   //checks whether a digram is found
        StringBuilder output = new StringBuilder();
        for(int i = 0; i < plainText.length(); i++) {
            output = output.append(plainText.charAt(i)); //append character of plaintext to output
            if(isDigram) { //if a digram is found
                length = output.length();
                if(output.charAt(length - 1) == output.charAt(length - 2)) {  //if digram has same characters
                    output = output.insert(length - 1,fillerLetter);    //insert filler letter to make it unique
                    isDigram = false;   //new char was inserted, so next character to be appended is going to complete a digram
                }
            }
            isDigram = !isDigram;  //toggle digram bit
        }
        if(output.length()%2 == 1) {    //if length of output is odd
            output = output.append(fillerLetter);
        }
        return output.toString();
    }

    static int locateCharacter(char playfairMatrix[][], char c) {  //locate a given character in the playfair matrix
        int location = 0,row,column;
        for(row = 0; row < 5; row++) {
			for (column = 0; column < 5; column++ ) {
				if(playfairMatrix[row][column] == c)
					return location;
                location++;
			}
		}
        return -1;  //character not found
	}

	static void displayMatrix(char playfairMatrix[][]) {
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 5; j++) {
				System.out.print(playfairMatrix[i][j]+" ");
			}
			System.out.println();
		}
	}
}
