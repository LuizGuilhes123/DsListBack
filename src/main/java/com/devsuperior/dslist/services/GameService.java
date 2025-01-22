package com.devsuperior.dslist.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import com.devsuperior.dslist.dto.GameDTO;
import com.devsuperior.dslist.dto.GameMinDTO;
import com.devsuperior.dslist.entities.Game;
import com.devsuperior.dslist.projections.GameMinProjection;
import com.devsuperior.dslist.repositories.GameRepository;

@Service
public class GameService {

	@Autowired
	private GameRepository gameRepository;

	@Transactional(readOnly = true)
	public GameDTO findById(@PathVariable Long listId) {
		Game result = gameRepository.findById(listId).orElseThrow(() -> new RuntimeException("Game not found"));

		GameDTO dto = new GameDTO();
		dto.setId(result.getId());
		dto.setTitle(result.getTitle());
		dto.setYear(result.getYear());
		dto.setGenre(result.getGenre());
		dto.setPlatforms(result.getPlatforms());
		dto.setImgUrl(result.getImgUrl());
		dto.setShortDescription(result.getShortDescription());
		dto.setLongDescription(result.getLongDescription());

		return dto;
	}

	@Transactional(readOnly = true)
	public List<GameMinDTO> findAll() {
		List<Game> result = gameRepository.findAll();

		return result.stream().map(game -> {
			GameMinDTO dto = new GameMinDTO();
			dto.setId(game.getId());
			dto.setTitle(game.getTitle());
			dto.setYear(game.getYear());
			dto.setImgUrl(game.getImgUrl());
			dto.setShortDescription(game.getShortDescription());
			return dto;
		}).toList();
	}

	@Transactional(readOnly = true)
	public List<GameMinDTO> findByGameList(Long listId) {
		List<GameMinProjection> games = gameRepository.searchByList(listId);

		return games.stream().map(projection -> {
			GameMinDTO dto = new GameMinDTO();
			dto.setId(projection.getId());
			dto.setTitle(projection.getTitle());
			dto.setYear(projection.getYear());
			dto.setImgUrl(projection.getImgUrl());
			dto.setShortDescription(projection.getShortDescription());
			return dto;
		}).toList();
	}
}
