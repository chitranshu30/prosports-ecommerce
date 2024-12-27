package com.prosports.dtos;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductTypeResponseDto {

	private String productTypeName;
	private String productTypeDescription;
	private List<AttributeDefinitionResponceDto> attributes = new ArrayList<>();

}
