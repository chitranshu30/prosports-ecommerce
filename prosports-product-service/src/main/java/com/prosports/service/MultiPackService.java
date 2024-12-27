package com.prosports.service;

import java.util.List;

import com.prosports.dtos.MultiPackRequestDto;
import com.prosports.dtos.MultiPackResponseDto;

public interface MultiPackService {

	MultiPackResponseDto createMultiPack(MultiPackRequestDto multiPackRequestDto);

	List<MultiPackResponseDto> findAllMultiPack();

}