package ar.edu.utn.frc.tup.p4.translators;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class TranslatorFactory {

    private static final Map<Class<?>, BaseTranslator<?, ?>> entityToTranslator = new ConcurrentHashMap<>();
    private static final Map<Class<?>, BaseTranslator<?, ?>> dtoToTranslator = new ConcurrentHashMap<>();

    private final ApplicationContext context;

    @PostConstruct
    public void initialize() {
        // Buscar todos los beans anotados con @Translator
        Map<String, Object> beans = context.getBeansWithAnnotation(Translator.class);

        for (Object bean : beans.values()) {
            if (bean instanceof BaseTranslator<?, ?> translator) {
                Translator annotation = bean.getClass().getAnnotation(Translator.class);
                entityToTranslator.put(annotation.entity(), translator);
                dtoToTranslator.put(annotation.dto(), translator);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <E, D> BaseTranslator<E, D> getByEntity(Class<E> entityClass) {
        return (BaseTranslator<E, D>) entityToTranslator.get(entityClass);
    }

    @SuppressWarnings("unchecked")
    public static <E, D> BaseTranslator<E, D> getByDto(Class<D> dtoClass) {
        return (BaseTranslator<E, D>) dtoToTranslator.get(dtoClass);
    }

    public static boolean hasEntity(Class<?> entityClass) {
        return entityToTranslator.containsKey(entityClass);
    }

    public static boolean hasDto(Class<?> dtoClass) {
        return dtoToTranslator.containsKey(dtoClass);
    }
}
