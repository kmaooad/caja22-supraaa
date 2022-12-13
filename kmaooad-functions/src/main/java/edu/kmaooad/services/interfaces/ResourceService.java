package edu.kmaooad.services.interfaces;

import edu.kmaooad.exceptions.IncorrectResourceParamsException;
import edu.kmaooad.exceptions.NotFoundException;
import edu.kmaooad.models.Resource;
import edu.kmaooad.models.ResourceType;

public interface ResourceService {

    Resource createResource(Long realId, ResourceType type) throws Exception;

    Resource getResourceById(Long id) throws NotFoundException;

    Resource updateResource(Long id, Long realId, ResourceType type) throws Exception;

    Resource deleteResourceById(Long id);

    Resource getResourceByRealIdAndType(Long realId, ResourceType type) throws IncorrectResourceParamsException;

    boolean existsById(Long id);

}
