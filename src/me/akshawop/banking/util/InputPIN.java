package me.akshawop.banking.util;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

public class InputPIN {
    public static String getInput(String message, char mask) throws Exception {
        Terminal terminal = TerminalBuilder.builder().system(true).build();

        LineReader reader = LineReaderBuilder.builder().terminal(terminal).build();

        String pin = reader.readLine(message, mask);

        terminal.close();

        return pin;
    }
}
