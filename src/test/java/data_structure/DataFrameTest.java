package data_structure;

import org.junit.Test;
import utils.CsvParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class DataFrameTest {

    private String[] mIndexes = new String[]{"1", "2", "3"};

    private String[] mLabels = new String[]{"A", "B", "C"};

    private CsvParser myParser;

    private DataFrame dataFrame = new DataFrame(mIndexes, mLabels, Arrays.asList("Test1","Test2","test3"),Arrays.asList(2,5,6),Arrays.asList("2","5","6"));
    private DataFrame df2;
    private DataFrame df3;

    @Test
    public void testContructor1(){
        assertNotEquals(null,dataFrame);
    }

    @Test
    public void testConstructor2() {
        try {
            myParser = new CsvParser("rsc/csv_examples/test.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
        DataFrame df2 = new DataFrame(myParser.getIndexes(), myParser.getLabels(), myParser.getColumns());
        assertNotEquals(null,df2);
    }


    @Test(expected = IllegalArgumentException.class)
    public void test_illegal_Constructor2() {
        try {
            myParser = new CsvParser("rsc/csv_examples/test.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> indexesbug = new ArrayList<>(myParser.getIndexes());
        indexesbug.add("erreur");
        DataFrame df2 = new DataFrame(indexesbug, myParser.getLabels(), myParser.getColumns());
        assertNotEquals(null,df2);
    }


    @Test(expected = IllegalArgumentException.class)
    public void test_illegal2_Constructor2() {
        try {
            myParser = new CsvParser("rsc/csv_examples/test.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> labelsbug = new ArrayList<>(myParser.getLabels());
        labelsbug.add("erreur");
        DataFrame df2 = new DataFrame(myParser.getIndexes(), labelsbug, myParser.getColumns());
        assertNotEquals(null,df2);
    }

    @Test
    public void testConstructor3() {
        DataFrame df3 = new DataFrame("rsc/csv_examples/test.csv");
        assertNotEquals(null,df3);
    }


    @Test
    public void testIndexs(){
        assertEquals("Les Indices passer en param??tre ne sont pas les m??mes dans le DataFrame",dataFrame.getIndexes(),Arrays.asList(mIndexes));
    }

    @Test
    public void testLabels(){
        assertEquals("Les labels passer en param??tre ne sont pas les m??mes dans le DataFrame",dataFrame.getLabels(),Arrays.asList(mLabels));
    }

    @Test
    public void testColumns() {
        List <Column> columns = new ArrayList<>();
        columns.add(new Column<>(Arrays.asList("Test1","Test2","test3")));
        columns.add(new Column<>(Arrays.asList(2,5,6)));
        columns.add(new Column<>(Arrays.asList("2","5","6")));
        assertEquals("",dataFrame.getColumns(),columns);
    }

    @Test(expected = NullPointerException.class)
    public void test_NullPointer_Contructor1(){
        new DataFrame(null,null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_Illegal_Argument_Index_Contructor1(){
        DataFrame dataFrameIndex = new DataFrame(mIndexes, mLabels, Arrays.asList("Test1","Test2","test3"),Arrays.asList(2,5,6),Arrays.asList("2","6"));
        assertNotEquals(null,dataFrameIndex);
    }



    @Test(expected = IllegalArgumentException.class)
    public void test_Illegal_Argument_Label_Contructor1(){
        DataFrame dataFrameLabel = new DataFrame(mIndexes, mLabels, Arrays.asList("Test1","Test2","test3"),Arrays.asList(2,5,6));
        assertNotEquals(null,dataFrameLabel);
    }




    @Test
    public void equals(){
        DataFrame df1 = new DataFrame(new String[]{"1", "2", "3"}, new String[]{"A", "B", "C"}, Arrays.asList("Test1","Test2","test3"),Arrays.asList(2,5,6),Arrays.asList("2","5","6"));
        DataFrame df2 = new DataFrame(new String[]{"1", "2", "3"}, new String[]{"A", "B", "C"}, Arrays.asList("Test1","Test2","test3"),Arrays.asList(2,5,6),Arrays.asList("2","5","6"));
        DataFrame df3 = new DataFrame(new String[]{"1", "2","3"}, new String[]{"A", "B"}, Arrays.asList("Test1","Test2","test3"),Arrays.asList(2,5,6));
        assertEquals(df1,df1);
        assertNotEquals("DataFrame ne doit pas equals a un object",df1, new Object());
        assertNotEquals("DataFrame ne doit pas equals a null",df1, null);
        assertEquals("probleme dans la fonction equals,ces deux objets ont le meme contenu",df1,df2);
        assertNotEquals(df1,df3);
    }


    @Test
    public void groupByAgregate() {
        DataFrame dataframe = new DataFrame("rsc/csv_examples/sum_liste_courses.csv");
        DataFrame d2 = dataframe.groupByAgregate("client_name","min");
        DataFrame d3 = dataframe.groupByAgregate("client_name","max");
        DataFrame d4 = dataframe.groupByAgregate("client_name","sum");
        DataFrame d5 = dataframe.groupByAgregate("client_name","prod");
        DataFrame d6 = dataframe.groupByAgregate("colonne_inexistante","min");
        assertNull("colonne inexistante", d6);
        assertEquals("Probleme dans le groupBy",25.0,d2.getColumns().get(2).getCells().get(0));
        assertEquals("Probleme dans le groupBy",20.0,d2.getColumns().get(2).getCells().get(1));

    }
}