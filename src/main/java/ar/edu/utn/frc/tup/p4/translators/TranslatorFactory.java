package ar.edu.utn.frc.tup.p4.translators;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Registro centralizado de traductores.
 * Permite obtener traductores por clase concreta o por tipo de DTO manejado.
 */
@Component
@RequiredArgsConstructor
@Log4j2
public class TranslatorFactory {

    private static final Map<Class<?>, BaseTranslator<?, ?>> dtoToTranslator = new ConcurrentHashMap<>();
    private static final Map<Class<? extends BaseTranslator<?, ?>>, BaseTranslator<?, ?>> translatorByClass = new ConcurrentHashMap<>();

    private final ApplicationContext context;

    @PostConstruct
    @SuppressWarnings("unchecked")
    public void initialize() {
        Map<String, Object> beans = context.getBeansWithAnnotation(Translator.class);

        for (Object bean : beans.values()) {
            if (bean instanceof BaseTranslator<?, ?> translator) {
                Class<? extends BaseTranslator<?, ?>> clazz = (Class<? extends BaseTranslator<?, ?>>) translator.getClass();
                Translator annotation = clazz.getAnnotation(Translator.class);
                if (!dtoToTranslator.containsKey(annotation.dto())) {
                    dtoToTranslator.put(annotation.dto(), translator);
                } else {
                    log.warn("A translator can't be register on dtoToTranslator registry cause is duplicated.");
                }
                translatorByClass.put(clazz, translator);
            }
        }
    }

    /**
     * Obtiene un traductor por su clase de DTO.
     */
    @SuppressWarnings("unchecked")
    public static <E, D> BaseTranslator<E, D> getTranslator(Class<D> dtoClass) {
        BaseTranslator<?, ?> translator = dtoToTranslator.get(dtoClass);
        if (translator == null) {
            throw new IllegalArgumentException("No se encontró traductor para DTO: " + dtoClass.getName());
        }
        return (BaseTranslator<E, D>) translator;
    }

    /**
     * Obtiene un traductor concreto por su clase.
     */
    @SuppressWarnings("unchecked")
    public static <T extends BaseTranslator<?, ?>> T getTranslatorClass(Class<T> clazz) {
        BaseTranslator<?, ?> translator = translatorByClass.get(clazz);
        if (translator == null) {
            throw new IllegalArgumentException("No se encontró traductor registrado para clase: " + clazz.getName());
        }
        return (T) translator;
    }

    /**
     * Verifica si existe traductor para un DTO.
     */
    public static boolean hasTranslator(Class<?> dtoClass) {
        return dtoToTranslator.containsKey(dtoClass);
    }

    /**
     * Verifica si existe un traductor concreto registrado.
     */
    public static boolean hasTranslatorClass(Class<? extends BaseTranslator<?, ?>> clazz) {
        return translatorByClass.containsKey(clazz);
    }
}
