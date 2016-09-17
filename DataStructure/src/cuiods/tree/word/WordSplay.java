package cuiods.tree.word;

import cuiods.tree.binary.BSTNode;
import cuiods.tree.binary.SplayTree;
import cuiods.tree.binary.SplayTreeNode;

import java.io.IOException;
import java.io.InputStream;

/**
 * word splay tree
 * @author cuiods
 */
public class WordSplay extends SplayTree<Word>{

    private int differentWords, wordCount;

    @Override
    protected void handleSame(Word data) {
        data.setFreq(data.getFreq()+1);
    }

    @Override
    protected void visit(Word word) {
        super.visit(word);
        differentWords ++;
        wordCount += word.getFreq();
    }

    public void run(InputStream inputStream, String fileName) {
        int ch = 1;
        Word p = null;
        try {
            while (ch >-1) {
                while (true) {
                    if (ch > -1 && !Character.isLetter((char) ch))
                        ch = inputStream.read();
                    else break;
                }
                if (ch == -1) break;
                String s = "";
                while (ch > -1 && Character.isLetter((char) ch)) {
                    s += Character.toUpperCase((char) ch);
                    ch = inputStream.read();
                }
                insert(new Word(s));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        inorder();
        System.out.println("\nFile "+fileName+" contains "+ wordCount + " words among which "+differentWords+" are different.\n");
    }
}
