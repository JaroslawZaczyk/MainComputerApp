package pl.edu.pk.mech.datasplitter;


import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Test
public class SplitterImplTest {

    private Splitter splitter;

    @BeforeClass
    public void setUp() {
        splitter = new SplitterImpl();
    }

    public void testSplitData_WithOKInput() throws Exception {
        //given
        String input = "L:3;VCB:10;ASW:10;UST:10;";
        //when
        Map<String, String> splitdata = splitter.splitData(input);
        //then
        assertEquals(splitdata.size(), 3);
        assertTrue(splitdata.containsKey("VCB"));
        assertTrue(splitdata.containsKey("ASW"));
        assertTrue(splitdata.containsKey("UST"));
    }

    public void testSplitData_WithOKInput_ContainingOneElement() throws Exception {
        //given
        String input = "L:1;VCB:10;";
        //when
        Map<String, String> splitdata = splitter.splitData(input);
        //then
        assertEquals(splitdata.size(), 1);
        assertTrue(splitdata.containsKey("VCB"));
    }

    public void testSplitData_WithOKInput_AndOneIncorrectParameter_ContainingOneElement() throws Exception {
        //given
        String input = "L:2;VCB:10;AAA:10;";
        //when
        Map<String, String> splitdata = splitter.splitData(input);
        //then
        assertEquals(splitdata.size(), 1);
        assertTrue(splitdata.containsKey("VCB"));
    }

    public void testSplitData_WithOKInput_AndAllIncorrectParameters_ReturnsEmptyList() throws Exception {
        //given
        String input = "L:2;BBB:10;AAA:10;";
        //when
        Map<String, String> splitdata = splitter.splitData(input);
        //then
        assertEquals(splitdata.size(), 0);
    }

    public void testSplitData_WithZeroLengthInput() throws Exception {
        //given
        String input = "L:0;";
        //when
        Map<String, String> splitdata = splitter.splitData(input);
        //then
        assertEquals(splitdata.size(), 0);
        assertEquals(splitdata, Collections.emptyMap());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testSplitData_WithNullInput() throws Exception {
        //given - when
        splitter.splitData(null);
        //then throw IAE
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testSplitData_WithEmptyInput() throws Exception {
        //given - when
        splitter.splitData("");
        //then throw IAE
    }

    @Test(expectedExceptions = DataValidationException.class)
    public void testSplitData_WithOKInput_AndInvalidNumberOfParams() throws Exception {
        //given
        String input = "L:2;AA:10;BB:10;CC:10;";
        //when
        splitter.splitData(input);
        //then throw exception
    }
}
