package ru.kyeeego.pikit.modules.requisition.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.kyeeego.pikit.exception.ForbiddenException;
import ru.kyeeego.pikit.modules.requisition.entity.Requisition;
import ru.kyeeego.pikit.modules.requisition.entity.RequisitionStatus;
import ru.kyeeego.pikit.modules.requisition.entity.dto.RequisitionUpdateDto;
import ru.kyeeego.pikit.modules.requisition.entity.dto.VotingTypes;
import ru.kyeeego.pikit.modules.requisition.exception.RequisitionNotFoundException;
import ru.kyeeego.pikit.modules.requisition.port.IModifyRequisition;
import ru.kyeeego.pikit.modules.requisition.port.RequisitionRepository;
import ru.kyeeego.pikit.modules.user.entity.UserRole;
import ru.kyeeego.pikit.utils.UpdateUtils;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ModifyRequisition implements IModifyRequisition {

    private final RequisitionRepository repository;

    @Autowired
    public ModifyRequisition(RequisitionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Requisition approve(Long id, VotingTypes votingDto) {
        Requisition requisition = repository
                .findByIdAndStatus(id, RequisitionStatus.MODERATING)
                .orElseThrow(RequisitionNotFoundException::new);

        requisition.setVotingTypes(votingDto);

        return votingDto.isStudent()
                ? setRequisitionStatus(requisition, RequisitionStatus.STUD_VOTING)
                : setRequisitionStatus(requisition, RequisitionStatus.EXP_VOTING);
    }

    @Override
    public Requisition close(Long id) {
        return setRequisitionStatusById(id, RequisitionStatus.CLOSED);
    }

    @Override
    // TODO: Make the code clean
    public Requisition updateOne(Long id, RequisitionUpdateDto requisitionUpdateDto, Principal user) {
        Requisition requisition = repository
                .findByIdAndStatus(id, RequisitionStatus.MODERATING)
                .orElseThrow(RequisitionNotFoundException::new);

        Collection<SimpleGrantedAuthority> authorities =
                (Collection<SimpleGrantedAuthority>)
                        SecurityContextHolder.getContext()
                                .getAuthentication()
                                .getAuthorities();

        if (!authorities.containsAll(
                Arrays.stream(UserRole.Access.MOD)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList())
        ) && !requisition.getAuthorEmail().equals(user.getName()))
            throw new ForbiddenException("Not the author or super user");

        System.out.println(authorities);
        System.out.println(requisition.getAuthorEmail());
        System.out.println(user.getName());

            UpdateUtils.copyNonNullProperties(requisitionUpdateDto, requisition);

        return repository.save(requisition);
    }

    private Requisition setRequisitionStatusById(Long id, RequisitionStatus status) {
        Requisition requisition = repository
                .findByIdAndStatus(id, RequisitionStatus.MODERATING)
                .orElseThrow(RequisitionNotFoundException::new);

        requisition.setStatus(status);

        return repository.save(requisition);
    }

    private Requisition setRequisitionStatus(Requisition requisition, RequisitionStatus status) {
        requisition.setStatus(status);

        return repository.save(requisition);
    }
}
