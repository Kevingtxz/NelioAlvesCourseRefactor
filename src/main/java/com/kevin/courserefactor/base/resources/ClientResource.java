package com.kevin.courserefactor.base.resources;

import com.kevin.courserefactor.base.domain.Client;
import com.kevin.courserefactor.base.dto.form.ClientForm;
import com.kevin.courserefactor.base.dto.form.ClientFormNew;
import com.kevin.courserefactor.base.dto.view.ClientView;
import com.kevin.courserefactor.base.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("clients")
public class ClientResource {

    @Autowired
    private ClientService service;



    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value = "/page")
    public ResponseEntity<Page<ClientView>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24")  Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "name")  String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC")  String direction) {
        Page<Client> list = service.findPage(page, linesPerPage, orderBy, direction);
        Page<ClientView> listDto = list
                .map(obj -> new ClientView(obj));
        return ResponseEntity.ok().body(listDto);
    }


    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<ClientView>> findAll() {
        List<Client> list = service.findAll();
        List<ClientView> listDto = list
                .stream()
                .map(obj -> new ClientView(obj))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Client> find(@PathVariable Integer id) {
        Client obj = service.find(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody ClientFormNew objNewDto) {
        Client obj = service.fromDto(objNewDto);
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(obj.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody ClientForm objDto, @PathVariable Integer id) {
        Client obj = service.fromDto(objDto);
        obj.setId(id);
        service.update(obj);
        return ResponseEntity.noContent().build();
    }


    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
