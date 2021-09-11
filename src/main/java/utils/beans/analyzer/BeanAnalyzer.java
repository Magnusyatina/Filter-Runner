package utils.beans.analyzer;

import utils.beans.analyzer.secure.SecureAgent;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * Bean Analyzer provides Java Bean analyze for obtaining all required getters
 * according to secure policy
 */
public interface BeanAnalyzer {

    /**
     * Configures methods map for given fields according hierarchy
     *
     * @param root   original element, from which starts methods configure.
     *               Root element must be Bean
     * @param fields Configure methods by given fields
     */
    void configureMethods(Class<?> root, List<String> fields);

    /**
     * Register methods
     *
     * @param rootJavaBean original element, from which starts methods register
     *                     Root element must be Bean
     * @param field        Register methods by given fields
     * @return Map<Full method name, Method>
     */
    Map<String, Method> registerMethod(Class<?> rootJavaBean, String field);

    /**
     * Assigning secure agent to bean analyzer
     *
     * @param secureAgent -- implementation of SecureAgent which check access allowance to the field
     */
    void setSecureAgent(SecureAgent secureAgent);

    /**
     * Get method by root and field
     *
     * @param root  original element, from which starts methods register. Root element must be Bean
     * @param field path to object field related to root
     * @return Method
     */
    Method getMethod(Class<?> root, String field);

    Map<String, Method> getMethods();

    Method getMethodByClass(Class<?> bean, String field);
}
