package rs.raf.banka2_bek.branch.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Lokacija na mapi — ekspozitura ili bankomat. Fake demo podaci u seed.sql.
 *
 * Lat/lon u WGS84 (standard GPS). Beograd centar ~44.787 N, 20.457 E.
 * Spread ±0.04° = ~5km radijus pokrivanja centralnih opstina.
 */
@Entity
@Table(name = "branches", indexes = {
        @Index(name = "ix_branches_type", columnList = "type"),
        @Index(name = "ix_branches_has24h", columnList = "has_24h")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private BranchType type;

    @Column(nullable = false, length = 200)
    private String address;

    /** Geografska sirina (latitude) — WGS84, precision 9 (npr. 44.787123). */
    @Column(nullable = false, precision = 9, scale = 6)
    private BigDecimal latitude;

    /** Geografska duzina (longitude) — WGS84. */
    @Column(nullable = false, precision = 9, scale = 6)
    private BigDecimal longitude;

    /** Radno vreme (free-text, npr. "08-16 radnim danima" ili "00-24"). */
    @Column(nullable = false, length = 100)
    private String openingHours;

    /** Ima li ATM 24-casovni pristup (samo za type=ATM). */
    @Column(name = "has_24h", nullable = false)
    @org.hibernate.annotations.ColumnDefault("0")
    @Builder.Default
    private Boolean has24h = false;

    /** Ima li ATM drive-through prilaz (samo za type=ATM). */
    @Column(name = "has_drive_through", nullable = false)
    @org.hibernate.annotations.ColumnDefault("0")
    @Builder.Default
    private Boolean hasDriveThrough = false;

    @Column(nullable = false, updatable = false)
    @org.hibernate.annotations.ColumnDefault("CURRENT_TIMESTAMP")
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}
