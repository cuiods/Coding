package cuiods.backTracing;

/**
 * @author cuiods
 */
public class EightQueue {
    private int size;
    private int[] position;
    private boolean[] columnAvailable;
    private boolean[] leftDialogAvailable;
    private boolean[] rightDialogAvailable;

    public EightQueue(int size) {
        assert size > 3;
        this.size = size;
        position = new int[size];
        columnAvailable = new boolean[size];
        leftDialogAvailable = new boolean[size * 2 - 1];
        rightDialogAvailable = new boolean[size * 2 - 1];
        for (int i = 0; i < size; i++) {
            position[i] = -1;
            columnAvailable[i] = true;
        }
        for (int i = 0; i < 2*size-1; i++) {
            leftDialogAvailable[i] = true;
            rightDialogAvailable[i] = true;
        }
    }

    public void putQueue() {
        putQueue(0);
    }

    private void putQueue(int row) {
        for (int col = 0; col < size; col++) {
            if (columnAvailable[col] && leftDialogAvailable[col+row] && rightDialogAvailable[row-col+size-1]) {
                position[row] = col;
                columnAvailable[col] = false;
                leftDialogAvailable[col+row] = false;
                rightDialogAvailable[row-col+size-1] = false;
                if (row != size-1) {
                    putQueue(row+1);
                } else {
                    printChessBoard();
                }
                position[row] = -1;
                columnAvailable[col] = true;
                leftDialogAvailable[col+row] = true;
                rightDialogAvailable[row-col+size-1] = true;
            }
        }
    }

    private void printChessBoard() {
        System.out.println("-----------------------------");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (position[i]==j)
                    System.out.print("x ");
                else
                    System.out.print(". ");
            }
            System.out.println();
        }
    }
}
