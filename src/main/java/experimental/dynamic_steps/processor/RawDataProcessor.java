package experimental.dynamic_steps.processor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import experimental.dynamic_steps.entity.CommonRawDataFormat;
import experimental.dynamic_steps.entity.SourceSystemCSV;

@Component
public class RawDataProcessor implements ItemProcessor<SourceSystemCSV, List<CommonRawDataFormat>> {

    private Map<String, JdbcTemplate> jdbcTemplates;
    // this would always be the local db's jdbcTemplate
    // as it was marked primary in configuration
    private JdbcTemplate localDBJdbcTemplate;
    private static final String FIND_START_TIME = "SELECT DATE_RECEIVED from RAW_DATA where SOURCE_SYSTEM = ? ORDER BY DATE_RECEIVED DESC LIMIT 1";

    public RawDataProcessor(Map<String, JdbcTemplate> jdbcTemplates, JdbcTemplate localDBJdbcTemplate) {
        this.jdbcTemplates = jdbcTemplates;
        this.localDBJdbcTemplate = localDBJdbcTemplate;
    }

    @Override
    @Nullable
    public List<CommonRawDataFormat> process(@NonNull SourceSystemCSV item) throws Exception {
        JdbcTemplate targetDBJdbcTemplate = this.jdbcTemplates.get(item.region());
        // get the scan start time for the particular vendor from local db
        LocalDateTime scanStartTime = localDBJdbcTemplate.queryForObject(FIND_START_TIME, LocalDateTime.class,
                item.name());
        LocalDateTime scanEndTime = LocalDateTime.now();
        // check if the item is the real time vendors
        // do not use queryForList() other wise you will not be able to map
        if (item.name().equalsIgnoreCase("ESIS Claims") || item.name().equalsIgnoreCase("Claim Connect")) {
            return targetDBJdbcTemplate.query(item.query(),
                    (rs, rowNum) -> new CommonRawDataFormat(
                            rs.getInt("BATCH_ID"),
                            item.name(),
                            rs.getTimestamp("RECEIVED_DATE").toLocalDateTime(),
                            rs.getInt("BATCH_STATUS"),
                            rs.getInt("RECORD_COUNT"),
                            rs.getInt("SUCCESS_COUNT"),
                            rs.getInt("FAILED_COUNT"),
                            rs.getInt("DUPLICATE_COUNT"),
                            rs.getString("FILENET_GUID")),
                    scanStartTime, scanEndTime);
        } else {
            return targetDBJdbcTemplate.query(item.query(),
                    (rs, rowNum) -> new CommonRawDataFormat(
                            rs.getInt("POLICY_ID"),
                            item.name(),
                            rs.getTimestamp("DATE_ADDED").toLocalDateTime(),
                            rs.getString("APPDB_STATUS_CODE"),
                            rs.getString("FN_STATUS_CODE"),
                            rs.getInt("APP_DB_RETRY_COUNT"),
                            rs.getInt("FN_RETRY_COUNT")),
                    scanStartTime, scanEndTime);
        }
    }

}
