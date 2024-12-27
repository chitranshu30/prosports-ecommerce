package com.prosports.models;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AttributeDefinition {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long attributeId;

	private String attributeName;

	@Enumerated(EnumType.STRING)
	private AttributeType attributeType;

	private List<String> allowedValues;

}
