package edu.kmaooad.services.implementations;

import edu.kmaooad.exceptions.IncorrectResourceParamsException;
import edu.kmaooad.exceptions.NotFoundException;
import edu.kmaooad.exceptions.ResourceCompositeFieldNotUniqueException;
import edu.kmaooad.models.Resource;
import edu.kmaooad.models.ResourceCompositeField;
import edu.kmaooad.models.ResourceType;
import edu.kmaooad.repositories.ResourceRepository;
import edu.kmaooad.services.interfaces.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceServiceImpl implements ResourceService {

    private final ResourceRepository resourceRepository;

    @Autowired
    public ResourceServiceImpl(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    @Override
    public Resource createResource(Long id, Long realId, ResourceType type) throws Exception {
        if(id == null) throw new IncorrectResourceParamsException("id","null");
        if(realId == null) throw new IncorrectResourceParamsException("realId","null");
        if(type == null) throw new IncorrectResourceParamsException("type","null");
        if(!isUniqueComposite(realId,type)) throw new ResourceCompositeFieldNotUniqueException();
        return resourceRepository.save(new Resource(id, realId, type));
    }

    @Override
    public Resource getResourceById(Long id) throws NotFoundException {
        return resourceRepository.findById(id).orElseThrow(() -> new NotFoundException(Resource.class.getSimpleName()));
    }

    @Override
    public Resource updateResource(Long id, Long realId, ResourceType type) throws Exception {
        if(id == null) throw new IncorrectResourceParamsException("id","null");
        if(realId == null) throw new IncorrectResourceParamsException("realId","null");
        if(type == null) throw new IncorrectResourceParamsException("type","null");
        if(!existsById(id)) throw new NotFoundException(Resource.class.getSimpleName());
        if(!isUniqueComposite(realId,type)) throw new ResourceCompositeFieldNotUniqueException();
        return resourceRepository.save(new Resource(id, realId, type));
    }

    @Override
    public Resource deleteResourceById(Long id) {
        Resource resource = resourceRepository.findById(id).orElse(null);
        if(resource != null) resourceRepository.deleteById(id);
        return resource;
    }

    @Override
    public Resource getResourceByRealIdAndType(Long realId, ResourceType type) throws IncorrectResourceParamsException {
        if(realId == null) throw new IncorrectResourceParamsException("realId","null");
        if(type == null) throw new IncorrectResourceParamsException("type","null");
        return resourceRepository.findByRealResource(new ResourceCompositeField(realId,type));
    }

    @Override
    public boolean existsById(Long id) {
        return resourceRepository.existsById(id);
    }

    private boolean isUniqueComposite(Long realId, ResourceType type){
        ResourceCompositeField rcf = new ResourceCompositeField(realId,type);
        return !resourceRepository.existsByRealResource(rcf);
    }

}