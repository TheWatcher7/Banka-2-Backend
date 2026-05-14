package rs.raf.banka2_bek.branch.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.raf.banka2_bek.branch.dto.BranchResponseDto;
import rs.raf.banka2_bek.branch.model.Branch;
import rs.raf.banka2_bek.branch.model.BranchType;
import rs.raf.banka2_bek.branch.repository.BranchRepository;
import rs.raf.banka2_bek.branch.service.BranchService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;

    @Override
    @Transactional(readOnly = true)
    public List<BranchResponseDto> listBranches(BranchType type, Boolean has24h, Boolean hasDriveThrough, String search) {
        String normalizedSearch = (search == null || search.isBlank()) ? null : search.trim();
        // has24h i hasDriveThrough samo prosledjujemo true/null — false znaci "skipni filter"
        Boolean has24hFilter = Boolean.TRUE.equals(has24h) ? Boolean.TRUE : null;
        Boolean hasDriveThroughFilter = Boolean.TRUE.equals(hasDriveThrough) ? Boolean.TRUE : null;
        return branchRepository.findByFilters(type, has24hFilter, hasDriveThroughFilter, normalizedSearch).stream()
                .map(this::toResponse)
                .toList();
    }

    private BranchResponseDto toResponse(Branch b) {
        return BranchResponseDto.builder()
                .id(b.getId())
                .name(b.getName())
                .type(b.getType())
                .address(b.getAddress())
                .latitude(b.getLatitude())
                .longitude(b.getLongitude())
                .openingHours(b.getOpeningHours())
                .has24h(b.getHas24h())
                .hasDriveThrough(b.getHasDriveThrough())
                .createdAt(b.getCreatedAt())
                .build();
    }
}
