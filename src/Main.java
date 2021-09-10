import filters.FilterExecutor;
import filters.Operator;
import filters.expressions.Expression;
import filters.expressions.ExpressionManagerImpl;
import utils.beans.anylyzers.BeanAnalyzer;
import utils.beans.anylyzers.BeanAnalyzerImpl;
import utils.beans.anylyzers.secure.SecureAgentStub;
import utils.beans.extractors.SimpleValueExtractorImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws ParseException {
        List<Mark> marks = new LinkedList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        marks.add(new Mark(5, simpleDateFormat.parse("2018-10-11"), simpleDateFormat.parse("2020-10-11")));
//        marks.add(new Mark(3, simpleDateFormat.parse("2018-10-11"), simpleDateFormat.parse("2019-10-11")));
//        marks.add(new Mark(4, simpleDateFormat.parse("2016-10-11"), simpleDateFormat.parse("2018-10-11")));
//        marks.add(new Mark(4, simpleDateFormat.parse("2015-10-11"), simpleDateFormat.parse("2020-10-11")));
//        marks.add(new Mark(3, simpleDateFormat.parse("2018-10-11"), simpleDateFormat.parse("2020-10-11")));
//        marks.add(new Mark(2, simpleDateFormat.parse("2018-10-11"), simpleDateFormat.parse("2020-10-11")));

        marks.add(new Mark(5.0, "2018-10-11", "2020-10-11"));
        marks.add(new Mark(5.0, "2018-10-11", "2019-10-11"));
        marks.add(new Mark(5.0, "2016-10-11", "2018-10-11"));
        marks.add(new Mark(5.0, "2015-10-11", "2020-10-11"));
        marks.add(new Mark(5.0, "2018-10-11", "2020-10-11"));
        marks.add(new Mark(5.0, "2018-10-11", "2020-10-11"));

        List<Expression> expressions = new LinkedList<>();
//        expressions.add(new Expression("startDate", Operator.LESS_THAN, simpleDateFormat.parse("2018-10-11")));
//        expressions.add(new Expression("startDate", Operator.BIGGER_THAN, simpleDateFormat.parse("2016-01-01")));
        expressions.add(new Expression("mark", Operator.IN, new Double[] {3.0, 4.0, 5.0}));
        expressions.add(new Expression("endDate", Operator.LESS_THAN, simpleDateFormat.parse("2020-01-01")));
        BeanAnalyzerImpl beanAnalyzer = new BeanAnalyzerImpl(new SecureAgentStub());
        configureBeanAnalyzer(beanAnalyzer, expressions);
        FilterExecutor filterExecutor = new FilterExecutor(new SimpleValueExtractorImpl(beanAnalyzer),
                new ExpressionManagerImpl(), (List<Object>) (List<?>) marks);
        List<Object> filteredBeans = filterExecutor.filter(expressions);
        System.out.println("");
    }

    public static void configureBeanAnalyzer(BeanAnalyzer beanAnalyzer, List<Expression> expressions) {
        for(Expression expression : expressions)
            beanAnalyzer.configureMethods(Mark.class, Arrays.asList(expression.getFieldPath()));
    }

    public static class Mark {

        private double mark;

        private String startDate;

        private String endDate;


        public Mark(double mark) {
            this.mark = mark;
        }

        public Mark(double mark, String startDate, String endDate) {
            this.mark = mark;
            this.startDate = startDate;
            this.endDate = endDate;
        }

        public double getMark() {
            return mark;
        }

        public void setMark(double mark) {
            this.mark = mark;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }
    }
}
