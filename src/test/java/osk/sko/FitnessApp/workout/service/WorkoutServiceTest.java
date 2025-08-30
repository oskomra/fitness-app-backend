package osk.sko.FitnessApp.workout.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import osk.sko.FitnessApp.exception.ResourceAlreadyExistsException;
import osk.sko.FitnessApp.exception.ResourceNotFoundException;
import osk.sko.FitnessApp.exercise.model.Exercise;
import osk.sko.FitnessApp.user.model.User;
import osk.sko.FitnessApp.user.service.UserDetailsServiceImpl;
import osk.sko.FitnessApp.workout.dto.WorkoutDTO;
import osk.sko.FitnessApp.workout.dto.WorkoutExerciseDTO;
import osk.sko.FitnessApp.workout.dto.WorkoutExerciseSetDTO;
import osk.sko.FitnessApp.workout.dto.WorkoutUpdateDTO;
import osk.sko.FitnessApp.workout.mapper.WorkoutMapper;
import osk.sko.FitnessApp.workout.model.Workout;
import osk.sko.FitnessApp.workout.model.WorkoutExercise;
import osk.sko.FitnessApp.workout.model.WorkoutExerciseSet;
import osk.sko.FitnessApp.workout.repository.WorkoutRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("WorkoutService Unit Test")
class WorkoutServiceTest {

    @Mock
    private WorkoutRepository workoutRepository;
    @Mock
    private UserDetailsServiceImpl userDetailsService;
    @Mock
    private WorkoutMapper workoutMapper;

    @InjectMocks
    private WorkoutService workoutService;

    private User user;
    private Workout workout;
    private WorkoutDTO workoutDTO;


    @BeforeEach
    void setUp() {
        this.user = User.builder()
                .id(1)
                .email("test@gmail.com")
                .name("name")
                .lastName("lastName")
                .phoneNumber("123456789")
                .password("password")
                .authority("USER")
                .build();

        Exercise exercise = Exercise.builder()
                .exerciseId("trmte8s")
                .name("band shrug")
                .gifUrl("https://static.exercisedb.dev/media/trmte8s.gif")
                .build();

        WorkoutExerciseSet workoutExerciseSet = WorkoutExerciseSet.builder()
                .id(1L)
                .setNumber(1)
                .reps(10)
                .weight(50.0)
                .build();

        WorkoutExercise workoutExercise = WorkoutExercise.builder()
                .id(1L)
                .exercise(exercise)
                .build();

        workoutExerciseSet.setWorkoutExercise(workoutExercise);
        workoutExercise.setSets(List.of(workoutExerciseSet));

        this.workout = Workout.builder()
                .id(1L)
                .title("Leg day")
                .description("Late night workout")
                .startDate(LocalDateTime.now())
                .endDate(null)
                .user(user)
                .exercises(Set.of(workoutExercise))
                .build();

        WorkoutExerciseSetDTO workoutExerciseSetDTO = WorkoutExerciseSetDTO.builder()
                .id(1L)
                .setNumber(1)
                .reps(10)
                .weight(50.0)
                .build();

        WorkoutExerciseDTO workoutExerciseDTO = WorkoutExerciseDTO.builder()
                .id(1L)
                .exerciseId(exercise.getExerciseId())
                .build();

        workoutExerciseSetDTO.setWorkoutExerciseId(workoutExerciseDTO.getId());
        workoutExerciseDTO.setSets(List.of(workoutExerciseSetDTO));

        this.workoutDTO = WorkoutDTO.builder()
                .id(1L)
                .title("Leg day")
                .description("Late night workout")
                .startDate(workout.getStartDate())
                .endDate(null)
                .userId(user.getId())
                .exercises(Set.of(workoutExerciseDTO))
                .build();

    }

    @DisplayName("getAllWorkouts Tests")
    @Nested
    class GetAllWorkoutsTests {

