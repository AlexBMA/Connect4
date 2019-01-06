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


}
