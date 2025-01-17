package fr.imt.invocations.Service;

import fr.imt.invocations.Entity.Invocation;
import fr.imt.invocations.Entity.InvocationDTO;
import fr.imt.invocations.Repository.InvocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class InvocationService {

    @Autowired
    private InvocationRepository invocationRepository;

    public Invocation createProduct(InvocationDTO invocationDTO) {
        return invocationRepository.save(new Invocation(invocationDTO.getId(), invocationDTO.getIdMonstre()));
    }

    public Invocation getInvocationById(UUID id) {
        return invocationRepository.findById(id);
    }

    public void deleteInvocation(UUID id) {
        invocationRepository.deleteById(id);
    }

    public List<Invocation> getInvocationsByIdMonstre(int id) {
        return invocationRepository.findAllByIdMonstre(id);
    }
}