        @Test
        @DisplayName("Should return list of WorkoutDTOs when workouts exist")
        void shouldReturnListOfWorkoutDTOsWhenWorkoutsExist() {
            // Given
            when(userDetailsService.getCurrentUser()).thenReturn(user);
            when(workoutRepository.findAllByUserId(user.getId()))
                    .thenReturn(List.of(workout));
            when(workoutMapper.toDTO(workout)).thenReturn(workoutDTO);

            // When
            final List<WorkoutDTO> result = workoutService.getAllWorkouts();

            // Then
            verify(userDetailsService, times(1)).getCurrentUser();
            verify(workoutRepository, times(1)).findAllByUserId(user.getId());
            verify(workoutMapper, times(1)).toDTO(workout);
            assertEquals(List.of(workoutDTO), result);
            assertEquals(workoutDTO.getId(), result.getFirst().getId());
        }

        @Test
        @DisplayName("Should return empty list when no workouts exist")
        void shouldReturnEmptyListIfWorkoutsDoNotExist() {
            // Given
            when(userDetailsService.getCurrentUser()).thenReturn(user);
            when(workoutRepository.findAllByUserId(user.getId()))
                    .thenReturn(List.of());

            // When
            final List<WorkoutDTO> result = workoutService.getAllWorkouts();

            // Then
            verify(userDetailsService, times(1)).getCurrentUser();
            verify(workoutRepository, times(1)).findAllByUserId(user.getId());
            verifyNoInteractions(workoutMapper);
            assertTrue(result.isEmpty());
        }
    }

    @DisplayName("getWorkoutById Tests")
    @Nested
    class GetWorkoutsByIdTests {

        @Test
        @DisplayName("Should return WorkoutDTO when workout exists")
        void shouldReturnWorkoutDTOWhenWorkoutExists() {
            // Given
            when(workoutRepository.findById(workout.getId())).thenReturn(Optional.of(workout));
            when(workoutMapper.toDTO(workout)).thenReturn(workoutDTO);

            // When
            final WorkoutDTO result = workoutService.getWorkoutById(workout.getId());

            // Then
            verify(workoutRepository, times(1)).findById(workout.getId());
            verify(workoutMapper, times(1)).toDTO(workout);
            assertEquals(workoutDTO, result);
            assertEquals(workoutDTO.getId(), result.getId());
        }

        @Test
        @DisplayName("Should throw ResourceNotFoundException when workout does not exist")
        void shouldThrowResourceNotFoundExceptionWhenWorkoutDoesNotExist() {
            // Given
            when(workoutRepository.findById(workout.getId())).thenReturn(Optional.empty());

            // When & Then
            final ResourceNotFoundException exception = assertThrows(
                    ResourceNotFoundException.class,
                    () -> workoutService.getWorkoutById(workout.getId())
            );

            // Then
            verify(workoutRepository, times(1)).findById(workout.getId());
            assertEquals("Workout not found", exception.getMessage());
            assertEquals(ResourceNotFoundException.class, exception.getClass());
            verifyNoInteractions(workoutMapper);
        }
    }

    @DisplayName("getActiveWorkout Tests")
    @Nested
    class GetActiveWorkoutTests {

        @Test
        @DisplayName("Should return active WorkoutDTO when active workout exists")
        void shouldReturnActiveWorkoutDTOWhenActiveWorkoutExists() {
            // Given
            when(userDetailsService.getCurrentUser()).thenReturn(user);
            when(workoutRepository.findByUserIdAndEndDateIsNull(user.getId()))
                    .thenReturn(Optional.of(workout));
            when(workoutMapper.toDTO(workout)).thenReturn(workoutDTO);

            // When
            final WorkoutDTO result = workoutService.getActiveWorkout();

            // Then
            verify(userDetailsService, times(1)).getCurrentUser();
            verify(workoutRepository, times(1)).findByUserIdAndEndDateIsNull(user.getId());
            verify(workoutMapper, times(1)).toDTO(workout);
            assertEquals(workoutDTO, result);
            assertEquals(workoutDTO.getId(), result.getId());
        }

