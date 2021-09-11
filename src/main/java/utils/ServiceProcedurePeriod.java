package utils;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class ServiceProcedurePeriod {
    
    private DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("MMMM yyyy");
    private YearMonth period;
    
    public ServiceProcedurePeriod(YearMonth period) {
        this.period = period;
    }

    public void setPeriod(YearMonth period) {
        this.period = period;
    }

    public YearMonth getPeriod() {
        return period;
    }
    
    @Override
    public String toString() {
        return period.format(outputFormat);
    }
    
}
