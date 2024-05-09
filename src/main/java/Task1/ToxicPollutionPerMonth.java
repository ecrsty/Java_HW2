package Task1;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDate;
import java.util.List;

public class ToxicPollutionPerMonth implements Serializable {
    private Map<LocalDate, List<Integer>> toxicPollutionPerMonth;

    // Данные о количестве выбросов за день в течение месяца
    public ToxicPollutionPerMonth(Map<LocalDate, List<Integer>> toxicPollutionPerMonth) {
        this.toxicPollutionPerMonth = toxicPollutionPerMonth;
    }

    public Map<LocalDate, List<Integer>> getToxicPollutionPerMonth() {
        return toxicPollutionPerMonth;
    }

    // Словарь для статистических данныхх, которые будут получены в результате обработки на сервере
    private Map<String, Double> processedData = new HashMap<>();

    public Map<String, Double> getProcessedData() {
        return processedData;
    }

    public void setProcessedData(Map<String, Double> processedData) {
        this.processedData = processedData;
    }
}
