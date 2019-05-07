package su.svn.href.models;

import static org.junit.jupiter.api.Assertions.*;
import static su.svn.utils.TestData.*;

public class ManagerTest
{
    public static Manager testManager = new Manager(
        TEST_ID, TEST_FIRST_NAME, TEST_LAST_NAME, TEST_EMAIL, TEST_PHONE_NUMBER
    );
}