package fr.imt.invocations.Service;

import fr.imt.invocations.Entity.Invocations;
import fr.imt.invocations.Entity.InvocationsDTO;
import fr.imt.invocations.Repository.InvocationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class InvocationsService {

    @Autowired
    private InvocationsRepository invocationsRepository;

    public Invocations createInvocation(InvocationsDTO invocationsDTO) {
        return invocationsRepository.save(new Invocations(invocationsDTO.getId(), invocationsDTO.getIdMonstre(), invocationsDTO.getIdJoueur()));
    }

    public Invocations getInvocationById(int id) {
        return invocationsRepository.findById(id);
    }

    public void deleteInvocation(int id) {
        invocationsRepository.deleteById(id);
    }

    public List<Invocations> getInvocationsByIdMonstre(int id) {
        return invocationsRepository.findAllByIdMonstre(id);
    }

    public List<Invocations> getInvocationsByIdJoueur(int id) {
        return invocationsRepository.findAllByIdJoueur(id);
    }
}