        @Test
        @DisplayName("Should throw ResourceNotFoundException when no active workout exists")
        void shouldThrowResourceNotFoundExceptionWhenNoActiveWorkoutExists() {
            // Given
            when(userDetailsService.getCurrentUser()).thenReturn(user);
            when(workoutRepository.findByUserIdAndEndDateIsNull(user.getId()))
                    .thenReturn(Optional.empty());

            // When & Then
            final ResourceNotFoundException exception = assertThrows(
                    ResourceNotFoundException.class,
                    () -> workoutService.getActiveWorkout()
            );

            // Then
            verify(userDetailsService, times(1)).getCurrentUser();
            verify(workoutRepository, times(1)).findByUserIdAndEndDateIsNull(user.getId());
            assertEquals("No active workout found for this user", exception.getMessage());
            assertEquals(ResourceNotFoundException.class, exception.getClass());
            verifyNoInteractions(workoutMapper);
        }
    }

    @DisplayName("startWorkout Tests")
    @Nested
    class StartWorkoutTests {

        @Test
        @DisplayName("Should start a new workout when no active workout exists")
        void shouldStartNewWorkoutWhenNoActiveWorkoutExists() {
            // Given
            when(userDetailsService.getCurrentUser()).thenReturn(user);
            when(workoutRepository.findByUserIdAndEndDateIsNull(user.getId()))
                    .thenReturn(Optional.empty());
            when(workoutRepository.save(any(Workout.class))).thenReturn(workout);
            when(workoutMapper.toDTO(any(Workout.class))).thenReturn(workoutDTO);

            // When
            final WorkoutDTO result = workoutService.startWorkout();

            // Then
            verify(userDetailsService, times(1)).getCurrentUser();
            verify(workoutRepository, times(1)).findByUserIdAndEndDateIsNull(user.getId());
            verify(workoutRepository, times(1)).save(any(Workout.class));
            verify(workoutMapper, times(1)).toDTO(any(Workout.class));
            assertEquals(workoutDTO, result);
            assertEquals(workoutDTO.getId(), result.getId());
            assertNotNull(workout.getStartDate());
            assertNotNull(workout.getUser());

        }

        @Test
        @DisplayName("Should throw ResourceAlreadyExistsException when an active workout already exists")
        void shouldThrowResourceAlreadyExistsExceptionWhenActiveWorkoutExists() {
            // Given
            when(userDetailsService.getCurrentUser()).thenReturn(user);
            when(workoutRepository.findByUserIdAndEndDateIsNull(user.getId()))
                    .thenReturn(Optional.of(workout));

            // When & Then
            final ResourceAlreadyExistsException exception = assertThrows(
                    ResourceAlreadyExistsException.class,
                    () -> workoutService.startWorkout()
            );

            // Then
            verify(userDetailsService, times(1)).getCurrentUser();
            verify(workoutRepository, times(1)).findByUserIdAndEndDateIsNull(user.getId());
            assertEquals("Workout already exists", exception.getMessage());
            assertEquals(ResourceAlreadyExistsException.class, exception.getClass());
            verify(workoutRepository, never()).save(any());
            verifyNoInteractions(workoutMapper);
        }
    }

    @DisplayName("endWorkout Tests")
    @Nested
    class EndWorkoutTests {

        @Test
        @DisplayName("Should end the active workout by setting the end date if workout exists")
        void shouldEndActiveWorkoutWhenWorkoutExists() {
            // Given
            when(userDetailsService.getCurrentUser()).thenReturn(user);
            when(workoutRepository.findByUserIdAndEndDateIsNull(user.getId()))
                    .thenReturn(Optional.of(workout));
            when(workoutRepository.save(any(Workout.class))).thenReturn(workout);
            when(workoutMapper.toDTO(any(Workout.class))).thenReturn(workoutDTO);

            // When
            final WorkoutDTO result = workoutService.endWorkout();

            // Then
            verify(userDetailsService, times(1)).getCurrentUser();
            verify(workoutRepository, times(1)).findByUserIdAndEndDateIsNull(user.getId());
            verify(workoutRepository, times(1)).save(any(Workout.class));
            verify(workoutMapper, times(1)).toDTO(any(Workout.class));
            assertEquals(workoutDTO, result);
            assertEquals(workoutDTO.getId(), result.getId());
            assertNotNull(workout.getEndDate());
        }

