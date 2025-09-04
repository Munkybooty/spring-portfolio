package com.hussain.projects.spring_portfolio;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvEntry;
import org.springframework.beans.factory.BeanCreationException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SpringPortfolioApplicationTests {

    @Test
    void main_setsDotenvVariablesAsSystemProperties() {
        Dotenv mockDotenv = Mockito.mock(Dotenv.class);
        DotenvEntry entry1 = new DotenvEntry("DB_URL", "jdbc:oracle:thin:@localhost:1521");
        Set<DotenvEntry> entries = new HashSet<>(Arrays.asList(entry1));

        Mockito.when(mockDotenv.entries()).thenReturn(entries);

        try (MockedStatic<Dotenv> mockedDotenv = Mockito.mockStatic(Dotenv.class)) {
            mockedDotenv.when(Dotenv::load).thenReturn(mockDotenv);
            SpringPortfolioApplication.main(new String[]{});
        } catch (BeanCreationException e) { // Workaround for Spring context load failure due to missing DB
            if (e.getMessage().contains("Unable to determine Dialect without JDBC metadata")) {
                assertEquals("jdbc:oracle:thin:@localhost:1521", System.getProperty("DB_URL"));
            } else {
                fail("Unexpected BeanCreationException: " + e.getMessage());
            }
        }
    }

}
