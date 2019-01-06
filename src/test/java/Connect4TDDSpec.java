import TDDConnect4.Connect4TDD;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.isEmptyString;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/*
    Req 1:
    The board is composed by seven horizontal
    and six vertical empty positions

    Req 2:
    Player introduce disc on top of the columns
    An introduced disc drops down the board if the column is empty
    Future disc introduced in the same column will stack over the previous ones

    Req 3:
    In is a two-person,so there is one colour for each player
    One player uses red('R') and the other one uses green('G')
    Player alternate turns, inserting one disc every time

    Req 4:
    We want feedback when either an event or an event or an error
    occurs within the game
    The output shows the status of the board on every move

    Req 5:
    When no more disc can be inserted, the game finished and it is
    considered a draw.

    Req 6:
    If a player inserts a disc and connects more than three disc
    of his color in a straight vertical line, then that player wins.

    Req 7:
    If a player inserts a disc and connects more than three disc
    of his color in a straight horizontal line, then that player wins

    Req 8:
    If a player inserts a disc and connects more than three discs of his
    colour in a straight diagonal line, then the player wins
 */


public class Connect4TDDSpec {

    private OutputStream outputStream;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private Connect4TDD tested;

    @Before
    public void beforeEachTest(){

        outputStream = new ByteArrayOutputStream();
        tested = new Connect4TDD(new PrintStream(outputStream));
    }

    @Test
    public void whenTheGameIsStartedTheBoardIsEmpty(){
        assertThat(tested.getNumberOfDiscs(),is(0));
    }


    @Test
    public void whenDiscOutsideBoardThenRuntimeException(){
        int column = -1;
        exception.expect(RuntimeException.class);
        exception.expectMessage("Invalid column "+column);
        tested.putDiscInColumn(column);
    }

    @Test
    public void whenFirstDiscInsertedInColumnThenPositionIsZero(){
        int column = 1;
        tested.putDiscInColumn(column);
        assertThat(tested.getNumberOfDiscs(),is(1));
    }

    @Test
    public void whenSecondDiscInsertedInColumnThenPositionIsOne(){
        int column =1;
        tested.putDiscInColumn(column);
        assertThat(tested.getNumberOfDiscs(),is(1));
    }

    @Test
    public void whenNoMoreRoomInColumnThenRuntimeException(){
        int column =1;
        int maxDiscsInColumn = 6; // the number of rows
        for(int times=0; times<maxDiscsInColumn; ++times ){
            tested.putDiscInColumn(column);
        }
        exception.expect(RuntimeException.class);
        exception.expectMessage("No more room in column "+tested.putDiscInColumn(column));
    }

    @Test
    public void whenFirstPlayerPlaysThenDiscColorIsRed(){
        assertThat(tested.getCurrentPlayer(),is("R"));
    }

    @Test
    public void whenSecondPlayerPlayerThenDiscColorIsRed(){
        int column = 1;
        tested.putDiscInColumn(column);
        assertThat(tested.getCurrentPlayer(),is("G"));
    }

    @Test
    public void whenTheGameStartsItIsNotFinished(){
        assertFalse("The game must not be finished", tested.isFinished());
    }

    @Test
    public void whenAskedForCurrentPlayerTheOutputNotice(){
        tested.getCurrentPlayer();
        assertThat(outputStream.toString(),containsString("Player R turn"));
    }
    @Test
    public void whenADiscIsIntroducedTheBoardIsPrinted() {
        int column = 1;
        tested.putDiscInColumn(column);
        assertThat(outputStream.toString(),containsString("| |R| | | | | |"));
    }

    @Test
    public void whenNoDiscCanBeIntroducedTheGameIsFinished(){
        for(int row =0;row<6;row++){
            for(int column =0;column<7;column++){
                tested.putDiscInColumn(column);
            }

        }
        assertTrue("The game is finished", tested.isFinished());

    }

    @Test
    public void when4VerticalDiscsAreConnectedThenPlayerWins(){
        for(int row =0;row<3;row++){
            tested.putDiscInColumn(1);
            tested.putDiscInColumn(2);
        }

        assertThat(tested.getWinner(),isEmptyString());
        tested.putDiscInColumn(1);//R
        assertThat(tested.getWinner(),is("R"));
    }


    @Test
    public void when4HorizontalDiscAreConnectedThenPlayerWins(){
        int column;
        for(column =0;column<3;column++){
            tested.putDiscInColumn(column);//R
            tested.putDiscInColumn(column);//G
        }
        assertThat(tested.getWinner(),isEmptyString());
        tested.putDiscInColumn(column); // R
        assertThat(tested.getWinner(),is("R"));
    }
}
