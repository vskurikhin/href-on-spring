package su.svn.href.models.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static su.svn.utils.TestData.TEST_SID;

@DisplayName("Class AnswerBadRequest")
class AnswerBadRequestTest
{
    AnswerBadRequest answer;

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNewAllArgsConstructor
    {
        @BeforeEach
        void createNew()
        {
            answer = new AnswerBadRequest(TEST_SID);
        }

        @Test
        @DisplayName("initialized values in instance of class")
        void defaults()
        {
            assertThat(answer).hasFieldOrPropertyWithValue("status", "Bad Request: " + TEST_SID);
        }

        @Test
        @DisplayName("equals and hashCode for class")
        void testEquals()
        {
            assertNotEquals(new AnswerBadRequest(""), answer);
            AnswerBadRequest expected = new AnswerBadRequest(TEST_SID);
            assertEquals(expected.hashCode(), answer.hashCode());
            assertEquals(expected, answer);
        }

        @Test
        @DisplayName("the length of string from toString is great than zero")
        void testToString()
        {
            assertTrue(answer.toString().length() > 0);
        }
    }
}