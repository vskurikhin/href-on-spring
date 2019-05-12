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

@DisplayName("Class Answer")
class AnswerTest
{
    Answer answer;

    @Test
    @DisplayName("is instantiated with new object")
    void isInstantiatedWithNew()
    {
        new Answer();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew
    {
        @BeforeEach
        void createNew()
        {
            answer = new Answer();
        }

        @Test
        @DisplayName("default values in the instance of class")
        void defaults()
        {
            assertThat(answer).hasFieldOrPropertyWithValue("status", "");
        }
    }

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNewAllArgsConstructor
    {
        @BeforeEach
        void createNew()
        {
            answer = new Answer(TEST_SID);
        }

        @Test
        @DisplayName("initialized values in instance of class")
        void defaults()
        {
            assertThat(answer).hasFieldOrPropertyWithValue("status", TEST_SID);
        }

        @Test
        @DisplayName("equals and hashCode for class")
        void testEquals()
        {
            assertNotEquals(new Answer(), answer);
            Answer expected = new Answer(TEST_SID);
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