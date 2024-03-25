package com.pramurta.movie.repositories;

import com.pramurta.movie.domain.entities.AvailableShow;
import com.pramurta.movie.utils.TestDataUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AvailableShowRepositoryIntegrationTests {

    private final AvailableShowRepository availableShowRepository;

    @Autowired
    public AvailableShowRepositoryIntegrationTests(AvailableShowRepository availableShowRepository) {
        this.availableShowRepository = availableShowRepository;
    }

    @Test
    public void testThatShowCanBeCreatedAndRecalled() {
        AvailableShow show = TestDataUtil.createShow();
        //TODO: Figure out why the data saved is not deleted after the test ends
        availableShowRepository.deleteAll();
        availableShowRepository.save(show);
        Optional<AvailableShow> result = availableShowRepository.findById(show.getId().toString());
        assertThat(result).isPresent();
        assertThat(result.get().getMovieName()).isEqualTo(show.getMovieName());
    }

    @Test
    public void testThatShowCanBeDeleted() {
        AvailableShow show = TestDataUtil.createShow();
        availableShowRepository.deleteAll();
        availableShowRepository.save(show);
        availableShowRepository.deleteById(show.getId().toString());
        Optional<AvailableShow> result = availableShowRepository.findById(show.getId().toString());
        assertThat(result).isEmpty();
    }

}
