package ru.kyeeego.pikit.modules.requisition.entity;

import com.vladmihalcea.hibernate.type.array.ListArrayType;
import com.vladmihalcea.hibernate.type.array.LongArrayType;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import ru.kyeeego.pikit.modules.requisition.entity.dto.RequisitionCreateDto;
import ru.kyeeego.pikit.modules.requisition.entity.dto.VotingTypes;
import ru.kyeeego.pikit.modules.user.entity.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "requisitions")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TypeDefs({
        @TypeDef(
                name = "list-array",
                typeClass = ListArrayType.class
        )
})
public class Requisition {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "authorEmail", nullable = false)
    private String authorEmail;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "body", nullable = false)
    private String body;

    @Column(name = "cost", nullable = false)
    private String cost;

    @Column(name = "adress")
    private String adress;

    @Type(type = "list-array")
    @Column(name = "docs",
            columnDefinition = "text[]",
            nullable = false)
    private List<String> docs;

    @Type(type = "list-array")
    @Column(name = "voted",
            columnDefinition = "bigint[]",
            nullable = false)
    private List<Long> voted;

    @Column(name = "status", nullable = false)
    private RequisitionStatus status;

    @Column(name = "studVoting", nullable = false)
    private boolean studVoting;

    @Column(name = "expVoting", nullable = false)
    private boolean expVoting;

    public Requisition(RequisitionCreateDto req) {
        this.title = req.getTitle();
        this.body = req.getBody();
        this.cost = req.getCost();
        this.adress = req.getAdress();

        this.docs = Collections.emptyList();
        this.voted = Collections.emptyList();

        VotingTypes votingTypes = new VotingTypes();
        this.studVoting = votingTypes.isStudent();
        this.expVoting = votingTypes.isExpert();

        this.status = RequisitionStatus.MODERATING;
    }

    public void setVotingTypes(VotingTypes votingTypes) {
        this.studVoting = votingTypes.isStudent();
        this.expVoting = votingTypes.isExpert();
    }

    public void addDoc(String doc) {
        this.docs.add(doc);
    }

    public void addVoter(Long userId) {
        this.voted.add(userId);
    }
}
