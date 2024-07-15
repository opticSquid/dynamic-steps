package experimental.dynamic_steps.writer;

import java.util.List;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.NonNull;

import experimental.dynamic_steps.entity.CommonRawDataFormat;

@Configuration
public class RawDataWriter implements ItemWriter<CommonRawDataFormat> {
    private static final String INSERT_DATA = "INSERT INTO RAW_DATA (RECORD_ID, SOURCE_SYSTEM, DATE_RECEIVED, APPDB_STATUS_CODE, FILENET_STATUS_CODE, APPDB_RETRY_COUNT, FILENET_RETRY_COUNT, BATCH_STATUS, RECORD_COUNT, SUCCESS_COUNT, FAILURE_COUNT, DUPLICATE_COUNT, FILENET_GUID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?);";
    // this is local db's jdbc template
    JdbcTemplate jdbcTemplate;

    public RawDataWriter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void write(@NonNull Chunk<? extends CommonRawDataFormat> chunk) throws Exception {
        List<? extends CommonRawDataFormat> items = chunk.getItems();
        int[] batchUpdate = jdbcTemplate.batchUpdate(INSERT_DATA, items, (ps, item) -> {
            // Set prepared statement parameters based on item fields
            ps.setString(1, item.getField1()); // Assuming field1
            ps.setInt(2, item.getField2()); // Assuming field2
            // ... Set values for other columns
        });
        // Check for successful writes (optional)
        int updatedCount = batchUpdate.length;
        if (updatedCount != items.size()) {
            // Handle potential errors during batch update
            // (log warnings, retry logic etc.)
        }

    }

}
