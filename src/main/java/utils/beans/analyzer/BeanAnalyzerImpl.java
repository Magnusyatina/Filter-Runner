package utils.beans.analyzer;

import utils.beans.analyzer.secure.SecureAgent;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanAnalyzerImpl implements BeanAnalyzer {

    private SecureAgent secureAgent;

    private Map<String, Method> methods = new HashMap<>();

    private Map<Class<?>, Map<String, Method>> classMethods = new HashMap<>();


    public BeanAnalyzerImpl() {
    }

    public BeanAnalyzerImpl(SecureAgent secureAgent) {
        this.secureAgent = secureAgent;
    }

    @Override
    public Map<String, Method> getMethods() {
        return this.methods;
    }

    @Override
    public void configureMethods(Class<?> root, List<String> fields) {
        if (secureAgent == null)
            return;

        for (String field : fields) {
            registerMethod(root, field);
        }

    }

    @Override
    public Map<String, Method> registerMethod(Class<?> rootJavaBean, String field) {
        //removes metainfo from property
        //for example name[meta:info].value will be replaced with name.value
        String regExReplace = "\\[([0-9A-z\\D]*)\\]";
        field = field.replaceAll(regExReplace, "");
        if (!secureAgent.check(field))
            return methods;
        Class<?> path = rootJavaBean;
        String[] elems = field.split("\\.");
        String key = elems[0];
        for (int elemIndex = 0; elemIndex < elems.length; elemIndex++) {
            try {

                PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(path).getPropertyDescriptors();

                for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                    if (propertyDescriptor.getName().equals(elems[elemIndex])) {
                        Method readMethod = propertyDescriptor.getReadMethod();
                        methods.put(key, readMethod);
                        Map<String, Method> classMethods = this.classMethods.get(path);
                        if(classMethods == null) {
                            classMethods = new HashMap<>();
                            this.classMethods.put(path, classMethods);
                        }
                        classMethods.put(elems[elemIndex], readMethod);

                        Class<?> propertyType = propertyDescriptor.getPropertyType();
                        if (Collection.class.isAssignableFrom(propertyType)) {
                            //if Collection
                            path = (Class<?>) ((ParameterizedType) readMethod
                                    .getGenericReturnType())
                                    .getActualTypeArguments()[0];
                        } else if (Map.class.isAssignableFrom(propertyType)) {
                            //if Map
                            path = (Class<?>) ((ParameterizedType) readMethod
                                    .getGenericReturnType())
                                    .getActualTypeArguments()[1];
                        } else {
                            path = propertyType;
                        }

                        if (elemIndex == elems.length - 1)
                            break;
                        key += "." + elems[elemIndex + 1];
                        continue;
                    }
                }
            } catch (IntrospectionException e) {
            }
        }
        return methods;
    }

    @Override
    public void setSecureAgent(SecureAgent secureAgent) {
        this.secureAgent = secureAgent;
    }

    @Override
    public Method getMethod(Class<?> root, String field) {
        if (secureAgent == null)
            return null;

        Method method = methods.get(field);
        if (method != null)
            return method;
        registerMethod(root, field);
        return methods.get(field);
    }

    @Override
    public Method getMethodByClass(Class<?> bean, String field) {
        if(secureAgent == null)
            return null;

        Map<String, Method> methods = classMethods.get(bean);
        if(methods == null)
            return null;
        return methods.get(field);
    }

    public static BeanAnalyzer create(SecureAgent secureAgent) {
        return new BeanAnalyzerImpl(secureAgent);
    }
}

