package experimental.dynamic_steps.entity;

public record SourceSystemCSV(
    String name, 
    String region, 
    String query, 
    Boolean appDb, 
    Boolean filenet
) {
}
