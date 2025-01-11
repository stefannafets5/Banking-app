package org.poo.commands;

public class TestCeva implements Command {
    private final String message;

    public TestCeva(String message) {
        this.message = message;
    }

    @Override
    public void execute() {
        System.out.println("Comanda necunoscută: " + message);
    }
}
