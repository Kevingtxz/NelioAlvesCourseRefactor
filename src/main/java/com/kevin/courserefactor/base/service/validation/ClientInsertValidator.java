package com.kevin.courserefactor.base.service.validation;

import com.kevin.courserefactor.base.domain.Client;
import com.kevin.courserefactor.base.domain.enums.ClientType;
import com.kevin.courserefactor.base.dto.form.ClientFormNew;
import com.kevin.courserefactor.base.repository.ClientRepository;
import com.kevin.courserefactor.base.resources.exceptions.FieldMessage;
import com.kevin.courserefactor.base.service.validation.utils.BR;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;


public class ClientInsertValidator
        implements ConstraintValidator<ClientInsert, ClientFormNew> {

    @Autowired
    private ClientRepository repo;

    @Override
    public boolean isValid(ClientFormNew objDto, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();

        if (objDto.getClientType().equals(ClientType.NATURALPERSON.getCod())
                && !BR.isValidCPF(objDto.getCpfOrCnpj()))
            list.add(new FieldMessage("cpfOrCnpj", "Invalid CPF"));
        if (objDto.getClientType().equals(ClientType.LEGALPERSON.getCod())
                && !BR.isValidCNPJ(objDto.getCpfOrCnpj()))
            list.add(new FieldMessage("cpfOrCnpj", "Invalid CPF"));

        Client aux = repo.findByEmail(objDto.getEmail());
        if (aux != null)
            list.add(new FieldMessage("email", "Email has already been used"));

        for (FieldMessage fm : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(fm.getMessage())
                    .addPropertyNode(fm.getFieldName())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}
