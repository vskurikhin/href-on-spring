package su.svn.href.models.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AnswerOkTest
{
    AnswerOk answer;

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNew
    {
        @BeforeEach
        void createNew()
        {
            answer = new AnswerOk();
        }

        @Test
        @DisplayName("initialized values in instance of class")
        void defaults()
        {
            assertThat(answer).hasFieldOrPropertyWithValue("status", "Ok");
        }

        @Test
        @DisplayName("equals and hashCode for class")
        void testEquals()
        {
            AnswerOk expected = new AnswerOk();
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