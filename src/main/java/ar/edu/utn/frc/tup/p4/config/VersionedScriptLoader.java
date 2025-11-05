package ar.edu.utn.frc.tup.p4.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
@Log4j2
public class VersionedScriptLoader implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;
    private final Environment env;

    private static final String DB_VERSION_QUERY = "SELECT sv.version FROM schema_version sV;";
    private static final String CREATE_SCHEMA_VERSION_QUERY = "CREATE TABLE schema_version (version VARCHAR(20) PRIMARY KEY,executed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP);";
    private static final String CREATE_EXECUTED_SCRIPTS_QUERY = "CREATE TABLE executed_scripts (id NUMBER PRIMARY KEY,version VARCHAR(20) NOT NULL,script_name VARCHAR(255) NOT NULL,executed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,app_version VARCHAR(20) NOT NULL,success BOOLEAN DEFAULT TRUE,error_message TEXT);";
    private static final String INSERT_SCHEMA_VERSION_QUERY = "INSERT INTO schema_version (version, executed_at) VALUES ('0.0.0', NOW());";

    public VersionedScriptLoader(JdbcTemplate jdbcTemplate, Environment env) {
        this.jdbcTemplate = jdbcTemplate;
        this.env = env;
    }

    @Override
    public void run(String... args) throws Exception {
        String appVersion = env.getProperty("app.version");

        if (Objects.isNull(appVersion)) {
            log.error("Propiedad app.version no encontrada.");
            return;
        }

        String dbVersion = getOrCreateDbVersion();

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        Resource[] versionDirs = resolver.getResources("classpath:db/versions/*/");
        List<String> availableVersions = Arrays.stream(versionDirs)
                .map(Resource::getFilename)
                .filter(version -> compareVersions(dbVersion, version) <= 0)
                .sorted()
                .toList();

        for (String version : availableVersions) {
            if (version.compareTo(dbVersion) <= 0) {
                executePendingScriptsForVersion(version, resolver, appVersion);
            }
        }
    }

    private void executePendingScriptsForVersion(String version, PathMatchingResourcePatternResolver resolver, String appVersion) throws IOException {
        Resource[] scripts = resolver.getResources("classpath:db/versions/" + version + "/*.sql");

        List<String> executedScripts = jdbcTemplate.queryForList(
                "SELECT script_name FROM executed_scripts WHERE version = ?", String.class, version
        );

        for (Resource script : scripts) {
            String name = script.getFilename();
            if (executedScripts.contains(name)) {
                continue;
            }

            String sql = new String(script.getInputStream().readAllBytes());
            try {
                jdbcTemplate.execute(sql);
                jdbcTemplate.update(
                        "INSERT INTO executed_scripts (version, script_name, app_version, success) VALUES (?, ?, ?, TRUE)",
                        version, name, appVersion
                );
                System.out.println("✔ Ejecutado " + name + " [versión " + version + "]");
            } catch (Exception e) {
                jdbcTemplate.update(
                        "INSERT INTO executed_scripts (version, script_name, app_version, success, error_message) VALUES (?, ?, ?, FALSE, ?)",
                        version, name, appVersion, e.getMessage()
                );
                System.err.println("Error ejecutando " + name + ": " + e.getMessage());
            }
        }
    }

    private String getOrCreateDbVersion() {
        String dbVersion = null;
        try {
            this.jdbcTemplate.queryForObject(DB_VERSION_QUERY, String.class);
        } catch (Exception ignore) {}

        if (Objects.isNull(dbVersion)) {
            this.jdbcTemplate.execute(CREATE_SCHEMA_VERSION_QUERY);
            this.jdbcTemplate.execute(CREATE_EXECUTED_SCRIPTS_QUERY);
            this.jdbcTemplate.update(INSERT_SCHEMA_VERSION_QUERY);
            dbVersion = "0.0.0";
        }

        return dbVersion;
    }

    private static int compareVersions(String v1, String v2) {
    String[] p1 = v1.split("\\.");
    String[] p2 = v2.split("\\.");
    int length = Math.max(p1.length, p2.length);
    for (int i = 0; i < length; i++) {
        int n1 = i < p1.length ? Integer.parseInt(p1[i]) : 0;
        int n2 = i < p2.length ? Integer.parseInt(p2[i]) : 0;
        if (n1 != n2) return Integer.compare(n1, n2);
    }
    return 0;
}
}
