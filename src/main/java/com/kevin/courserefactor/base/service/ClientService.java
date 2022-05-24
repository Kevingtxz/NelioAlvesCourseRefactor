package com.kevin.courserefactor.base.service;

import com.kevin.courserefactor.base.domain.AddressEntity;
import com.kevin.courserefactor.base.domain.CityEntity;
import com.kevin.courserefactor.base.domain.ClientEntity;
import com.kevin.courserefactor.base.domain.enums.ClientType;
import com.kevin.courserefactor.base.domain.enums.ProfileRole;
import com.kevin.courserefactor.base.dto.form.ClientForm;
import com.kevin.courserefactor.base.dto.form.ClientFormNew;
import com.kevin.courserefactor.base.repository.AddressRepository;
import com.kevin.courserefactor.base.repository.ClientRepository;
import com.kevin.courserefactor.base.service.exceptions.AuthorizationException;
import com.kevin.courserefactor.base.service.exceptions.DataIntegrityException;
import com.kevin.courserefactor.base.service.exceptions.ObjectNotFoundException;
import com.kevin.courserefactor.security.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private BCryptPasswordEncoder pe;

    @Autowired
    private ClientRepository repo;

    @Autowired
    private AddressRepository addressRepository;


    public Page<ClientEntity> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    public List<ClientEntity> findAll() {
        return repo.findAll();
    }

    public ClientEntity find(Integer id) {
        UserSS user = UserService.authenticated();
        if (user == null || !user.hasRole(ProfileRole.ADMIN) && !id.equals(user.getId()))
            throw new AuthorizationException("Access denied");

        return repo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Object not found. ID: " + id
                                + ", Type: " + ClientEntity.class.getName()));
    }

    public ClientEntity findByEmail(String email) {
        return repo.findByEmail(email);
    }

    @Transactional
    public ClientEntity insert(ClientEntity obj) {
        obj.setId(null);
        obj = repo.save(obj);
        addressRepository.saveAll(obj.getAddresses());
        return obj;
    }

    public void update(ClientEntity updObj) {
        ClientEntity obj = this.find(updObj.getId());
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

    public ClientEntity fromDto(ClientFormNew objNewDto) {
        ClientEntity client = new ClientEntity(
                null,
                objNewDto.getName(),
                objNewDto.getEmail(),
                objNewDto.getCpfOrCnpj(),
                ClientType.toEnum(objNewDto.getClientType()),
                pe.encode(objNewDto.getPassword()));
        AddressEntity address = new AddressEntity(
                null,
                objNewDto.getStreet(),
                objNewDto.getNumber(),
                objNewDto.getComplement(),
                objNewDto.getNeighborhood(),
                objNewDto.getCep(),
                client,
                new CityEntity(objNewDto.getCityId(), null, null));
        client.getAddresses().add(address);

        client.getPhoneNumbers().add(objNewDto.getPhoneNumber1());
        if (objNewDto.getPhoneNumber2() != null)
            client.getPhoneNumbers().add(objNewDto.getPhoneNumber2());
        if (objNewDto.getPhoneNumber3() != null)
            client.getPhoneNumbers().add(objNewDto.getPhoneNumber3());
        return client;
    }

    public ClientEntity fromDto(ClientForm objDto) {
        return new ClientEntity(null, objDto.getName(), objDto.getEmail(), null, null, null);
    }

    private void updateData(ClientEntity obj, ClientEntity updObj) {
        obj.setName(updObj.getName());
        obj.setEmail(updObj.getEmail());
    }
}
