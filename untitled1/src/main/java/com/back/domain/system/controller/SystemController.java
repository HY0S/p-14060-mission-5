package com.back.domain.system.controller;

import java.util.Scanner;

public class SystemController {
    private Scanner scanner;

    public SystemController() {
    }

    public SystemController(Scanner scanner) {
        this.scanner = scanner;
    }

    public void exit() {
        System.out.println("finished.");
        System.exit(0);
    }
}
