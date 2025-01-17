package fr.imt.invocations.Controller;

import fr.imt.invocations.Entity.Invocation;
import fr.imt.invocations.Entity.InvocationDTO;
import fr.imt.invocations.Service.InvocationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/invocation")
public class InvocationController {

    @Autowired
    private InvocationService invocationService;

    @GetMapping("/{id}")
    public Invocation getInvocation(@PathVariable UUID id) {
        return invocationService.getInvocationById(id);
    }

    @PostMapping("/new")
    public void createInvocation(@Valid InvocationDTO invocation) {
        invocationService.createProduct(invocation);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        invocationService.deleteInvocation(id);
    }

    @GetMapping("/invocations/{id}")
    public List<Invocation> getProductsByPrix(@PathVariable int id) {
        return invocationService.getInvocationsByIdMonstre(id);
    }
}
