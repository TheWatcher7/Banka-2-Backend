package rs.raf.banka2_bek.branch.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rs.raf.banka2_bek.branch.dto.BranchResponseDto;
import rs.raf.banka2_bek.branch.model.BranchType;
import rs.raf.banka2_bek.branch.service.BranchService;

import java.util.List;

/**
 * Mapa lokacija — ekspoziture i bankomati. Pristupacno svim role-ovima
 * (authenticated u {@link rs.raf.banka2_bek.auth.config.GlobalSecurityConfig}).
 * Feature dodat 14.05.2026 vece-4 za KT3 demo.
 */
@Tag(name = "Branches", description = "Mapa lokacija: ekspoziture + bankomati")
@RestController
@RequestMapping("/branches")
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;

    @Operation(summary = "List branches",
            description = "Vraca filtrirana lokacija (BRANCH + ATM). Svi parametri su opcionalni.")
    @GetMapping
    public ResponseEntity<List<BranchResponseDto>> listBranches(
            @RequestParam(required = false) BranchType type,
            @RequestParam(required = false) Boolean has24h,
            @RequestParam(required = false) Boolean hasDriveThrough,
            @RequestParam(required = false) String search) {
        return ResponseEntity.ok(branchService.listBranches(type, has24h, hasDriveThrough, search));
    }
}
