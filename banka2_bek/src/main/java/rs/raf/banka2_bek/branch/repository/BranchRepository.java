package rs.raf.banka2_bek.branch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.raf.banka2_bek.branch.model.Branch;
import rs.raf.banka2_bek.branch.model.BranchType;

import java.util.List;

public interface BranchRepository extends JpaRepository<Branch, Long> {

    /**
     * Vraca branches filtrirano po opcionalnim kriterijumima:
     * - {@code type}: BRANCH / ATM / null (svi)
     * - {@code has24h}: true / null (ignorisi filter)
     * - {@code hasDriveThrough}: true / null
     * - {@code search}: case-insensitive name + address LIKE / null
     *
     * PG null-safe pattern sa {@code cast(:param as string) IS NULL OR ...}
     * koriscen u celom codebase-u (vidi CLAUDE.md Runda 24.04 — Hibernate
     * ne moze da zakljuci tip null parametra u boolean OR-u).
     */
    @Query("SELECT b FROM Branch b WHERE "
            + "(:type IS NULL OR b.type = :type) AND "
            + "(:has24h IS NULL OR b.has24h = :has24h) AND "
            + "(:hasDriveThrough IS NULL OR b.hasDriveThrough = :hasDriveThrough) AND "
            + "(cast(:search as string) IS NULL OR "
            + " LOWER(b.name) LIKE LOWER(CONCAT('%', cast(:search as string), '%')) OR "
            + " LOWER(b.address) LIKE LOWER(CONCAT('%', cast(:search as string), '%'))) "
            + "ORDER BY b.type ASC, b.name ASC")
    List<Branch> findByFilters(@Param("type") BranchType type,
                               @Param("has24h") Boolean has24h,
                               @Param("hasDriveThrough") Boolean hasDriveThrough,
                               @Param("search") String search);
}