        @Test
        @DisplayName("Should throw ResourceNotFoundException when no active workout exists")
        void shouldThrowResourceNotFoundExceptionWhenNoActiveWorkoutExists() {
            // Given
            when(userDetailsService.getCurrentUser()).thenReturn(user);
            when(workoutRepository.findByUserIdAndEndDateIsNull(user.getId()))
                    .thenReturn(Optional.empty());

            // When & Then
            final ResourceNotFoundException exception = assertThrows(
                    ResourceNotFoundException.class,
                    () -> workoutService.endWorkout()
            );

            // Then
            verify(userDetailsService, times(1)).getCurrentUser();
            verify(workoutRepository, times(1)).findByUserIdAndEndDateIsNull(user.getId());
            assertEquals("No active workout found for this user", exception.getMessage());
            assertEquals(ResourceNotFoundException.class, exception.getClass());
            verify(workoutRepository, never()).save(any());
            verifyNoInteractions(workoutMapper);
        }
    }

    @DisplayName("updateActiveWorkout Tests")
    @Nested
    class UpdateActiveWorkoutTests {

        @Test
        @DisplayName("Should update the active workout's title and description when workout exists")
        void shouldUpdateActiveWorkoutWhenWorkoutExists() {
            // Given
            final String newTitle = "Updated Title";
            final String newDescription = "Updated Description";
            final WorkoutUpdateDTO workoutUpdateDTO = new WorkoutUpdateDTO(newTitle, newDescription);

            when(userDetailsService.getCurrentUser()).thenReturn(user);
            when(workoutRepository.findByUserIdAndEndDateIsNull(user.getId()))
                    .thenReturn(Optional.of(workout));
            when(workoutRepository.save(any(Workout.class))).thenReturn(workout);
            when(workoutMapper.toDTO(any(Workout.class))).thenReturn(workoutDTO);

            // When
            final WorkoutDTO result = workoutService.updateActiveWorkout(workoutUpdateDTO);

            // Then
            verify(userDetailsService, times(1)).getCurrentUser();
            verify(workoutRepository, times(1)).findByUserIdAndEndDateIsNull(user.getId());
            verify(workoutRepository, times(1)).save(any(Workout.class));
            verify(workoutMapper, times(1)).toDTO(any(Workout.class));
            assertEquals(workoutDTO, result);
            assertEquals(workoutDTO.getId(), result.getId());
            assertEquals(newTitle, workout.getTitle());
            assertEquals(newDescription, workout.getDescription());
        }

        @Test
        @DisplayName("Should update only title when description is null")
        void shouldUpdateOnlyTitleWhenDescriptionIsNull() {
            // Given
            final String newTitle = "Updated Title";
            final WorkoutUpdateDTO workoutUpdateDTO = new WorkoutUpdateDTO(newTitle, null);
            final String originalDescription = workout.getDescription();

            when(userDetailsService.getCurrentUser()).thenReturn(user);
            when(workoutRepository.findByUserIdAndEndDateIsNull(user.getId()))
                    .thenReturn(Optional.of(workout));
            when(workoutRepository.save(any(Workout.class))).thenReturn(workout);
            when(workoutMapper.toDTO(any(Workout.class))).thenReturn(workoutDTO);

            // When
            final WorkoutDTO result = workoutService.updateActiveWorkout(workoutUpdateDTO);

            // Then
            verify(userDetailsService, times(1)).getCurrentUser();
            verify(workoutRepository, times(1)).findByUserIdAndEndDateIsNull(user.getId());
            verify(workoutRepository, times(1)).save(any(Workout.class));
            verify(workoutMapper, times(1)).toDTO(any(Workout.class));
            assertEquals(workoutDTO, result);
            assertEquals(workoutDTO.getId(), result.getId());
            assertEquals(newTitle, workout.getTitle());
            assertEquals(originalDescription, workout.getDescription());
        }

