/*implementation of caesar cipher*/
import java.util.Scanner;

class CaesarCipher {

	public static void main(String args[]) {
		String plainText,cipherText;
		int key;		
		while(true) {
			System.out.println("\n\n1> Encrypt message\n2> Decrypt message\n3> exit");
			switch((new Scanner(System.in)).nextInt()) {
				case 1:
				System.out.println("Enter the plaintext:");
				plainText = (new Scanner(System.in)).nextLine();
				System.out.print("\nEnter the key: ");
				key = (new Scanner(System.in)).nextInt()%26;
				cipherText = encrypt(plainText,key);
				System.out.println("The Encrypted text is : "+cipherText);
				break;
				
				case 2:
				System.out.println("Enter the ciphertext: ");
				cipherText = (new Scanner(System.in)).nextLine();
				System.out.print("\nEnter the key: ");
				key = (new Scanner(System.in)).nextInt()%26;
				plainText = decrypt(cipherText,key);
				System.out.println("The decrypted text is : "+plainText);
				break;
				
				default: return;
			}
		}
	}
	
	static String encrypt(String plainText, int key) {
		String cipherText = "";
		char c;
		plainText = plainText.toLowerCase();
		for(int i = 0; i < plainText.length(); i++) {
			c = plainText.charAt(i);
			if(c >= 'a' && c <= 'z') {
				cipherText += (char)('a' + ((c - 'a') + key)%26);
			}		
		}
		return cipherText;
	}
	
	static String decrypt(String cipherText, int key) {
		String plainText = "";
		char c;
		cipherText = cipherText.toLowerCase();
		for(int i = 0; i < cipherText.length(); i++) {
			c = cipherText.charAt(i);
			if(c >= 'a' && c <= 'z') {
				plainText += (char)('a' + ((c - 'a') - key + 26)%26);
			}
		}
		return plainText;
	}
}
