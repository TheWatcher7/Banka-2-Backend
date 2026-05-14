package rs.raf.banka2_bek.branch.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.raf.banka2_bek.branch.dto.BranchResponseDto;
import rs.raf.banka2_bek.branch.model.Branch;
import rs.raf.banka2_bek.branch.model.BranchType;
import rs.raf.banka2_bek.branch.repository.BranchRepository;
import rs.raf.banka2_bek.branch.service.implementation.BranchServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BranchServiceImplTest {

    @Mock
    private BranchRepository branchRepository;

    @InjectMocks
    private BranchServiceImpl branchService;

    private Branch branch1;
    private Branch atm1;

    @BeforeEach
    void setUp() {
        branch1 = Branch.builder()
                .id(1L).name("Banka 2 - Slavija").type(BranchType.BRANCH)
                .address("Trg Slavija 2").latitude(BigDecimal.valueOf(44.8023))
                .longitude(BigDecimal.valueOf(20.4668)).openingHours("08-16")
                .has24h(false).hasDriveThrough(false)
                .createdAt(LocalDateTime.now()).build();
        atm1 = Branch.builder()
                .id(2L).name("ATM Knez Mihailova").type(BranchType.ATM)
                .address("Knez Mihailova 15").latitude(BigDecimal.valueOf(44.816))
                .longitude(BigDecimal.valueOf(20.4565)).openingHours("00-24")
                .has24h(true).hasDriveThrough(false)
                .createdAt(LocalDateTime.now()).build();
    }

    @Test
    void listBranches_noFilters_returnsAll() {
        when(branchRepository.findByFilters(isNull(), isNull(), isNull(), isNull()))
                .thenReturn(List.of(branch1, atm1));

        List<BranchResponseDto> result = branchService.listBranches(null, null, null, null);

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("Banka 2 - Slavija");
        assertThat(result.get(1).getType()).isEqualTo(BranchType.ATM);
    }

    @Test
    void listBranches_filterByType_returnsOnlyMatching() {
        when(branchRepository.findByFilters(any(), isNull(), isNull(), isNull()))
                .thenReturn(List.of(branch1));

        List<BranchResponseDto> result = branchService.listBranches(BranchType.BRANCH, null, null, null);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getType()).isEqualTo(BranchType.BRANCH);
    }

    @Test
    void listBranches_filterByHas24hTrue_passesTrueToRepo() {
        when(branchRepository.findByFilters(isNull(), any(Boolean.class), isNull(), isNull()))
                .thenReturn(List.of(atm1));

        List<BranchResponseDto> result = branchService.listBranches(null, true, null, null);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getHas24h()).isTrue();
    }

    @Test
    void listBranches_filterByHas24hFalse_skipsFilter() {
        when(branchRepository.findByFilters(isNull(), isNull(), isNull(), isNull()))
                .thenReturn(List.of(branch1, atm1));

        branchService.listBranches(null, false, null, null);

        // Boolean.FALSE prosledjuje se kao null filter (skipni)
        verify(branchRepository).findByFilters(isNull(), isNull(), isNull(), isNull());
    }

    @Test
    void listBranches_blankSearchTreatedAsNull() {
        when(branchRepository.findByFilters(isNull(), isNull(), isNull(), isNull()))
                .thenReturn(List.of(branch1, atm1));

        branchService.listBranches(null, null, null, "   ");

        verify(branchRepository).findByFilters(isNull(), isNull(), isNull(), isNull());
    }

    @Test
    void listBranches_trimsSearchString() {
        when(branchRepository.findByFilters(isNull(), isNull(), isNull(), any(String.class)))
                .thenReturn(List.of(branch1));

        branchService.listBranches(null, null, null, "  Slavija  ");

        verify(branchRepository).findByFilters(isNull(), isNull(), isNull(), org.mockito.ArgumentMatchers.eq("Slavija"));
    }
}
