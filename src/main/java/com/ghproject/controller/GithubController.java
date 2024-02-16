package com.ghproject.controller;

import com.ghproject.dto.Response;
import com.ghproject.service.GithubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class GithubController {
	private final GithubService githubService;

	@GetMapping("/{username}/repositories")
	@ResponseStatus(HttpStatus.OK)
	public List<Response> getData(@PathVariable(name = "username") String username) {
		return githubService.getData(username);
	}

}
