import com.fasterxml.jackson.databind.ObjectMapper;
import filters.FilterExecutor;
import filters.Operator;
import filters.expressions.Expression;
import filters.expressions.ExpressionManagerImpl;
import models.Discipline;
import models.Mark;
import org.junit.Test;
import utils.beans.analyzer.BeanAnalyzerImpl;
import utils.beans.analyzer.secure.SecureAgentStub;
import utils.beans.extractors.SimpleValueExtractorImpl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

public class MainTest {

    private BeanAnalyzerImpl beanAnalyzer = new BeanAnalyzerImpl(new SecureAgentStub());

    @Test
    public void testJackson() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Expression expression = objectMapper.readValue("{ \"fieldPath\":\"sssss\", \"operator\":{ \"operator\":\"IN\"}, \"value\":1}", Expression.class);
        System.out.println("OK");
    }

    @Test
    public void checkNotRecursiveFiltrating() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<Mark> marks = new LinkedList<>();
        marks.add(new Mark(5.0, "2018-10-11", "2020-10-11"));
        marks.add(new Mark(5.0, "2018-10-11", "2019-10-11"));
        marks.add(new Mark(5.0, "2016-10-11", "2018-10-11"));
        marks.add(new Mark(5.0, "2015-10-11", "2020-10-11"));
        marks.add(new Mark(5.0, "2018-10-11", "2020-10-11"));
        marks.add(new Mark(5.0, "2018-10-11", "2020-10-11"));
        List<Expression> expressions = new LinkedList<>();
        expressions.add(new Expression("startDate", Operator.LESS_THAN, simpleDateFormat.parse("2018-10-11")));
//        expressions.add(new Expression("startDate", Operator.BIGGER_THAN, simpleDateFormat.parse("2016-01-01")));
        FilterExecutor filterExecutor = new FilterExecutor(new SimpleValueExtractorImpl(beanAnalyzer),
                new ExpressionManagerImpl(), (List<Object>) (List<?>) marks);
        List<Object> filteredBeans = filterExecutor.filter(expressions);
        System.out.println("OK");
    }

    @Test
    public void checkRecursiveFiltrating() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<Discipline> disciplines = new LinkedList<>();
        disciplines.add(new Discipline(new Mark(5.0, "2018-10-11", "2020-10-11")));
        disciplines.add(new Discipline(new Mark(5.0, "2018-10-11", "2019-10-11")));
        disciplines.add(new Discipline(new Mark(5.0, "2016-10-11", "2018-10-11")));
        disciplines.add(new Discipline(new Mark(5.0, "2015-10-11", "2020-10-11")));
        disciplines.add(new Discipline(new Mark(5.0, "2018-10-11", "2020-10-11")));
        disciplines.add(new Discipline(new Mark(5.0, "2018-10-11", "2020-10-11")));
        List<Expression> expressions = new LinkedList<>();
        expressions.add(new Expression("mark.mark", Operator.IN, new Double[] {3.0, 4.0, 5.0}));
        expressions.add(new Expression("mark.endDate", Operator.LESS_THAN, simpleDateFormat.parse("2022-01-01")));
        FilterExecutor filterExecutor = new FilterExecutor(new SimpleValueExtractorImpl(beanAnalyzer),
                new ExpressionManagerImpl(), (List<Object>) (List<?>) disciplines);
        List<Object> filteredBeans = filterExecutor.filter(expressions);
        System.out.println("OK");
    }

}
