package cuiods.tree.word;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * splay word test
 */
public class Main {
    public static void main(String[] args) {
        InputStream inputStream = null;
        String fileName = "";
        try {
            if (args.length == 0) {
                System.out.print("Enter a file name: ");
                Scanner scanner = new Scanner(System.in);
                fileName = scanner.nextLine();
                inputStream = new FileInputStream(fileName);
            } else {
                inputStream = new FileInputStream(args[0]);
                fileName = args[0];
            }
            WordSplay splay = new WordSplay();
            splay.run(inputStream,fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
