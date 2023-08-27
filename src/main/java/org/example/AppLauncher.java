package org.example;

import org.example.frames.StartWindow;

import java.io.IOException;
import java.util.List;

public class AppLauncher {
    private static List<String> cities;

    public static void main(String[] args) throws IOException {
        new StartWindow();
    }
}