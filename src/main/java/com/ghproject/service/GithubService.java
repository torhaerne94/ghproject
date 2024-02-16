package com.ghproject.service;

import com.ghproject.dto.Branch;
import com.ghproject.dto.BranchCommitSha;
import com.ghproject.dto.Repository;
import com.ghproject.dto.Response;
import com.ghproject.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GithubService {
	private final String reposURI = "/users/{username}/repos";
	private final String branchesURI = "/repos/{username}/{repository}/branches";

	private final WebClient webClient;

	public List<Response> getData(String username) {
		List<Response> response = new ArrayList<>();
		Repository[] allRepositories = getUserRepositories(username);
		List<Repository> notForkRepositories = filterNotForkRepositories(allRepositories);

		for (Repository repository : notForkRepositories) {
			Response responseTemp = Response.builder()
					.repositoryName(repository.getName())
					.ownerLogin(repository.getOwner().getLogin())
					.branchesCommit(new ArrayList<>()).build();
			for (Branch branch : getRepositoryBranches(username, repository.getName())) {
				responseTemp.getBranchesCommit().add(new BranchCommitSha(branch.getName(), branch.getCommit().getSha()));
			}
			response.add(responseTemp);
		}
		return response;
	}

	private List<Repository> filterNotForkRepositories(Repository[] allRepositories) {
		return Arrays.stream(allRepositories).filter(e -> !e.isFork()).collect(Collectors.toList());
	}

	private Repository[] getUserRepositories(String username) {
		return webClient.get()
				.uri(uriBuilder -> uriBuilder.path(reposURI).build(username))
				.retrieve()
				.onStatus(HttpStatusCode::isError, this::handleError)
				.bodyToMono(Repository[].class)
				.block();
	}

	private Branch[] getRepositoryBranches(String username, String repository) {
		return webClient.get()
				.uri(uriBuilder -> uriBuilder.path(branchesURI).build(username, repository))
				.retrieve()
				.bodyToMono(Branch[].class)
				.block();
	}

	private Mono<RuntimeException> handleError(ClientResponse clientResponse) {
		return switch (clientResponse.statusCode()) {
			case HttpStatus.NOT_FOUND ->
					Mono.just(new ResourceNotFoundException(HttpStatus.NOT_FOUND, "user not exists"));
			default -> throw new IllegalStateException("Unexpected value: " + clientResponse.statusCode().value());
		};
	}

}
