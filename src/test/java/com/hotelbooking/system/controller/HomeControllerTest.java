package com.hotelbooking.system.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HomeControllerTest {

        @Test
        void testHomePage() {
            HomeController controller =
                    new HomeController();
            String page = controller.homePage();
            assertEquals("home", page);
        }
    }

