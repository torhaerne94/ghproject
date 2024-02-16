package com.ghproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Response {
	private String repositoryName;
	private String ownerLogin;
	private List<BranchCommitSha> branchesCommit;
}
