package utils.beans.extractors;

import filters.annotations.Represent;
import filters.converters.Converter;
import utils.beans.analyzer.BeanAnalyzer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SimpleValueExtractorImpl implements ValueExtractor {

    private BeanAnalyzer beanAnalyzer;

    public SimpleValueExtractorImpl() {
    }

    public SimpleValueExtractorImpl(BeanAnalyzer beanAnalyzer) {
        this.beanAnalyzer = beanAnalyzer;
    }

    @Override
    public void setBeanAnalyzer(BeanAnalyzer beanAnalyzer) {
        this.beanAnalyzer = beanAnalyzer;
    }

    @Override
    public BeanAnalyzer getBeanAnalyzer() {
        return beanAnalyzer;
    }

    @Override
    public Object getValue(Object inRoot, String fieldPath) {
        if (beanAnalyzer == null)
            return null;

        String[] elem = fieldPath.split("\\.");
        Object root = inRoot;

        for (int elemIndex = 0; elemIndex < elem.length; elemIndex++) {
            String currentFieldPath = elem[elemIndex];
            Method method = beanAnalyzer.getMethodByClass(inRoot.getClass(), currentFieldPath);
            if (method != null) {
                try {
                    root = method.invoke(root);

                    if (elemIndex == elem.length - 1 || root == null)
                        return root;

                    currentFieldPath += "." + elem[elemIndex + 1];
                } catch (IllegalAccessException | InvocationTargetException e) {
                    return null;
                }
            }
        }
        return null;
    }

    @Override
    public Object getValue(Object bean, String fieldName, Represent represent) {
        Object value = getValue(bean, fieldName);
        try {
            Converter converter = represent.using().newInstance();
            return converter.convert(represent, value);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
