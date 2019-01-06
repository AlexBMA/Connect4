import TDDConnect4.Connect4TDD;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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

 */
public class Connect4TDDSpec {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private Connect4TDD tested;

    @Before
    public void beforeEachTest(){
            tested = new Connect4TDD();
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

}
