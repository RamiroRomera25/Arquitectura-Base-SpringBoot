package ar.edu.utn.frc.tup.p4.translators;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Registro centralizado de traductores.
 * Permite obtener instancias concretas de traductores que extienden BaseTranslator.
 *
 * Ejemplo:
 *   StatusTranslator translator = TranslatorFactory.getTranslator(StatusTranslator.class);
 */
@Component
@RequiredArgsConstructor
public class TranslatorFactory {

    private static final Map<Class<? extends BaseTranslator<?, ?>>, BaseTranslator<?, ?>> translatorByClass = new ConcurrentHashMap<>();

    private final ApplicationContext context;

    @PostConstruct
    @SuppressWarnings("unchecked")
    public void initialize() {
        Map<String, Object> beans = context.getBeansWithAnnotation(Translator.class);

        for (Object bean : beans.values()) {
            if (bean instanceof BaseTranslator<?, ?> translator) {
                translatorByClass.put((Class<? extends BaseTranslator<?, ?>>) translator.getClass(), translator);
            }
        }
    }

    /**
     * Obtiene un traductor concreto por su clase.
     *
     * @param clazz Clase concreta del traductor (ej. StatusTranslator.class)
     * @return Instancia del traductor, o lanza excepción si no existe
     */
    @SuppressWarnings("unchecked")
    public static <T extends BaseTranslator<?, ?>> T getTranslator(Class<T> clazz) {
        BaseTranslator<?, ?> translator = translatorByClass.get(clazz);
        if (translator == null) {
            throw new IllegalArgumentException("No se encontró traductor registrado para clase: " + clazz.getName());
        }
        return (T) translator;
    }

    /**
     * Verifica si hay un traductor registrado para una clase concreta.
     */
    public static boolean hasTranslator(Class<? extends BaseTranslator<?, ?>> clazz) {
        return translatorByClass.containsKey(clazz);
    }

    /**
     * Devuelve todos los traductores registrados.
     */
    public static Map<Class<? extends BaseTranslator<?, ?>>, BaseTranslator<?, ?>> getAll() {
        return Map.copyOf(translatorByClass);
    }
}
