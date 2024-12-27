package com.prosports.dtos;

import java.util.List;

import com.prosports.models.AttributeType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AttributeDefinitionResponceDto {

	private String attributeName;

	@Enumerated(EnumType.STRING)
	private AttributeType attributeType;

	private List<String> allowedValues;

	
}
