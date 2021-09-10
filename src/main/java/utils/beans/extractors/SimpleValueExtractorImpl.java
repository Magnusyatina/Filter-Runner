package utils.beans.extractors;

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
            Method method = beanAnalyzer.getMethod(inRoot.getClass(), currentFieldPath);
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

}