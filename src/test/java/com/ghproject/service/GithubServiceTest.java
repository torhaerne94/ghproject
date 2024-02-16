package com.ghproject.service;

import com.ghproject.dto.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.when;

public class GithubServiceTest {
	private static GithubService githubService;

	@Mock
	private static WebClient webClient = Mockito.mock();

	@BeforeAll
	public static void init() {
		githubService = new GithubService(webClient);
	}

	@Test
	public void shouldReturnNotForkRepositories() {
		String username = "username1";
		String notForkRepoName = "rep1";

		Owner owner = new Owner("login");
		Repository r1 = Repository.builder().id(1).fork(false).name(notForkRepoName).owner(owner).build();
		Repository r2 = Repository.builder().id(2).fork(true).name("rep2").owner(owner).build();
		Repository r3 = Repository.builder().id(3).fork(true).name("rep3").owner(owner).build();
		Repository[] allRepositories = new Repository[3];
		allRepositories[0] = r1;
		allRepositories[1] = r2;
		allRepositories[2] = r3;

		String branch1Name = "b1n";
		String branch2Name = "b2n";
		Branch b1 = new Branch(branch1Name, new Commit("sha1"));
		Branch b2 = new Branch(branch2Name, new Commit("sha2"));
		Branch[] branches = new Branch[2];
		branches[0] = b1;
		branches[1] = b2;

		final var uriSpecMock = Mockito.mock(WebClient.RequestHeadersUriSpec.class);
		final var headersSpecMock = Mockito.mock(WebClient.RequestHeadersSpec.class);
		final var responseSpecMock = Mockito.mock(WebClient.ResponseSpec.class);

		when(webClient.get()).thenReturn(uriSpecMock);
		when(uriSpecMock.uri(Mockito.any(Function.class))).thenReturn(headersSpecMock);
		when(headersSpecMock.header(notNull(), notNull())).thenReturn(headersSpecMock);
		when(headersSpecMock.headers(notNull())).thenReturn(headersSpecMock);
		when(headersSpecMock.retrieve()).thenReturn(responseSpecMock);
		when(responseSpecMock.onStatus(Mockito.any(Predicate.class), Mockito.any(Function.class)))
				.thenReturn(responseSpecMock);
		when(responseSpecMock.bodyToMono(Repository[].class)).thenReturn(Mono.just(allRepositories));
		when(responseSpecMock.bodyToMono(Branch[].class)).thenReturn(Mono.just(branches));


		List<Response> responses = githubService.getData(username);

		assertEquals(1, responses.size());
		assertEquals(notForkRepoName, responses.getFirst().getRepositoryName());
		assertEquals(owner.getLogin(), responses.getFirst().getOwnerLogin());
		assertEquals(2, responses.getFirst().getBranchesCommit().size());
		assertEquals(branch1Name, responses.getFirst().getBranchesCommit().getFirst().getName());
		assertEquals(branch2Name, responses.getFirst().getBranchesCommit().getLast().getName());
	}

}