package ru.kyeeego.pikit.modules.requisition.entity;

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
import java.util.Set;
import java.util.TreeSet;

@Entity
@Table(name = "requisitions")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TypeDefs({
        @TypeDef(
                name = "string-array",
                typeClass = StringArrayType.class
        ),
        @TypeDef(
                name = "id-array",
                typeClass = LongArrayType.class
        )
})
public class Requisition {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "authorId", nullable = false)
    private Long authorId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "body", nullable = false)
    private String body;

    @Column(name = "cost", nullable = false)
    private String cost;

    @Column(name = "adress")
    private String adress;

    @Type(type = "string-array")
    @Column(name = "docs",
            columnDefinition = "text[]",
            nullable = false)
    private Set<String> docs;

    @Type(type = "id-array")
    @Column(name = "voted",
            columnDefinition = "bigint[]",
            nullable = false)
    private Set<Long> voted;

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

        this.docs = new TreeSet<String>();
        this.voted = new TreeSet<Long>();

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
