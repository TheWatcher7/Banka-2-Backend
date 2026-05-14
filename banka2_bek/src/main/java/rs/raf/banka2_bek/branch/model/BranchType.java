package rs.raf.banka2_bek.branch.model;

/**
 * Tip lokacije na mapi:
 * <ul>
 *   <li>{@link #BRANCH} — fizicka ekspozitura (filijala) sa zaposlenima, radno vreme</li>
 *   <li>{@link #ATM} — bankomat (moze biti 24h, drive-through, ili standard)</li>
 * </ul>
 *
 * Feature dodat 14.05.2026 — mapa Beograda za demo prezentaciju.
 */
public enum BranchType {
    BRANCH,
    ATM
}
