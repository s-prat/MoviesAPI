package com.returners.movies.service;

import com.returners.movies.model.*;
import com.returners.movies.repository.CertificationRepository;
import com.returners.movies.repository.GenreRepository;
import com.returners.movies.exception.MovieIdNotFound;
import com.returners.movies.model.Certification;
import com.returners.movies.model.Genre;
import com.returners.movies.model.Movie;
import com.returners.movies.repository.MovieRepository;
import com.returners.movies.repository.UserRepository;
import io.micrometer.core.instrument.search.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    CertificationRepository certificationRepository;

    @Autowired
    GenreRepository genreRepository;

    @Autowired
    UserRepository userRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Movie> getAllMovies() {
        List<Movie> movies = new ArrayList<>();
        movieRepository.findAll().forEach(movies::add);
        return movies;
    }


    @Override
    public Movie addMovie(Movie movie) {
        if(movieRepository.findByTitle(movie.getTitle())!= null) return null;
        else{
            Genre genre = entityManager.find(Genre.class,movie.getGenre().getId());
            Certification certification = entityManager.find(Certification.class,movie.getCertification().getId());
            movie.setGenre(genre);
            movie.setCertification(certification);
            return movieRepository.save(movie);
        }
    }

    @Override
    public void deleteMovie(Long movieId) throws MovieIdNotFound {
            if (!movieRepository.existsById(movieId)) {
              throw new MovieIdNotFound();
            }
            movieRepository.deleteById(movieId);
    }

    @Override
    public List<Movie> getMoviesBySearchCriteria(SearchCriteria search){
        if (search.getId() == null && search.getRating() == 0  && search.getActors() == null && search.getTitle() == null  && search.getGenreId() == null && search.getCertificationId() == null  && search.getYear() == 0 ){
            return movieRepository.findAll();
        }else {
            if (search.getActors() == null)
                return movieRepository.findBySearchCriteriaWithoutActor(
                        search.getId(),
                        search.getRating(),
                        search.getTitle(),
                        search.getYear(),
                        search.getCertificationId(),
                        search.getGenreId()
                );
            else
                return movieRepository.findBySearchCriteriaWithActor(
                        search.getId(),
                        search.getRating(),
                        search.getActors(),
                        search.getTitle(),
                        search.getYear(),
                        search.getCertificationId(),
                        search.getGenreId()
                );
        }

    }

    @Override
    public List<Movie> getMoviesForUserBySearchCriteria(Long userId,SearchCriteria search){

        List<Long> certList = getCertificationList(userId);
        if (search.getId() == null && search.getRating() == 0  && search.getActors() == null && search.getTitle() == null  && search.getGenreId() == null && search.getCertificationId() == null  && search.getYear() == 0 ){
            return movieRepository.findAllForUser(certList.toArray(new Long[certList.size()]));
        }else {
            if (search.getActors() == null)
                return movieRepository.findBySearchCriteriaForUserWithoutActor(
                        search.getId(),
                        search.getRating(),
                        search.getTitle(),
                        search.getYear(),
                        certList.toArray(new Long[certList.size()]),
                        search.getCertificationId(),
                        search.getGenreId()
                );
            else
                return movieRepository.findBySearchCriteriaForUserWithActor(
                        search.getId(),
                        search.getRating(),
                        search.getActors(),
                        search.getTitle(),
                        search.getYear(),
                        certList.toArray(new Long[certList.size()]),
                        search.getCertificationId(),
                        search.getGenreId()
                );
        }
    }

    public List<Long> getCertificationList(Long userId){
        User user = userRepository.findById(userId).get();
        int userAge = 99;
        if(user != null) {
            userAge = user.getAge();
        }
        ArrayList<String> certNames = new ArrayList<>();

        certNames.add("U");
        certNames.add("12A");
        if (userAge >= 18){
            certNames.add("18");
            certNames.add("R");
            certNames.add("TBC");
        }
        if (userAge >= 15){
            certNames.add("15");
        }
        if (userAge >= 12){
            certNames.add("12");
        }
        if (userAge >= 8){
            certNames.add("PG");
        }

        List<Long> certList = certificationRepository.findByNames(certNames);
        return certList;
    }

}
