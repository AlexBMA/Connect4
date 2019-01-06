package noTDDConnect4;

import java.util.Arrays;
import java.util.StringJoiner;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.regex.Pattern;


public class Connect4 {

    /* Req 1 Connect 4*/
    public enum Color {

        RED('R'), GREEN('G'), EMPTY(' ');

        private final char value;

        Color(char value) {
            this.value = value;
        }

        @Override
        public String toString(){
            return String.valueOf(value);
        }
    }

    public static final int COLUMNS =7;
    public static final int ROWS =6;

    private Color[][] board = new Color[COLUMNS][ROWS];

    public Connect4(){
        for (Color[] column: board){
            Arrays.fill(column,Color.EMPTY);
        }
    }
    /* End of Req 1 Connect 4 */

    /* Req 2 Connect 4 begin */
    public void putDics(int column){
        if(column>0 && column<=COLUMNS){
            int numOfDiscs = getNumberOfDiscsInColumn(column-1);
            if(numOfDiscs <ROWS){
                // changes for Req 3
                board[column-1][numOfDiscs] = currentPlayer;
                printBoard();
                // changes for Req 6
                checkWinCondition(column -1, numOfDiscs);
                switchPlayer();
            } // changes for Req 4
            else {
                System.out.println(numOfDiscs);
                System.out.println("There's no room "+ "for a new disc in this column");
                printBoard();
            }

        }else {
            System.out.println("Column out ov bounds");
            printBoard();
        }
    }


    private int getNumberOfDiscsInColumn(int column) {

        if (column >= 0 && column < COLUMNS) {
        int row;
        for (row =0;row<=ROWS;row++) {
                if(Color.EMPTY == board[column][row]){
                    return row;
                }
            }
            return row;
        }
        return -1;
    }
    /* End of Req 2 */
    /* Begin Req 3 */

    private Color currentPlayer = Color.RED;

    private  void switchPlayer(){
        if(Color.RED == currentPlayer){
            currentPlayer = Color.GREEN;
        } else {
            currentPlayer = Color.RED;
        }
        // Change for req 4
        System.out.println("Current turn: "+currentPlayer);
    }
    /* End of Req 3 */

    /* Begin Req 4 */

    private static final String DELIMITER = "|";

    public void printBoard(){

        for( int row = ROWS -1; row >=0; --row){

            StringJoiner  stringJoiner = new StringJoiner(DELIMITER, DELIMITER, DELIMITER);

            for (int col =0; col<COLUMNS; ++col){
                stringJoiner.add(board[col][row].toString());
            }
        }
    }
    /* End of Req 4 */
    /* Begin Req 5 */

    public boolean isFinished(){
        // change for req 6
        if(winner!=null) return true;

        int numOfDiscs = 0;
        for( int col =0;col<COLUMNS;++col){
            numOfDiscs+= getNumberOfDiscsInColumn(col);
        }
        if(numOfDiscs >=COLUMNS * ROWS){
            System.out.println("It's a draw");
            return true;
        }
        return false;
    }

    /* End Req 5 */
    /* Begin Req 6 */

    public Color winner;
    public static final int DISCS_FOR_WIN = 4;

    private void checkWinCondition(int col, int row) {
        Pattern winPattern = Pattern.compile(".*"+currentPlayer+"{"+DISCS_FOR_WIN+"}.*");

        // Vertical check
        StringJoiner stringJoiner = new StringJoiner("");
        for(int auxRow =0;auxRow<ROWS;++auxRow){
            stringJoiner.add(board[col][auxRow].toString());
        }
        if(winPattern.matcher(stringJoiner.toString()).matches()){
            winner = currentPlayer;
            System.out.println(currentPlayer+" wins");
            return;
        }
        // Changes for Req 7
        // Horizontal check
        stringJoiner = new StringJoiner("");
        for(int column =0;column<COLUMNS; ++column){
            stringJoiner.add(board[column][row].toString());
        }
        if(winPattern.matcher(stringJoiner.toString()).matches()){
            winner = currentPlayer;
            System.out.println(currentPlayer+" wins");
            return;
        }
        // Diagonal checks
        int startOffset = Math.min(col, row);
        int column = col- startOffset;
        int auxRow = row - startOffset;
        stringJoiner = new StringJoiner("");
        do{
            stringJoiner.add(board[column++][auxRow++].toString());
        } while(column<COLUMNS && auxRow < ROWS);

        if( winPattern.matcher(stringJoiner.toString()).matches()){
            winner = currentPlayer;
            System.out.println(currentPlayer+" wins ");
            return;
        }

        startOffset = Math.min(col,ROWS -1-row);
        column = col - startOffset;
        auxRow = row + startOffset;
        stringJoiner = new StringJoiner("");
        do{
            stringJoiner.add(board[column++][auxRow--].toString());
        }while (column<COLUMNS && auxRow >=0);

        if(winPattern.matcher(stringJoiner.toString()).matches()){
            winner = currentPlayer;
            System.out.println(currentPlayer+" wins");
        }
    }
    /* End Req 6 */
    /* Begin Req 7 */
    /* End of Req 7 */
    /* Begin req 8 */

    /* End of Req 8 */

}
