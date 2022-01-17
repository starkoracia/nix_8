package ua.com.alevel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

@SpringBootApplication
public class DataTableJdbcMain {

    public static void main(String[] args) {
        SpringApplication.run(DataTableJdbcMain.class, args);
    }

    @EventListener(ApplicationStartedEvent.class)
    public void initialize() {

    }
}


