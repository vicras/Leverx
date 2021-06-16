package com.vicras.service;

import com.vicras.dto.CatDto;

import java.util.List;

/**
 * @author viktar hraskou
 */
public interface CatService {

    List<CatDto> getAll();

    List<CatDto> getById(Long id);

    void updateCat(CatDto catDto);

    void addCat(CatDto catDto);

    void removeCatById(Long id);
}
