package fr.imt.invocations.Controller;

import fr.imt.invocations.Entity.Invocations;
import fr.imt.invocations.Entity.InvocationsDTO;
import fr.imt.invocations.Service.InvocationsService;
import jakarta.validation.Valid;
import org.aopalliance.intercept.Invocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/invocations")
public class InvocationsController {

    @Autowired
    private InvocationsService invocationsService;

    @GetMapping("/{id}")
    public Invocations getInvocation(@PathVariable UUID id) {
        return invocationsService.getInvocationById(id);
    }

//    @PostMapping("/new")
//    public void createInvocation(@Valid @RequestBody InvocationsDTO invocation) {
//        invocationsService.createInvocation(invocation);
//    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        invocationsService.deleteInvocation(id);
    }

    @GetMapping("/monstres/{id}")
    public List<Invocations> getMonstreById(@PathVariable int id) {
        return invocationsService.getInvocationsByIdMonstre(id);
    }

    @GetMapping("/joueurs/{id}")
    public List<Invocations> getJoueurById(@PathVariable int id) {
        return invocationsService.getInvocationsByIdJoueur(id);
    }

    @PostMapping("/invocations/{playerId}")
    public int invoquerMonstre(@PathVariable int playerId) {
        return invocationsService.invoquerMonstre(playerId);
    }
}
