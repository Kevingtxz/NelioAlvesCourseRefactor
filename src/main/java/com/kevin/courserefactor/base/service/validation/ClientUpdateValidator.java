package com.kevin.courserefactor.base.service.validation;

import com.kevin.courserefactor.base.domain.Client;
import com.kevin.courserefactor.base.dto.form.ClientForm;
import com.kevin.courserefactor.base.repository.ClientRepository;
import com.kevin.courserefactor.base.resources.exceptions.FieldMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ClientUpdateValidator
        implements ConstraintValidator<ClientUpdate, ClientForm> {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ClientRepository repo;

    @Override
    public boolean isValid(ClientForm objDto, ConstraintValidatorContext context) {

        Map<String, String> map =
                (Map<String, String>) request.getAttribute(
                        HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Integer uriId = Integer.parseInt(map.get("id"));

        List<FieldMessage> list = new ArrayList<>();

        Client aux = repo.findByEmail(objDto.getEmail());
        if (aux != null && !aux.getId().equals(uriId))
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
