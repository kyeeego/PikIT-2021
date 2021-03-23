package ru.kyeeego.pikit.modules.requisition.port;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kyeeego.pikit.modules.requisition.entity.Requisition;
import ru.kyeeego.pikit.modules.requisition.entity.RequisitionStatus;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface RequisitionRepository extends JpaRepository<Requisition, Long> {
    List<Requisition> findByStatus(RequisitionStatus status);
    Optional<Requisition> findByIdAndStatus(Long id, RequisitionStatus status);
}
