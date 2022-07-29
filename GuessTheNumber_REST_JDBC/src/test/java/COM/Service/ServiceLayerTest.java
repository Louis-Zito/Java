package COM.Service;
import COM.ServiceLayer.ServiceLayer;
import COM.TestApplicationConfiguration;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class ServiceLayerTest {
    @Autowired
    ServiceLayer serviceLayer;

    @Test
    public void calculateResultTest(){
        //1 in proper place - 3 not included
        String guess1 = "1234";
        String answer1 = "1567";
        String result1 = serviceLayer.calculateResult(guess1, answer1);
        assertEquals(result1, "e:1:p:0");

        //1 in proper place - 1 included
        String guess2 = "1234";
        String answer2 = "1456";
        String result2 = serviceLayer.calculateResult(guess2, answer2);
        assertEquals(result2, "e:1:p:1");

        //2 in proper place - 2 included
        String guess3 = "1234";
        String answer3 = "1243";
        String result3 = serviceLayer.calculateResult(guess3, answer3);
        assertEquals(result3, "e:2:p:2");

        //3 in proper place - 1 not included
        String guess4 = "1234";
        String answer4 = "1235";
        String result4 = serviceLayer.calculateResult(guess4, answer4);
        assertEquals(result4, "e:3:p:0");

        //4 in proper place
        String guess5 = "1234";
        String answer5 = "1234";
        String result5 = serviceLayer.calculateResult(guess5, answer5);
        assertEquals(result5, "e:4:p:0");
    }

    //test generateAnswer: no duplicates and proper len of 4
    @Test
    public void generateAnswerTest(){
        String returnedAnswer = serviceLayer.generateAnswer();
        //answer has a length of 4
        Assert.assertEquals(4, returnedAnswer.length());
        String[] answerSplit = returnedAnswer.split("");
        ArrayList answerAsList = new ArrayList<>(Arrays.asList(answerSplit));
        //answer returns no duplicate chars in the String
        Assert.assertEquals(new HashSet<Long>(answerAsList).size(), answerAsList.size());
    }
}
