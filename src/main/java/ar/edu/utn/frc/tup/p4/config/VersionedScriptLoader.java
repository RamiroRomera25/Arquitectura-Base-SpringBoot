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

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        Resource[] versionDirs = resolver.getResources("classpath:db/versions/*/");
        List<String> availableVersions = Arrays.stream(versionDirs)
                .map(Resource::getFilename)
                .sorted()
                .toList();

        for (String version : availableVersions) {
            if (version.compareTo(appVersion) <= 0) {
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
}
