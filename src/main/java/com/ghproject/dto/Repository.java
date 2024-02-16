package com.ghproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Repository {
	private int id;
	private Owner owner;
	private String name;
	private boolean fork;
}
