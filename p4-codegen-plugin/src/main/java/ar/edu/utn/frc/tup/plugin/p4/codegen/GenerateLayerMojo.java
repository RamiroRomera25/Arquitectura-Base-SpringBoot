
package ar.edu.utn.frc.tup.plugin.p4.codegen;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * v0.2.0
 * mvn ar.edu.utn.frc.tup.p4:p4-codegen-plugin:0.2.0:generate-layer -Dname=Person -Dtype=Named -DbasePackage=ar.edu.utn.frc.tup.p4
 */
@Mojo(name = "generate-layer")
public class GenerateLayerMojo extends AbstractMojo {

    @Parameter(property = "name", required = true)
    private String name;

    @Parameter(property = "type", defaultValue = "Base")
    private String type; // Base | Named | Audit | Status | Temporal | Versioned

    @Parameter(property = "basePackage", defaultValue = "ar.edu.utn.frc.tup.p4")
    private String basePackage;

    @Parameter(property = "withTests", defaultValue = "true")
    private boolean withTests;

    @Parameter(property = "projectDir", defaultValue = "${project.basedir}")
    private File projectDir;

    private static class Triple {
        final String entityExt;
        final String dtoExt;
        final String repoExt;
        Triple(String e, String d, String r){ this.entityExt=e; this.dtoExt=d; this.repoExt=r; }
    }

    private final Map<String, Triple> map = new HashMap<>();

    public GenerateLayerMojo() {
        map.put("Base",      new Triple("BaseEntity<Long>",         "BaseDTO<Long>",            "GenericRepository"));
        map.put("Named",     new Triple("BaseNamedEntity<Long>",    "BaseNamedDTO<Long>",       "NamedEntityRepository"));
        map.put("Audit",     new Triple("BaseAuditEntity<Long>",    "BaseAuditDTO<Long>",       "GenericRepository"));
        map.put("Status",    new Triple("BaseStatusEntity<Long>",   "StatusDTO<Long>",          "StatusEntityRepository"));
        map.put("Temporal",  new Triple("BaseTemporalEntity<Long>", "BaseDTO<Long>",            "GenericRepository"));
        map.put("Versioned", new Triple("BaseVersionedEntity<Long>","BaseVersionedDTO<Long>",   "GenericRepository"));
    }

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        String entityName = capitalize(name);
        String dtoName = entityName + "DTO";
        String repoName = entityName + "Repository";
        String serviceName = entityName + "Service";
        String controllerName = entityName + "Controller";
        String translatorName = entityName + "Translator";

        Triple triple = map.getOrDefault(type, map.get("Base"));

        Path srcMainJava = projectDir.toPath().resolve("src/main/java");
        Path srcTestJava = projectDir.toPath().resolve("src/test/java");
        String pkgPath = basePackage.replace('.', '/');

        Path entitiesDir     = srcMainJava.resolve(pkgPath + "/entities");
        Path dtosDir         = srcMainJava.resolve(pkgPath + "/dtos");
        Path reposDir        = srcMainJava.resolve(pkgPath + "/repositories");
        Path servicesDir     = srcMainJava.resolve(pkgPath + "/services");
        Path controllersDir  = srcMainJava.resolve(pkgPath + "/controllers");
        Path translatorsDir  = srcMainJava.resolve(pkgPath + "/translators");
        Path diagramsDir     = projectDir.toPath().resolve("docs/diagrams");

        mkdirs(entitiesDir); mkdirs(dtosDir); mkdirs(reposDir);
        mkdirs(servicesDir); mkdirs(controllersDir); mkdirs(translatorsDir);
        mkdirs(diagramsDir);

        writeIfAbsent(entitiesDir.resolve(entityName + ".java"),
            entityTemplate(basePackage, entityName, triple.entityExt));
        writeIfAbsent(dtosDir.resolve(dtoName + ".java"),
            dtoTemplate(basePackage, dtoName, triple.dtoExt));
        writeIfAbsent(reposDir.resolve(repoName + ".java"),
            repositoryTemplate(basePackage, entityName, repoName, triple.repoExt));
        writeIfAbsent(servicesDir.resolve(serviceName + ".java"),
            serviceTemplate(basePackage, entityName, serviceName));
        writeIfAbsent(controllersDir.resolve(controllerName + ".java"),
            controllerTemplate(basePackage, entityName, serviceName, controllerName));
        writeIfAbsent(translatorsDir.resolve(translatorName + ".java"),
            translatorTemplate(basePackage, entityName, dtoName, translatorName));

        writeIfAbsent(diagramsDir.resolve(entityName + ".puml"),
            plantUmlTemplate(basePackage, entityName));

        if (withTests) {
            Path testBase = srcTestJava.resolve(pkgPath);
            mkdirs(testBase.resolve("controllers"));
            mkdirs(testBase.resolve("services"));
            writeIfAbsent(testBase.resolve("controllers/" + controllerName + "Test.java"),
                controllerTestTemplate(basePackage, controllerName));
            writeIfAbsent(testBase.resolve("services/" + serviceName + "Test.java"),
                serviceTestTemplate(basePackage, serviceName));
        }

