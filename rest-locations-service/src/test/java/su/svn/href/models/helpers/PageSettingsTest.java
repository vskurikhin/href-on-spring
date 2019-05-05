package su.svn.href.models.helpers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PageSettingsTest
{
    private PageSettings paging;

    @Test
    @DisplayName("is instantiated with new object")
    void isInstantiatedWithNew()
    {
        new PageSettings();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew
    {
        @BeforeEach
        void createNew()
        {
            paging = new PageSettings();
        }

        @Test
        @DisplayName("default values in the instance of class")
        void defaults()
        {
            assertThat(paging).hasFieldOrPropertyWithValue("min", 0);
            assertThat(paging).hasFieldOrPropertyWithValue("max", 0);
            assertThat(paging).hasFieldOrPropertyWithValue("size", 0);
        }

        @Test
        @DisplayName("setter and getter for min")
        void testGetSetMin()
        {
            paging.setMin(11);
            assertThat(paging).hasFieldOrPropertyWithValue("min", 11);
            assertEquals(11, paging.getMin());
        }

        @Test
        @DisplayName("setter and getter for max")
        void testGetSetMax()
        {
            paging.setMax(1111);
            assertThat(paging).hasFieldOrPropertyWithValue("max", 1111);
            assertEquals(1111, paging.getMax());
        }

        @Test
        @DisplayName("setter and getter for size")
        void testGetSetSize()
        {
            paging.setSize(13);
            assertThat(paging).hasFieldOrPropertyWithValue("size", 13);
            assertEquals(13, paging.getSize());
        }
    }

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNewAllArgsConstructor
    {
        @BeforeEach
        void createNew()
        {
            paging = new PageSettings(11, 1111, 13);
        }

        @Test
        @DisplayName("initialized values in instance of class")
        void defaults()
        {
            assertThat(paging).hasFieldOrPropertyWithValue("min", 11);
            assertThat(paging).hasFieldOrPropertyWithValue("max", 1111);
            assertThat(paging).hasFieldOrPropertyWithValue("size", 13);

        }

        @Test
        @DisplayName("equals and hashCode for class")
        void testEquals()
        {
            assertNotEquals(new PageSettings(), paging);
            PageSettings expected = new PageSettings(11, 1111, 13);
            assertEquals(expected.hashCode(), paging.hashCode());
            assertEquals(expected, paging);
        }

        @Test
        @DisplayName("the length of string from toString is great than zero")
        void testToString()
        {
            assertTrue(paging.toString().length() > 0);
        }
    }

    @Nested
    @DisplayName("when new with two args constructor")
    class WhenNewTwoArgsConstructor
    {
        @BeforeEach
        void createNew()
        {
            paging = new PageSettings(11, 1111);
        }

        @Test
        @DisplayName("initialized values in instance of class")
        void defaults()
        {
            assertThat(paging).hasFieldOrPropertyWithValue("min", 11);
            assertThat(paging).hasFieldOrPropertyWithValue("max", 1111);
            assertThat(paging).hasFieldOrPropertyWithValue("size", 0);

        }

        @Test
        @DisplayName("equals and hashCode for class")
        void testEquals()
        {
            assertNotEquals(new PageSettings(), paging);
            PageSettings expected = new PageSettings(11, 1111, 0);
            assertEquals(expected.hashCode(), paging.hashCode());
            assertEquals(expected, paging);
        }

        @Test
        @DisplayName("equals and hashCode for class")
        void testGetLimit()
        {
            assertEquals(paging.getMin(), paging.getLimit(paging.getMin() - 1));
            assertEquals(paging.getMax(), paging.getLimit(paging.getMax() + 1));
        }

        @Test
        @DisplayName("equals and hashCode for class")
        void testGetOffset()
        {
            assertEquals(0, paging.getOffset(-1));
            assertEquals(0, paging.getOffset(1));
            assertEquals(2*3, paging.getOffset(3,3));
            assertEquals(5*13, paging.getOffset(6,13));
        }
    }
}