package com.free.agent.service.impl;



import com.free.agent.dao.FilmDao;
import com.free.agent.model.Film;
import com.free.agent.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("FilmService")
public class FilmServiceImpl implements FilmService {

    @Autowired
    private FilmDao filmDao;

    @Transactional(readOnly = true)
    public List<Film> getAllFilms() {
        return filmDao.getAll();
    }

    @Transactional(readOnly = true)
    public Film getFilm(Integer id) {
        return filmDao.read(id);
    }
}