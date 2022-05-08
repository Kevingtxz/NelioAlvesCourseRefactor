package com.kevin.courserefactor.base.service;

import com.kevin.courserefactor.base.domain.Address;
import com.kevin.courserefactor.base.domain.City;
import com.kevin.courserefactor.base.domain.Client;
import com.kevin.courserefactor.base.domain.enums.ClientType;
import com.kevin.courserefactor.base.dto.form.ClientForm;
import com.kevin.courserefactor.base.dto.form.ClientFormNew;
import com.kevin.courserefactor.base.repository.AddressRepository;
import com.kevin.courserefactor.base.repository.ClientRepository;
import com.kevin.courserefactor.base.service.exceptions.DataIntegrityException;
import com.kevin.courserefactor.base.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repo;

    @Autowired
    private AddressRepository addressRepository;


    public Page<Client> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    public List<Client> findAll() {
        return repo.findAll();
    }

    public Client find(Integer id) {
        return repo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Object not found. ID: " + id
                                + ", Type: " + Client.class.getName()));
    }

    @Transactional
    public Client insert(Client obj) {
        obj.setId(null);
        obj = repo.save(obj);
        addressRepository.saveAll(obj.getAddresses());
        return obj;
    }

    public void update(Client updObj) {
        Client obj = this.find(updObj.getId());
        this.updateData(obj, updObj);
        repo.save(obj);
    }

    public void delete(Integer id) {
        this.find(id);
        try {
            repo.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("There are entities relations");
        }
    }

    public Client fromDto(ClientFormNew objNewDto) {
        Client client = new Client(
                null,
                objNewDto.getName(),
                objNewDto.getEmail(),
                objNewDto.getCpfOrCnpj(),
                ClientType.toEnum(objNewDto.getClientType()));
        Address address = new Address(
                null,
                objNewDto.getStreet(),
                objNewDto.getNumber(),
                objNewDto.getComplement(),
                objNewDto.getNeighborhood(),
                objNewDto.getCep(),
                client,
                new City(objNewDto.getCityId(), null, null));
        client.getAddresses().add(address);

        client.getPhoneNumbers().add(objNewDto.getPhoneNumber1());
        if (objNewDto.getPhoneNumber2() != null)
            client.getPhoneNumbers().add(objNewDto.getPhoneNumber2());
        if (objNewDto.getPhoneNumber3() != null)
            client.getPhoneNumbers().add(objNewDto.getPhoneNumber3());
        return client;
    }

    public Client fromDto(ClientForm objDto) {
        return new Client(null, objDto.getName(), objDto.getEmail(), null, null);
    }

    private void updateData(Client obj, Client updObj) {
        obj.setName(updObj.getName());
        obj.setEmail(updObj.getEmail());
    }
}
