package osk.sko.FitnessApp.exercise.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import osk.sko.FitnessApp.exception.ResourceNotFoundException;
import osk.sko.FitnessApp.exercise.dto.ExerciseSummaryDTO;
import osk.sko.FitnessApp.exercise.model.Exercise;
import osk.sko.FitnessApp.exercise.repository.ExerciseRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ExerciseService Unit Tests")
class ExerciseServiceTest {

    @Mock
    private ExerciseRepository exerciseRepository;

    @InjectMocks
    private ExerciseService exerciseService;

    private Exercise exercise;

    @BeforeEach
    void setUp() {
        this.exercise = Exercise.builder()
                .exerciseId("trmte8s")
                .name("band shrug")
                .gifUrl("https://static.exercisedb.dev/media/trmte8s.gif")
                .build();
    }


    @DisplayName("getExerciseById Tests")
    @Nested
    class getExerciseByIdTests {

        @Test
        @DisplayName("Should return exercise when ID exists")
        void shouldReturnExerciseWhenIdExists() {
            //Given
            final String exerciseId = "trmte8s";

            when(exerciseRepository.findById(exerciseId))
                    .thenReturn(Optional.of(exercise));

            //When
            final Exercise exercise = exerciseService.getExerciseById(exerciseId);

            //Then
            assertNotNull(exercise);
            assertEquals(Exercise.class, exercise.getClass());
            assertEquals(exerciseId, exercise.getExerciseId());
            verify(exerciseRepository, times(1)).findById(exerciseId);
        }

        @Test
        @DisplayName("Should throw exception when ID does not exist")
        void shouldReturnExceptionWhenIdDoesNotExist() {
            //Given
            final String exerciseId = "nonExistentId";

            when(exerciseRepository.findById(exerciseId))
                    .thenReturn(Optional.empty());

            //When
            final ResourceNotFoundException exception = assertThrows(
                    ResourceNotFoundException.class,
                    () -> {exerciseService.getExerciseById(exerciseId);
            });

            //Then
            assertEquals("Exercise not found", exception.getMessage());
            verify(exerciseRepository, times(1)).findById(exerciseId);
        }
    }

    @DisplayName("getAllExercises Tests")
    @Nested
    class getAllExercisesTests {

        @Test
        @DisplayName("Should return list of exercises")
        void shouldReturnListOfExercises() {
            //Given
            when(exerciseRepository.findAllWithTargetMuscles())
                    .thenReturn(
                            java.util.List.of(
                                    exercise,
                                    Exercise.builder()
                                            .exerciseId("0001")
                                            .name("3/4 sit-up")
                                            .gifUrl("https://static.exercisedb.dev/media/0001.gif")
                                            .build()
                            )
                    );

            //When
            final List<ExerciseSummaryDTO> exercises = exerciseService.getAllExercises();

            //Then
            assertNotNull(exercises);
            assertEquals(2, exercises.size());
            assertEquals("trmte8s", exercises.getFirst().exerciseId());
            assertEquals("band shrug", exercises.getFirst().name());
            assertEquals("https://static.exercisedb.dev/media/trmte8s.gif", exercises.getFirst().gifUrl());
            assertEquals("0001", exercises.get(1).exerciseId());
            assertEquals("3/4 sit-up", exercises.get(1).name());
            assertEquals("https://static.exercisedb.dev/media/0001.gif", exercises.get(1).gifUrl());
            verify(exerciseRepository, times(1)).findAllWithTargetMuscles();
        }

        @Test
        @DisplayName("Should return empty list when no exercises exist")
        void shouldReturnEmptyListWhenNoExercisesExist() {
            //Given
            when(exerciseRepository.findAllWithTargetMuscles())
                    .thenReturn(List.of());

            //When
            final List<ExerciseSummaryDTO> exercises = exerciseService.getAllExercises();

            //Then
            assertNotNull(exercises);
            assertTrue(exercises.isEmpty());
            verify(exerciseRepository, times(1)).findAllWithTargetMuscles();
        }
    }

}