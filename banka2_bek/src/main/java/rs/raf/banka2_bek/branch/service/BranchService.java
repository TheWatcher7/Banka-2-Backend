package rs.raf.banka2_bek.branch.service;

import rs.raf.banka2_bek.branch.dto.BranchResponseDto;
import rs.raf.banka2_bek.branch.model.BranchType;

import java.util.List;

public interface BranchService {
    List<BranchResponseDto> listBranches(BranchType type, Boolean has24h, Boolean hasDriveThrough, String search);
}
