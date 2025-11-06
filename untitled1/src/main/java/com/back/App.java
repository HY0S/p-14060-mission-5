package com.back;

import com.back.domain.system.controller.SystemController;
import com.back.domain.wiseSaying.controller.WiseSayingController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class App {
    private Scanner scanner;
    private SystemController systemController;
    private WiseSayingController wiseSayingController;

    @Autowired
    public App(WiseSayingController wiseSayingController) {
        this.wiseSayingController = wiseSayingController;
        this.systemController = new SystemController();
    }

    public App(Scanner scanner, WiseSayingController wiseSayingController) {
        this.scanner = scanner;
        this.wiseSayingController = wiseSayingController;
        this.systemController = new SystemController(scanner);
        this.wiseSayingController.setScanner(scanner);
    }
    
    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
        if (systemController != null) {
            systemController = new SystemController(scanner);
        }
        if (wiseSayingController != null) {
            wiseSayingController.setScanner(scanner);
        }
    }

    public void run() {
        System.out.println("== WiseSaying App ==");

        wiseSayingController.dummyData();

        while (true) {
            System.out.print("command) ");
            String command = scanner.nextLine().trim();

            if (command.equals("exit")) {
                systemController.exit();
                break;
            } else if (command.equalsIgnoreCase("register")) {
                wiseSayingController.write();
            } else if (command.startsWith("list")) {
                wiseSayingController.list(command);
            } else if (command.startsWith("remove")) {
                wiseSayingController.remove(command);
            } else if (command.startsWith("modify")) {
                wiseSayingController.modify(command);
            } else if (command.startsWith("build")) {
                wiseSayingController.build();
            }
            else {
                System.out.println("not a valid command.");
            }
        }
    }
}
