import java.util.Scanner;

////////////////////////////////////////////////////////////////////////////////
//  Course:   CSC 130 SPRING 2018
//  Section:  0001
// 
//  Project:  Lab2
//  File:     vigenere
//  
//  Name:     Ben Pearlstine
//  Email:    bgpearlstine@my.waketech.edu
////////////////////////////////////////////////////////////////////////////////
//
public class vigenere
{

    static Scanner scanner = new Scanner(System.in);

    public static void main (String[] args)
    {
        System.out.println("Enter a multi-word message with punctuation: ");
        String toEncrypt = scanner.nextLine();
        toEncrypt.toLowerCase();
        System.out.println("Enter a single word key with no punctuation: ");
        String key = scanner.nextLine();
        key.toLowerCase();
        String fullKey = "";
        int count = 0;
        for (int i = 0; i < toEncrypt.length(); i++)
        {
            if (Character.isWhitespace(toEncrypt.charAt(i)))
            {
                fullKey += " ";
            }
            else if (toEncrypt.charAt(i) == '.')
            {
                fullKey += '.';
                count++;
            }
            else
            {
                fullKey += key.charAt(count);
                count++;
            }
            if (count > 5)
            {
                count = 0;
            }
        }
        String encryptedMessage = encrypt(toEncrypt,fullKey);
        System.out.println("The encoded message is: \n" + encryptedMessage);

        String plainText = decrypt(encryptedMessage,fullKey);
        System.out.println("The decoded message is: \n" + plainText);


    }

    /**
     * Create vigenere square
     * @return vigenere square
     */
    static char[][]  createVigSquare(){
        char[] alphabet = {'a','b','c','d','e','f','g','h','i','j','k',
                           'l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
        char[][] table = new char[alphabet.length][alphabet.length];
        int a;
        for(int i = 0; i < alphabet.length; i++){
            for(int j = 0; j < alphabet.length; j++){
                a = i+j;
                if(a >= alphabet.length){
                    a = a - alphabet.length;
                }
                table[i][j] = alphabet[a];
            }
        }
        return table;
    }

    /**
     * encrypt message using public key
     * @param message
     * @param key
     * @return encrypted message
     */
    static String encrypt(String message, String key)
    {
        char [][] vigSquare = createVigSquare();
        String encryptedMessage = "";
        for (int i = 0;i < message.length();i++)
        {
            if (Character.isWhitespace(message.charAt(i)))
            {
                encryptedMessage +=" ";
            }
            else if (message.charAt(i) == '.')
            {
                encryptedMessage += ".";
            }
            else
            {
                int rowIndex = getRowIndex(key.charAt(i), vigSquare);
                int colIndex = getColIndex(message.charAt(i), vigSquare);
                encryptedMessage += vigSquare[rowIndex][colIndex];
            }
        }
        return encryptedMessage;
    }

    /**
     * find the correct index along the column of the vigenere square
     * @param letter
     * @param vigSquare
     * @return column index
     */
    static int getColIndex(char letter, char[][] vigSquare)
    {
        for (int i = 0; i < vigSquare.length; i++)
        {
            if (letter == vigSquare[0][i])
            {
                return i;
            }
        }
        return -1;
    }

    /**
     * find the correct index along the row of the vigenere square
     * @param letter
     * @param vigSquare
     * @return row index
     */

    static int getRowIndex(char letter, char[][] vigSquare)
    {
        for (int i = 1;i < vigSquare.length;i++)
        {
            if (letter == vigSquare[i][0])
            {
                return i;
            }
        }
        return -1;
    }

    /**
     * decrypt message using public key,
     * @param message
     * @param key
     * @return decrypted message
     */
    static String decrypt (String message, String key)
    {
        char [][] vigSquare = createVigSquare();
        String plainText = "";
        for (int i = 0;i < message.length();i++)
        {
            if (Character.isWhitespace(message.charAt(i)))
            {
                plainText +=" ";
            }
            else if (message.charAt(i) == '.')
            {
                plainText += ".";
            }
            else
            {
                int rowIndex = getRowIndex(key.charAt(i), vigSquare);
                char plainChar = getPlainTextChar(rowIndex,message.charAt(i),vigSquare);
                plainText += plainChar;
            }
        }
        return plainText;
    }

    /**
     * convert encrypted message to plaintext
     * @param rowIndex
     * @param encryptedChar
     * @param vigSquare
     * @return plaintext
     */
    static char getPlainTextChar(int rowIndex, char encryptedChar,char[][] vigSquare)
    {
        for (int i = 0; i<vigSquare.length;i++)
        {
            if (encryptedChar == vigSquare[rowIndex][i])
            {
                return vigSquare[0][i];
            }
        }
        return 'a';
    }

}
