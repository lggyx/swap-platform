package com.lggyx;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import io.github.cdimascio.dotenv.Dotenv;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@SpringBootApplication
public class SwapPlatformApplication {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure()
                .directory(".")
                .load();
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
        SpringApplication.run(SwapPlatformApplication.class, args);
    }
}

@Component
class DatabaseInitializer implements CommandLineRunner {
    private final DataSource dataSource;

    public DatabaseInitializer(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void run(String... args) {
        String[] scripts = {
            "docs/db/swap-platform.sql",
            "docs/db/mock-data.sql"
        };

        for (String scriptPath : scripts) {
            try {
                executeSqlScript(scriptPath);
                System.out.println("Database init script executed: " + scriptPath);
            } catch (Exception e) {
                System.err.println("Database init failed for " + scriptPath + ": " + e.getMessage());
                System.err.println("Application will continue to start without this script.");
            }
        }
    }

    private void executeSqlScript(String scriptPath) throws IOException, SQLException, InterruptedException {
        String sql = new String(Files.readAllBytes(Path.of(scriptPath)));
        int maxRetries = 3;
        for (int i = 0; i < maxRetries; i++) {
            try (Connection conn = dataSource.getConnection();
                 Statement stmt = conn.createStatement()) {
                stmt.execute(sql);
                return;
            } catch (SQLException e) {
                if (i == maxRetries - 1) throw e;
                System.err.println("Retry " + (i + 1) + " for " + scriptPath + ": " + e.getMessage());
                Thread.sleep(2000);
            }
        }
    }
}
