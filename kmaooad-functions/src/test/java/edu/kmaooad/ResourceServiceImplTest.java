package edu.kmaooad;

import edu.kmaooad.exceptions.IncorrectResourceParamsException;
import edu.kmaooad.exceptions.NotFoundException;
import edu.kmaooad.exceptions.ResourceCompositeFieldNotUniqueException;
import edu.kmaooad.models.Resource;
import edu.kmaooad.models.ResourceCompositeField;
import edu.kmaooad.models.ResourceType;
import edu.kmaooad.repositories.ResourceRepository;
import edu.kmaooad.services.implementations.ResourceServiceImpl;
import edu.kmaooad.services.interfaces.ResourceService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ResourceServiceImplTest {

    private final ResourceRepository resourceRepository = mock(ResourceRepository.class);
    private final ResourceService resourceService = new ResourceServiceImpl(resourceRepository);

    @Test
    void whenCreateResourceThenSuccess() throws Exception {
        Long realId = 2L;
        ResourceType type = ResourceType.DEPARTMENT;

        ResourceCompositeField rcf = new ResourceCompositeField(realId, type);
        when(resourceRepository.existsByRealResource(rcf)).thenReturn(false);

        Resource resource = new Resource(null, realId, type);
        when(resourceRepository.save(any())).thenReturn(resource);

        assertThat(resourceService.createResource(realId, type)).isEqualTo(resource);
    }

    @Test
    void whenCreateResourceWithNullRealIdThenFailure() {
        assertThrows(IncorrectResourceParamsException.class,
                () -> resourceService.createResource(null, ResourceType.DEPARTMENT));
    }

    @Test
    void whenCreateResourceWithNullResTypeThenFailure() {
        assertThrows(IncorrectResourceParamsException.class,
                () -> resourceService.createResource(1L, null));
    }

    @Test
    void whenCreateExistingResourceThenFailure() {
        Long realId = 2L;
        ResourceType type = ResourceType.DEPARTMENT;

        ResourceCompositeField rcf = new ResourceCompositeField(realId, type);
        when(resourceRepository.existsByRealResource(rcf)).thenReturn(true);

        assertThrows(ResourceCompositeFieldNotUniqueException.class,
                () -> resourceService.createResource(realId, type));
    }

    @Test
    void whenGetResourceByIdThenSuccess() {
        Long id = 1L;
        Resource resource = new Resource();
        when(resourceRepository.findById(id)).thenReturn(Optional.of(resource));

        assertThat(resourceService.getResourceById(id)).isEqualTo(resource);
    }

    @Test
    void whenGetResourceByNonExistingIdThenFailure() {
        Long id = 1L;
        when(resourceRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> resourceService.getResourceById(id));
    }

    @Test
    void whenUpdateResourceThenSuccess() throws Exception {
        Long id = 1L;
        Long realId = 2L;
        ResourceType type = ResourceType.DEPARTMENT;

        ResourceCompositeField rcf = new ResourceCompositeField(realId, type);
        when(resourceRepository.existsByRealResource(rcf)).thenReturn(false);

        when(resourceRepository.existsById(id)).thenReturn(true);

        Resource resource = new Resource(null, realId, type);
        when(resourceRepository.save(any())).thenReturn(resource);

        assertThat(resourceService.updateResource(id, realId, type)).isEqualTo(resource);
    }

    @Test
    void whenUpdateResourceWithNullIdThenFailure() {
        assertThrows(IncorrectResourceParamsException.class,
                () -> resourceService.updateResource(null, 1L, ResourceType.DEPARTMENT));
    }

    @Test
    void whenUpdateResourceWithNullRealIdThenFailure() {
        assertThrows(IncorrectResourceParamsException.class,
                () -> resourceService.updateResource(1L, null, ResourceType.DEPARTMENT));
    }

    @Test
    void whenUpdateResourceWithNullResTypeThenFailure() {
        assertThrows(IncorrectResourceParamsException.class,
                () -> resourceService.updateResource(1L, 2L, null));
    }

    @Test
    void whenUpdateResourceByNonExistingIdThenFailure() {
        Long id = 1L;

        when(resourceRepository.existsById(id)).thenReturn(false);

        assertThrows(NotFoundException.class,
                () -> resourceService.updateResource(id, 2L, ResourceType.DEPARTMENT));
    }

    @Test
    void whenUpdateResourceByExistingCompKeyThenFailure() {
        Long id = 1L;
        Long realId = 2L;
        ResourceType type = ResourceType.DEPARTMENT;

        when(resourceRepository.existsById(id)).thenReturn(true);

        ResourceCompositeField rcf = new ResourceCompositeField(realId, type);
        when(resourceRepository.existsByRealResource(rcf)).thenReturn(true);

        assertThrows(ResourceCompositeFieldNotUniqueException.class,
                () -> resourceService.updateResource(id, realId, type));
    }


    @Test
    void whenDeleteResourceByIdThenSuccess() {
        Long id = 1L;
        Resource resource = new Resource();
        when(resourceRepository.findById(id)).thenReturn(Optional.of(resource));

        assertThat(resourceService.deleteResourceById(id)).isEqualTo(resource);
    }

    @Test
    void whenDeleteResourceByNonExistingIdThenFailure() {
        Long id = 1L;
        when(resourceRepository.findById(id)).thenReturn(Optional.empty());

        assertThat(resourceService.deleteResourceById(id)).isNull();
    }

    @Test
    void whenExistsByExistingIdThenTrue() {
        Long id = 1L;
        when(resourceRepository.existsById(id)).thenReturn(true);

        assertThat(resourceService.existsById(id)).isTrue();
    }

    @Test
    void whenExistsByNonExistingIdThenFalse() {
        Long id = 1L;
        when(resourceRepository.existsById(id)).thenReturn(false);

        assertThat(resourceService.existsById(id)).isFalse();
    }
}