        @Test
        @DisplayName("Should update only description when title is null")
        void shouldUpdateOnlyDescriptionWhenTitleIsNull() {
            // Given
            final String newDescription = "Updated Description";
            final WorkoutUpdateDTO workoutUpdateDTO = new WorkoutUpdateDTO(null, newDescription);
            final String originalTitle = workout.getTitle();

            when(userDetailsService.getCurrentUser()).thenReturn(user);
            when(workoutRepository.findByUserIdAndEndDateIsNull(user.getId()))
                    .thenReturn(Optional.of(workout));
            when(workoutRepository.save(any(Workout.class))).thenReturn(workout);
            when(workoutMapper.toDTO(any(Workout.class))).thenReturn(workoutDTO);

            // When
            final WorkoutDTO result = workoutService.updateActiveWorkout(workoutUpdateDTO);

            // Then
            verify(userDetailsService, times(1)).getCurrentUser();
            verify(workoutRepository, times(1)).findByUserIdAndEndDateIsNull(user.getId());
            verify(workoutRepository, times(1)).save(any(Workout.class));
            verify(workoutMapper, times(1)).toDTO(any(Workout.class));
            assertEquals(workoutDTO, result);
            assertEquals(workoutDTO.getId(), result.getId());
            assertEquals(originalTitle, workout.getTitle());
            assertEquals(newDescription, workout.getDescription());
        }

        @Test
        @DisplayName("Should throw ResourceNotFoundException when no active workout exists")
        void shouldThrowResourceNotFoundExceptionWhenNoActiveWorkoutExists() {
            // Given
            when(userDetailsService.getCurrentUser()).thenReturn(user);
            when(workoutRepository.findByUserIdAndEndDateIsNull(user.getId()))
                    .thenReturn(Optional.empty());
            final WorkoutUpdateDTO workoutUpdateDTO = new WorkoutUpdateDTO("Title", "Description");

            // When & Then
            final ResourceNotFoundException exception = assertThrows(
                    ResourceNotFoundException.class,
                    () -> workoutService.updateActiveWorkout(workoutUpdateDTO)
            );

            // Then
            verify(userDetailsService, times(1)).getCurrentUser();
            verify(workoutRepository, times(1)).findByUserIdAndEndDateIsNull(user.getId());
            assertEquals("No active workout found for this user", exception.getMessage());
            assertEquals(ResourceNotFoundException.class, exception.getClass());
            verify(workoutRepository, never()).save(any());
            verifyNoInteractions(workoutMapper);
        }

        @Test
        @DisplayName("Should not update title and description when both are null")
        void shouldNotUpdateWhenTitleAndDescriptionAreNull() {
            // Given
            final String originalTitle = workout.getTitle();
            final String originalDescription = workout.getDescription();
            final WorkoutUpdateDTO workoutUpdateDTO = new WorkoutUpdateDTO(null, null);

            when(userDetailsService.getCurrentUser()).thenReturn(user);
            when(workoutRepository.findByUserIdAndEndDateIsNull(user.getId()))
                    .thenReturn(Optional.of(workout));
            when(workoutRepository.save(any(Workout.class))).thenReturn(workout);
            when(workoutMapper.toDTO(any(Workout.class))).thenReturn(workoutDTO);

            // When
            final WorkoutDTO result = workoutService.updateActiveWorkout(workoutUpdateDTO);

            // Then
            verify(userDetailsService, times(1)).getCurrentUser();
            verify(workoutRepository, times(1)).findByUserIdAndEndDateIsNull(user.getId());
            verify(workoutRepository, times(1)).save(any(Workout.class));
            verify(workoutMapper, times(1)).toDTO(any(Workout.class));
            assertEquals(workoutDTO, result);
            assertEquals(workoutDTO.getId(), result.getId());
            assertEquals(originalTitle, workout.getTitle());
            assertEquals(originalDescription, workout.getDescription());
        }
    }

}