package utils.beans.extractors;

import filters.annotations.Represent;
import filters.converters.Converter;
import utils.beans.analyzer.BeanAnalyzer;

/**
 * Provides interface for obtaining object data
 */
public interface ValueExtractor {

    /**
     * @param beanAnalyzer BeanAnalyzer to be set
     */
    void setBeanAnalyzer(BeanAnalyzer beanAnalyzer);

    BeanAnalyzer getBeanAnalyzer();

    /**
     * @param inRoot citizen object to explore
     * @param field  incoming property from json
     * @return String value
     */
    Object getValue(Object inRoot, String field);

    Object getValue(Object bean, String fieldName, Represent represent);
}