        getLog().info("Layer generated for " + entityName + " (" + type + ") at " + LocalDateTime.now());
    }

    private static void mkdirs(Path p) throws MojoExecutionException {
        try { Files.createDirectories(p); }
        catch (IOException e) { throw new MojoExecutionException("Cannot create directory: " + p, e); }
    }

    private static void writeIfAbsent(Path file, String content) throws MojoExecutionException {
        try {
            if (Files.exists(file)) return;
            Files.writeString(file, content, StandardCharsets.UTF_8, StandardOpenOption.CREATE_NEW);
        } catch (IOException e) {
            throw new MojoExecutionException("Cannot write file: " + file, e);
        }
    }

    private static String capitalize(String s) {
        if (s == null || s.isEmpty()) return s;
        return s.substring(0,1).toUpperCase() + s.substring(1);
    }

    private static String entityTemplate(String pkg, String entity, String entityExt) {
        return ""
            + "package " + pkg + ".entities;\n\n"
            + "import jakarta.persistence.*;\n"
            + "import lombok.*;\n\n"
            + "@Entity\n"
            + "@Table(name = \"" + entity.toLowerCase() + "\")\n"
            + "@Getter\n@Setter\n@NoArgsConstructor\n@AllArgsConstructor\n@Builder\n"
            + "public class " + entity + " extends " + entityExt + " {\n"
            + "    // TODO: add fields\n"
            + "}\n";
    }

    private static String dtoTemplate(String pkg, String dto, String dtoExt) {
        return ""
            + "package " + pkg + ".dtos;\n\n"
            + "import lombok.*;\n"
            + "import ar.edu.utn.frc.tup.p4.dtos.common." + dtoExt.split("<")[0] + ";\n\n"
            + "@Getter\n@Setter\n@NoArgsConstructor\n@AllArgsConstructor\n@Builder\n"
            + "public class " + dto + " extends " + dtoExt + " {\n"
            + "    // TODO: add fields\n"
            + "}\n";
    }

    private static String repositoryTemplate(String pkg, String entity, String repo, String repoExt) {
        return ""
            + "package " + pkg + ".repositories;\n\n"
            + "import org.springframework.stereotype.Repository;\n"
            + "import " + pkg + ".entities." + entity + ";\n\n"
            + "@Repository\n"
            + "public interface " + repo + " extends " + repoExt + "<" + entity + ", Long> {\n"
            + "}\n";
    }

    private static String serviceTemplate(String pkg, String entity, String service) {
        return ""
            + "package " + pkg + ".services;\n\n"
            + "import org.springframework.stereotype.Service;\n"
            + "import " + pkg + ".entities." + entity + ";\n\n"
            + "@Service\n"
            + "public class " + service + " {\n"
            + "}\n";
    }

    private static String controllerTemplate(String pkg, String entity, String service, String controller) {
        String basePath = "/api/" + entity.toLowerCase() + "s";
        return ""
            + "package " + pkg + ".controllers;\n\n"
            + "import org.springframework.web.bind.annotation.*;\n"
            + "import ar.edu.utn.frc.tup.p4.services." + service + ";\n"
            + "import lombok.RequiredArgsConstructor;\n\n"
            + "@RestController\n"
            + "@RequestMapping(\"" + basePath + "\")\n"
            + "@RequiredArgsConstructor\n"
            + "public class " + controller + " {\n\n"
            + "    private final " + service + " service;\n\n"
            + "    // TODO: endpoints\n"
            + "}\n";
    }

    private static String translatorTemplate(String pkg, String entity, String dto, String translator) {
        return ""
            + "package " + pkg + ".translators;\n\n"
            + "import " + pkg + ".entities." + entity + ";\n"
            + "import " + pkg + ".dtos." + dto + ";\n\n"
            + "@Translator(dto = " + dto + ".class)\n"
            + "public class " + translator + " extends BaseTranslator<" + entity + ", " + dto + "> {\n"
            + "\n"
            + "    // TODO: Completar la logica de los metodos.\n"
            + "\n"
            + "    public PersonDTO toDto(Person entity) {\n"
            + "        return null;\n"
            + "    }\n"
            + "\n"
            + "    public Person toEntity(PersonDTO dto) {\n"
            + "        return null;\n"
            + "    }\n"
            + "}\n";
    }

    private static String plantUmlTemplate(String pkg, String entity) {
        return ""
            + "@startuml\n"
            + "skinparam dpi 150\n"
            + "package \"" + pkg + "\" {\n"
            + "  class " + entity + " <<Entity>>\n"
            + "  class " + entity + "DTO <<DTO>>\n"
            + "  class " + entity + "Repository <<Repository>>\n"
            + "  class " + entity + "Service <<Service>>\n"
            + "  class " + entity + "Controller <<Controller>>\n"
            + "  class " + entity + "Translator <<Translator>>\n"
            + "  " + entity + " --> " + entity + "DTO\n"
            + "  " + entity + " --> " + entity + "Repository\n"
            + "  " + entity + "Repository --> " + entity + "Service\n"
            + "  " + entity + "Service --> " + entity + "Controller\n"
            + "  " + entity + "DTO --> " + entity + "Translator\n"
            + "}\n"
            + "@enduml\n";
    }

    private static String controllerTestTemplate(String pkg, String controller) {
        return ""
            + "package " + pkg + ".controllers;\n\n"
            + "import org.junit.jupiter.api.Test;\n"
            + "import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;\n"
            + "@WebMvcTest(" + controller + ".class)\n"
            + "class " + controller + "Test {\n"
            + "  @Test void contextLoads() {}\n"
            + "}\n";
    }

    private static String serviceTestTemplate(String pkg, String service) {
        return ""
            + "package " + pkg + ".services;\n\n"
            + "import org.junit.jupiter.api.Test;\n"
            + "import org.springframework.boot.test.context.SpringBootTest;\n"
            + "@SpringBootTest\n"
            + "class " + service + "Test {\n"
            + "  @Test void contextLoads() {}\n"
            + "}\n";
    }
}
