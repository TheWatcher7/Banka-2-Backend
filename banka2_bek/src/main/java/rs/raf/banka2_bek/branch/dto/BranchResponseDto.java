package rs.raf.banka2_bek.branch.dto;

import lombok.Builder;
import lombok.Data;
import rs.raf.banka2_bek.branch.model.BranchType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class BranchResponseDto {
    private Long id;
    private String name;
    private BranchType type;
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String openingHours;
    private Boolean has24h;
    private Boolean hasDriveThrough;
    private LocalDateTime createdAt;
}
